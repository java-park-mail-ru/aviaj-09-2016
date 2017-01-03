package ru.aviaj.mechanics.baseobject;


@SuppressWarnings("unused")
public class Player {

    private final String userLogin;
    private final long userId;
    private PlanePosition planePosition;

    private long ping;
    private long timeShift;

    private long ratingToUpdate = 0;

    public Player(long userId, String userLogin) {

        this.userId = userId;
        this.userLogin = userLogin;
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

    public long getPing() { return ping; }

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

    public String getUserLogin() { return userLogin; }

    public long getRatingToUpdate() {
        return ratingToUpdate;
    }

    public void updateRating(long ratingValue) {
        ratingToUpdate += ratingValue;
    }
}
