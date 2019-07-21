package response.assertion;

import org.assertj.core.api.AbstractObjectAssert;
import response.dto.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class GetUsersAssert extends AbstractObjectAssert<GetUsersAssert, List<UserDto>> {

    public GetUsersAssert(List<UserDto> actual) {
        super(actual, GetUsersAssert.class);
    }

    public GetUsersAssert hasHighRollerForUserId(boolean expectedHighRollerValue, int userId) {

        Boolean highRoller = actual.stream()
                .filter(user -> user.getId() == userId)
                .map(UserDto::isHighRoller)
                .findFirst()
                .orElse(null);

        assertThat(highRoller)
                .as("check of High-Roller field for user id = %d", userId)
                .isEqualTo(expectedHighRollerValue);

        return this;
    }

    public GetUsersAssert hasCurrency(String expectedCurrency) {
        actual.stream()
                .map(UserDto::getCurrency)
                .forEach(c -> assertThat(c).isEqualTo(expectedCurrency));

        return this;
    }
}
