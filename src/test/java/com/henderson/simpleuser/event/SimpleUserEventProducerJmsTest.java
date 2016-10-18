package com.henderson.simpleuser.event;

import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.event.model.SimpleUserEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link SimpleUserEventProducerJms}
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleUserEventProducerJmsTest {

    @Mock
    private JmsTemplate template;

    @Test
    public void userCreated() throws Exception {
        SimpleUserEventProducerJms producer = new SimpleUserEventProducerJms(template);

        producer.userCreated(mock(SimpleUser.class));

        verify(template).convertAndSend(eq("created-users"), any(SimpleUserEvent.class));
    }

    @Test
    public void userUpdated() throws Exception {
        SimpleUserEventProducerJms producer = new SimpleUserEventProducerJms(template);

        producer.userUpdated(mock(SimpleUser.class), mock(SimpleUser.class));

        verify(template).convertAndSend(eq("updated-users"), any(SimpleUserEvent.class));
    }

}