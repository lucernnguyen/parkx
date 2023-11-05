package org.parkz.modules.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.parkz.modules.statistic.model.RevenueChart;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.wallet.enums.TransactionStatus;
import org.parkz.modules.wallet.enums.TransactionType;
import org.parkz.shared.constant.TableName;
import org.springframework.fastboot.jpa.entity.Audit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = TableName.TRANSACTION,
        indexes = {
                @Index(name = "idx_created_date_desc", columnList = "createdDate desc")
        }
)
@NamedNativeQuery(
        name = "revenueByDate",
        query = """
                SELECT CAST(t.created_date AS DATE) AS createdDate, COALESCE(SUM(t.amount), 0.0) AS amount
                FROM parkx_transaction AS t
                WHERE
                    t.transaction_type = :transactionType
                    AND t.created_date >= :from
                    AND t.created_date <= :to
                GROUP BY CAST(t.created_date AS DATE)
                """,
        resultSetMapping = "RevenueChart"
)
@SqlResultSetMapping(
        name = "RevenueChart",
        classes = @ConstructorResult(
                targetClass = RevenueChart.class,
                columns = {
                        @ColumnResult(name = "createdDate", type = LocalDate.class),
                        @ColumnResult(name = "amount", type = BigDecimal.class)
                }
        )
)
public class TransactionEntity extends Audit<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal balance;
    @ColumnDefault("0.0")
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @JdbcTypeCode(SqlTypes.JSON)
    private CreateOrderResponse orderData;
    @Column(length = 40)
    private String refTransactionId;
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = WalletEntity.class, optional = false)
    @JoinColumn(name = "wallet_id", foreignKey = @ForeignKey(name = "fk_transaction_wallet_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private WalletEntity wallet;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_transaction_user_id"), insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserEntity user;
}
