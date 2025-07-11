import config.RouteConfig;
import controller.BookingController;
import dbconfig.DBInit;
import io.muserver.*;
import org.h2.tools.Server;
import service.BookingService;

public class ReservationApplication {


    public static void main(String[] args) {


        try {
            // Start H2 Web Console on port 8082
            Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
            System.out.println("H2 Web Console started at http://localhost:8082");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the H2 database and create tables
        DBInit.initializeDatabase();


        BookingService bookingService = new BookingService();
        BookingController bookingController = new BookingController(bookingService);

        // Create RouteConfig object
        RouteConfig routeConfig = new RouteConfig(bookingController);

        MuServerBuilder builder= MuServerBuilder
                .muServer().withHttpPort(8081);
        routeConfig.configureRoutes(builder);
        MuServer server = builder.start();

        System.out.println("Started server at " + server.uri());
    }
}
