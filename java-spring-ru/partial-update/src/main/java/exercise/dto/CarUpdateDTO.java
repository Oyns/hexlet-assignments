package exercise.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.NotNull;

// BEGIN
@Data
public class CarUpdateDTO {
    @NotNull
    private JsonNullable<String> model;

    @NotNull
    private JsonNullable<String> manufacturer;

    @NotNull
    private JsonNullable<Integer> enginePower;
}
// END
