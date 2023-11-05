package org.parkz.modules.parking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.parkz.modules.statistic.model.VehicleChart;
import org.parkz.shared.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = TableName.PARKING_SLOT,
        indexes = {
                @Index(name = "idx_row_column", columnList = "rowIndex,columnIndex", unique = true)
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.PARKING_SLOT)
@NamedNativeQuery(
        name = "vehicleStatistic",
        query = """
                SELECT vt.name AS vehicleName, COALESCE(COUNT(v.id), 0) AS amount
                FROM parkx_vehicle_type AS vt
                    LEFT JOIN parkx_vehicle v ON vt.id = v.vehicle_type_id
                GROUP BY vt.id
                """,
        resultSetMapping = "VehicleChart"
)
@SqlResultSetMapping(
        name = "VehicleChart",
        classes = @ConstructorResult(
                targetClass = VehicleChart.class,
                columns = {
                        @ColumnResult(name = "vehicleName", type = String.class),
                        @ColumnResult(name = "amount", type = BigDecimal.class)
                }
        )
)
public class ParkingSlotEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(length = 50)
    private String name;
    private Integer rowIndex;
    private Integer columnIndex;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean filled = false;
    @Column(name = "parking_id", nullable = false)
    private UUID parkingId;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean hasParking = false;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ParkingEntity.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parking_id", foreignKey = @ForeignKey(name = "fk_parking_slot_parking_id"), insertable = false, updatable = false)
    private ParkingEntity parking;
}
