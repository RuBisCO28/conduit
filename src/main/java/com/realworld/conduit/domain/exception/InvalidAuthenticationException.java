package com.realworld.conduit.domain.exception;

public class InvalidAuthenticationException extends RuntimeException {
  public InvalidAuthenticationException() {
    super("invalid email or password");
  }
}
