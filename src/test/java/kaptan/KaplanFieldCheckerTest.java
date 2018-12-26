/*
 * Copyright (c) 2018.
 * Author: Halim Burak Yesilyurt (h.burakyesilyurt@gmail.com)
 * This library is distributed with MIT licence.
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package kaptan;
import kaptan.exceptions.FieldViolationException;
import org.junit.jupiter.api.Test;
import kaptan.testmodels.*;

import java.util.LinkedList;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;


public class KaplanFieldCheckerTest {

    @Test
    public void checkIfKaplanInitializeWell()
    {
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertNotNull(kaptanFieldChecker);

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
    public void checkWithPrimitiveVariables() {
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
    public void checkWithCollectionsAndPrimitivesForSizeAndIntervalEnforcement()
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
    public void checkWithCollectionsAndPrimitiveForSizeAndNonNullAndIntervalAndRegex() {

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
   public void checkWithUserWhoHasEnumarations()
    {
        UserHasEnums userHasEnums = new UserHasEnums();
        userHasEnums.setSampleEnum(SampleEnum.ENUM_1);
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertDoesNotThrow(()->{kaptanFieldChecker.check(userHasEnums);},"Exception is thrown: Unexpected");
    }

    @Test
    public void checkWithUserWhoHasFollowingValuesConstraintWithOKPrimitiveValues()
    {
        UserWithFollowingValuesAnnotations user = new UserWithFollowingValuesAnnotations();
        user.setaByte( new Integer(0x1b).byteValue());
        user.setaDouble(12.23);
        user.setaLong(2);
        user.setaName("BRK");
        user.setAnInt(1);
        user.setaShort((short)1);
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();

        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown: Unexpected");

    }
    @Test
    public void checkWithUserWhoHasFollowingValuesConstraintWithBadPrimitiveValues()
    {
        UserWithFollowingValuesAnnotations user = new UserWithFollowingValuesAnnotations();
        user.setaByte( new Integer(0xff).byteValue());
        user.setaDouble(1);
        user.setaLong(3);
        user.setaName("FALSE");
        user.setAnInt(0);
        user.setaShort((short)3);
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        try {
        kaptanFieldChecker.check(user);
        }
        catch (FieldViolationException ex)
        {
            assertTrue(ex.getViolations().size() == 6);
        }

    }

    @Test
    public void checkWithUserWhoHasFollowingValuesConstraintWithOKObjectValues()
    {
        UserWithFollowingValuesAnnotationsForObjects user = new UserWithFollowingValuesAnnotationsForObjects();
        user.setaByte( new Integer(0x1b).byteValue());
        user.setaDouble(new Double(12.23));
        user.setaLong(new Long(2));
        user.setaName("BRK");
        user.setAnInt(new Integer(1));
        user.setaShort(new Short((short) 1));
        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();

        assertDoesNotThrow(()->{kaptanFieldChecker.check(user);},"Exception is thrown: Unexpected");

    }
    @Test
    public void checkWithUserWhoHasFollowingValuesConstraintWithBadObjectValues()
    {
        UserWithFollowingValuesAnnotationsForObjects user = new UserWithFollowingValuesAnnotationsForObjects();
        user.setaByte( new Integer(0xff).byteValue());
        user.setaDouble(new Double(1));
        user.setaLong(new Long(3));
        user.setaName("FALSE");
        user.setAnInt(new Integer(0));
        user.setaShort(new Short((short) 3));

        KaptanFieldChecker kaptanFieldChecker = new KaptanFieldChecker();
        assertThrows(FieldViolationException.class,()->{ kaptanFieldChecker.check((user));});
        try {
            kaptanFieldChecker.check(user);
        }
        catch (FieldViolationException ex)
        {
            assertTrue(ex.getViolations().size() == 6);
        }

    }
}
