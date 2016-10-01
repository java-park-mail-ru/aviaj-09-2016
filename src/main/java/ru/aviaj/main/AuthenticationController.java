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
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import javax.servlet.http.HttpSession;

/**
 * Created by sibirsky on 25.09.16.
 */

@RestController
public class AuthenticationController {

    private final AccountService accountService;
    private final SessionService sessionService;

    public static final class UserRequest {
        private String login;
        private String password;

        private UserRequest() { };

        private UserRequest(String login, String email, String password) {
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

    public static final class UserResponse {
        private String id;
        private String login;
        private String email;
        private String rating;

        private UserResponse() { };

        private UserResponse(UserProfile user) {
            this.id = Long.toString(user.getId());
            this.login = user.getLogin();
            this.email = user.getEmail();
            this.rating = Long.toString(user.getRating());
        }

        public String getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getEmail() { return email; }

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
    public ResponseEntity login(@RequestBody UserRequest body, HttpSession httpSession) {

        String loginedUserLogin = sessionService.getUserLoginBySession(httpSession.getId());
        UserProfile loginedUser = accountService.getUserByLogin(loginedUserLogin);
        if (loginedUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
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
        if (!truePassword.equals(bodyPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.WRONGPASSWORD)
            );
        }

        sessionService.addSession(httpSession.getId(), requestUser);

        return ResponseEntity.ok(new UserResponse(requestUser));
    }

    @RequestMapping(path = "/api/auth/authenticate", method = RequestMethod.GET)
    public ResponseEntity authenticate(HttpSession httpSession) {

        String loginedUserLogin = sessionService.getUserLoginBySession(httpSession.getId());
        if (StringUtils.isEmpty(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorList(Error.ErrorType.NOTLOGINED)
            );
        }

        UserProfile loginedUser = accountService.getUserByLogin(loginedUserLogin);
        if (loginedUser == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorList(Error.ErrorType.UNEXPECTEDERROR)
            );
        }

        return ResponseEntity.ok(new UserResponse(loginedUser));

    }

    @RequestMapping(path = "/api/auth/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {

        String sessionId = httpSession.getId();

        String loginedUserLogin = sessionService.getUserLoginBySession(sessionId);
        if(StringUtils.isEmpty(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorList(Error.ErrorType.NOTLOGINED)
            );
        }

        String removedLogin = sessionService.removeSession(sessionId);
        if (!removedLogin.equals(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(Error.ErrorType.UNEXPECTEDERROR)
            );
        }

        return ResponseEntity.ok("{}");
    }

}



