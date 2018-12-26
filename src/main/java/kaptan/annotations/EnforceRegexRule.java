package kaptan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnforceRegexRule:
 * <br> It is for creating a set of rule about what values can be assigned to the class field.
 * <br> It enforce a regular expression to check if assigned value is valid.
 * <br> It accepts a string version of regular expression.
 * <br> It supports only String class variables.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnforceRegexRule {
    String value();
}
