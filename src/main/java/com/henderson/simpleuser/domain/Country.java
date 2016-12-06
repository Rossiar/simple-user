package com.henderson.simpleuser.domain;

import org.springframework.data.annotation.Id;

/**
 * Represents the countries data type, see {@link SimpleUser} for more
 */
public class Country {

    @Id
    private Long id;

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
