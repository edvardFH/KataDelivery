package fr.kata.delivery.application;

import fr.kata.delivery.application.exceptions.BusinessRuleViolationException;
import fr.kata.delivery.application.exceptions.EntityNotFoundException;
import fr.kata.delivery.application.exceptions.OperationNotAllowedException;
import fr.kata.delivery.domain.Address;
import fr.kata.delivery.domain.CustomerId;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;
import fr.kata.delivery.domain.DeliverySlot;
import fr.kata.delivery.domain.DomainException;

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

        try {
            Delivery updated = current.updateDetails(newAddress, newSlot);
            repository.save(updated);
            return updated;
        } catch (DomainException exception) {
            throw new BusinessRuleViolationException("Could not update delivery details", exception);
        }
    }


    private Delivery getCustomerDelivery(DeliveryId deliveryId, CustomerId customerId) {
        Delivery delivery = repository.findById(deliveryId)
                                      .orElseThrow(() -> new EntityNotFoundException("Delivery not found: " + deliveryId.value()));
        ensureOwnership(delivery, customerId);

        return delivery;
    }


    private static void ensureOwnership(Delivery d, CustomerId customerId) {
        if (!d.customerId().equals(customerId)) {
            throw new OperationNotAllowedException("Delivery does not belong to customer: " + customerId.value());
        }
    }
}