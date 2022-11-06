package com.kondziu.projects.TastyAppBackend.interceptors;

import com.kondziu.projects.TastyAppBackend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Slf4j
@Service
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private String startTimeAttr = "startTime";
    private String sessionIdAttribute = "session";
    private String userIdAttribure = "userId";

    private UserLogRepository repository;

    public LoggingInterceptor(UserLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();

        if(principal != null) {
            UserPrincipal user = (UserPrincipal)(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());

            long startTime = System.currentTimeMillis();
            HttpSession session = request.getSession();

            request.setAttribute(startTimeAttr, startTime);
            request.setAttribute(userIdAttribure, user.getId());
            request.setAttribute(sessionIdAttribute, session.getId());
        }


        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object userIdFromAttribute = request.getAttribute(userIdAttribure);
        if (userIdFromAttribute != null) {
            Long userId = Long.parseLong(request.getAttribute(userIdAttribure) + "");

            String endpoint = request.getRequestURI();
            Long startTime = Long.parseLong(request.getAttribute(startTimeAttr) + "");
            String sessionId = request.getAttribute(sessionIdAttribute) + "";

            UserLog log = new UserLog();
            log.setEndpoint(endpoint);
            log.setUserId(userId);
            log.setUserSessionId(sessionId);
            log.setActivityStart(startTime);
            log.setActivityEnd(System.currentTimeMillis());

            repository.save(log);
        }

        super.postHandle(request, response, handler, modelAndView);
    }
}

