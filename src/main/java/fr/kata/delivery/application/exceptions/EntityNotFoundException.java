package fr.kata.delivery.application.exceptions;

public class EntityNotFoundException extends UseCaseException {
    public EntityNotFoundException(String message) { super(message); }
}
