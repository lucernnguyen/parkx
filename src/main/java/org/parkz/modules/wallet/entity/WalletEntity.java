package org.parkz.modules.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.shared.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
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
    @Builder.Default
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
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

    public void addBalance(BigDecimal amount) {
        amount = Objects.requireNonNullElse(amount, BigDecimal.ZERO);
        this.balance = this.balance.add(amount);
        log.info("[WALLET] Wallet ID {} has add amount {}", getId(), amount);
    }

    public void subtractBalance(BigDecimal amount) {
        amount = Objects.requireNonNullElse(amount, BigDecimal.ZERO);
        this.balance = this.balance.subtract(amount);
        log.info("[WALLET] Wallet ID {} has subtract amount {}", getId(), amount);
    }

    public boolean checkBalance(BigDecimal paymentAmount) {
        return balance.subtract(paymentAmount).compareTo(BigDecimal.ZERO) >= 0;
    }
}
