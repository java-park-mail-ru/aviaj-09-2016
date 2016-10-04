package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.model.UserProfile;

import java.util.HashMap;
import java.util.Map;


@Service
public class SessionService {

    private Map<String, String> sessionIdToUser = new HashMap<>();

    public String addSession(String sessionId, UserProfile user) {
        return sessionIdToUser.put(sessionId, user.getLogin());
    }

    public String getUserLoginBySession(String sessionId) {
        return sessionIdToUser.get(sessionId);
    }

    public String removeSession(String sessionId) {
        return sessionIdToUser.remove(sessionId);
    }

}
