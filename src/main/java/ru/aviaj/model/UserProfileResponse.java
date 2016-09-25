package ru.aviaj.model;

/**
 * Created by sibirsky on 25.09.16.
 */
public class UserProfileResponse {

    private long id;
    private String login;
    private long rating;

    public UserProfileResponse(UserProfile originalProfile) {
        id = originalProfile.getId();
        login = originalProfile.getLogin();
        rating = originalProfile.getRating();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public long getRating() {
        return  rating;
    }
}
