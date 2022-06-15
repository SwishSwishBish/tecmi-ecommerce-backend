package com.sena.tecmiecommercebackend.service;

import com.sena.tecmiecommercebackend.config.AuthenticationMessage;
import com.sena.tecmiecommercebackend.dto.ResponseDto;
import com.sena.tecmiecommercebackend.dto.user.RegisterDto;
import com.sena.tecmiecommercebackend.dto.user.SignInDto;
import com.sena.tecmiecommercebackend.dto.user.SignInResponseDto;
import com.sena.tecmiecommercebackend.exceptions.AuthenticationFailException;
import com.sena.tecmiecommercebackend.exceptions.CustomException;
import com.sena.tecmiecommercebackend.repository.IUserRepository;
import com.sena.tecmiecommercebackend.repository.entity.AuthenticationToken;
import com.sena.tecmiecommercebackend.repository.entity.User;
import com.sena.tecmiecommercebackend.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public ResponseDto register(RegisterDto registerDto) {
        if(Objects.nonNull(userRepository.findByEmail(registerDto.getEmail()))){
            throw new CustomException("User already present.");
        }

        String encryptedPassword = registerDto.getPassword();
        try{
            encryptedPassword = hashPassword(registerDto.getPassword());
        }catch (NoSuchAlgorithmException exception){
            exception.printStackTrace();
            logger.error("Hashing password failed: ", exception.getMessage());
        }

        User user = new User(registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                encryptedPassword );
        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);

        return new ResponseDto("SUCCESS", "Account created successfully.");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (!Helper.notNull(user)) {
            throw new AuthenticationFailException("User is not valid.");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException(AuthenticationMessage.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Hashing password failed: ", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (!Helper.notNull(token)) {
            // token not present
            throw new CustomException("Token not available");
        }

        return new SignInResponseDto("SUCCESS", token.getToken());
    }
}
