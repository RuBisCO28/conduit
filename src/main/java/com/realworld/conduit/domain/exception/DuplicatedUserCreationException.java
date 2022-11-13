package com.realworld.conduit.domain.exception;

public class DuplicatedUserCreationException extends RuntimeException {

  public DuplicatedUserCreationException() {
    super("duplicated user creation error");
  }
}
