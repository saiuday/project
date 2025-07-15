package service;

import dbconfig.DBConfig;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    public void saveBooking(Booking booking) throws SQLException {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO bookings (customer_name, date, time, people) " +
                             "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, booking.getCustomerName());
            statement.setString(2, booking.getDate());
            statement.setString(3, booking.getTime());
            statement.setInt(4, booking.getPeople());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> getBookingsByDate(String date) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT customer_name, date, time, " +
                             "people FROM bookings WHERE date = ?")) {

            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setDate(rs.getString("date"));
                booking.setTime(rs.getString("time"));
                booking.setPeople(rs.getInt("people"));
                bookings.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

}
