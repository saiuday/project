package config;

import controller.BookingController;
import io.muserver.Method;
import io.muserver.MuResponse;
import io.muserver.MuServerBuilder;
import io.muserver.MuRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BookingService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static filters.FilterWrapper.wrap;

public class RouteConfig {
    private  final BookingController bookingController;
    public RouteConfig(BookingController bookingController) {
        this.bookingController = bookingController;
    }
    private static final Logger logger = LoggerFactory.getLogger(RouteConfig.class);


    public void configureRoutes(MuServerBuilder serverBuilder) {
        serverBuilder.addHandler(Method.POST, "/booking",
                wrap(this::handleCreateBooking));
        serverBuilder.addHandler(Method.GET, "/bookings/{date}",
                wrap(this::handleGetBookingsByDate));
    }
    private void handleCreateBooking(MuRequest request, MuResponse response,Map<String, String> params) {
        try {
            bookingController.createBooking(request, response);
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void handleGetBookingsByDate(MuRequest request, MuResponse response, Map<String, String> params) {
        String date = params.get("date");

        if (date == null || date.isBlank()) {
            respondBadRequest(response, "Missing or blank 'date' parameter.");
            return;
        }

        if (!isValidDate(date)) {
            respondBadRequest(response, "Invalid date format. Expected yyyy-MM-dd.");
            return;
        }
        logger.info("executed get call for date: {}", date);
        try {
            bookingController.getAllBookingsByDate(response, date);
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void handleError(MuResponse response, Exception e) {
        logger.error("Error processing request", e);
        response.contentType("application/json");
        String errorResponse = """
            {
              "error": "%s"
            }
            """.formatted(e.getMessage());
        response.write(errorResponse);
        response.status(500);
    }

    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
    private void respondBadRequest(MuResponse response, String message) {
        response.status(400);
        response.headers().set("Content-Type", "application/json");
        response.write("""
        {
          "error": "%s"
        }
        """.formatted(message));
    }




}
