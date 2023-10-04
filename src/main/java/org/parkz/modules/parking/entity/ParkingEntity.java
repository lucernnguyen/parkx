package org.parkz.modules.parking.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.parkz.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = TableName.PARKING)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = TableName.PARKING)
public class ParkingEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;
    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean fullSlot = true;
}
