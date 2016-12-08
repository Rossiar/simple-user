package com.henderson.simpleuser.event;

import com.github.fakemongo.Fongo;
import com.henderson.simpleuser.UserApplication;
import com.henderson.simpleuser.config.FongoConfiguration;
import com.henderson.simpleuser.domain.SimpleUser;
import com.mongodb.Mongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for {@link SimpleUserEventProducerJms}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserApplication.class, FongoConfiguration.class})
public class SimpleUserEventProducerJmsIT {

    @MockBean
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
