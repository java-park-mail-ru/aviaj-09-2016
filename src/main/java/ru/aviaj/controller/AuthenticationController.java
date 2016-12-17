package ru.aviaj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Abonent;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.MessageSystem;
import ru.aviaj.messagesystem.message.sessionservice.MsgAuth;
import ru.aviaj.messagesystem.message.sessionservice.MsgLogin;
import ru.aviaj.messagesystem.message.sessionservice.MsgLogout;
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.ErrorType;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings({"unused", "Duplicates", "InfiniteLoopStatement"})
@RestController
public class AuthenticationController implements Abonent, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AccountService accountService;
    private final SessionService sessionService;

    @Autowired
    MessageSystem messageSystem;

    private Address address = new Address();

    private ConcurrentMap<String, Long> waitingClients = new ConcurrentHashMap<>();

    private Executor threadExecutor = Executors.newSingleThreadExecutor();

    public static final long NULLUSER = 0;
    public static final long ERRORUSER = -1;
    public static final long WAITINGUSER = -2;
    public static final long LOGOUTUSER = -3;

    @Autowired
    public AuthenticationController(AccountService accountService, SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @PostConstruct
    public void postConstruct() {
        threadExecutor.execute(this);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void addToWaiters(String sessionId) {
        waitingClients.put(sessionId, WAITINGUSER);
    }

    public boolean hasWaiter(String sessionId) {
        return waitingClients.containsKey(sessionId);
    }

    public long getWaiterStatus(String sessionId) {
        return waitingClients.get(sessionId);
    }

    public void setWaiterStatus(String sessionId, long status) {
        waitingClients.put(sessionId, status);
    }

    public void removeFromWaiters(String sessionId) {
        waitingClients.remove(sessionId);
    }

    @Override
    public void run() {
        while(true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error("Unable to sleep thread!", e);
            }
        }
    }

    @RequestMapping(path = "/api/auth/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login(@RequestBody UserRequest body, HttpSession httpSession) {

        if (httpSession.getAttribute("AVIAJSESSIONID") != null) {

            String sessionId = httpSession.getAttribute("AVIAJSESSIONID").toString();

            if (!hasWaiter(sessionId))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        new ErrorList(ErrorType.ALREADYLOGIN)
                );

            if (getWaiterStatus(sessionId) == WAITINGUSER) {
                return ResponseEntity.ok("WAIT");
            }

            if (getWaiterStatus(sessionId) > 0) {
                final UserProfile requestUser;
                try {
                    requestUser = accountService.getUserById(getWaiterStatus(sessionId));
                    if (requestUser == null) {
                        removeFromWaiters(sessionId);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                new ErrorList(ErrorType.NOLOGIN)
                        );
                    }
                } catch (DbException e) {
                    removeFromWaiters(sessionId);
                    LOGGER.error("Login error:", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new ErrorList(ErrorType.DBERROR)
                    );
                }

                removeFromWaiters(sessionId);
                return ResponseEntity.ok(new UserResponse(requestUser));
            }
        }

        final UserProfile requestUser;
        try {
            requestUser = accountService.getUserByLogin(body.getLogin());
            if (requestUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ErrorList(ErrorType.NOLOGIN)
                );
            }
        } catch (DbException e) {
            LOGGER.error("Login error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.DBERROR)
            );
        }

        final String bodyPassword = body.getPassword();
        if (bodyPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.WRONGPASSWORD)
            );
        }

        final String truePasswordHash = requestUser.getPassword();
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(bodyPassword, truePasswordHash)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorList(ErrorType.WRONGPASSWORD)
            );
        }

        final String sessionId = httpSession.getId();
        final long userId = requestUser.getId();

        addToWaiters(sessionId);
        MsgLogin msgLogin = new MsgLogin(getAddress(), sessionService.getAddress(), sessionId, userId);
        messageSystem.send(msgLogin);

        httpSession.setAttribute("AVIAJSESSIONID", sessionId);

        return ResponseEntity.ok("WAIT");

    }

    @RequestMapping(path = "/api/auth/authenticate", method = RequestMethod.GET)
    public ResponseEntity authenticate(HttpSession httpSession) {

        if (httpSession.getAttribute("AVIAJSESSIONID") == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorList(ErrorType.NOTLOGINED)
            );

        final String sessionId = httpSession.getAttribute("AVIAJSESSIONID").toString();

        if (!hasWaiter(sessionId)) {
            addToWaiters(sessionId);
            final MsgAuth msgAuth = new MsgAuth(getAddress(), sessionService.getAddress(), sessionId);
            messageSystem.send(msgAuth);

            return ResponseEntity.ok("WAIT");
        }

        final long status = getWaiterStatus(sessionId);

        if (status == WAITINGUSER)
            return ResponseEntity.ok("WAIT");

        if (status == NULLUSER) {
            removeFromWaiters(sessionId);
            httpSession.removeAttribute("AVIAJSESSIONID");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorList(ErrorType.NOTLOGINED)
            );
        }

        if (status == ERRORUSER) {
            removeFromWaiters(sessionId);
            httpSession.removeAttribute("AVIAJSESSIONID");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.UNEXPECTEDERROR)
            );
        }

        try {
            final UserProfile loginedUser = accountService.getUserById(status);
            if (loginedUser == null) {
                removeFromWaiters(sessionId);
                httpSession.removeAttribute("AVIAJSESSIONID");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ErrorList(ErrorType.UNEXPECTEDERROR)
                );
            }

            removeFromWaiters(sessionId);
            return ResponseEntity.ok(new UserResponse(loginedUser));

        } catch (DbException e) {
            removeFromWaiters(sessionId);
            LOGGER.error("Authenticate error:", e);
            httpSession.removeAttribute("AVIAJSESSIONID");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.DBERROR)
            );
        }

    }

    @RequestMapping(path = "/api/auth/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {

        if (httpSession.getAttribute("AVIAJSESSIONID") == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorList(ErrorType.NOTLOGINED));

        final String sessionId = httpSession.getAttribute("AVIAJSESSIONID").toString();

        if (!hasWaiter(sessionId)) {
            addToWaiters(sessionId);
            final MsgLogout msgLogout = new MsgLogout(getAddress(), sessionService.getAddress(), sessionId);
            messageSystem.send(msgLogout);

            return ResponseEntity.ok("WAIT");
        }

        final long status = getWaiterStatus(sessionId);

        if (status == WAITINGUSER)
            return ResponseEntity.ok("WAIT");

        if (status == ERRORUSER) {
            removeFromWaiters(sessionId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorList(ErrorType.UNEXPECTEDERROR)
            );
        }

        removeFromWaiters(sessionId);
        httpSession.removeAttribute("AVIAJSESSIONID");

        return ResponseEntity.ok("{\"success\": \"true\"}");
    }

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

}



