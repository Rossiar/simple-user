package com.henderson.simpleuser.event;

import com.henderson.simpleuser.domain.SimpleUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for {@link SimpleUserEventProducerJms}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleUserEventProducerJmsIT {

    @Autowired
    private JmsTemplate template;


    @Test
    public void userCreated() throws Exception {
        SimpleUserEventProducerJms eventProducer = new SimpleUserEventProducerJms(template);

        eventProducer.userCreated(new SimpleUser());
    }

    @Test
    public void userUpdated() throws Exception {
        SimpleUserEventProducerJms eventProducer = new SimpleUserEventProducerJms(template);

        eventProducer.userUpdated(new SimpleUser(), new SimpleUser());
    }

}
