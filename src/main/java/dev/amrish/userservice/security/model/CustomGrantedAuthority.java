package dev.amrish.userservice.security.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.amrish.userservice.modles.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@NoArgsConstructor
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
