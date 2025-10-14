package fr.kata.delivery.application;

public class UseCaseException extends RuntimeException {
    public UseCaseException(String message) { super(message); }
}