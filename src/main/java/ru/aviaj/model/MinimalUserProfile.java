package ru.aviaj.model;

import ru.aviaj.model.UserProfile;

public class MinimalUserProfile {

    private long id;
    private String login;

    public MinimalUserProfile(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.login = userProfile.getLogin();
    }

    public MinimalUserProfile(long id, String login) {
        this.id = id;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
