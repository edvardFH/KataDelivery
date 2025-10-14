package fr.kata.delivery.adapters.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.kata.delivery.domain.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressDTO(
        @NotBlank String line1,
        String line2,
        @NotBlank String postalCode,
        @NotBlank String city,
        @NotBlank String countryCode
) {
    public Address toDomain() {
        return new Address(line1, line2, postalCode, city, countryCode);
    }
}
