package org.parkz.event.user;

import org.parkz.modules.user.entity.PermissionEntity;

import java.util.List;

public record PermissionInitializedEvent(List<PermissionEntity> permissions) {
}
