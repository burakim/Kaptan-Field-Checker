import kaptan.KaptanFieldChecker;
import kaptan.User;
import kaptan.annotations.MustBeEmpty;
import kaptan.annotations.MustBeNonEmpty;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class main {



    public static void main(String [] args) {
        HashMap<String,String> h = new HashMap<>();
        if(h instanceof Collection)
        {
            System.out.println("");
        }
//        User u1 = new User();
//        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
//        kaptanFieldChecker.check(u1);

    }
}
