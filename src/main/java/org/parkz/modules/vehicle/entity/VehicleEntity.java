package org.parkz.modules.vehicle.entity;

import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import org.parkz.shared.constant.TableName;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
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
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean checkin = false;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("""
            (SELECT ps.id
            FROM parkx_parking_session ps
            WHERE ps.vehicle_id = id
                AND ps.check_in_time IS NOT NULL
                AND ps.check_out_time IS NULL)
            """)
    private ParkingSessionEntity parkingSessionActive;
}
