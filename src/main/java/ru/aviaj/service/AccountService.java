package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.model.UserProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AccountService {

    private Map<String, UserProfile> loginToUser = new HashMap<>();

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

    /*public UserProfile addUser(String login, String email, String password) throws ConnectException {

        Connection dbConnection = getConnection();
        if (dbConnection == null)
            throw new ConnectException("Cannot connect to MySQL database!");

        UserProfileDAO userDao = new UserProfileDAO(dbConnection);
        UserProfile user = userDao.addUser(login, email, password);
        if (user == null) {

        }
        return null;
    } */
}
