package br.com.vitorcarvalho.order_management_api.modules.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.type.descriptor.java.LocalDateJavaType;

import lombok.Data;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
