package filters;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

import java.util.Map;

public class FilterWrapper {
    public static RouteHandler wrap(RouteHandler actualHandler) {
        return (MuRequest request, MuResponse response, Map<String, String> pathParams) -> {
            try {
                // CORS Headers
                response.headers().set("Access-Control-Allow-Origin", "*");
                response.headers().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                response.headers().set("Access-Control-Allow-Headers", "Content-Type");

                if ("OPTIONS".equalsIgnoreCase(request.method().toString())) {
                    response.status(200);
                    return;
                }

                actualHandler.handle(request, response, pathParams);

            } catch (Exception e) {
                response.status(500);
                response.headers().set("Content-Type", "application/json");
                response.write("{\"error\": \"Internal Server Error\"}");
                e.printStackTrace();
            }
        };
    }
}
