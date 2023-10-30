package dev.deh.rys.customer;


import dev.deh.rys.entity.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationVerification {
    @Autowired
    Validator validator;
    public <T> List<ValidationResult> validate(T address) {
        return validator.validate(address , Default.class)
                .stream()
                .map(ValidationResult::new)
                .collect(Collectors.toList());
    }


    // I added this to Skip the First Violation
    public <T> List<ValidationResult> validateSkipFirst(T address) {
        List<ConstraintViolation<T>> violations = validator.validate(address, Default.class)
                .stream()
                .collect(Collectors.toList());

        // Skip the first violation if it exists
        if (!violations.isEmpty()) {
            violations.remove(0);
        }

        return violations.stream()
                .map(ValidationResult::new)
                .collect(Collectors.toList());
    }


    public String validationMessage(String errorType) {
        return "{jakarta.validation.constraints."+ errorType +".message}";
    }

    public <T> ValidationResult validateFirst(T address) {
        return validate(address)
                .get(0);
    }


    public static class ValidationResult<T>{

        private final ConstraintViolation<T> violation;

        public  ValidationResult(ConstraintViolation<T> violation) {

            this.violation = violation;
        }

        public String getAttribute() {
            return violation.getPropertyPath().toString();
        }

        public String getErrorType() {
            return violation.getMessageTemplate();
        }
    }
}
