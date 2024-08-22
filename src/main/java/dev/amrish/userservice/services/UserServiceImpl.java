package dev.amrish.userservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.amrish.userservice.dtos.SendEmailDto;
import dev.amrish.userservice.modles.Token;
import dev.amrish.userservice.modles.User;

import dev.amrish.userservice.repositories.TokenRepository;
import dev.amrish.userservice.repositories.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;


    UserServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            KafkaTemplate kafkaTemplate
    ){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
        objectMapper = new ObjectMapper();

    }

    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            // throew exception or redirect to Sigup
            return null;
        }

        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            // throw exception
        }
        Token token = createToken(user);
        tokenRepository.save(token);

       return token;

    }

    @Override
    public User signUp(String name, String email, String password) {
           Optional<User>  optionalUser = userRepository.findByEmail(email);

           if(optionalUser.isPresent()){
               // throw exception or redirect to login page

               return null;
           }

           User user = new User();
           user.setName(name);
           user.setEmail(email);
           user.setHashedPassword(bCryptPasswordEncoder.encode(password));

           userRepository.save(user);

            SendEmailDto sendEmailDto = new SendEmailDto();
            sendEmailDto.setFrom("test@gmail.com");
            sendEmailDto.setTo("amrish@gmail.com");
            sendEmailDto.setSubject("welcome");
            sendEmailDto.setBody("welcome welcome welcome......");

            String sendEmaildto = null;

            try{
                sendEmaildto = objectMapper.writeValueAsString(sendEmaildto);
            }catch (Exception exception){
                System.out.println("Something went wrong");
            }

            kafkaTemplate.send("sendEmail", sendEmaildto);


           return user;

    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());
        if(tokenOptional.isEmpty()){
            // throe exception
        }

        return  tokenOptional.get().getUser();

    }

    @Override
    public void logout(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndDeleted(tokenValue, true);
        if(tokenOptional.isEmpty()){
            //throw exception
        }

        Token token = tokenOptional.get();
        token.setDeleted(true);
        tokenRepository.save(token);

    }


    public Token createToken(User user){

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphabetic(128));

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date date30DaysFromToday = calendar.getTime();

        token.setExpiryAt(date30DaysFromToday);
        token.setDeleted(false);

        return token;

    }
}
