import kaptan.KaptanFieldChecker;
import kaptan.exception.FieldViolationException;
import org.junit.jupiter.api.Test;
import testmodels.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class KaplanFieldCheckerTest {

    @Test
    public void checkIfKaplanInitializeWell()
    {
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();

    }

    @Test
    public void testIllegalArgumentException()
    {
        assertThrows(IllegalArgumentException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(null);});
    }

    @Test
    public void checkIfMustBeNullTagWorks()
    {

        AllNullUser allNullUserTrue = new AllNullUser();
        allNullUserTrue.setPassword("123");
        allNullUserTrue.setUsername("22");
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(allNullUserTrue);});
        allNullUserTrue.setUsername(null);
        allNullUserTrue.setPassword(null);
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(allNullUserTrue);
    }

    @Test
    public void checkIfMustBeNonNullTagWorks()
    {
        AllNonNullUser allNonNullUser = new AllNonNullUser();
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(allNonNullUser);});

        allNonNullUser.setPassword("123");
        allNonNullUser.setUsername("Burak");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(allNonNullUser);


    }

    @Test
    public void checkIfMustBeNonEmptyTagWorks()
    {
        AllMustBeNonEmpty user = new AllMustBeNonEmpty();
        user.setPassword("");
        user.setUsername("");
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});

        user.setPassword("123");
        user.setUsername("Burak");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(user);
    }

    @Test
    public void checkIfMustBeEmptyTagWorks()
    {
        AllMustBeEmptyUser user = new AllMustBeEmptyUser();
        user.setPassword("22");
        user.setUsername("323");
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});

        user.setPassword("");
        user.setUsername("");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(user);
    }

    @Test
    public void checkIfMustBeEmptyAndNonNullTagWorks()
    {
        NonNullAndEmptyUser user = new NonNullAndEmptyUser();
        user.setUsername("323");
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});
        user.setUsername(null);
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});

        user.setUsername("");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(user);
    }

    @Test
    public void checkIfMustBeEmptyAndNullTagWorks()
    {
        NullAndNonEmptyUser user = new NullAndNonEmptyUser();
        user.setUsername(null);
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});
        user.setUsername("");
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(user);});

    }

    @Test
    void checkWithPrimitiveVariables() {
        UserWithPrimitiveVariables user = new UserWithPrimitiveVariables();
        user.setUsername(null);
        user.setPassword("123");
        assertThrows(FieldViolationException.class,()->{KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check((user));});
        user.setUsername("uname");
        user.setPassword("");
        assertThrows(FieldViolationException.class,()->{KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check((user));});

        user.setUsername("Burak");
        user.setPassword("TestPWD");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        kaptanFieldChecker.check(user);


    }
}
