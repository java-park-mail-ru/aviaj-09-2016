package ru.aviaj.model;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sibirsky on 25.09.16.
 */
public class UserProfile {

    private long id;
    private String login;
    private String email;
    //private String passwordHash;
    private String password;
    private long rating;

    private static final AtomicLong idGenerator = new AtomicLong(1);

    public UserProfile(String login, String email, String password) {

        this.id = idGenerator.getAndIncrement();
        this.login = login;
        this.email = email;
        this.password = password;
        this.rating = 0;
      /*  SecureRandom randomGenerator = new SecureRandom();
        int seed = email.hashCode();
        long salt = randomGenerator.nextInt(seed)*100;
        ShaPasswordEncoder sha256Encoder = new ShaPasswordEncoder(256);
        this.passwordHash = sha256Encoder.encodePassword(password, salt); */
    }

    public long getId() {
        return id;
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

    public long getRating() {
        return rating;
    }

    public void updateRating(long updateValue) {
        rating += updateValue;
    }

}
