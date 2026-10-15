package br.com.vitorcarvalho.order_management_api.modules.user.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitorcarvalho.order_management_api.modules.user.dto.CreateUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.DeleteUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.PatchUserRequest;
import br.com.vitorcarvalho.order_management_api.modules.user.dto.UserResponse;
import br.com.vitorcarvalho.order_management_api.modules.user.service.UserService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping("/")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest dto){ 
        UserResponse user = this.userService.create(dto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(user); 
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> list(@RequestParam(required = false) String name, @RequestParam(required = false) Boolean active) {
        List<UserResponse> users = userService.findByFilter(name, active);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> patch(@PathVariable UUID id, @Valid @RequestBody PatchUserRequest dto){
        UserResponse patchedUser = this.userService.patch(id, dto);
        return ResponseEntity.ok(patchedUser);
    }

    @DeleteMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @Valid @RequestBody DeleteUserRequest dto){
        this.userService.delete(id, dto);
        return ResponseEntity.noContent().build();
    }
}
