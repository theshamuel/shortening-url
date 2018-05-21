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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theshamuel.shrturl.baseclasses.entity.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * The User entity class.
 *
 * @author Alex Gladkikh
 */
@Document(collection = "users")
public class User extends BaseEntity {


    @Field("login")
    private String login;

    @Field("password")
    private String password;

    @JsonIgnore
    @Field("salt")
    private String salt;

    @Field("role")
    private String role;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param builder the builder
     */
    public User(Builder builder) {

        setId(builder.id);

        setCreatedDate(builder.createdDate);

        setModifyDate(builder.modifyDate);

        setAuthor(builder.author);

        setLogin(builder.login);

        setPassword(builder.password);

        setSalt(builder.salt);

        setRole(builder.role);
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login.trim();
    }

    /**
     * Gets salt.
     *
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets salt.
     *
     * @param salt the salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static final Builder builder(){
        return new Builder();
    }


    /**
     * The type Builder.
     */
    public static class Builder{
        private String id;

        private Date createdDate;

        private Date modifyDate;

        private String author;

        private String login;

        private String password;

        private String salt;

        private String role;

        /**
         * Instantiates a new User builder.
         */
        public Builder() {

        }

        /**
         * Id user builder.
         *
         * @param id the id
         * @return the user builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Created date builder.
         *
         * @param createdDate the created date
         * @return the builder
         */
        public Builder createdDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        /**
         * Modify date builder.
         *
         * @param modifyDate the modify date
         * @return the builder
         */
        public Builder modifyDate(Date modifyDate) {
            this.modifyDate = modifyDate;
            return this;
        }

        /**
         * Author user builder.
         *
         * @param author the author
         * @return the user builder
         */
        public Builder author(String author) {
            this.author = author;
            return this;
        }

        /**
         * Login user builder.
         *
         * @param login the login
         * @return the user builder
         */
        public Builder login(String login) {
            this.login = login;
            return this;
        }


        /**
         * Password user builder.
         *
         * @param password the password
         * @return the user builder
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Salt user builder.
         *
         * @param salt the salt
         * @return the user builder
         */
        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }


        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return new User(this);
        }

    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .append(getId(), user.getId())
                .append(login, user.login)
                .append(password, user.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(login)
                .append(password)
                .toHashCode();
    }
}
