package ru.aviaj.main;

import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.model.Error;
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by sibirsky on 25.09.16.
 */

@RestController
public class AuthenticationController {

    private final AccountService accountService;
    private final SessionService sessionService;

    public static final class UserLoginRequest {
        private String login;
        private String password;

        private UserLoginRequest() { };

        private UserLoginRequest(String login, String email, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static final class UserLoginResponse {
        private String id;
        private String login;
        private String rating;

        private UserLoginResponse() { };

        private UserLoginResponse(UserProfile user) {
            this.id = Long.toString(user.getId());
            this.login = user.getLogin();
            this.rating = Long.toString(user.getRating());
        }

        public String getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getRating() {
            return rating;
        }
    }

    @Autowired
    public AuthenticationController(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @RequestMapping(path = "/api/auth/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login(@RequestBody UserLoginRequest body, HttpSession httpSession) {

        String loginedUserLogin = sessionService.getUserLoginBySession(httpSession.getId());
        UserProfile loginedUser = accountService.getUserByLogin(loginedUserLogin);
        if (loginedUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorList(Error.ErrorType.ALREADYLOGIN)
            );
        }

        UserProfile requestUser = accountService.getUserByLogin(body.getLogin());
        if (requestUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.NOLOGIN)
            );
        }

        String truePassword = requestUser.getPassword();
        String bodyPassword = body.getPassword();
        System.out.println(truePassword + " " + bodyPassword);
        if (!truePassword.equals(bodyPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.WRONGPASSWORD)
            );
        }

        sessionService.addSession(httpSession.getId(), requestUser);

        return ResponseEntity.ok(new UserLoginResponse(requestUser));
    }

   /* @RequestMapping(path = "/api/auth/authenticate", method = RequestMethod.GET)
    public ResponseEntity authenticate(HttpSession httpSession) {

        UserProfile loginedUser = sessionService.getUserBySession(httpSession.getId());
        if (loginedUser == null) {

        }
    }*/

}



