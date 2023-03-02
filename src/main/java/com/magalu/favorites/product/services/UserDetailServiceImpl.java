package com.magalu.favorites.product.services;

import com.magalu.favorites.product.data.UserDataDetails;
import com.magalu.favorites.product.model.User;
import com.magalu.favorites.product.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
        Optional<User> user = userRepository.findByLogin(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User [" + username + "] not found");
        }

        return new UserDataDetails(user);
    }

}
