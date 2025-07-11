package config;

import controller.BookingController;
import io.muserver.Method;
import io.muserver.MuServerBuilder;
import io.muserver.MuRequest;
import jakarta.ws.rs.core.Request;
import service.BookingService;

public class RouteConfig {
    private  final BookingController bookingController;
    public RouteConfig(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    public  void configureRoutes(MuServerBuilder serverBuilder){
        System.out.println("entered calls");

        try {
            serverBuilder
                    //.withHttp2Config(Http2ConfigBuilder.http2Config())
                    .addHandler(Method.POST, "/booking", (request, response, params) ->
                    {
                        bookingController.createBooking(request, response);
                    });

            System.out.println("executed post call");


            serverBuilder
                    //.withHttp2Config(Http2ConfigBuilder.http2Config())
                    .addHandler(Method.GET, "/bookings/{date}", (request, response, params) ->
                    {
                        String date = params.get("date");
                        System.out.println("executed get call"+date);

                        bookingController.getAllBookingsByDate(response, date);
                    });

            System.out.println("executed get call");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
