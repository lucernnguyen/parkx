package org.parkz.modules.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.parkz.constant.TableName;
import org.parkz.modules.user.enums.GroupKind;
import org.springframework.fastboot.jpa.entity.Audit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
@Table(
        name = TableName.GROUP,
        indexes = {
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_kind", columnList = "kind")
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = TableName.GROUP)
public class GroupEntity extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 1000)
    private String description;
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupKind kind;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean defaultGroup = false;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "group", targetEntity = UserEntity.class)
    private List<UserEntity> users = Collections.emptyList();

    @Builder.Default
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "group",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            targetEntity = GroupPermissionEntity.class
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private List<GroupPermissionEntity> groupPermissions = new ArrayList<>();

    public void setPermissions(List<PermissionEntity> permissions) {
        if (permissions == null) {
            return;
        }
        this.groupPermissions.clear();
        for (PermissionEntity permission : permissions) {
            GroupPermissionEntity groupPermission = new GroupPermissionEntity()
                    .setGroupId(this.id)
                    .setPermissionId(permission.getId())
                    .setGroup(this)
                    .setPermission(permission);
            this.groupPermissions.add(groupPermission);
        }
    }

    public List<PermissionEntity> getPermissions() {
        return this.groupPermissions.stream()
                .map(GroupPermissionEntity::getPermission)
                .toList();
    }

    public List<String> getPermissionIds() {
        return this.groupPermissions.stream()
                .map(GroupPermissionEntity::getPermissionId)
                .toList();
    }
}
