package com.magalu.favorites.product.data;

import com.magalu.favorites.product.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDataDetails  implements UserDetails {
    private final Optional<User> optUser;

    public UserDataDetails(Optional<User> user) {
        this.optUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return new ArrayList<>();}

    @Override
    public String getPassword()  {
        return optUser.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername()  {
        return optUser.orElse(new User()).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
