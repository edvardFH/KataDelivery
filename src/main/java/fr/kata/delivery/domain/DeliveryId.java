package fr.kata.delivery.domain;

public record DeliveryId(String value) {
    public DeliveryId {
        if (value == null || value.isBlank()) throw new DomainException("DeliveryId must be non-blank");
    }
}
