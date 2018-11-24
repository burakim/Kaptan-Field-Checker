package kaptan.exception;

import kaptan.AnnotationType;

import java.util.ArrayList;

public class FieldViolationException extends NullPointerException {

    private ArrayList<AnnotationType> violations;
    public FieldViolationException(String message,ArrayList<AnnotationType> violations)
    {
        super(message);
        violations = new ArrayList<>();
    }

    public ArrayList<AnnotationType> getViolations()
    {
        return violations;
    }

}
