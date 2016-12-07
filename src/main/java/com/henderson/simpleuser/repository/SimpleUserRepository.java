package com.henderson.simpleuser.repository;

import com.henderson.simpleuser.domain.SimpleUser;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Spring allows us to {@link @Autowired} this into any class that needs it and never actually instantiate it ourselves.
 * The methods shown here are all provided by {@link SimpleJpaRepository}.
 */
public interface SimpleUserRepository extends CrudRepository<SimpleUser, Long> {

    @Override
    List<SimpleUser> findAll();

    @Override
    SimpleUser findOne(Long id);

    @Override
    SimpleUser save(SimpleUser user);

    @Override
    void delete(Long id);

    @Override
    boolean exists(Long id);

}
