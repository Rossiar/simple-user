package com.henderson.simpleuser.event;

import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.event.model.SimpleUserCreatedEvent;
import com.henderson.simpleuser.event.model.SimpleUserEvent;
import com.henderson.simpleuser.event.model.SimpleUserUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Produces events that are put onto a queue that other services can access
 */
@Component
public class SimpleUserEventProducerJms implements SimpleUserEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserEventProducerJms.class);
    /**
     * Submits the events to the queue
     */
    private final JmsTemplate jmsTemplate;


    @Autowired
    public SimpleUserEventProducerJms(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void userCreated(SimpleUser created) {
        SimpleUserEvent event = new SimpleUserCreatedEvent(created, Instant.now());
        LOGGER.info("Submitting user created event {}", event);
        jmsTemplate.convertAndSend("created-users", event);
    }

    @Override
    public void userUpdated(SimpleUser original, SimpleUser updated) {
        SimpleUserEvent event = new SimpleUserUpdatedEvent(original, updated, Instant.now());
        LOGGER.info("Submitting user updated event {}", event);
        jmsTemplate.convertAndSend("updated-users", event);
    }
}
