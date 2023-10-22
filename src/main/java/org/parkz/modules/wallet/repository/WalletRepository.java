package org.parkz.modules.wallet.repository;

import org.parkz.modules.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID>, JpaSpecificationExecutor<WalletEntity> {

    Optional<WalletEntity> findByUserId(String userId);
}
