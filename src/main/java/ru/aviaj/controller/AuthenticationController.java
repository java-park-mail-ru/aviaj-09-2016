package ru.aviaj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

@SuppressWarnings("unused")
@RestController
public class AuthenticationController {

    private final AccountService accountService;
    private final SessionService sessionService;

    @SuppressWarnings("unused")
    public static final class UserRequest {
        private String login;
        private String password;

        private UserRequest() { }

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

    @SuppressWarnings("unused")
    public static final class UserResponse {
        private long id;
        private String login;
        private String email;
        private long rating;

        private UserResponse() { }

        private UserResponse(UserProfile user) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.email = user.getEmail();
            this.rating = user.getRating();
        }

        public long getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getEmail() { return email; }

        public long getRating() {
            return rating;
        }
    }

    @Autowired
    public AuthenticationController(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    /*@RequestMapping(path = "/api/auth/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login(@RequestBody UserRequest body, HttpSession httpSession) {

        final String loginedUserLogin = sessionService.getUserLoginBySession(httpSession.getId());
        final UserProfile loginedUser = accountService.getUserByLogin(loginedUserLogin);
        if (loginedUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorList(ErrorType.ALREADYLOGIN)
            );
        }

        final UserProfile requestUser = accountService.getUserByLogin(body.getLogin());
        if (requestUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.NOLOGIN)
            );
        }

        final String truePassword = requestUser.getPassword();
        final String bodyPassword = body.getPassword();
        if (!truePassword.equals(bodyPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.WRONGPASSWORD)
            );
        }

        sessionService.addSession(httpSession.getId(), requestUser);

        return ResponseEntity.ok(new UserResponse(requestUser));
    }

    @RequestMapping(path = "/api/auth/authenticate", method = RequestMethod.GET)
    public ResponseEntity authenticate(HttpSession httpSession) {

        final String loginedUserLogin = sessionService.getUserLoginBySession(httpSession.getId());
        if (StringUtils.isEmpty(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorList(ErrorType.NOTLOGINED)
            );
        }

        final UserProfile loginedUser = accountService.getUserByLogin(loginedUserLogin);
        if (loginedUser == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorList(ErrorType.UNEXPECTEDERROR)
            );
        }

        return ResponseEntity.ok(new UserResponse(loginedUser));

    }

    @RequestMapping(path = "/api/auth/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {

        final String sessionId = httpSession.getId();

        final String loginedUserLogin = sessionService.getUserLoginBySession(sessionId);
        if(StringUtils.isEmpty(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorList(ErrorType.NOTLOGINED)
            );
        }

        final String removedLogin = sessionService.removeSession(sessionId);
        if (!removedLogin.equals(loginedUserLogin)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.UNEXPECTEDERROR)
            );
        }


        return ResponseEntity.ok("{\"success\": \"true\"}");
    } */

}



