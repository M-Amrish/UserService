package dev.amrish.userservice.security.model;

import dev.amrish.userservice.modles.Role;
import dev.amrish.userservice.modles.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean acountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.password = user.getHashedPassword();
        this.username = user.getEmail();
        this.accountNonExpired = true;
        this.acountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;

        authorities = new ArrayList<>();
        for(Role role : user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return acountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
