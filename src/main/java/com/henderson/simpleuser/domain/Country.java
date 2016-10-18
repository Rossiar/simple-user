package com.henderson.simpleuser.domain;

import javax.persistence.*;

/**
 * Represents the countries data type, see {@link SimpleUser} for more
 */
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    public Country() {
        super();
    }

    public Country(String name) {
        this(0L, name);
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
