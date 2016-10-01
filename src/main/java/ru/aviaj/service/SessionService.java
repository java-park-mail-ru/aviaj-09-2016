package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sibirsky on 01.10.16.
 */

@Service
public class SessionService {

    private Map<String, UserProfile> sessionIdToUser = new HashMap<String, UserProfile>();

    public UserProfile addSession(String sessionId, UserProfile user) {
        return sessionIdToUser.put(sessionId, user);
    }

    public UserProfile getUserBySession(String sessionId) {
        return sessionIdToUser.get(sessionId);
    }

}
