package utils;


import client.UsersRestClient;
import io.restassured.response.ValidatableResponse;
import response.dto.UserDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.mapper.ObjectMapperType.GSON;

public class UserUtils {

    private UsersRestClient usersRestClient;

    public UserUtils(UsersRestClient restClient) {
        this.usersRestClient = restClient;
    }

    public int getUserIdWithHighestBalance(ValidatableResponse response) {
        List<UserDto> users = Arrays.asList(response.extract().as(UserDto[].class, GSON));
        return users.stream()
                .max(Comparator.comparing(UserDto::getBalance))
                .get().getId();
    }

    public UserDto getUserByName(String name) {

        ValidatableResponse getUsers = usersRestClient.getUsers();
        List<UserDto> users = Arrays.asList(getUsers.extract().as(UserDto[].class, GSON));
        return users.stream()
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void printUserNamesByLikesForGame(String likes, String game) {
        ValidatableResponse response = usersRestClient.getUsers();
        List<UserDto> users = Arrays.asList(response.extract().as(UserDto[].class, GSON));
        List<String> targetCustomers = users.stream()
                .filter(u -> u.getLikes().contains(likes))
                .map(UserDto::getName)
                .collect(Collectors.toList());

        System.out.println("Target Customers for " + game + ":\n" + targetCustomers);
    }

    public Map<Integer, BigDecimal> getBalanceForEachUserId() {

        Map<Integer, BigDecimal> map = new HashMap<>();
        ValidatableResponse response = usersRestClient.getUsers();
        List<UserDto> users = Arrays.asList(response.extract().as(UserDto[].class, GSON));
        users.stream()
                .forEach(u -> map.put(u.getId(), u.getBalance()));

        return map;
    }

    public List<UserDto> getAllUsersWithCurrencyNotGBP() {
        ValidatableResponse response = usersRestClient.getUsers();
        List<UserDto> users = Arrays.asList(response.extract().as(UserDto[].class, GSON));
        return users.stream()
                .filter(user -> !user.getCurrency().equals("GBP"))
                .collect(Collectors.toList());
    }
}