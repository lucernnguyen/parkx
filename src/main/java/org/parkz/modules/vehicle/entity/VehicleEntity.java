package org.parkz.modules.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.parkz.constant.TableName;
import org.parkz.modules.user.entity.UserEntity;
import org.springframework.fastboot.jpa.entity.Audit;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = TableName.VEHICLE,
        indexes = {
                @Index(name = "idx_license_plate", columnList = "licensePlate", unique = true)
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.VEHICLE)
public class VehicleEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(length = 50)
    private String name;
    @Column(length = 20)
    private String licensePlate;
    @Column(length = 20)
    private String color;
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> images = Collections.emptyList();
    @Column(name = "vehicle_type_id", nullable = false)
    private Integer vehicleTypeId;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = VehicleTypeEntity.class, optional = false)
    @JoinColumn(name = "vehicle_type_id", foreignKey = @ForeignKey(name = "fk_vehicle_vehicle_type_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private VehicleTypeEntity vehicleType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_vehicle_user_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}
