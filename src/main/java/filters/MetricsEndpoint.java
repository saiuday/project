package filters;

import io.muserver.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsEndpoint {
    private final AtomicInteger requestCount;
    private final Instant startTime;

    public MetricsEndpoint(AtomicInteger requestCount, Instant startTime) {
        this.requestCount = requestCount;
        this.startTime = startTime;
    }

    public RouteHandler handler() {
        return (req, res, pathParams) -> {
            long uptime = Duration.between(startTime, Instant.now()).getSeconds();
            res.headers().set("Content-Type", "text/plain");
            res.writer().write("mu_requests_total " + requestCount.get() + "\n");
            res.writer().write("mu_uptime_seconds " + uptime + "\n");
        };
    }
}
