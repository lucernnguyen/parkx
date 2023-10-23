package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.user.enums.Gender;
import org.springframework.fastboot.rest.serializer.json.View;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonView(View.ExtendedDetails.class)
public class UserDetails extends UserInfo {

    @Schema(example = "Thu Duc City, Ho Chi Minh City")
    private String address;
    @Schema(example = "2001-01-01")
    private LocalDate dateOfBirth;
    @Schema(example = "MALE")
    private Gender gender;
    @Schema(example = "736284649274")
    private String idCardNo;
    @Schema(example = "2017-01-01")
    private LocalDateTime idCardIssuedDate;
    @Schema(example = "Police")
    private String idCardIssuedBy;

}
