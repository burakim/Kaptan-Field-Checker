package kaptan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * EnforceIntervalConstaint:
 * <br> It is for setting boundaries for target class field about what value can be assigned to it.
 * <br> It generates a interval by not including min value and including max value.
 * <br> You can use it for variables other than double as long as you are passing a double version of your variable.
 * <br> Mathematical way of explanation: (min,max]
 * <br> The Default value for min argument is 0.
 * <br> The Default value for max argument is 10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnforceIntervalConstraint {
    double min() default 0;
    double max() default 10;
}
