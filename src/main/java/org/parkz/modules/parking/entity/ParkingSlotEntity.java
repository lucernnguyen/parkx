package org.parkz.modules.parking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.parkz.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ParkingEntity.class, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parking_id", nullable = false, foreignKey = @ForeignKey(name = "fk_parking_slot_parking_id"))
    private ParkingEntity parking;
}
