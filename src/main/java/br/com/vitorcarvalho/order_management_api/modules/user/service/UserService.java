package br.com.vitorcarvalho.order_management_api.modules.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.order_management_api.config.PasswordConfig;
import br.com.vitorcarvalho.order_management_api.modules.exceptions.EmailAlreadyExistsException;
import br.com.vitorcarvalho.order_management_api.modules.exceptions.InvalidCredentialException;
import br.com.vitorcarvalho.order_management_api.modules.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.order_management_api.modules.user.UserEntity;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.CreateUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.DeleteUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.PatchUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.UserResponse;
import br.com.vitorcarvalho.order_management_api.modules.user.mappers.UserMapper;
import br.com.vitorcarvalho.order_management_api.modules.user.repositories.UserRepository;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(CreateUserRequest dto) {
        if (this.userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        UserEntity user = this.userMapper.toEntity(dto);
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        UserEntity savedUser = this.userRepository.save(user);
        return this.userMapper.toResponse(savedUser);
    }

    public List<UserResponse> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::toResponse)
                .toList();
    }

    public List<UserResponse> findByFilter(String name, Boolean active) {
        List<UserEntity> users;

        if (name != null) {
            users = this.userRepository.findByNameContainingIgnoreCase(name);
        } else {
            users = this.userRepository.findAll();
        }

        if (active != null) {
            users = users.stream().filter(user -> user.isActive() == active).toList();
        }

        return users.stream().map(this.userMapper::toResponse).toList();
    }

    public UserResponse patch(UUID id, PatchUserRequest dto) {
        UserEntity user = this.findActiveUserOrThrow(id);
        this.userMapper.patchUserFromDTO(dto, user);

        if (dto.getPassword() != null) {
            user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }

        UserEntity patchedUser = this.userRepository.save(user);
        return this.userMapper.toResponse(patchedUser);
    }

    public void delete(UUID id, DeleteUserRequest dto) {
        UserEntity user = this.userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException());

        if (!this.passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException();
        }

        user.setActive(false);
        this.userRepository.save(user);
    }

    private UserEntity findActiveUserOrThrow(UUID id) {
        return this.userRepository.findById(id)
                .filter(user -> user.isActive())
                .orElseThrow(
                        () -> new UserNotFoundException());
    }
}
