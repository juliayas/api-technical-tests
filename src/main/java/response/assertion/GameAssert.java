package response.assertion;

import org.assertj.core.api.AbstractObjectAssert;
import response.dto.GameDto;

import java.util.List;


public class GameAssert extends AbstractObjectAssert<GameAssert, List<GameDto>> {

    public GameAssert(List<GameDto> actual) {
        super(actual, GameAssert.class);
    }
}