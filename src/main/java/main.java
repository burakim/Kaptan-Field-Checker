import kaptan.KaptanFieldChecker;
import kaptan.User;
import kaptan.annotations.MustBeEmpty;
import kaptan.annotations.MustBeNonEmpty;

public class main {



    public static void main(String [] args) {
        User u1 = new User();
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(u1);
    }
}
