//package dev.amrish.userservice.services;
//
//import dev.amrish.userservice.modles.Token;
//import dev.amrish.userservice.modles.User;
//import dev.amrish.userservice.repositories.TokenRepository;
//import dev.amrish.userservice.repositories.UserRepository;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Optional;
//
//public class UserSerivceImpleCmd {
//
//
//    package dev.amrish.userservice.services;
//
//import dev.amrish.userservice.modles.Token;
//import dev.amrish.userservice.modles.User;
//import dev.amrish.userservice.repositories.TokenRepository;
//import dev.amrish.userservice.repositories.UserRepository;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Optional;
//
//    @Service
//    public class UserServiceImpl implements  UserService{
//
//        private UserRepository userRepository;
//
//        private TokenRepository tokenRepository;
//
//        private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//
//
//        UserServiceImpl(
//                UserRepository userRepository,
//                TokenRepository tokenRepository,
//                BCryptPasswordEncoder bCryptPasswordEncoder
//
//        ){
//
//            this.userRepository = userRepository;
//            this.tokenRepository = tokenRepository;
//            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//
//        }
//
//        @Override
//        public Token login(String email, String password) {
//
//            Optional<User> userOptional = userRepository.findByEmail(email);
//
//            if(userOptional.isEmpty()){
//                //throw exception or redirect to sigup page
//
//                return null;
//            }
//
//            User user = userOptional.get();
//            if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
//                //throw exception;
//
//                return null;
//            }
//
//            Token token = createToken(user);
//
//            return tokenRepository.save(token);
//        }
//
//        @Override
//        public User signUp(String name, String email, String password) {
//
//            Optional<User> userOptional  = userRepository.findByEmail(email);
//
//            if(userOptional.isPresent()){
//                //Throw exception or redirect user login
//
//                return null;
//            }
//
//            User user = new User();
//            user.setName(name);
//            user.setEmail(email);
//            user.setHashedPassword(bCryptPasswordEncoder.encode(password));
//
//            userRepository.save(user);
//
//
//            return user;
//        }
//
//        @Override
//        public User validateToken(String token) {
//            Optional<Token> optionalToken = tokenRepository.findByValueAndDeleteAndExpiryAtGreaterThan(token, false, new Date());
//
//            if(optionalToken.isEmpty()){
//                // throw an exception
//            }
//            return optionalToken.get().getUser();
//        }
//
//        @Override
//        public void logout(String token) {
//
//        }
//
//
//        public Token createToken(User user){
//
//            Token token = new Token();
//            token.setUser(user);
//            token.setValue(RandomStringUtils.randomAlphanumeric(128)); // Read about UUIDs
//
//            Date currentDate = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(currentDate);
//            calendar.add(Calendar.DAY_OF_YEAR, 30);
//            Date date30DaysFromToday = calendar.getTime();
//
//            token.setExpiryAt(date30DaysFromToday);
//            token.setDeleted(false);
//
//            return token;
//
//        }
//    }
//
//}
