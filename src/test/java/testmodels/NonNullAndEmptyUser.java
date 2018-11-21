package testmodels;

import kaptan.annotations.MustBeEmpty;
import kaptan.annotations.MustBeNonNull;

public class NonNullAndEmptyUser {
    @MustBeEmpty
    @MustBeNonNull
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
