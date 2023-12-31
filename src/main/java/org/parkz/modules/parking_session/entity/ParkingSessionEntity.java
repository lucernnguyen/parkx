package org.parkz.modules.parking_session.entity;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.enums.ParkingSessionStatus;
import org.parkz.modules.parking_session.enums.PaymentType;
import org.parkz.modules.statistic.model.ParkingSessionByTodayAndCurrentMonth;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.parkz.shared.constant.TableName;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Entity
@Builder
@Table(
        name = TableName.PARKING_SESSION,
        indexes = {
                @Index(name = "idx_created_date", columnList = "createdDate desc")
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.PARKING_SESSION)
@NamedNativeQuery(
        name = "parkingSessionByTime",
        query = """
                SELECT
                    COALESCE(SUM(CASE WHEN ps.check_in_time IS NOT NULL THEN 1 ELSE 0 END), 0) AS checkedIn,
                    COALESCE(SUM(CASE WHEN ps.check_in_time IS NOT NULL AND ps.check_out_time IS NOT NULL THEN 1 ELSE 0 END), 0) AS checkedOut
                FROM parkx_parking_session ps
                WHERE
                    ps.created_date >= :from
                    AND ps.created_date <= :to
                """,
        resultSetMapping = "ParkingSessionByTodayAndCurrentMonth.SessionStatus"
)
@SqlResultSetMapping(
        name = "ParkingSessionByTodayAndCurrentMonth.SessionStatus",
        classes = @ConstructorResult(
                targetClass = ParkingSessionByTodayAndCurrentMonth.SessionStatus.class,
                columns = {
                        @ColumnResult(name = "checkedIn", type = int.class),
                        @ColumnResult(name = "checkedOut", type = int.class)
                }
        )
)
public class ParkingSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @CreatedDate
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;
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
    @Enumerated(EnumType.STRING)
    private ParkingSessionStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "vehicle_id")
    private UUID vehicleId;
    @Column(name = "parking_slot_id")
    private UUID parkingSlotId;
    @Column(name = "transaction_id")
    private UUID transactionId;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean confirmed = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(updatable = false)
    private VehicleInfo vehicleSnapShot;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(updatable = false)
    private ParkingSlotInfo parkingSlotSnapShot;
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    @ColumnDefault("'[]'::jsonb")
    private List<String> checkInCapture = new ArrayList<>();
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    @ColumnDefault("'[]'::jsonb")
    private List<String> checkOutCapture = new ArrayList<>();


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

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "fk_parking_session_transaction_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private TransactionEntity transaction;

}
