package br.com.vitorcarvalho.order_management_api.modules.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.order_management_api.modules.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.order_management_api.modules.user.UserEntity;
import br.com.vitorcarvalho.order_management_api.modules.user.assistant.UserPrincipal;
import br.com.vitorcarvalho.order_management_api.modules.user.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException{
        UserEntity user = this.userRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException()
        );

        return new UserPrincipal(user);
    }
}
