/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.controllers;

import com.theshamuel.shrturl.exceptions.NotFoundParamsException;
import com.theshamuel.shrturl.user.dao.UserRepository;
import com.theshamuel.shrturl.user.entity.User;
import com.theshamuel.shrturl.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * The Auth controller class.
 *
 * @author Alex Gladkikh
 */
@RestController
public class AuthController {
    private static Logger logger =  LoggerFactory.getLogger(UserController.class);


    private UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authorizing method.
     *
     * @param loginForm the login form
     * @return the response entity
     * @throws NotFoundParamsException
     */
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity auth(@RequestBody final LoginForm loginForm)
            throws NotFoundParamsException {

        User user = userRepository.findByLogin(loginForm.login);

        if (loginForm.login!= null && user!=null) {

                if (user.getPassword().equals(Utils.pwd2sha256(loginForm.password, user.getSalt()))) {
                    //Expired token time is 20m
                    LoginResponse result = new LoginResponse(Jwts.builder().setSubject(loginForm.login)
                            .claim("role", user.getRole()).setIssuedAt(new Date())
                            .signWith(SignatureAlgorithm.HS256, "SuperKey182").setExpiration(new Date(System.currentTimeMillis() + 12000000)).compact(),user.getLogin());
                    return new ResponseEntity(result, HttpStatus.OK);
                } else {
                    throw new NotFoundParamsException("Wrong password");
                }

        }else
            throw new NotFoundParamsException("There is no user with this login");

    }

    public static class LoginForm {

        public String login;

        public String password;
    }


    public static class LoginResponse {

        public String name;

        public String token;


        /**
         * Instantiates a new Login response.
         *
         * @param token the token
         * @param name the user name
         */
        public LoginResponse(final String token, final String name ) {
            this.token = token;
            this.name = name;
        }

    }
}
