package metrics;

import filters.MetricsEndpoint;
import io.muserver.Headers;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MetricsEndpointTest {

    @Test
    void shouldReturnMetricsWithRequestCountAndUptime() throws Exception {
        AtomicInteger counter = new AtomicInteger(3);
        Instant startTime = Instant.now().minusSeconds(30);
        MetricsEndpoint endpoint = new MetricsEndpoint(counter, startTime);

        MuRequest req = mock(MuRequest.class);
        MuResponse res = mock(MuResponse.class);
        Headers headers = mock(Headers.class);

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        when(res.headers()).thenReturn(headers);
        when(res.writer()).thenReturn(printWriter);

        endpoint.handler().handle(req, res, null);

        printWriter.flush(); // ensure all data is written

        String result = writer.toString();
        System.out.println("Response = \n" + result);

        assertTrue(result.contains("requests"));
        assertTrue(result.contains("uptime"));
    }
}
