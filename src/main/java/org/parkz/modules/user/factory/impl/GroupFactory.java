package org.parkz.modules.user.factory.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.enums.GroupErrorCode;
import org.parkz.modules.user.factory.IGroupFactory;
import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.modules.user.repository.PermissionRepository;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.fastboot.rest.common.filter.IFilter;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupFactory
        extends BasePersistDataFactory<UUID, GroupInfo, GroupDetails, UUID, GroupEntity, GroupRepository>
        implements IGroupFactory {

    private final PermissionRepository permissionRepository;

    @Override
    protected GroupDetails preCreate(GroupDetails detail) throws InvalidException {
        if (repository.existsByName(detail.getName())) {
            throw new InvalidException(GroupErrorCode.GROUP_NAME_EXISTED);
        }
        return detail;
    }

    @Override
    protected void postCreate(GroupEntity entity, GroupDetails detail) {
        List<PermissionEntity> permissions = permissionRepository.findAllById(detail.getPermissions());
        entity.setPermissions(permissions);
        repository.save(entity);
    }

    @Override
    protected GroupDetails preUpdate(UUID id, GroupDetails detail) throws InvalidException {
        if (repository.existsByNameAndIdNot(detail.getName(), detail.getId())) {
            throw new InvalidException(conflict());
        }
        return detail;
    }

    @Override
    protected void postUpdate(GroupEntity entity, GroupDetails detail) throws InvalidException {
        List<PermissionEntity> permissions = permissionRepository.findAllById(detail.getPermissions());
        entity.setPermissions(permissions);
        repository.save(entity);
    }

    @Override
    protected <F extends IFilter> F preDelete(UUID id, F filter) throws InvalidException {
        if (repository.existsByIdAndDefaultGroup(id, true)) {
            throw new InvalidException(GroupErrorCode.GROUP_IS_DEFAULT);
        }
        if (repository.countUserInGroup(id) > 0) {
            throw new InvalidException(GroupErrorCode.GROUP_HAVE_USERS_EXISTED_IN);
        }
        return filter;
    }

    @Override
    public SuccessResponse changeDefault(UUID uuid) throws InvalidException {
        GroupEntity requestGroup = repository.findById(uuid)
                .orElseThrow(() -> new InvalidException(GroupErrorCode.GROUP_NOT_FOUND));
        repository.findFirstByKindAndDefaultGroup(requestGroup.getKind(), true)
                .ifPresent(defaultGroup -> {
                    defaultGroup.setDefaultGroup(false);
                    repository.save(defaultGroup);
                });
        requestGroup.setDefaultGroup(true);
        repository.save(requestGroup);
        return SuccessResponse.status(true);
    }
}
