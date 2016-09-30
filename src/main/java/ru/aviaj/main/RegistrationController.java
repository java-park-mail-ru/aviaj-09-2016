package ru.aviaj.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.model.Error;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;

import java.util.ArrayList;
import java.util.List;

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

        private UserSignupRequest() { };

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

        private UserProfileResponse() { };

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

    @RequestMapping(path = "/api/users/signup", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity signup(@RequestBody UserSignupRequest body) {
        final String login = body.getLogin();
        final String email = body.getEmail();
        final String password = body.getPassword();

        Boolean hasErrors = false;
        List<Error> errorList = new ArrayList<Error>();

        if (StringUtils.isEmpty(login)) {
            hasErrors = true;
            errorList.add(new Error(Error.ErrorType.EMPTYLOGIN));
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{error}");
        }

        if (StringUtils.isEmpty(email)) {
            hasErrors = true;
            errorList.add(new Error(Error.ErrorType.EMPTYEMAIL));
        }

        if (StringUtils.isEmpty(password)) {
            hasErrors = true;
            errorList.add(new Error(Error.ErrorType.EMPTYPASSWORD));
        }

        if (hasErrors) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
        }

        final UserProfile existingUser = accountService.getUserByLogin(login);
        if (existingUser != null) {
            errorList.add(new Error(Error.ErrorType.DUBLICATELOGIN));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
        }

        accountService.addUser(login, email, password);

        return ResponseEntity.ok(new UserProfileResponse(login, email));
    }

}
