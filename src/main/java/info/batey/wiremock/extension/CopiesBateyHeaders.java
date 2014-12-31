package info.batey.wiremock.extension;

import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.util.Collection;
import java.util.Set;

public class CopiesBateyHeaders extends ResponseTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition) {
        Collection<HttpHeader> originalResponseHeaders = responseDefinition.getHeaders().all();
        Set<String> allHeaderKeys = request.getAllHeaderKeys();
        for (String headerName : allHeaderKeys) {
            if (headerName.startsWith("Batey")) {
                originalResponseHeaders.add(new HttpHeader(headerName, request.getHeader(headerName)));
            }
        }
        responseDefinition.setHeaders(new HttpHeaders(originalResponseHeaders));
        return responseDefinition;
    }

    @Override
    public String name() {
        return "CopiesBateyHeaders";
    }

    @Override
    public boolean applyGlobally() {
        return true;
    }
}
