package org.parkz.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.parkz.shared.constant.TableName;
import org.parkz.modules.user.enums.Gender;
import org.springframework.fastboot.jpa.entity.Audit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = TableName.USER,
        indexes = {
                @Index(name = "idx_username", columnList = "username", unique = true),
                @Index(name = "idx_user_phone", columnList = "phone", unique = true),
                @Index(name = "idx_user_email", columnList = "email", unique = true),
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = TableName.USER)
public class UserEntity extends Audit<String> {

    @Id
    @Column(length = 30, updatable = false, nullable = false)
    private String id;

    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String email;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean emailVerified = false;
    @Column(length = 10)
    private String phone;
    @Column(length = 1000)
    private String avatar;
    private String address;
    private LocalDate dateOfBirth;
    @Column(length = 6)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;
    @Column(length = 20)
    private String idCardNo;
    private LocalDateTime idCardIssuedDate;
    private String idCardIssuedBy;
    @Column(length = 32)
    private String username;
    private String password;
    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = GroupEntity.class, optional = false)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_user_group_id"), insertable = false, updatable = false)
    private GroupEntity group;
}
