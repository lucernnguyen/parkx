package org.parkz.modules.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.user.enums.Gender;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class UserDetails extends UserInfo {

    @Schema(example = "Thu Duc City, Ho Chi Minh City")
    private String address;
    @Schema(example = "2001-01-01")
    private LocalDateTime dateOfBirth;
    @Schema(example = "MALE")
    private Gender gender;
    @Schema(example = "736284649274")
    private String idCardNo;
    @Schema(example = "2017-01-01")
    private LocalDateTime idCardIssuedDate;
    @Schema(example = "Police")
    private String idCardIssuedBy;
}
