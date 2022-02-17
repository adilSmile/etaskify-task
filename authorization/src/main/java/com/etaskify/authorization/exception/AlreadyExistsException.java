package com.etaskify.authorization.exception;

public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(String message) {
    super(message);
  }
}
