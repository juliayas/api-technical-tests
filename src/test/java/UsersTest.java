import client.GamesRestClient;
import client.UsersRestClient;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.dto.UserDto;
import utils.GameUtils;
import utils.UserUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static environment.Configuration.CONFIGURATION;
import static response.assertion.ResponseAssert.assertThat;

public class UsersTest {

    private UsersRestClient usersRestClient;
    private UserUtils userUtils;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        usersRestClient = new UsersRestClient();
        userUtils = new UserUtils(usersRestClient);
    }

    @Test
    public void test001_getUsers_UserWithHighestBalance_HighRollerIsTrue() {

        boolean highRoller = true;

        ValidatableResponse response = usersRestClient.getUsers();
        int userIdWithMaxBalance = userUtils.getUserIdWithHighestBalance(response);

        assertThat(response)
                .isSuccess()
                .hasUsers()
                .hasHighRollerForUserId(highRoller, userIdWithMaxBalance);
    }

    @Test
    public void test002_getUsers_updateCurrencyToGBP_Success() {

        String currency = CONFIGURATION.getCurrencyGbp();

        List<UserDto> usersToUpdate = userUtils.getAllUsersWithCurrencyNotGBP();
        usersRestClient.updateCurrencyForUsersToGBP(usersToUpdate);

        ValidatableResponse response = usersRestClient.getUsers();
        assertThat(response)
                .isSuccess()
                .hasUsers()
                .hasCurrency(currency);
    }

    @Test
    public void test003_updateUserBalance_Success() {

        String name = "Brian";
        BigDecimal balance = BigDecimal.valueOf(20.000);

        ValidatableResponse response = usersRestClient.updateUserBalanceByName(name, balance);

        assertThat(response)
                .isSuccess()
                .hasUpdatedUser()
                .hasBalance(balance);

    }

    @Test
    public void test004_createNewUser_Success() {

        String name = "James";
        BigDecimal balance = BigDecimal.valueOf(20);
        String currency = CONFIGURATION.getCurrencyGbp();
        String likes = "Bingo";

        ValidatableResponse response = usersRestClient.createUser(name, balance, currency, likes);

        assertThat(response)
                .isCreated()
                .hasUpdatedUser()
                .hasName(name)
                .hasBalance(balance)
                .hasCurrency(currency)
                .hasLikes(likes);
    }

    @Test
    public void test007_getUsers_getSpinsCountForEachGameStake_printSuccess() {

        Map<Integer, BigDecimal> idBalanceMap = userUtils.getBalanceForEachUserId();

        GamesRestClient gamesRestClient = new GamesRestClient();
        GameUtils utils = new GameUtils(gamesRestClient);
        ValidatableResponse getGames = gamesRestClient.getGames();

        idBalanceMap.entrySet().stream()
                .forEach(entry -> {
                    int id = entry.getKey();
                    BigDecimal balance = entry.getValue();
                    utils.printNumberOfSpinsForEachStakeByUser(id, balance, getGames);
                });
    }
}
