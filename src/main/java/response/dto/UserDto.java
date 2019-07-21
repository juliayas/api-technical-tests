package response.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDto {

    private int id;
    private String Name;
    private BigDecimal Balance;
    private String Currency;
    private String likes;

    @SerializedName("High-Roller")
    private boolean highRoller;

}
