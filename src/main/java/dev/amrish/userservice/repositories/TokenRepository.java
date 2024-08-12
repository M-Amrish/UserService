package dev.amrish.userservice.repositories;

import dev.amrish.userservice.modles.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Override
   Token save(Token token);

   //Optional<Token> findByValueAndDeleteAndExpiryAtGreaterThan(String tokenValue, Boolean deleted,Date date);


    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(
            String tokenValue,
            Boolean deleted,
            Date date
    );

//    Optional<Token> findByValueAndDelete(String tokenValue, Boolean deleted);
    Optional<Token> findByValueAndDeleted(String value, Boolean deleted);



}
