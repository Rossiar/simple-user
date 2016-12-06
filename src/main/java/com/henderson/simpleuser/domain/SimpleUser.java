package com.henderson.simpleuser.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * "User" is a reserved word in SQL, so for Hibernate to work SimpleUser is our domain model. This class will
 * automatically be created as a table in the database, the columns relate directly to the class variables.
 *
 * Spring also handles serialization of this class into / out of JSON
 */
public class SimpleUser {

    @Id
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private Country country;


    public SimpleUser() {
        super();
    }

    public SimpleUser(Long id, String firstName, String lastName, String nickname, String password, String email,
                      Country country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.country = country;
    }

    public SimpleUser(Long id, String firstName, String lastName, String nickname, String password, String email,
                      String country) {
        this(id, firstName, lastName, nickname, password, email, new Country(country));
    }

    public SimpleUser(String firstName, String lastName, String nickname, String password, String email,
                      String country) {
        this(-1L, firstName, lastName, nickname, password, email, country);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Country getCountry() {
        return country;
    }
}
