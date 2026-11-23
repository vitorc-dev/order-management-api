package br.com.vitorcarvalho.order_management_api;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vitorcarvalho.order_management_api.modules.jwt.JWTService;
import br.com.vitorcarvalho.order_management_api.modules.user.UserEntity;
import br.com.vitorcarvalho.order_management_api.modules.user.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerSecurityTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ITEM_BODY = 
                """
                    {
                        "name" : "Hobbit",
                        "category" : "Livros",
                        "price" : 50.00,
                        "quantity" : 10
                    }
                 """;

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
    void shouldReturn200WhenListingWithoutToken() throws Exception{
        mockMvc.perform(get("/items")).andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenCreatingItemWithoutToken() throws Exception{
        mockMvc.perform(post("/items/").contentType(MediaType.APPLICATION_JSON)
                .content(ITEM_BODY)).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403WhenUserCreatesItem() throws Exception{
        String token = jwtService.generateToken("user@email.com");

        mockMvc.perform(post("/items/").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(ITEM_BODY)).andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn201WhenAdminCreatesItem() throws Exception{
        String token = jwtService.generateToken("admin@email.com");

        mockMvc.perform(post("/items/").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(ITEM_BODY)).andExpect(status().isCreated());
    }
}
