package kaptan.exception;


import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class FieldViolationException extends NullPointerException {

    private ArrayList<Class<? extends Annotation>> violations;
    public FieldViolationException(String message,ArrayList<Class<? extends Annotation>> violations)
    {
        super(message);
        violations = new ArrayList<>();
    }

    public ArrayList<Class<? extends Annotation>> getViolations()
    {
        return violations;
    }

}
