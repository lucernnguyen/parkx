package org.parkz.modules.user.repository;

import jakarta.persistence.QueryHint;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.enums.GroupKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID>, JpaSpecificationExecutor<GroupEntity> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    @Query(value = """
            SELECT COUNT(u.id)
            FROM GroupEntity g
                INNER JOIN UserEntity u ON g.id = u.group.id
            WHERE g.id = :gid
            """
    )
    long countUserInGroup(@Param("gid") UUID gid);

    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<GroupEntity> findFirstByKind(GroupKind kind);

}
