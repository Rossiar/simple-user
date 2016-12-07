package com.henderson.simpleuser.repository;

import com.henderson.simpleuser.domain.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Provides CRUD database access for Country objects, see {@link SimpleUserRepository} for more.
 */
public interface CountryRepository extends MongoRepository<Country, Long> {

    @Override
    Country save(Country country);

    @Override
    Country findOne(Long aLong);
}
