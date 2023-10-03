package org.parkz.modules.user.factory;

import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;

import java.util.UUID;

public interface IGroupFactory extends IDataFactory<UUID, GroupInfo, GroupDetails> {

    SuccessResponse changeDefault(UUID uuid) throws InvalidException;
}
