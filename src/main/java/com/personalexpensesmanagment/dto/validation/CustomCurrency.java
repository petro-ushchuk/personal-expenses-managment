package com.personalexpensesmanagment.dto.validation;
import com.personalexpensesmanagment.dto.validation.CustomCurrency.CustomCurrencyValidator;

import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomCurrencyValidator.class)
public @interface CustomCurrency {

    String message() default "Currency isn't valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class CustomCurrencyValidator implements ConstraintValidator<CustomCurrency, String> {

        @Override
        public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
            return currency.matches("[a-zA-Z]{3}");
        }
    }


}
