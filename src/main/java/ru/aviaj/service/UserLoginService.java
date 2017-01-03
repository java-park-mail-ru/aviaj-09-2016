package ru.aviaj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.model.UserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
@Service
public class UserLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginService.class);

    private Map<Long, String> users = new ConcurrentHashMap<>();

    final AccountService accountService;

    @Autowired
    public UserLoginService(AccountService accountService) {
        this.accountService = accountService;
        try {
            for (UserProfile userProfile : accountService.getAllUsers()) {
                users.put(userProfile.getId(), userProfile.getLogin());
            }
        } catch (DbException e) {
            LOGGER.error("Unable to get user profiles!", e);
        }
    }

    public void addUserLogin(long id, String login) {
        users.put(id, login);
    }

    public String getUserLogin(long id) {
        return users.get(id);
    }
}
