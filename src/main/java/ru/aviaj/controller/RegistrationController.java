package ru.aviaj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.ErrorType;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;
import ru.aviaj.service.UserLoginService;

import javax.servlet.http.HttpSession;


@SuppressWarnings("unused")
@RestController
public class RegistrationController {

    private final AccountService accountService;
    private final SessionService sessionService;
    private final UserLoginService userLoginService;

    @Autowired
    public RegistrationController(AccountService accountService, SessionService sessionService,
                                  UserLoginService userLoginService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
        this.userLoginService = userLoginService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @SuppressWarnings("OverlyComplexMethod")
    @RequestMapping(path = "/api/auth/signup", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity signup(@RequestBody UserSignupRequest body, HttpSession httpSession) {

        if (httpSession.getAttribute("AVIAJSESSIONID") != null) {

            try {
                final long loginedUserId = sessionService.getUserIdBySession(httpSession.getAttribute("AVIAJSESSIONID")
                        .toString());
                if (loginedUserId != 0) {
                    httpSession.removeAttribute("AVIAJSESSIONID");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                            new ErrorList(ErrorType.ALREADYLOGIN)
                    );
                }
            } catch (DbException e) {
                LOGGER.error("Signup error:", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ErrorList(ErrorType.DBERROR)
                );
            }
        }

        final String login = body.getLogin();
        final String email = body.getEmail();
        final String password = body.getPassword();

        final ErrorList errorList = new ErrorList();
        if (StringUtils.isEmpty(login)) {
            errorList.addError(ErrorType.EMPTYLOGIN);
        }
        if (StringUtils.isEmpty(email)) {
            errorList.addError(ErrorType.EMPTYEMAIL);
        }
        if (StringUtils.isEmpty(password)) {
            errorList.addError(ErrorType.EMPTYPASSWORD);
        }
        if (!errorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
        }

        try {
            final UserProfile existingUser = accountService.getUserExistance(login, email);
            if (existingUser != null) {
                if (login.equals(existingUser.getLogin()))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ErrorList(ErrorType.DUBLICATELOGIN)
                    );
                if (email.equals(existingUser.getEmail()))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ErrorList(ErrorType.DUBLICATEEMAIL)
                    );
            }
        } catch (DbException e) {
            LOGGER.error("Signup error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.DBERROR)
            );
        }

        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String encodedPassword = encoder.encode(password);
        try {
            final UserProfile registeredUser = accountService.addUser(login, email, encodedPassword);
            if (registeredUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ErrorList(ErrorType.UNEXPECTEDERROR)
                );
            }

            userLoginService.addUserLogin(registeredUser.getId(), registeredUser.getLogin());
            return ResponseEntity.ok(new UserProfileResponse(registeredUser));

        } catch (DbException e) {
            LOGGER.error("Signup error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.DBERROR)
            );
        }

    }

    @SuppressWarnings("unused")
    public static final class UserSignupRequest {
        private String login;
        private String email;
        private String password;

        private UserSignupRequest() { }

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

    @SuppressWarnings("unused")
    private static final class UserProfileResponse {
        private long id;
        private String login;
        private String email;
        private long rating;


        private UserProfileResponse() { }

        private UserProfileResponse(UserProfile user) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.email = user.getEmail();
            this.rating = user.getRating();
        }

        public long getId() { return id; }

        public String getLogin() {
            return login;
        }

        public String getEmail() {
            return email;
        }

        public long getRating() {
            return rating;
        }
    }

}
