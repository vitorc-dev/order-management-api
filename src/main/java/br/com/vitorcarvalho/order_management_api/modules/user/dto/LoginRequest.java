package br.com.vitorcarvalho.order_management_api.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "The email is mandatory.")
    private String email;

    @NotBlank(message = "The password is mandatory.")
    private String password;
}
