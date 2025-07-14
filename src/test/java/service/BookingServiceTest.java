package service;

import dbconfig.DBInit;
import model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        DBInit.initializeDatabase();
        bookingService = new BookingService();
    }

    @Test
    void shouldSaveBookingSuccessfully() {
        Booking booking = new Booking("Alice", "2025-07-20", "12:00", 4);
        assertDoesNotThrow(() -> bookingService.saveBooking(booking));
    }

    @Test
    void shouldReturnEmptyListForUnknownDate() {
        List<Booking> bookings = bookingService.getBookingsByDate("2099-01-01");
        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void shouldReturnBookingForExistingDate() throws SQLException {
        Booking booking = new Booking("Bob", "2025-07-21", "13:00", 2);
        bookingService.saveBooking(booking);

        List<Booking> bookings = bookingService.getBookingsByDate("2025-07-21");
        assertEquals(1, bookings.size());
        Booking saved = bookings.get(0);

        assertEquals("Bob", saved.getCustomerName());
        assertEquals("2025-07-21", saved.getDate());
        assertEquals("13:00", saved.getTime());
        assertEquals(2, saved.getPeople());
    }

    @Test
    void shouldThrowWhenCustomerNameIsNull() {
        Booking booking = new Booking(null, "2025-07-22", "14:00", 2);
        assertThrows(RuntimeException.class, () -> bookingService.saveBooking(booking));
    }

}
