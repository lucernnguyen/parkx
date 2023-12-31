package org.parkz.modules.user.repository;

import org.parkz.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
