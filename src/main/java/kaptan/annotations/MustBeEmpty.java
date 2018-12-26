package kaptan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MustBeEmpty:
 * <br> It defines a rule that says whatever is in this class field should be empty.
 * <br> To illustrate this rule, Let's say we have String, it should be "" with this rule. For ArrayList data structure, it should contains zero element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustBeEmpty {
}
