package fr.kata.delivery.adapters.persistence.repositories;

import fr.kata.delivery.adapters.persistence.entities.DeliveryEntity;
import fr.kata.delivery.adapters.persistence.mappers.DeliveryJpaMapper;
import fr.kata.delivery.application.DeliveryRepository;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaDeliveryRepository implements DeliveryRepository {

    private final DeliveryDAO deliveryDAO;


    public JpaDeliveryRepository(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }


    @Override
    public Optional<Delivery> findById(DeliveryId id) {
        return deliveryDAO.findById(id.value()).map(DeliveryJpaMapper::toDomain);
    }


    @Override
    public Delivery save(Delivery delivery) {
        DeliveryEntity entity = DeliveryJpaMapper.toEntity(delivery);
        DeliveryEntity persisted = deliveryDAO.save(entity);
        return DeliveryJpaMapper.toDomain(persisted);
    }
}
