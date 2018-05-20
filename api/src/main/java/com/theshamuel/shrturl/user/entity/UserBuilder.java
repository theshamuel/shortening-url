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
package com.theshamuel.shrturl.user.entity;

/**
 * The User entity builder.
 */
public final class UserBuilder {

    private User user;

    /**
     * Instantiates a new User builder.
     */
    public UserBuilder() {
        user = new User();
    }

    /**
     * Id user builder.
     *
     * @param id the id
     * @return the user builder
     */
    public UserBuilder id(String id) {
        user.setId(id);
        return this;
    }

    /**
     * Login user builder.
     *
     * @param login the login
     * @return the user builder
     */
    public UserBuilder login(String login) {
        user.setLogin(login);
        return this;
    }

    /**
     * Author user builder.
     *
     * @param author the author
     * @return the user builder
     */
    public UserBuilder author(String author) {
        user.setAuthor(author);
        return this;
    }


    /**
     * Password user builder.
     *
     * @param password the password
     * @return the user builder
     */
    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    /**
     * Salt user builder.
     *
     * @param salt the salt
     * @return the user builder
     */
    public UserBuilder salt(String salt) {
        user.setSalt(salt);
        return this;
    }


    /**
     * Build user.
     *
     * @return the user
     */
    public User build() {
        return user;
    }
}
