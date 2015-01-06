package info.batey.wiremock.extention;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import info.batey.wiremock.extension.AwesomeHttpClient;
import info.batey.wiremock.extension.CopiesBateyHeaders;
import info.batey.wiremock.extension.NoBateyAuthHeader;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class AwesomeHttpClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).extensions(new CopiesBateyHeaders()));

    @Test
    public void shouldForwardAuthHeaders() throws Exception {
        //given
        stubFor(get(urlEqualTo("/batey-service"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Original body")
                .withTransformers("CopiesBateyHeaders")));
        AwesomeHttpClient awesomeHttpClient = new AwesomeHttpClient("http://localhost:8089");

        //when
        awesomeHttpClient.callBatey();

        //then
        verify(getRequestedFor(urlEqualTo("/batey-service"))
                .withHeader("Batey-Auth", equalTo("super-token")));
    }

    @Test(expected = NoBateyAuthHeader.class)
    public void throwsExceptionIfNoAuthHeaderReturned() throws Exception {
        //given
        stubFor(get(urlEqualTo("/batey-service"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Original body")));
//                        .withTransformers("CopiesBateyHeaders"))); // no auth header copying this time
        AwesomeHttpClient awesomeHttpClient = new AwesomeHttpClient("http://localhost:8089");

        //when
        awesomeHttpClient.callBatey();

        //then - exception expected
    }
}
