package org.parkz.modules.user.entity.composite;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GroupPermissionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private UUID groupId;
    private String permissionId;
}
