package jpashop.jpashop.validator.order.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import jpashop.jpashop.validator.order.annotation.CustomEnum;

public class CustomEnumValidator implements ConstraintValidator<CustomEnum, String> {

    private CustomEnum annotation;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (Enum enumValue : annotation.enumClass().getEnumConstants()) {
            if (enumValue.name().toUpperCase().equals(value.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(CustomEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }
}
