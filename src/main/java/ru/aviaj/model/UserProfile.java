package ru.aviaj.model;


import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings({"ConstantNamingConvention", "unused"})
public class UserProfile {

    private long id;
    private String login;
    private String email;
    private String password;
    private long rating;

    public UserProfile(String login, String email, String password) {

        this.id = 0;
        this.login = login;
        this.email = email;
        this.password = password;
        this.rating = 0;
    }

    public UserProfile(String login, String email, String password, long id, long rating) {

        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.rating = rating;
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
