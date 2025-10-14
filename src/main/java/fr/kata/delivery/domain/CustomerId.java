package fr.kata.delivery.domain;

public record CustomerId(String value) {
    public CustomerId {
        if (value == null || value.isBlank()) throw new DomainException("CustomerId must be non-blank");
    }
}
