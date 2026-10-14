package br.com.vitorcarvalho.order_management_api.modules.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PatchUserRequest {
    private String name;

    @Email(message = "The e-mail must be valid.")
    private String email;

    @Length(min = 10, max = 100, message = "The password's length must to be between 10 and 100 characters.")
    private String password;
}
