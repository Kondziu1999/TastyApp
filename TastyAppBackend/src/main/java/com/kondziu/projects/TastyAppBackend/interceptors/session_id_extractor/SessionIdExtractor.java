package com.kondziu.projects.TastyAppBackend.interceptors.session_id_extractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface SessionIdExtractor {


    Optional<String> retrieveSessionId(HttpServletRequest request);
}
