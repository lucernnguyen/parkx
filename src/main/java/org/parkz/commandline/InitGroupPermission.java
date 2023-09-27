package org.parkz.commandline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.event.user.PermissionInitializedEvent;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.enums.GroupKind;
import org.parkz.modules.user.repository.GroupRepository;
import org.parkz.modules.user.repository.PermissionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitGroupPermission {

    private static final Set<String> EXCLUDE_APIS;

    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ApplicationEventPublisher applicationEventPublisher;

    static {
        EXCLUDE_APIS = Set.of(
                "/swagger-ui.html",
                "/error",
                "/v3/api-docs.yaml",
                "/v3/api-docs",
                "/v3/api-docs/swagger-config"
        );
    }

    @EventListener
    @Transactional
    @SuppressWarnings({"unused"})
    public void handleContextRefresh(ContextRefreshedEvent event) {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, PermissionEntity> permissionMap = permissionRepository.findAllMap();
        List<PermissionEntity> permissionWillSave = new CopyOnWriteArrayList<>();
        map.entrySet()
                .parallelStream()
                .filter(entry -> {
                    var key = entry.getKey();
                    return key.getPathPatternsCondition() == null
                            || key.getPathPatternsCondition().getPatternValues()
                            .stream()
                            .noneMatch(EXCLUDE_APIS::contains);
                })
                .forEach(entry -> {
                    var key = entry.getKey();
                    var value = entry.getValue();
                    String permissionId = getKeyRequestMappingInfo(key);
                    PermissionEntity permission = permissionMap.get(permissionId);
                    if (permission == null) {
                        permission = PermissionEntity.builder()
                                .id(permissionId)
                                .name(key.getName())
                                .actions(key.getPatternValues())
                                .methods(key.getMethodsCondition().getMethods())
                                .description(value.getShortLogMessage())
                                .nameGroup(value.getMethod().getDeclaringClass().getSimpleName())
                                .build();
                    } else {
                        permission.setName(key.getName())
                                .setActions(key.getPatternValues())
                                .setMethods(key.getMethodsCondition().getMethods())
                                .setNameGroup(value.getMethod().getDeclaringClass().getSimpleName());
                    }
                    permissionWillSave.add(permission);
                    permissionMap.remove(permissionId);
                });

        applicationEventPublisher.publishEvent(
                new PermissionInitializedEvent(permissionRepository.saveAll(permissionWillSave))
        );
        permissionRepository.deleteAll(permissionMap.values());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, classes = {PermissionInitializedEvent.class})
    public void initSuperAdminRole(PermissionInitializedEvent event) {
        GroupEntity superAdminGroup = groupRepository.findFirstByKind(GroupKind.SUPER_ADMIN)
                .orElseGet(() ->
                        groupRepository.save(GroupEntity.builder()
                                .name("Super Admin")
                                .description("Super Admin")
                                .defaultGroup(true)
                                .kind(GroupKind.SUPER_ADMIN)
                                .build())
                );
        superAdminGroup.setPermissions(event.permissions());
        groupRepository.save(superAdminGroup);
    }

    private String getKeyRequestMappingInfo(RequestMappingInfo info) {
        return info.getMethodsCondition().getMethods().toString() + info.getPathPatternsCondition();
    }
}
