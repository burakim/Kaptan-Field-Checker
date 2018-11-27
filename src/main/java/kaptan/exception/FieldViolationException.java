package kaptan.exception;


import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * FieldViolationException says that there is at least one class class field value assignment violation.
 * KaptanFieldChecker throws it to warn user about violations.
 */
public class FieldViolationException extends Exception {

    private ArrayList<Class<? extends Annotation>> violations;

    /**
     * It is initialized with a violation summary message and an annotation array that contains class field value assignment violations.
     * @param message It should contains a message to shown up in terminal to inform developer. In Kaptan environment, it contains a summary of class field violations.
     * @param violations An ArrayList that contains current annotations of class filed violations.
     */
    public FieldViolationException(String message,ArrayList<Class<? extends Annotation>> violations)
    {
        super(message);
        this.violations = violations;
    }

    /**
     * It returns the class variable assignment violations in ArrayList format.
     * @return ArrayList
     */
    public ArrayList<Class<? extends Annotation>> getViolations()
    {
        return violations;
    }

}
