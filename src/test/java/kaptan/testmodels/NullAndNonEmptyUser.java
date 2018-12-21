package kaptan.testmodels;

import kaptan.annotations.MustBeNonEmpty;
import kaptan.annotations.MustBeNull;

public class NullAndNonEmptyUser {
    @MustBeNull
    @MustBeNonEmpty
    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
