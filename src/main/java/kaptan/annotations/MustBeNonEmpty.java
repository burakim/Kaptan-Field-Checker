package kaptan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MustBeNonEmpty:
 * <br> It defines a rule that says whatever is in this class field should not be empty.
 * <br> To illustrate this rule, Let's say we have String, it should not be "" with this rule. For ArrayList data structure, it should contains at least one element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustBeNonEmpty {
}
