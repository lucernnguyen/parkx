package org.parkz.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.parkz.constant.TableName;
import org.parkz.modules.user.entity.composite.GroupPermissionId;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = TableName.PERMISSION_GROUP)
@IdClass(GroupPermissionId.class)
public class GroupPermissionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "group_id")
    private UUID groupId;

    @Id
    @Column(name = "permission_id")
    private String permissionId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private GroupEntity group;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @MapsId("permissionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private PermissionEntity permission;
}
