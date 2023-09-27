package org.parkz.modules.auth.filter;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.repository.PermissionRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.security.DefaultAuthenticationEntryPoint;
import org.springframework.fastboot.security.DefaultAuthenticationException;
import org.springframework.fastboot.security.DefaultUserDetail;
import org.springframework.fastboot.security.properties.SecurityProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(value = IgnoreAuthorizationProperties.class)
public class JwtAuthorizationFilter extends OncePerRequestFilter implements Filter {

    private final AuthenticationEntryPoint authenticationEntryPoint = new DefaultAuthenticationEntryPoint();
    private final List<AntPathRequestMatcher> antPathRequestMatchers = new ArrayList<>();
    private final PermissionRepository permissionRepository;
    private final IgnoreAuthorizationProperties ignoreAuthorizationProperties;
    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            DefaultUserDetail userDetail = (DefaultUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String groupName = userDetail.getClaims().getOrDefault("group", "").toString();
            List<PermissionEntity> permissions = permissionRepository.findByGroupName(groupName);
            for (var permission : permissions) {
                for (var pattern : permission.getActions()) {
                    AntPathRequestMatcher antPathRequestMatcher = AntPathRequestMatcher.antMatcher(pattern);
                    if (antPathRequestMatcher.matches(request) && permission.getMethods().contains(RequestMethod.resolve(request.getMethod()))) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }
            authenticationEntryPoint.commence(request, response, new DefaultAuthenticationException(ErrorCode.UNAUTHORIZED));
        } catch (Exception e) {
            authenticationEntryPoint.commence(request, response, new DefaultAuthenticationException(ErrorCode.UNAUTHORIZED));
        }

    }

    @PostConstruct
    public void postConstruct() {
        for (var pattern : ignoreAuthorizationProperties.getPattern()) {
            antPathRequestMatchers.add(AntPathRequestMatcher.antMatcher(pattern));
        }
        for (var pattern : securityProperties.getPathMatcher().getPermitAllPathPatterns()) {
            antPathRequestMatchers.add(AntPathRequestMatcher.antMatcher(pattern));
        }
        if (securityProperties.getPathMatcher().getPermitAllMap() == null) {
            return;
        }
        for (var entry : securityProperties.getPathMatcher().getPermitAllMap().entrySet()) {
            for (var pattern : entry.getValue()) {
                antPathRequestMatchers.add(AntPathRequestMatcher.antMatcher(entry.getKey(), pattern));
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return antPathRequestMatchers.stream()
                .anyMatch(ant -> ant.matches(request));
    }
}
