package org.parkz.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.parkz.constant.TableName;
import org.parkz.modules.user.enums.Gender;
import org.springframework.fastboot.jpa.entity.Audit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = TableName.USER,
        indexes = {
                @Index(name = "idx_username", columnList = "username", unique = true),
                @Index(name = "idx_subject_id", columnList = "subjectId", unique = true),
                @Index(name = "idx_user_phone", columnList = "phone")
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = TableName.USER)
public class UserEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 30)
    private String subjectId;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = GroupEntity.class, optional = false)
    @JoinColumn(name = "group_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_group_id"))
    private GroupEntity group;
}
