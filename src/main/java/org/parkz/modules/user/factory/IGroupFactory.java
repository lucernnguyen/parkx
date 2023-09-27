package org.parkz.modules.user.factory;

import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IGroupFactory extends IDataFactory<UUID, GroupInfo, GroupDetails> {
}
