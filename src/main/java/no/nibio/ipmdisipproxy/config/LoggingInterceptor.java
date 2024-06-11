package no.nibio.ipmdisipproxy.config;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequestDetails(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        LOGGER.trace("URI: " + request.getURI());
        LOGGER.trace("Method: " + request.getMethod());
        LOGGER.trace("Headers: " + request.getHeaders());
        LOGGER.trace("Request body: " + new String(body));
    }

    private void logResponseDetails(ClientHttpResponse response) throws IOException {
        LOGGER.trace("Status code: " + response.getStatusCode());
        LOGGER.trace("Status text: " + response.getStatusText());
        LOGGER.trace("Headers: " + response.getHeaders());
        //LOGGER.info("Response body: " + StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
    }
}
