package com.henderson.simpleuser.repository;

import com.github.fakemongo.Fongo;
import com.henderson.simpleuser.domain.Country;
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

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CountryRepositoryTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("demo-test");

    @Autowired
    private CountryRepository countryRepository;


    @Test
    @ShouldMatchDataSet(location = "/country/country.json")
    public void save() throws Exception {
        countryRepository.save(new Country(1L, "United Kingdom"));
    }

    @Test
    public void findOne() throws Exception {
        long id = 1L;
        Country country = countryRepository.findOne(id);
        assertThat(country.getId()).isEqualTo(id);
        assertThat(country.getName()).isEqualTo("United Kingdom");
    }

    @Test
    @UsingDataSet(locations = "/country/country.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void findOneNotFound() throws Exception {
        Country country = countryRepository.findOne(2L);
        assertThat(country).isNull();
    }

    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {CountryRepository.class})
    static class CountryRepositoryConfiguration extends AbstractMongoConfiguration {


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
            return CountryRepository.class.getPackage().getName();
        }

    }

}