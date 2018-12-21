package kaptan.testmodels;


import kaptan.annotations.MustBeNonNull;

public class AllNonNullUser {

    @MustBeNonNull
    private String username;
    @MustBeNonNull
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
