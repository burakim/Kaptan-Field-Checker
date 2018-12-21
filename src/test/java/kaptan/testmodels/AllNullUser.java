package kaptan.testmodels;

import kaptan.annotations.MustBeNull;

public class AllNullUser {
    @MustBeNull
    private String username;
    @MustBeNull
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
