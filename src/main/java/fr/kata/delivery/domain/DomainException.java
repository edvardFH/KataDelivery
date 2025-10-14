package fr.kata.delivery.domain;

public class DomainException extends RuntimeException {
    public DomainException(String message) { super(message); }
}