package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.model.UserProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sibirsky on 25.09.16.
 */

@Service
public class AccountService {

    private Map<String, UserProfile> loginToUser = new HashMap<String, UserProfile>();

    public UserProfile addUser(String login, String email, String password) {
        final UserProfile newUserProfile = new UserProfile(login, email, password);
        loginToUser.put(login, newUserProfile);
        return  newUserProfile;
    }

    public UserProfile getUserByLogin(String login) {
        return loginToUser.get(login);
    }

    public Set<Map.Entry<String, UserProfile>> getEntrySet() {
        return loginToUser.entrySet();
    }
}
