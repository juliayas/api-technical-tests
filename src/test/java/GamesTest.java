import client.GamesRestClient;
import client.UsersRestClient;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.assertion.ResponseAssert;
import utils.GameUtils;
import utils.UserUtils;

public class GamesTest {

    private GamesRestClient gamesRestClient;
    private GameUtils gameUtils;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        gamesRestClient = new GamesRestClient();
        gameUtils = new GameUtils(gamesRestClient);
    }

    @Test
    public void test005_getGames_orderByMostPopular_printSuccess() {

        ValidatableResponse response = gamesRestClient.getGames();

        ResponseAssert.assertThat(response)
                .isSuccess()
                .hasGames();

        gameUtils.printGamesOrderedByPopularity(response);
    }

    @Test
    public void test006_getGames_targetCustomersForGame_printSuccess(){

        String name = "Starburst";

        String gameType = gameUtils.getGameTypeByName(name);

        UserUtils provider = new UserUtils(new UsersRestClient());
        provider.printUserNamesByLikesForGame(gameType, name);
    }
}
