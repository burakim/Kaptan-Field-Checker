package kaptan.testmodels;


import kaptan.annotations.MustBeNonEmpty;

public class AllMustBeNonEmpty {
    @MustBeNonEmpty
    private String username;
    @MustBeNonEmpty
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
