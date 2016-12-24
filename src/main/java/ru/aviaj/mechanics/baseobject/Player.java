package ru.aviaj.mechanics.baseobject;

import ru.aviaj.model.UserProfile;

public class Player {

    private final UserProfile userProfile;
    private final long userId;
    private PlanePosition planePosition;

    private long ping;
    private long timeShift;

    private long ratingToUpdate = 0;

    public Player(UserProfile userProfile) {

        this.userProfile = userProfile;
        this.userId = userProfile.getId();
        this.planePosition = new PlanePosition();

        this.ping = 0;
        this.timeShift = 0;
    }

    public PlanePosition getPlanePosition() {
        return planePosition;
    }

    public long getUserId() {
        return userId;
    }

    public long getPing() {
        return ping;
    }

    public long getTimeShift() {
        return timeShift;
    }

    public void setPlanePosition(PlanePosition planePosition) {
        this.planePosition = planePosition;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }

    public void setTimeShift(long timeShift) {
        this.timeShift = timeShift;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public long getRatingToUpdate() {
        return ratingToUpdate;
    }

    public void updateRating(long ratingValue) {
        ratingToUpdate += ratingValue;
    }
}
