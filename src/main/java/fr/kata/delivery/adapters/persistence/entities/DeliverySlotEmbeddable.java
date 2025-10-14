package fr.kata.delivery.adapters.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class DeliverySlotEmbeddable {
    @Column(name = "start", nullable = false)
    private LocalDateTime start;
    @Column(name = "end", nullable = false)
    private LocalDateTime end;


    protected DeliverySlotEmbeddable() { }


    public DeliverySlotEmbeddable(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }


    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
}
