package br.com.vitorcarvalho.order_management_api.modules.user.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    
    @NotBlank(message = "The name is mandatory.")
    private String name;

    @NotBlank(message = "The e-mail is mandatory.")
    @Email(message = "The e-mail must be valid.")
    private String email;

    @NotBlank(message = "The password is mandatory.")
    @Length(min = 10, max = 100, message = "The password's length must to be between 10 and 100 characters.")
    private String password;

}
