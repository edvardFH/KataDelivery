package fr.kata.delivery.application.exceptions;

public class OperationNotAllowedException extends UseCaseException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
