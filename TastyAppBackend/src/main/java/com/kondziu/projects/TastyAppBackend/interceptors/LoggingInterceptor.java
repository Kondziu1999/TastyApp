package com.kondziu.projects.TastyAppBackend.interceptors;

import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUser;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.ActivityUserRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.UserLogRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.session_id_extractor.SessionIdExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private final String startTimeAttr = "startTime";
    private final String sessionIdAttribute = "session";
    private final String userIdAttribute = "userId";

    private final String nonAssetRequestAttribute = "nonAssetRequest";

    private final UserLogRepository repository;
    private final SessionIdExtractor sessionIdExtractor;
    private final ActivityUserRepository activityUserRepository;
    private final  NonAssetRequestVerifier nonAssetRequestVerifier;
    public LoggingInterceptor(UserLogRepository repository, SessionIdExtractor sessionIdExtractor, ActivityUserRepository activityUserRepository, NonAssetRequestVerifier nonAssetRequestVerifier, NonAssetRequestVerifier nonAssetRequestVerifier1) {
        this.repository = repository;
        this.sessionIdExtractor = sessionIdExtractor;
        this.activityUserRepository = activityUserRepository;
        this.nonAssetRequestVerifier = nonAssetRequestVerifier1;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (nonAssetRequestVerifier.verifyNotAsset(request)) {
            Principal principal = request.getUserPrincipal();

            if(principal != null) {
                HasStringUserIdPrincipal user = (HasStringUserIdPrincipal)(((Authentication)request.getUserPrincipal()).getPrincipal());

                request.setAttribute(userIdAttribute, user.getStringUserId());
                request.setAttribute(sessionIdAttribute, sessionIdExtractor.retrieveSessionId(request).get());
            }
            long startTime = System.currentTimeMillis();
            request.setAttribute(startTimeAttr, startTime);
            request.setAttribute(nonAssetRequestAttribute, true);
        } else {
            request.setAttribute(nonAssetRequestAttribute, false);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (Boolean.parseBoolean(request.getAttribute(nonAssetRequestAttribute).toString())) {

            Object userIdFromAttribute = request.getAttribute(userIdAttribute);
            Long startTime = Long.parseLong(request.getAttribute(startTimeAttr) + "");
            String endpoint = request.getRequestURI();
            UserLog userLog = new UserLog();
            userLog.setActivityStart(startTime);
            userLog.setEndpoint(endpoint);

            if (userIdFromAttribute != null) {
                String userId = request.getAttribute(userIdAttribute) + "";
                Optional<String> sessionsId = Optional.ofNullable(request.getAttribute(sessionIdAttribute)).map(Object::toString);

                userLog.setUserSessionId(sessionsId.get());
                userLog.setActivityUserId(userId);
                createActivityUserIfNotExists(userId);

                var logs = activityUserRepository.getOne(userId).getUserLogs();
                log.info(logs.toString());
            }

            userLog.setActivityEnd(System.currentTimeMillis());
            repository.save(userLog);
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    private void createActivityUserIfNotExists(String userId) {
        // TODO cache
        if (!activityUserRepository.existsById(userId)) {
            ActivityUser user = new ActivityUser();
            user.setId(userId);
            activityUserRepository.save(user);
        }
    }
}

