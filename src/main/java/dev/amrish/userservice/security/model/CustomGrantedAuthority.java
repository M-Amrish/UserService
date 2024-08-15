package dev.amrish.userservice.security.model;

import dev.amrish.userservice.modles.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private  String authority;

    public CustomGrantedAuthority(Role role){
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
