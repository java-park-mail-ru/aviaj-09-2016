package ru.aviaj.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.model.Error;
import ru.aviaj.model.ErrorList;
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
        String id;
        String login;
        String rating;

        private UserResponse() { }

        private UserResponse(UserProfile user) {
            this.id = Long.toString(user.getId());
            this.login = user.getLogin();
            this.rating = Long.toString(user.getRating());
        }

        public String getId() { return id;}

        public String getLogin() { return login; }

        public String getRating() { return rating; }
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

    @RequestMapping(path = "/api/users")
    public ResponseEntity getUsers() {
        final UserResponseList users = new UserResponseList();
        for (Map.Entry<String, UserProfile> it : accountService.getEntrySet()) {
            users.addUserResponse(new UserResponse(it.getValue()));
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping(path = "/api/users/top")
    public ResponseEntity getTop() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorList(Error.ErrorType.NOTREALISED)
        );
    }

    @RequestMapping(path = "/api/users/id/{userId}")
    public ResponseEntity getById(@PathVariable long userId) {
        if (userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.WRONGUSERID)
            );
        }

        for (Map.Entry<String, UserProfile> it : accountService.getEntrySet()) {
            if (it.getValue().getId() == userId)
                return ResponseEntity.ok(new UserResponse(it.getValue()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorList(Error.ErrorType.NOUSERID)
        );
    }

    @RequestMapping(path = "/api/users/login/{login}")
    public ResponseEntity getByLogin(@PathVariable String login) {
        if(StringUtils.isEmpty(login)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.EMPTYLOGIN)
            );
        }

        final UserProfile user = accountService.getUserByLogin(login);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorList(Error.ErrorType.NOLOGIN)
            );
        }

        return ResponseEntity.ok(new UserResponse(user));
    }

}
