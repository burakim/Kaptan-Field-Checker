package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MustContainsFollowingStringValues:
 * <br> It checks a string typed class field if it contains any element from the provided set as an annotation argument.
 * <br> It accepts primitive string values.
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@ValueSearchAnnotation()
public @interface MustContainsFollowingStringValues {
    String[] values();
}
