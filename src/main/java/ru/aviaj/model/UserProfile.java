package ru.aviaj.model;



@SuppressWarnings({"ConstantNamingConvention", "unused", "OverlyComplexBooleanExpression"})
public class UserProfile {

    private long id;
    private String login;
    private String email;
    private String password;
    private long rating;

    public UserProfile(String login, String email, String password) {
        this(login, email, password, 0, 0);
    }

    public UserProfile(String login, String email, String password, long id, long rating) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.rating = rating;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object == this)
            return true;
        if (!(object instanceof UserProfile))
            return false;

        final UserProfile other = (UserProfile)object;
        if ( (this.id == other.id) && (this.rating == other.rating) && (this.email.equals(other.email))
                && (this.login.equals(other.login)) && (this.password.equals(other.password)))
            return true;
        return false;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() { return email; }

    public String getPassword() {
        return password;
    }

    public long getRating() {
        return rating;
    }

    public void updateRating(long updateValue) {
        rating += updateValue;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + login.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        return result;
    }
}
