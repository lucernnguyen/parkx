package org.parkz.modules.user.factory.system;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.parkz.infrastructure.email.EmailModel;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.GroupErrorCode;
import org.parkz.modules.user.enums.GroupKind;
import org.parkz.modules.user.enums.UserErrorCode;
import org.parkz.modules.user.factory.impl.UserFactory;
import org.parkz.modules.user.mapper.UserMapper;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.shared.utils.PhoneUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.filter.IFilter;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected UserDetails preCreate(UserDetails userDetails) throws InvalidException {
        if (repository.existsByEmail(userDetails.getEmail())) {
            throw new InvalidException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
        if (repository.existsByPhone(userDetails.getPhone())) {
            throw new InvalidException(UserErrorCode.USER_PHONE_ALREADY_EXISTS);
        }
        String randomPassword = RandomStringUtils.randomAlphanumeric(12);
        CreateRequest request = new CreateRequest()
                .setEmail(userDetails.getEmail())
                .setEmailVerified(false)
                .setPassword(randomPassword)
                .setPhoneNumber(PhoneUtils.convertPhoneNumberWithIdentifier(userDetails.getPhone()))
                .setDisplayName(userDetails.getName())
                .setDisabled(false);
        if (StringUtils.isNotBlank(userDetails.getAvatar())) {
            request.setPhotoUrl(userDetails.getAvatar());
        }
        GroupEntity group = groupRepository.findById(userDetails.getGroupId())
                .orElseThrow(() -> new InvalidException(GroupErrorCode.GROUP_NOT_FOUND));
        UserRecord userRecord;
        try {
            userRecord = firebaseAuth.createUser(request);
            firebaseAuth.setCustomUserClaims(
                    userRecord.getUid(),
                    Map.of("group", group.getName())
            );
            log.info("Create admin {} successfully with password {}", userDetails.getEmail(), randomPassword);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Auth Error:", e);
            throw new InvalidException(ErrorCode.SERVER_ERROR);
        }
        userDetails.setId(userRecord.getUid());
        userDetails.setPassword(randomPassword);
        return userDetails;
    }

    @Override
    protected void postCreate(UserEntity entity, UserDetails detail) throws InvalidException {
        applicationEventPublisher.publishEvent(EmailModel.builder()
                .to(new String[]{entity.getEmail()})
                .subject("[Parkx] Mat khau dang nhap")
                .content("Mat khau dang nhap ParkX cua ban la: " + detail.getPassword())
                .isHtml(false)
                .hasAttachment(false)
                .build()
        );
    }

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
        newAdmin.setGroupId(group.getId());
        return convertToDetail(repository.save(newAdmin));
    }

    @Override
    protected <F extends IFilter> void postDelete(String id, F filter) throws InvalidException {
        try {
            firebaseAuth.deleteUser(id);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Auth Error:", e);
            throw new InvalidException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    protected BaseMapper<UserInfo, UserDetails, UserEntity> getMapper() {
        return userMapper;
    }
}
