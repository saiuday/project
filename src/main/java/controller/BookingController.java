package controller;

import com.google.gson.Gson;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BookingService;
import utility.TimeSlot;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private  final Gson gson = new Gson();
    private  final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void createBooking(MuRequest request, MuResponse response) {
        try (InputStreamReader reader = new InputStreamReader(request.inputStream().get())) {
            Booking booking = gson.fromJson(reader, Booking.class);

            if(!(TimeSlot.isValid(booking.getTime()))){
                respondBadRequest(response,"Invalid time Allowed Values are: 10:00, 12:00,...20:00");
            }

            //Validate Booking object fields here
            bookingService.saveBooking(booking);

            respondSuccess(response, Map.of("status", "Table reserved  successfully for two hours"));
        } catch (SQLException e) {
            logger.error("DB error while saving booking", e);
            respondServerError(response, "Database error while saving booking");
        } catch (Exception e) {
            logger.error("Error parsing booking or saving", e);
            respondBadRequest(response, "Invalid booking payload or internal error");
        }
    }

    public  void getAllBookingsByDate( MuResponse response, String date) {
        try {
            List<Booking> bookings = bookingService.getBookingsByDate(date);
            if (bookings == null || bookings.isEmpty()) {
                respondNotFound(response, Map.of(
                        "status", "No bookings found",
                        "date", date
                ));
            } else {
                respondSuccess(response, Map.of(
                        "status", "success",
                        "bookings", bookings
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse(response, "Failed to fetch bookings");
        }
    }

    private void respondSuccess(MuResponse response, Object body) {
        response.status(200);
        response.headers().set("Content-Type", "application/json");
        response.write(gson.toJson(body));
    }

    private void respondBadRequest(MuResponse response, String message) {
        response.status(400);
        response.headers().set("Content-Type", "application/json");
        response.write(gson.toJson(Map.of("error", message)));
    }

    private void respondNotFound(MuResponse response, Object body) {
        response.status(404);
        response.headers().set("Content-Type", "application/json");
        response.write(gson.toJson(body));
    }

    private void respondServerError(MuResponse response, String message) {
        response.status(500);
        response.headers().set("Content-Type", "application/json");
        response.write(gson.toJson(Map.of("error", message)));
    }

    private void ErrorResponse(MuResponse response, String errorMessage) {
        response.status(500);
        response.contentType("application/json");
        response.write(gson.toJson(Map.of(
                "error", errorMessage
        )));
    }

}
