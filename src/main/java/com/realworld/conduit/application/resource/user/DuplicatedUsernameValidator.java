package com.realworld.conduit.application.resource.user;

import com.realworld.conduit.domain.repository.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DuplicatedUsernameValidator implements
  ConstraintValidator<DuplicatedUsernameConstraint, String> {

  @Autowired
  private UserRepository userRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return (value == null || value.isEmpty()) || userRepository.findByName(value) == null;
  }
}
