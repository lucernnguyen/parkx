package org.parkz.modules.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import org.parkz.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TableName.PERMISSION)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = TableName.PERMISSION)
public class PermissionEntity extends Audit<String> {

    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    @Column(length = 50)
    private String name;
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT", nullable = false)
    private Set<String> actions = new HashSet<>();
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT", nullable = false)
    private Set<RequestMethod> methods = new HashSet<>();
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean showMenu = true;
    @Column(length = 1000)
    private String description;
    @Column(length = 50)
    private String nameGroup;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "permission",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            targetEntity = GroupPermissionEntity.class
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GroupPermissionEntity> groups = new ArrayList<>();

    public boolean authorize(String pathTemplate, RequestMethod method) {
        return actions.contains(pathTemplate) && methods.contains(method);
    }
}
