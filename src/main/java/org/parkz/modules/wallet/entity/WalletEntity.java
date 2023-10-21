package org.parkz.modules.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.shared.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.WALLET)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = TableName.WALLET)
public class WalletEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal balance;
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal debt;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_wallet_user_id"), insertable = false, updatable = false, unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserEntity user;
}
