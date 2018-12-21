import kaptan.KaptanField;
import kaptan.KaptanFieldChecker;
import kaptan.exception.FieldViolationException;
import org.junit.jupiter.api.Test;
import testmodels.*;

import java.util.LinkedList;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        assertDoesNotThrow(()->{kaptanFieldChecker.check(allNullUserTrue);},"Exception is thrown.");
    }

    @Test
    public void checkIfMustBeNonNullTagWorks()
    {
        AllNonNullUser allNonNullUser = new AllNonNullUser();
        assertThrows(FieldViolationException.class,()->{ KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker(); kaptanFieldChecker.check(allNonNullUser);});

        allNonNullUser.setPassword("123");
        allNonNullUser.setUsername("Burak");
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertDoesNotThrow(()->{kaptanFieldChecker.check(allNonNullUser);},"Exception is thrown.");




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
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
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
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
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
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
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
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");


    }
    @Test
    void checkWithCollectionsAndPrimitivesForSizeAndIntervalEnforcement()
    {
        UserWithCollectionsAndPrimitives user = new UserWithCollectionsAndPrimitives();
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        user.setAge(34);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.addName("TST");
        user.addName("BRK1");
        user.addName("BRK1a");
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
        user.addName("BRK2");
        user.addName("BRK3");
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
        user.addName("BRK5");
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.removeLastName();
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
        user.setAge(1);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setAge(67);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setAge(32);
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
    }

    @Test
    void checkWithCollectionsAndPrimitiveForSizeAndNonNullAndIntervalAndRegex() {

        UserWithRegexAndSizeAndIntervalConstraint user = new UserWithRegexAndSizeAndIntervalConstraint();
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        user.setIdNumber(5);
        user.setFavoriteByte(new Long(1).byteValue());
        user.setHeight(123);
        user.addInterest("TEST");
        user.setName("Burak3#");
        user.addWaitingFindings("tst");
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
        user.setName("Burak");
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setName("");
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setName(null);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setName("Burak3#");
        user.setInterest(null);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setInterest(new LinkedList<>());
        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown.");
        user.setFavoriteByte(new Long(2323232).byteValue());
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setFavoriteByte(new Long(0).byteValue());
        user.setWaitingFindings(null);
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        user.setWaitingFindings(new PriorityQueue<>());
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});

    }

    @Test
    void checkWithUserWhoHasEnumarations()
    {
        UserHasEnums userHasEnums = new UserHasEnums();
        userHasEnums.setSampleEnum(SampleEnum.ENUM_1);
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertDoesNotThrow(()->{kaptanFieldChecker.check(userHasEnums);},"Exception is thrown: Unexpected");
    }
}
