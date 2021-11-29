package com.fintech.api.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {

  private static final ValidatorFactory VALIDATION_FACTORY =
      Validation.buildDefaultValidatorFactory();

  public static void validate(Object object, Class<?>... groups) {
    Validator validator = VALIDATION_FACTORY.getValidator();
    Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
    if (!violations.isEmpty()) {
      String message =
          violations.stream()
              .map(ConstraintViolation<Object>::getMessage)
              .collect(Collectors.joining(", "));

      throw new ValidationException(message);
    }
  }
}
