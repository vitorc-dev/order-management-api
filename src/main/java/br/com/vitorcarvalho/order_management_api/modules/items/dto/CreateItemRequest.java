package br.com.vitorcarvalho.order_management_api.modules.items.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateItemRequest {
    @NotBlank(message = "The name is mandatory.")
    private String name;

    @NotBlank(message = "The category is mandatory.")
    private String category;

    @NotNull
    @DecimalMin(value = "0.01", message = "The price has to be greater than one.")
    private BigDecimal price;

    @NotNull
    @Positive(message = "The quantity has to be at least one.")
    private Integer quantity;
}
