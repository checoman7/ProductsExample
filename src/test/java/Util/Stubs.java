package Util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Stubs {
    public static void configureStubForSuccessWithJsonBody(String endpoint, String bodyPath) {
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
                        .withFixedDelay(5000)
                        .withStatus(408)
                        .withBody(errorMessage)));
    }
    private void configureStubForValidRequestBody(String endpoint, String validMessage, String validJsonBody) {
        stubFor(post(urlEqualTo(endpoint))
                .withRequestBody(equalToJson(validJsonBody))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(validMessage)));
    }

    private void configureStubForInvalidRequestBody(String endpoint, String inValidMessage, String inValidJsonBody) {
        stubFor(post(urlEqualTo(endpoint))
                .withRequestBody(notMatching(inValidMessage))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(inValidJsonBody)));
    }

    public static void configureStubForSuccessPostWithJsonBody(String endpoint, String bodyPath, String validJsonBody) {
        stubFor(post(urlEqualTo(endpoint))
                .withRequestBody(equalToJson(validJsonBody))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(bodyPath)));
    }
}
