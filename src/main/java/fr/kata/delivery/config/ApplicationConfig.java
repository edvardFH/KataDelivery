package fr.kata.delivery.config;

import fr.kata.delivery.application.CustomerDeliveryService;
import fr.kata.delivery.application.DeliveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "fr.kata.delivery")
public class ApplicationConfig {

    @Bean
    public CustomerDeliveryService customerDeliveryService(DeliveryRepository repository) {
        return new CustomerDeliveryService(repository);
    }
}
