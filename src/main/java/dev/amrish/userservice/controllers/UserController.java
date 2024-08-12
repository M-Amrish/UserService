package dev.amrish.userservice.controllers;

import dev.amrish.userservice.dtos.*;
import dev.amrish.userservice.dtos.ResponseStatus;
import dev.amrish.userservice.modles.Token;
import dev.amrish.userservice.modles.User;
import dev.amrish.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = userService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);

        return loginResponseDto;
    }


    @PostMapping("/signup")
    public SignUpResponseDto  sigUp(@RequestBody SignUpRequestDto requestDto){

       User user  = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setUser(user);
        signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return signUpResponseDto;
    }

    @PostMapping("/validate")
    public UserDto  validateToken(@RequestHeader("Authorization")String token){
        User user = userService.validateToken(token);

        return UserDto.fromUser(user);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());
        return  new ResponseEntity<>(HttpStatus.OK);


    }
}
