package ru.aviaj.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;

/**
 * Created by sibirsky on 25.09.16.
 */

@RestController
public class RegistrationController {

    private final AccountService accountService;

    public static final class UserSignupRequest {
        private String login;
        private String email;
        private String password;

        private UserSignupRequest(String login, String email, String password) {
            this.login = login;
            this.email = email;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final class UserProfileResponse {
        private String login;
        private String email;
        private String rating;

        private UserProfileResponse(String login, String email) {
            this.login = login;
            this.email = email;
            this.rating = "0";
        }

        public String getLogin() {
            return login;
        }

        public String getEmail() {
            return email;
        }

        public String getRating() {
            return rating;
        }
    }

    @Autowired
    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/api/signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestBody UserSignupRequest body) {
        final String login = body.getLogin();
        final String email = body.getEmail();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        if (StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        if (StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        UserProfile existingUser = accountService.getUserByLogin(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        accountService.addUser(login, email, password);

        return ResponseEntity.ok(new UserProfileResponse(login, email));
    }

}
