package org.parkz.modules.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.parkz.shared.constant.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = TableName.VEHICLE_TYPE,
        indexes = {
                @Index(name = "idx_vehicle_type_name", columnList = "name", unique = true)
        }
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.VEHICLE_TYPE)
public class VehicleTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String name;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = false;
    @Builder.Default
    @Column(columnDefinition = "INTEGER")
    private Integer totalSlot = 0;
    @Builder.Default
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal price = new BigDecimal("0.0");
}
