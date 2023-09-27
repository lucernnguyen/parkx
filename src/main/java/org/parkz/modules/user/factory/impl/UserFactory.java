package org.parkz.modules.user.factory.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.GroupErrorCode;
import org.parkz.modules.user.enums.GroupKind;
import org.parkz.modules.user.enums.UserErrorCode;
import org.parkz.modules.user.factory.IUserFactory;
import org.parkz.modules.user.mapper.UserMapper;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.model.request.RegisterUserRequest;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.fastboot.security.utils.JwtUtils;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Setter(onMethod_ = @Autowired)
@NoArgsConstructor
public abstract class UserFactory
        extends BasePersistDataFactory<UUID, UserInfo, UserDetails, UUID, UserEntity, UserRepository>
        implements IUserFactory, UserMapper {

    private GroupRepository groupRepository;
    private FirebaseAuth firebaseAuth;

    @Override
    public UserDetails userDetails() throws InvalidException {
        UserEntity user = repository.findBySubjectId(JwtUtils.getUserIdString())
                .orElseThrow(() -> new InvalidException(notFound()));
        return convertToDetail(user);
    }

    @Override
    public UserDetails register(RegisterUserRequest request) throws InvalidException {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(JwtUtils.getUserIdString());
            UserEntity user = fromUserRecordToEntity(userRecord);
            updateInfoRegisterToUserEntity(user, request);
            GroupEntity group = groupRepository.findFirstByKind(GroupKind.USER)
                    .orElseThrow(() -> new InvalidException(GroupErrorCode.GROUP_NOT_FOUND));
            user.setGroup(group);
            firebaseAuth.setCustomUserClaims(userRecord.getUid(), Map.of("group", group.getName()));
            return convertToDetail(repository.save(user));
        } catch (FirebaseAuthException e) {
            log.warn("Get user record from Firebase error ", e);
            throw new InvalidException(UserErrorCode.FIREBASE_NOT_FOUND);
        }
    }

    @Override
    protected IErrorCode notFound() {
        return UserErrorCode.USER_NOT_FOUND;
    }
}
