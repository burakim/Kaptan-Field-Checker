package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * MustContainsFollowingDoubleValues:
 * <br> It checks a double typed class field if it contains any element from the provided set as an annotation argument.
 * <br> It accepts primitive double values.
 * @since 1.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@ValueSearchAnnotation()
public @interface MustContainsFollowingDoubleValues {
    double[] values();

}
