package client;


import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {

    private String baseUri;
    private String basePath;

    RestClient(String baseUri, String basePath) {

        this.baseUri = baseUri;
        this.basePath = basePath;
    }

    RequestSpecification givenSigned() {
        return given()
                .log().all()
                .baseUri(baseUri)
                .basePath(basePath);
    }
}