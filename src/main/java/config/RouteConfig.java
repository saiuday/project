package config;

import io.muserver.Http2ConfigBuilder;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;

public class RouteConfig {

    public static void configureRoutes(MuServerBuilder serverBuilder){
        System.out.println("entered calls");


        serverBuilder
                //.withHttp2Config(Http2ConfigBuilder.http2Config())
                .addHandler(Method.POST,"/booking",(request, response, params)->
                {
                    response.write("this is where you send a booking");
                });

        System.out.println("executed post call");


        serverBuilder
                //.withHttp2Config(Http2ConfigBuilder.http2Config())
                .addHandler(Method.GET, "/bookings/{date}",(request, response, params)->
                {
                    response.write("this is where you get all bookings as per date");
                });

        System.out.println("executed get call");


    }
}
