package com.henderson.simpleuser.repository;

import com.github.fakemongo.Fongo;
import com.henderson.simpleuser.domain.Country;
import com.henderson.simpleuser.domain.SimpleUser;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SimpleUserRepositoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("demo-test");

    @Autowired
    private SimpleUserRepository simpleUserRepository;


    @Test
    @UsingDataSet(locations = "/users.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void findAll() throws Exception {
        List<SimpleUser> users = simpleUserRepository.findAll();
        assertThat(users.size()).isEqualTo(5);
    }

    @Test
    @UsingDataSet(locations = "/empty.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void findAllEmptyDatabase() throws Exception {
        List<SimpleUser> users = simpleUserRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    @UsingDataSet(locations = "/user.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void findOne() throws Exception {
        Long id = 1L;
        String firstName = "Master";
        String lastName = "Chief";
        String nickname = "John-117";
        String password = "SPARTAN-117";
        String email = "john@orion.com";
        Long countryId = new Long("1");
        String name = "United Kingdom";
        SimpleUser user = simpleUserRepository.findOne(id);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getEmail()).isEqualTo(email);
        Country country = user.getCountry();
        assertThat(country.getId()).isEqualTo(countryId);
        assertThat(country.getName()).isEqualTo(name);
    }

    @Test
    @UsingDataSet(locations = "/user.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void findOneNotFound() throws Exception {
        SimpleUser user = simpleUserRepository.findOne(2L);
        assertThat(user).isNull();
    }

    @Test
    @ShouldMatchDataSet(location = "/user.json")
    public void save() throws Exception {
        simpleUserRepository.save(new SimpleUser(1L, "Master", "Chief", "John-117", "SPARTAN-117", "john@orion.com",
                new Country(1L, "United Kingdom")));
    }

    /**
     * This test confirms that the database will replace a record totally if a new record is saved with the same `id`
     */
    @Test
    @UsingDataSet(locations = "/user.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    @ShouldMatchDataSet(location = "/newuser.json")
    public void saveAlreadyExists() throws Exception {
        simpleUserRepository.save(new SimpleUser(1L, "Kelly", "Shaddock", "Kelly-087", "SPARTAN-087", "kelly@orion.com",
                new Country(1L, "United Kingdom")));
    }

    @Test
    @UsingDataSet(locations = "/user.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void delete() throws Exception {
        simpleUserRepository.delete(1L);
        assertThat(simpleUserRepository.findAll()).isEmpty();
    }

    @Test
    @UsingDataSet(locations = "/user.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void exists() throws Exception {
        boolean exists = simpleUserRepository.exists(1L);
        assertThat(exists).isTrue();
    }

    @Test
    @UsingDataSet(locations = "/empty.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void doesNotExist() throws Exception {
        boolean exists = simpleUserRepository.exists(1L);
        assertThat(exists).isFalse();
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {SimpleUserRepository.class})
    static class SimpleUserRepositoryConfiguration extends AbstractMongoConfiguration {


        @Override
        protected String getDatabaseName() {
            return "demo-test";
        }

        @Bean
        public Mongo mongo() {
            Fongo queued = new Fongo("something");
            return queued.getMongo();
        }

        @Override
        protected String getMappingBasePackage() {
            return SimpleUserRepository.class.getPackage().getName();
        }

    }

}