package response.assertion;


import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.AbstractAssert;
import response.dto.GameDto;
import response.dto.UserDto;

import java.util.Arrays;
import java.util.List;

import static io.restassured.mapper.ObjectMapperType.GSON;

public class ResponseAssert extends AbstractAssert<ResponseAssert, ValidatableResponse> {

    public ResponseAssert(ValidatableResponse validatableResponse) {
        super(validatableResponse, ResponseAssert.class);
    }

    public static ResponseAssert assertThat(ValidatableResponse actual) {
        return new ResponseAssert(actual);
    }

    public ResponseAssert isSuccess(){
        actual.statusCode(200);
        return this;
    }

    public ResponseAssert isCreated(){
        actual.statusCode(201);
        return this;
    }

    public GetUsersAssert hasUsers(){
        isNotNull();

        List<UserDto> usersResponse = Arrays.asList(actual.extract().as(UserDto[].class, GSON));
        return new GetUsersAssert(usersResponse);
    }

    public UpdateUserAssert hasUpdatedUser(){
        isNotNull();
        UserDto response = actual.extract().as(UserDto.class, GSON);
        return new UpdateUserAssert(response);
    }

    public GameAssert hasGames() {
        isNotNull();

        List<GameDto> gamesResponse = Arrays.asList(actual.extract().as(GameDto[].class, GSON));
        return new GameAssert(gamesResponse);
    }
}
