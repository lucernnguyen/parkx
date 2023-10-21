package org.parkz.modules.auth.factory.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.auth.enums.AuthenticationErrorCode;
import org.parkz.modules.auth.factory.IAuthFactory;
import org.parkz.modules.auth.model.request.RegisterUserRequest;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.GroupErrorCode;
import org.parkz.modules.user.enums.GroupKind;
import org.parkz.modules.user.enums.UserErrorCode;
import org.parkz.modules.user.mapper.UserMapper;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.modules.user.repository.UserRepository;
import org.parkz.shared.event.user.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Setter(onMethod_ = @Autowired)
@Service
@RequiredArgsConstructor
public class AuthFactory implements IAuthFactory {

    private GroupRepository groupRepository;
    private FirebaseAuth firebaseAuth;
    private UserMapper userMapper;
    private UserRepository userRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public UserDetails register(RegisterUserRequest request) throws InvalidException {
        try {
            if (userRepository.existsById(JwtUtils.getUserIdString())) {
                throw new InvalidException(AuthenticationErrorCode.SUBJECT_ID_EXISTED);
            }
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(JwtUtils.getUserIdString());
            UserEntity user = userMapper.fromUserRecordToEntity(userRecord);
            userMapper.updateInfoRegisterToUserEntity(user, request);
            GroupEntity group = groupRepository.findFirstByKindAndDefaultGroup(GroupKind.USER, true)
                    .orElseThrow(() -> new InvalidException(GroupErrorCode.GROUP_NOT_FOUND));
            user.setGroupId(group.getId());
            firebaseAuth.setCustomUserClaims(
                    userRecord.getUid(),
                    Map.of("group", group.getName())
            );
            user = userRepository.save(user);
            applicationEventPublisher.publishEvent(new UserCreatedEvent(user.getId()));
            return userMapper.convertToDetail(user);
        } catch (FirebaseAuthException e) {
            log.warn("Get user record from Firebase error ", e);
            throw new InvalidException(UserErrorCode.FIREBASE_NOT_FOUND);
        }
    }
}
