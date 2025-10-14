package fr.kata.delivery.application;

import fr.kata.delivery.domain.Address;
import fr.kata.delivery.domain.CustomerId;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;
import fr.kata.delivery.domain.DeliverySlot;

import java.util.Objects;

public class CustomerDeliveryService {

    private final DeliveryRepository repository;


    public CustomerDeliveryService(DeliveryRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }


    public Delivery viewDelivery(DeliveryId deliveryId, CustomerId customerId) {
        return getCustomerDelivery(deliveryId, customerId);
    }


    public Delivery updateDeliveryDetails(DeliveryId deliveryId, CustomerId customerId, Address newAddress, DeliverySlot newSlot) {
        Delivery current = getCustomerDelivery(deliveryId, customerId);
        Delivery updated = current.updateDetails(newAddress, newSlot);
        repository.save(updated);
        return updated;
    }


    private Delivery getCustomerDelivery(DeliveryId deliveryId, CustomerId customerId) {
        Delivery delivery = repository.findById(deliveryId)
                                      .orElseThrow(() -> new UseCaseException("Delivery not found: " + deliveryId.value()));
        ensureOwnership(delivery, customerId);

        return delivery;
    }


    private static void ensureOwnership(Delivery d, CustomerId customerId) {
        if (!d.customerId().equals(customerId)) {
            throw new UseCaseException("Delivery does not belong to customer: " + customerId.value());
        }
    }
}