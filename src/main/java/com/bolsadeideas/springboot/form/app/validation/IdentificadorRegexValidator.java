package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentificadorRegexValidator implements ConstraintValidator<IdentificadorRegex, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.matches("[\\d]{2}[.][\\d]{3}[.][\\d]{3}-[A-Z]")) {
            return true;
        }
        return false;
    }
}
