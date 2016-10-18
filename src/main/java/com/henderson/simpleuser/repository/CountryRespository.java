package com.henderson.simpleuser.repository;

import com.henderson.simpleuser.domain.Country;
import org.springframework.data.repository.CrudRepository;

/**
 * Provides CRUD database access for Country objects, see {@link SimpleUserRepository} for more.
 */
public interface CountryRespository extends CrudRepository<Country, Long> {

    @Override
    Country save(Country country);

    @Override
    Country findOne(Long aLong);
}
