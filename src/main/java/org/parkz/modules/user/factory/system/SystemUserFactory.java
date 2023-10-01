package org.parkz.modules.user.factory.system;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.GroupErrorCode;
import org.parkz.modules.user.enums.GroupKind;
import org.parkz.modules.user.factory.impl.UserFactory;
import org.parkz.modules.user.mapper.UserMapper;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.utils.PhoneUtils;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.google.firebase.auth.UserRecord.CreateRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserFactory extends UserFactory implements ISystemUserFactory {

    private final FirebaseAuth firebaseAuth;
    private final UserMapper userMapper;
    private final GroupRepository groupRepository;

    @Override
    public UserDetails createAdmin(UserDetails userDetails) throws InvalidException {
        String randomPassword = RandomStringUtils.randomAlphanumeric(12);
        CreateRequest request = new CreateRequest()
                .setEmail(userDetails.getEmail())
                .setEmailVerified(false)
                .setPassword(randomPassword)
                .setPhoneNumber(PhoneUtils.convertPhoneNumberWithIdentifier(userDetails.getPhone()))
                .setDisplayName(userDetails.getName())
                .setPhotoUrl(userDetails.getAvatar())
                .setDisabled(false);
        GroupEntity group = groupRepository.findFirstByKindAndDefaultGroup(GroupKind.SUPER_ADMIN, true)
                .orElseThrow(() -> new InvalidException(GroupErrorCode.GROUP_NOT_FOUND));
        UserRecord adminRecord;
        try {
            adminRecord = firebaseAuth.createUser(request);
            firebaseAuth.setCustomUserClaims(
                    adminRecord.getUid(),
                    Map.of("group", group.getName())
            );
            log.info("Create admin {} successfully with password {}", userDetails.getEmail(), randomPassword);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Auth Error:", e);
            throw new InvalidException(ErrorCode.SERVER_ERROR);
        }
        UserEntity newAdmin = userMapper.fromUserRecordToEntity(adminRecord);
        newAdmin.setGroup(group);
        return convertToDetail(repository.save(newAdmin));
    }

    @Override
    protected BaseMapper<UserInfo, UserDetails, UserEntity> getMapper() {
        return userMapper;
    }
}
