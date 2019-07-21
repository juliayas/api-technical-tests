package response.assertion;


import org.assertj.core.api.AbstractObjectAssert;
import response.dto.UserDto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserAssert extends AbstractObjectAssert<UpdateUserAssert, UserDto> {

    public UpdateUserAssert(UserDto actual) {
        super(actual, UpdateUserAssert.class);
    }

    public UpdateUserAssert hasName(String name) {
        assertThat(actual.getName())
                .isEqualTo(name);
        return this;
    }

    public UpdateUserAssert hasBalance(BigDecimal balance) {
        DecimalFormat f = new DecimalFormat("#");
        assertThat(actual.getBalance())
                .isEqualTo(f.format(balance));

        return this;
    }

    public UpdateUserAssert hasCurrency(String currency) {
        assertThat(actual.getCurrency())
                .isEqualTo(currency);
        return this;
    }

    public UpdateUserAssert hasLikes(String likes) {
        assertThat(actual.getLikes())
                .isEqualTo(likes);
        return this;
    }
}
