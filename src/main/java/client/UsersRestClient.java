package client;


import io.restassured.response.ValidatableResponse;
import response.dto.UserDto;
import utils.CurrencyExchangeRates;
import utils.UserUtils;

import java.math.BigDecimal;
import java.util.List;

import static environment.Configuration.CONFIGURATION;

public class UsersRestClient extends RestClient {

    public UsersRestClient() {
        super(CONFIGURATION.getHost(), "/users");
    }

    public ValidatableResponse getUsers() {
        return givenSigned()
                .when()
                .get()
                .then()
                .log().all();
    }


    public void updateCurrencyForUsersToGBP(List<UserDto> users) {

        users.stream()
                .forEach(user -> {

                    BigDecimal balanceUpd = CurrencyExchangeRates.exchangeToGBP(user.getBalance(), user.getCurrency());
                    UserDto userDtoToUpdate = UserDto.builder()
                            .id(user.getId())
                            .Name(user.getName())
                            .Balance(balanceUpd)
                            .Currency("GBP")
                            .likes(user.getLikes())
                            .highRoller(user.isHighRoller())
                            .build();

                    givenSigned()
                            .pathParam("userId", user.getId())
                            .header("Content-Type", "application/json")
                            .body(userDtoToUpdate)
                            .when()
                            .put("/{userId}")
                            .then()
                            .log().all();
                });
    }

    public ValidatableResponse updateUserBalanceByName(String name, BigDecimal balance) {

        UserUtils provider = new UserUtils(this);
        UserDto userDto = provider.getUserByName(name);


        UserDto userDtoToUpdate = UserDto.builder()
                .id(userDto.getId())
                .Name(name)
                .Balance(balance)
                .Currency(userDto.getCurrency())
                .likes(userDto.getLikes())
                .highRoller(userDto.isHighRoller())
                .build();

        return givenSigned()
                .pathParam("userId", userDto.getId())
                .header("Content-Type", "application/json")
                .body(userDtoToUpdate)
                .when()
                .put("/{userId}")
                .then()
                .log().all();
    }

    public ValidatableResponse createUser(String name, BigDecimal balance, String currency, String likes) {
        UserDto user = UserDto.builder()
                .Name(name)
                .Balance(balance)
                .Currency(currency)
                .likes(likes)
                .build();

        return givenSigned()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post()
                .then()
                .log().all();
    }
}
