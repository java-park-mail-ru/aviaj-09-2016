package ru.aviaj.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.database.exception.ConnectException;
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.ErrorType;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserSelectionController {

    private final AccountService accountService;

    @SuppressWarnings("unused")
    private static final class UserResponse {
        private long id;
        private String login;
        private long rating;

        private UserResponse() { }

        private UserResponse(UserProfile user) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.rating = user.getRating();
        }

        public long getId() { return id;}

        public String getLogin() { return login; }

        public long getRating() { return rating; }
    }

    @SuppressWarnings("unused")
    private static final class UserResponseList {
        private final List<UserResponse> users = new ArrayList<>();

        private UserResponseList() { }

        private UserResponseList(UserResponse user) {
            users.add(user);
        }

        public void addUserResponse(UserResponse user) {
            users.add(user);
        }

        public List<UserResponse> getUsers() {
            return users;
        }
    }

    @Autowired
    public UserSelectionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/api/users", method = RequestMethod.GET)
    public ResponseEntity getUsers() {
        final UserResponseList users = new UserResponseList();
        for (Map.Entry<String, UserProfile> it : accountService.getEntrySet()) {
            users.addUserResponse(new UserResponse(it.getValue()));
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping(path = "/api/users/top", method = RequestMethod.GET)
    public ResponseEntity getTop() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorList(ErrorType.NOTREALISED)
        );
    }

    @RequestMapping(path = "/api/users/id/{userId}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable long userId) {
        if (userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.WRONGUSERID)
            );
        }

        for (Map.Entry<String, UserProfile> it : accountService.getEntrySet()) {
            if (it.getValue().getId() == userId)
                return ResponseEntity.ok(new UserResponse(it.getValue()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorList(ErrorType.NOUSERID)
        );
    }

    @RequestMapping(path = "/api/users/login/{login}", method = RequestMethod.GET)
    public ResponseEntity getByLogin(@PathVariable String login) {
        if(StringUtils.isEmpty(login)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.EMPTYLOGIN)
            );
        }

        try {
            final UserProfile user = accountService.getUserByLogin(login);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorList(ErrorType.NOLOGIN)
                );
            }
            return ResponseEntity.ok(new UserResponse(user));
        } catch (ConnectException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.UNEXPECTEDERROR)
            );
        }

        //return ResponseEntity.ok(new UserResponse(user));
    }

}