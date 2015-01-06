package info.batey.wiremock.extension;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
/*
This is in the main code base so can run wiremock stand alone and show extensions working.

If you're only using wiremock via the java api then transformers should be in your test src.
 */
public class CopiesBateyHeaders extends ResponseTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files) {
        HttpHeaders headers = responseDefinition.getHeaders();
        Collection<HttpHeader> originalResponseHeaders;
        if (headers == null) {
            originalResponseHeaders = new ArrayList<HttpHeader>();
        } else {
            originalResponseHeaders = headers.all();
        }
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
        return false;
    }
}
