package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * MustBeNonNull:
 * <br> It defines a rule that says whatever is in this class field should be null.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MustBeNull {
}
