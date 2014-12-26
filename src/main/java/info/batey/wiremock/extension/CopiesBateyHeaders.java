package info.batey.wiremock.extension;

import com.github.tomakehurst.wiremock.http.*;

import java.util.Collection;
import java.util.Set;

public class CopiesBateyHeaders implements WiremockExtension {
    @Override
    public ResponseDefinition filter(Request request, ResponseDefinition responseDefinition) {
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
    public String getName() {
        return "Batey Header Copier";
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
