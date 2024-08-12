package dev.amrish.userservice.services;

import dev.amrish.userservice.modles.Token;
import dev.amrish.userservice.modles.User;

public interface UserService {
    public Token login(String email, String password);
    public User signUp(String name, String email, String password);
    public User validateToken(String token);
    public void logout(String token);
}
