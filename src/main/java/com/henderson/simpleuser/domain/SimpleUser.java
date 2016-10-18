package com.henderson.simpleuser.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * "User" is a reserved word in SQL, so for Hibernate to work SimpleUser is our domain model. This class will
 * automatically be created as a table in the database, the columns relate directly to the class variables.
 *
 * Spring also handles serialization of this class into / out of JSON
 */
@Entity
@Table(name = "users")
public class SimpleUser {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    @ManyToOne
    private Country country;


    public SimpleUser() {
        super();
    }

    public SimpleUser(Long id, String first_name, String last_name, String nickname, String password, String email,
                      Country country) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.country = country;
    }

    public SimpleUser(Long id, String first_name, String last_name, String nickname, String password, String email,
                      String country) {
        this(id, first_name, last_name, nickname, password, email, new Country(country));
    }

    public SimpleUser(String first_name, String last_name, String nickname, String password, String email,
                      String country) {
        this(-1L, first_name, last_name, nickname, password, email, country);
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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
