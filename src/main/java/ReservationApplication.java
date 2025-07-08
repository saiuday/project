import config.RouteConfig;
import io.muserver.*;

public class ReservationApplication {


    public static void main(String[] args) {
        MuServerBuilder builder= MuServerBuilder
                .muServer().withHttpPort(8081);
        RouteConfig.configureRoutes(builder);
        MuServer server = builder.start();

        System.out.println("Started server at " + server.uri());
    }
}
