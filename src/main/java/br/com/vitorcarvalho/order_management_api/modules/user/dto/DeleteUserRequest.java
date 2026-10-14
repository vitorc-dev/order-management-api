package br.com.vitorcarvalho.order_management_api.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUserRequest {
    
    @NotBlank(message = "The password is mandatory to confirm delection.")
    private String password;
}
