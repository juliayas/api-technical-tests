package utils;

import client.GamesRestClient;
import io.restassured.response.ValidatableResponse;
import model.UserSpinsByBalance;
import response.dto.GameDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.mapper.ObjectMapperType.GSON;

public class GameUtils {

    private GamesRestClient gamesRestClient;

    public GameUtils(GamesRestClient gamesRestClient) {
        this.gamesRestClient = gamesRestClient;
    }

    public void printGamesOrderedByPopularity(ValidatableResponse response) {
        List<GameDto> games = Arrays.asList(response.extract().as(GameDto[].class, GSON));

        List<String> orderedList = games.stream()
                .sorted(Comparator.comparing(GameDto::getStakesThisWeek).reversed())
                .map(GameDto::getName)
                .collect(Collectors.toList());

        System.out.println("List of Games by Popularity:\n" + orderedList);
    }

    public String getGameTypeByName(String name) {

        ValidatableResponse response = gamesRestClient.getGames();

        List<GameDto> games = Arrays.asList(response.extract().as(GameDto[].class, GSON));

        return games.stream()
                .filter(g -> g.getName().equals(name))
                .map(GameDto::getType)
                .findFirst()
                .orElse(null);
    }

    public void printNumberOfSpinsForEachStakeByUser(int userId, BigDecimal balance, ValidatableResponse getGames) {

        List<GameDto> games = Arrays.asList(getGames.extract().as(GameDto[].class, GSON));
        Map<String, List<Double>> map = new HashMap<>();

        games.stream()
                .forEach(game -> map.put(game.getName(), game.getStake()));

        UserSpinsByBalance spinsCalc = UserSpinsByBalance.builder()
                .id(userId)
                .balance(balance)
                .spinsMap(map)
                .build();

        System.out.println(spinsCalc);
    }
}
