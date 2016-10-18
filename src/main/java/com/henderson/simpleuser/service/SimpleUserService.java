package com.henderson.simpleuser.service;

import com.henderson.simpleuser.controller.SimpleUserController;
import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.exceptions.AlreadyExistsException;
import com.henderson.simpleuser.exceptions.NotFoundException;
import com.henderson.simpleuser.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides a service layer to the database to loosely couple the endpoint code from the database code, this allows us
 * to switch out the back-end easily by implementing a different type of repository for the service to utilise,
 * {@link SimpleUserController} has no visibility of this and does not need to change.
 */
@Service
public class SimpleUserService {
    /**
     * Provides database CRUD functionality
     */
    private final SimpleUserRepository repository;

    @Autowired
    public SimpleUserService(SimpleUserRepository repository) {
        this.repository = repository;
    }

    public List<SimpleUser> allUsers() {
        return repository.findAll();
    }

    public SimpleUser createNewUser(SimpleUser user) throws AlreadyExistsException {
        if (repository.exists(user.getId()))
            throw new AlreadyExistsException("User already exists");

        return repository.save(user);
    }

    public SimpleUser updateUser(SimpleUser user) throws NotFoundException {
        if (!repository.exists(user.getId()))
            throw new NotFoundException("Could not update, user not found");

        return repository.save(user);
    }

    public void removeUser(Long id) throws NotFoundException {
        if (!repository.exists(id))
            throw new NotFoundException("Could not delete, user not found");

        repository.delete(id);
    }

}
