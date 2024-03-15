package Util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Stubs {
    public static void configureStubForSuccess(String endpoint, String bodyPath) {
        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(bodyPath)));
    }

    public static void configureStubForInternalServerError(String endpoint, String errorMessage) {
        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody(errorMessage)));
    }

    public static void configureStubForTimeout(String endpoint, String errorMessage) {
        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withFixedDelay(5000) // Simula un timeout de 5 segundos
                        .withStatus(408)
                        .withBody(errorMessage)));
    }
}
