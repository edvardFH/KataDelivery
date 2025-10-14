package fr.kata.delivery.application;

import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;

import java.util.Optional;

public interface DeliveryRepository {
    Optional<Delivery> findById(DeliveryId id);
    Delivery save(Delivery delivery);
}