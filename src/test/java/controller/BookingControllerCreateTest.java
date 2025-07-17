package controller;

import io.muserver.Headers;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BookingService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookingControllerCreateTest {

    private BookingController bookingController;
    private BookingService bookingService;
    private MuResponse response;
    private MuRequest request;

    private StringBuilder output;

    @BeforeEach
    void setup() {
        bookingService = mock(BookingService.class);
        bookingController = new BookingController(bookingService);

        request = mock(MuRequest.class);
        response = mock(MuResponse.class);

        Headers headers = mock(Headers.class);
        when(response.headers()).thenReturn(headers);

        output = new StringBuilder();

        // Capture output passed to response.write
        doAnswer(invocation -> {
            String content = invocation.getArgument(0, String.class);
            output.append(content);
            return null;
        }).when(response).write(anyString());
    }

    @Test
    void shouldCreateBookingSuccessfully() throws Exception {
        // Prepare a sample booking JSON
        String bookingJson = """
                {
                    "customerName": "John",
                    "date": "2025-07-11",
                    "time": "19:00",
                    "people": 4
                }
                """;

        InputStream inputStream = new ByteArrayInputStream(bookingJson.getBytes());
        when(request.inputStream()).thenReturn(Optional.of(inputStream));

        // No exception thrown when saving booking
        doNothing().when(bookingService).saveBooking(any());

        // Act
        bookingController.createBooking(request, response);

        // Assert
        assertTrue(output.toString().contains("Table reserved  successfully for two hours"));
        verify(response).status(200);
        verify(response.headers()).set(eq("Content-Type"), eq("application/json"));
    }
}
