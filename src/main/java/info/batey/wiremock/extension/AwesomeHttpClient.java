package info.batey.wiremock.extension;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class AwesomeHttpClient {

    private final String host;

    public AwesomeHttpClient(String host) {
        this.host = host;
    }

    public void callBatey() throws IOException {
        HttpResponse httpResponse = Request.Get(host + "/batey-service")
                .addHeader("Batey-Auth", "super-token")
                .execute().returnResponse();

        Header authHeader = httpResponse.getFirstHeader("Batey-Auth");

        if (authHeader == null) {
            throw new NoBateyAuthHeader();
        }
    }
}
