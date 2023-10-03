package org.parkz.modules.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.parkz.modules.user.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RegisterUserRequest {

    @Size(min = 1, max = 50)
    @Schema(example = "Nguyen Van A")
    private String name;
    @NotBlank
    @Schema(example = "Thu Duc City, Ho Chi Minh City")
    private String address;
    private LocalDate dateOfBirth;
    @NotNull
    private Gender gender;
}
