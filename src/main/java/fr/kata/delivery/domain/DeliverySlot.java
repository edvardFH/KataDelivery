package fr.kata.delivery.domain;

import java.time.LocalDateTime;

public record DeliverySlot(LocalDateTime start, LocalDateTime end) {
    public DeliverySlot {
        if (start == null || end == null) throw new DomainException("DeliverySlot start/end must be provided");
        if (!end.isAfter(start)) throw new DomainException("DeliverySlot end must be after start");
    }

    public boolean contains(LocalDateTime t) {
        return (t.equals(start) || t.isAfter(start)) && t.isBefore(end);
    }
}
