package exercise.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

// BEGIN
@Data
public class GuestCreateDTO {

    @NotNull
    private String name;

    @Email
    private String email;

    @Size(min = 11, max = 13)
    @Pattern(regexp = "^[0-9\\-\\+]{11,13}$")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]*$")
    @Digits(integer = 4, fraction = 0)
    private String clubCard;

    @FutureOrPresent
    private LocalDate cardValidUntil;
}
// END
