package com.henderson.simpleuser.event.model;

import com.henderson.simpleuser.domain.SimpleUser;

import java.time.Instant;

/**
 * Created by ross on 18/10/16.
 */
public class SimpleUserUpdatedEvent extends SimpleUserEvent {

    private final SimpleUser updated;

    public SimpleUserUpdatedEvent(SimpleUser original, SimpleUser updated, Instant eventSubmittedAt) {
        super(original, eventSubmittedAt);
        this.updated = updated;
    }

    public SimpleUser getUpdated() {
        return updated;
    }
}
