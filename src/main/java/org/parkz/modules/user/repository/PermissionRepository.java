package org.parkz.modules.user.repository;

import jakarta.persistence.QueryHint;
import org.parkz.modules.user.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, String>, JpaSpecificationExecutor<PermissionEntity> {

    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query(value = """
            SELECT p
            FROM PermissionEntity p
                INNER JOIN GroupPermissionEntity gp ON p.id = gp.permissionId
                INNER JOIN GroupEntity g ON gp.groupId = g.id
            WHERE g.name = :groupName
            """)
    List<PermissionEntity> findByGroupName(@Param("groupName") String groupName);

    @Override
    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<PermissionEntity> findAll();

    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    default Map<String, PermissionEntity> findAllMap() {
        return findAll().stream()
                .collect(Collectors.toMap(
                        PermissionEntity::getId,
                        v -> v
                ));
    }
}
