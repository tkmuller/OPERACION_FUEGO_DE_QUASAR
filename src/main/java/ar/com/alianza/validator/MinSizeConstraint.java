package ar.com.alianza.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSizeConstraint {
    String message() default "Satellites must contain at least 3 satellite.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
