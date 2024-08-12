package dev.amrish.userservice.dtos;

import dev.amrish.userservice.modles.Token;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponseDto {
    private Token token;
}
