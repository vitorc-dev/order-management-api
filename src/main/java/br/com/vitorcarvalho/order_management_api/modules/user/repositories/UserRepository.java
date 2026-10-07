package br.com.vitorcarvalho.order_management_api.modules.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vitorcarvalho.order_management_api.modules.user.UserEntity;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    List<UserEntity> findByNameContainingIgnoreCase(String name);
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByEmail(String email);
}
