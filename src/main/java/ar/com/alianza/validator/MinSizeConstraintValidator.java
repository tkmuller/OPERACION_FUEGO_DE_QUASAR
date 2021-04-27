package ar.com.alianza.validator;

import ar.com.alianza.contract.request.Satellites;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinSizeConstraintValidator implements ConstraintValidator<MinSizeConstraint, List<Satellites>> {
    @Override
    public boolean isValid(List<Satellites> satellitesList, ConstraintValidatorContext constraintValidatorContext) {
        return satellitesList.size() >= 3;
    }
}
