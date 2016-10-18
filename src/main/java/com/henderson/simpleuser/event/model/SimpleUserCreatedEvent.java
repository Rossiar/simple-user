package com.henderson.simpleuser.event.model;

import com.henderson.simpleuser.domain.SimpleUser;

import java.time.Instant;

/**
 * Created by ross on 18/10/16.
 */
public class SimpleUserCreatedEvent extends SimpleUserEvent {

    public SimpleUserCreatedEvent(SimpleUser original, Instant eventSubmittedAt) {
        super(original, eventSubmittedAt);
    }
}
