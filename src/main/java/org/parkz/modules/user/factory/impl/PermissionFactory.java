package org.parkz.modules.user.factory.impl;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.factory.IPermissionFactory;
import org.parkz.modules.user.mapper.PermissionMapper;
import org.parkz.modules.user.model.PermissionInfo;
import org.parkz.modules.user.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.List;

@Slf4j
@Setter(onMethod_ = @Autowired)
@NoArgsConstructor
public abstract class PermissionFactory
        extends BasePersistDataFactory<String, PermissionInfo, PermissionInfo, String, PermissionEntity, PermissionRepository>
        implements IPermissionFactory, PermissionMapper {

    @Override
    public List<PermissionEntity> fromPermissionIdStringListToEntityList(List<String> permissionIds) {
        return repository.findAllById(permissionIds);
    }
}
