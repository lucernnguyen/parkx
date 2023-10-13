package org.parkz.modules.parking_session.entity;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import org.parkz.constant.TableName;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.model.VehicleInfo;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = TableName.PARKING_SESSION)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.PARKING_SESSION)
public class ParkingSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime checkInTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private LocalDateTime checkOutTime;
    @Column(length = 50)
    private String guestName;
    @Column(length = 20)
    private String guestPhone;
    @Column(name = "vehicle_id")
    private UUID vehicleId;
    @Column(name = "parking_slot_id")
    private UUID parkingSlotId;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean confirmed = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(updatable = false)
    private VehicleInfo vehicleSnapShot;
    @JdbcTypeCode(SqlTypes.JSON)
    private ParkingSlotInfo parkingSlotSnapShot;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = VehicleEntity.class, optional = false)
    @JoinColumn(name = "vehicle_id", foreignKey = @ForeignKey(name = "fk_parking_session_vehicle_type_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private VehicleEntity vehicle;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ParkingSlotEntity.class, optional = false)
    @JoinColumn(name = "parking_slot_id", foreignKey = @ForeignKey(name = "fk_parking_session_parking_slot_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ParkingSlotEntity parkingSlot;

}
