package dev.amrish.userservice.security.service;
import dev.amrish.userservice.modles.*;
import dev.amrish.userservice.repositories.UserRepository;
import dev.amrish.userservice.security.model.CustomUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }

        User user = optionalUser.get();




        return new CustomUserDetails(user);
    }
}
