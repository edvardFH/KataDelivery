package fr.kata.delivery.application.exceptions;

public class BusinessRuleViolationException extends UseCaseException {
  public BusinessRuleViolationException(String message) {
    super(message);
  }
  public BusinessRuleViolationException(String message, Throwable cause) {
    super(message, cause);
  }
}
