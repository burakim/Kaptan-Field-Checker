package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * EnforceSizeConstraint:
 * <br> It is for setting boundaries for size of element that is the variable.
 * <br> It generates a interval by not including min value and including max value.
 * <br> Mathematical way of explanation: (min,max]
 * <br> The Default value for min argument is 0.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EnforceSizeConstraint {
    int min() default 0; // For size
    int max();

}
