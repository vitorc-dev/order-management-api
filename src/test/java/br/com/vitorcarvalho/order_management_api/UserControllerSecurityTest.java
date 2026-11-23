package br.com.vitorcarvalho.order_management_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vitorcarvalho.order_management_api.modules.jwt.JWTService;
import br.com.vitorcarvalho.order_management_api.modules.user.UserEntity;
import br.com.vitorcarvalho.order_management_api.modules.user.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSecurityTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        UserEntity admin = new UserEntity();
        admin.setName("admin");
        admin.setEmail("admin@email.com");
        admin.setPassword(passwordEncoder.encode("0123456789"));
        admin.setRole(UserEntity.Role.ADMIN);

        UserEntity user = new UserEntity();
        user.setName("user");
        user.setEmail("user@email.com");
        user.setPassword(passwordEncoder.encode("0123456789"));
        user.setRole(UserEntity.Role.USER);

        userRepository.save(admin);
        userRepository.save(user);
    }

    @Test
    void shouldReturn401WhenNoToken() throws Exception{
        mockMvc.perform(get("/users")).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn200WhenAdminHasValidToken() throws Exception{
        String token = jwtService.generateToken("admin@email.com");

        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403WhenUserHasValidToken() throws Exception{
        String token = jwtService.generateToken("user@email.com");

        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn401WhenTokenIsTampered() throws Exception{
        String token = jwtService.generateToken("admin@email.com");
        
        int meio = token.length() / 2;
        char original = token.charAt(meio);
        char trocado = (original == 'a') ? 'b' : 'a';

        String tampered = token.substring(0, meio) + trocado + 
                            token.substring(meio + 1);

        mockMvc.perform(get("/users").header("Authorization","Bearer " + tampered))
                .andExpect(status().isUnauthorized());
    }
}
