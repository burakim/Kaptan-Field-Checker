package kaptan.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnforceIntervalConstraint {
    double min() default 0;
    double max() default 10;
}
