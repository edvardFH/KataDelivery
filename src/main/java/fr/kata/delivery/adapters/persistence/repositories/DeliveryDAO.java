package fr.kata.delivery.adapters.persistence.repositories;

import fr.kata.delivery.adapters.persistence.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDAO extends JpaRepository<DeliveryEntity, String> {
}
