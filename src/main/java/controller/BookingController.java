package controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import model.Booking;
import service.BookingService;

import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookingController {
    private  final Gson gson = new Gson();
    private  final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public  void createBooking(MuRequest request, MuResponse response) throws SQLException {

        try {
            Booking booking = gson.fromJson(new InputStreamReader(request.inputStream().get())
                    , Booking.class);
            bookingService.saveBooking(booking);
            response.contentType("application/json");
            response.write(new Gson().toJson(Map.of("status", "Booking saved successfully")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void getAllBookingsByDate( MuResponse response, String date) {
        try {
            List<Booking> bookings = bookingService.getBookingsByDate(date);
            if (bookings == null || bookings.isEmpty()) {
                writeNoBookingsResponse(response, date);
            } else {
                writeSuccessResponse(response, bookings);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeErrorResponse(response, "Failed to fetch bookings");
        }
    }

    private void writeSuccessResponse(MuResponse response, List<Booking> bookings) {
        response.contentType("application/json");
        response.write(gson.toJson(bookings));
    }

    private void writeNoBookingsResponse(MuResponse response, String date) {
        response.status(404);
        response.contentType("application/json");
        response.write(gson.toJson(Map.of(
                "status", "No bookings found",
                "date", date
        )));
    }
    private void writeErrorResponse(MuResponse response, String errorMessage) {
        response.status(500);
        response.contentType("application/json");
        response.write(gson.toJson(Map.of(
                "error", errorMessage
        )));
    }
}
