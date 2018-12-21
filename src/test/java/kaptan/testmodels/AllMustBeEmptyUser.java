package kaptan.testmodels;


import kaptan.annotations.MustBeEmpty;

public class AllMustBeEmptyUser {
    @MustBeEmpty
    private String username;
    @MustBeEmpty
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
