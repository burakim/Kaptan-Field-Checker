package kaptan.exceptions;


import kaptan.models.Violation;

import java.util.ArrayList;

/**
 * FieldViolationException says that there is at least one class class field value assignment violation.
 * KaptanFieldChecker throws it to warn user about violations.
 */
public class FieldViolationException extends Exception {

    // It contains violations which are found by Kaptan in the target class.
    private ArrayList<Violation> violations;

    /**
     * It is initialized with a violation summary message and an annotation array that contains class field value assignment violations.
     * @param message It should contains a message to shown up in terminal to inform developer. In Kaptan environment, it contains a summary of class field violations.
     * @param violations An ArrayList that contains current annotations of class filed violations.
     */
    public FieldViolationException(String message,ArrayList<Violation> violations)
    {
        super(message);
        this.violations = violations;
    }

    /**
     * It returns the class variable assignment violations in ArrayList format.
     * @return ArrayList
     */
    public ArrayList<Violation> getViolations()
    {
        return violations;
    }

}
