package model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserSpinsByBalance {

    private int id;
    private BigDecimal balance;
    private Map<String, List<Double>> spinsMap;

    public String toString() {

        StringBuilder str = new StringBuilder("\n\nUser with id ")
                .append(id).append(" and balance ").append(balance).append(" can make following spins:");

        spinsMap.entrySet().stream()
                .forEach(entry -> {
                    str.append("\n").append(entry.getKey());
                    str.append(":\n");
                    entry.getValue().stream()
                            .forEach(stake -> {
                                int count = balance.divide(BigDecimal.valueOf(stake)).intValue();
                                str.append("stake ").append(stake).append(" - ").append(count).append(" spins; ");
                            });
                });
        return str.toString();
    }
}