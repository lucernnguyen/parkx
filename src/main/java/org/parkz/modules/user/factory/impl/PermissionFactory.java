package org.parkz.modules.user.factory.impl;

import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.factory.IPermissionFactory;
import org.parkz.modules.user.model.PermissionInfo;
import org.parkz.modules.user.repository.PermissionRepository;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PermissionFactory
        extends BasePersistDataFactory<String, PermissionInfo, PermissionInfo, String, PermissionEntity, PermissionRepository>
        implements IPermissionFactory {

}
