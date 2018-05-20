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


import com.theshamuel.shrturl.exceptions.DuplicateRecordException;
import com.theshamuel.shrturl.exceptions.NotFoundEntityException;
import com.theshamuel.shrturl.user.dao.UserRepository;
import com.theshamuel.shrturl.user.entity.User;
import com.theshamuel.shrturl.utils.Roles;
import com.theshamuel.shrturl.utils.Utils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * The User's controller class.
 *
 * @author Alex Gladkikh
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    private static Logger logger =  LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    /**
     * Instantiates a new User controller.
     *
     * @param userRepository for all operations with users
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets claims.
     *
     * @param request the servlet request
     * @return the claims
     */
    @ModelAttribute("claims")
    public Claims getClaims(HttpServletRequest request) {
        return (Claims) request.getAttribute("claims");
    }


    /**
     * Gets users order by login.
     *
     * @param claims the claims
     * @param sort   the sort
     * @return the users order by login
     * @throws ServletException the servlet exceptions
     */
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsersOrderByLogin(@ModelAttribute("claims") Claims claims,
                                                           @RequestParam(value="sort",
            defaultValue="ASC") String sort) throws ServletException {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sort.toUpperCase().equals("DESC"))
            sortDirection = Sort.Direction.DESC;
        if (claims.get("roles",String.class)!=null &&
                claims.get("roles",String.class).toLowerCase().equals("admin")) {
            List<User> result = userRepository.findAll(new Sort(new Sort.Order(sortDirection, "login")));
            return new ResponseEntity(result, HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.FORBIDDEN);


    }


    /**
     * Find user by id.
     *
     * @param id the user's id
     * @return the response entity included user
     * @throws ServletException the servlet exceptions
     */
    @GetMapping (value = "/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") String id) throws ServletException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NotFoundEntityException("User with id ["+id+"] has not found");
        }
        return new ResponseEntity(user, HttpStatus.OK);

    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the response entity included saved user
     * @throws ServletException the servlet exceptions
     */
    @PostMapping (value = "/users" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody User user) throws ServletException {
        if (userRepository.findByLogin(user.getLogin())!=null)
            throw new DuplicateRecordException("There is user with the same login in system.");
        else{
            user.setCreatedDate(new Date());
            user.setSalt(Utils.genSalt());
            if(user.getRole()!=null && user.getRole().equals("admin"))
                user.setRole(Roles.ADMIN.toString());
            else
                user.setRole(Roles.USER.toString());
            user.setPassword(Utils.pwd2sha256(user.getPassword(),user.getSalt()));
            return new ResponseEntity(userRepository.save(user), HttpStatus.CREATED);
        }
    }


    /**
     * Delete user.
     *
     * @param claims the claims
     * @param id     the user's id
     * @return the response entity with status of operation
     * @throws ServletException the servlet exceptions
     */
    @DeleteMapping (value = "/users/{id}")
    public ResponseEntity<User> deleteUser(@ModelAttribute("claims") Claims claims,
                                           @PathVariable(value = "id") String id) throws ServletException {
        if (claims.get("roles",String.class)!=null &&
                claims.get("roles",String.class).toLowerCase().equals("admin")) {
            User user = userRepository.findOne(id);
            if (user == null)
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            userRepository.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }
}
