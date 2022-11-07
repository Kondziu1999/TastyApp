package com.kondziu.projects.TastyAppBackend.interceptors;

import com.kondziu.projects.TastyAppBackend.interceptors.model.ActivityUser;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.ActivityUserRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.repository.UserLogRepository;
import com.kondziu.projects.TastyAppBackend.interceptors.session_id_extractor.SessionIdExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private String startTimeAttr = "startTime";
    private String sessionIdAttribute = "session";
    private String userIdAttribure = "userId";

    private UserLogRepository repository;
    private SessionIdExtractor sessionIdExtractor;

    private ActivityUserRepository activityUserRepository;
    public LoggingInterceptor(UserLogRepository repository, SessionIdExtractor sessionIdExtractor, ActivityUserRepository activityUserRepository) {
        this.repository = repository;
        this.sessionIdExtractor = sessionIdExtractor;
        this.activityUserRepository = activityUserRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();

        if(principal != null) {
            HasStringUserIdPrincipal user = (HasStringUserIdPrincipal)(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());

            request.setAttribute(userIdAttribure, user.getStringUserId());
            request.setAttribute(sessionIdAttribute, sessionIdExtractor.retrieveSessionId(request).get());
        }
        long startTime = System.currentTimeMillis();
        request.setAttribute(startTimeAttr, startTime);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object userIdFromAttribute = request.getAttribute(userIdAttribure);
        Long startTime = Long.parseLong(request.getAttribute(startTimeAttr) + "");
        UserLog userLog = new UserLog();
        userLog.setActivityStart(startTime);

        if (userIdFromAttribute != null) {
            String userId = request.getAttribute(userIdAttribure) + "";
            Optional<String> sessionsId = Optional.ofNullable(request.getAttribute(sessionIdAttribute)).map(Object::toString);
            String endpoint = request.getRequestURI();

            userLog.setEndpoint(endpoint);
            userLog.setUserSessionId(sessionsId.get());
            userLog.setActivityUserId(userId);
            createActivityUserIfNotExists(userId);

            var logs = activityUserRepository.getOne(userId).getUserLogs();
            log.info(logs.toString());
        }

        userLog.setActivityEnd(System.currentTimeMillis());
        repository.save(userLog);


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

