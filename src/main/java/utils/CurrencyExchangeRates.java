package utils;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static environment.Configuration.CONFIGURATION;

public class CurrencyExchangeRates {

    private static Map<String, Double> currencyMap = new HashMap<>();

    static {
        currencyMap.put(CONFIGURATION.getCurrencyUsd(), CONFIGURATION.getExchangeRateUsdToGbp());
        currencyMap.put(CONFIGURATION.getCurrencyEur(), CONFIGURATION.getExchangeRateEurToGbp());
        currencyMap.put(CONFIGURATION.getCurrencyPln(), CONFIGURATION.getExchangeRatePlnToGbp());
        currencyMap.put(CONFIGURATION.getCurrencyYen(), CONFIGURATION.getExchangeRateYenToGbp());
    }

    public static BigDecimal exchangeToGBP(BigDecimal balance, String currency) {

        Map.Entry<String, Double> entry = currencyMap.entrySet().stream()
                .filter(c -> c.getKey().equals(currency))
                .findFirst()
                .orElse(null);


        return balance.multiply(BigDecimal.valueOf(entry.getValue()));
    }
}
