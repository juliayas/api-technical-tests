package client;


import io.restassured.response.ValidatableResponse;

import static environment.Configuration.CONFIGURATION;

public class GamesRestClient extends RestClient {

    public GamesRestClient() {
        super(CONFIGURATION.getHost(), "/games");
    }

    public ValidatableResponse getGames() {
        return givenSigned()
                .when()
                .get()
                .then()
                .log().all();
    }
}