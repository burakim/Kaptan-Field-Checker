package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnforceIntervalConstraint {
    long min() default 0;
    long max() default 10;



}
