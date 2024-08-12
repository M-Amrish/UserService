package dev.amrish.userservice.modles;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class User extends  BaseModel {
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany
    private List<Role> roles;
}
