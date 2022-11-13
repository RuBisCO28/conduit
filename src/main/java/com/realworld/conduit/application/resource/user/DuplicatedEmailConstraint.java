package com.realworld.conduit.application.resource.user;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DuplicatedEmailValidator.class)
@Retention(RUNTIME)
public @interface DuplicatedEmailConstraint {
  String message() default "duplicated email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
