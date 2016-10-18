package com.henderson.simpleuser.controller;

import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.event.SimpleUserEventProducer;
import com.henderson.simpleuser.exceptions.AlreadyExistsException;
import com.henderson.simpleuser.exceptions.NotFoundException;
import com.henderson.simpleuser.service.SimpleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Handles the request/responses, this class is a representation of the rest endpoint that will be served at
 * the {@link RequestMapping} path.
 */
@RestController
@RequestMapping("users")
public class SimpleUserController {
    /**
     * Class logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserController.class);
    /**
     * Provides user related operations (masks the database calls and allows for extra steps of validation)
     */
    private final SimpleUserService userService;
    /**
     * Fires events to notify other services
     */
    private SimpleUserEventProducer eventProducer;


    @Autowired
    public SimpleUserController(SimpleUserService userService, SimpleUserEventProducer eventProducer) {
        this.userService = userService;
        this.eventProducer = eventProducer;
    }

    @RequestMapping(method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<SimpleUser> userList() {
        return userService.allUsers();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody SimpleUser user, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            SimpleUser created = userService.createNewUser(user);
            eventProducer.userCreated(created);
            response.setStatus(HttpStatus.CREATED.value());
            MultiValueMap<String, String> headers = new LinkedMultiValueMap();
            headers.set(HttpHeaders.LOCATION,
                    String.format("%s/%d", request.getRequestURL(), created.getId()));
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            LOGGER.error(String.format("User with id %s already exists", user.getId()), e);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleUser> replace(@Valid @RequestBody SimpleUser user, HttpServletResponse response) {
        try {
            SimpleUser updated = userService.updateUser(user);
            eventProducer.userUpdated(user, updated);
            return new ResponseEntity<>(updated, HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            LOGGER.error(String.format("User with id %s was not found", user.getId()), e);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method=RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable Long id) {
        try {
            userService.removeUser(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            LOGGER.error(String.format("User with id %s was not found", id), e);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
