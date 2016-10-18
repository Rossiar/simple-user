package com.henderson.simpleuser.event.model;

import com.henderson.simpleuser.domain.SimpleUser;

import java.time.Instant;

/**
 * Base class for events, all events that modify users should at least have these properties.
 */
public abstract class SimpleUserEvent {

    private final SimpleUser original;
    private final Instant submittedAt;

    public SimpleUserEvent(SimpleUser original, Instant submittedAt) {
        this.original = original;
        this.submittedAt = submittedAt;
    }

    public SimpleUser getOriginal() {
        return original;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }
}
