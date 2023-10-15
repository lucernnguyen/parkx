package org.parkz.modules.user.factory.system;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.factory.impl.PermissionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemPermissionFactory extends PermissionFactory implements ISystemPermissionFactory {
}
