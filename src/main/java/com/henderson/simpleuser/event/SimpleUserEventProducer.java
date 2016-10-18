package com.henderson.simpleuser.event;

import com.henderson.simpleuser.domain.SimpleUser;

/**
 * Created by ross on 18/10/16.
 */
public interface SimpleUserEventProducer {

    void userCreated(SimpleUser created);

    void userUpdated(SimpleUser old, SimpleUser updated);
}
