package response.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameDto {

    private String id;
    private String Name;
    private String Type;
    private String Currency;
    private List<Double> Stake;
    private Integer StakesThisWeek;
}
