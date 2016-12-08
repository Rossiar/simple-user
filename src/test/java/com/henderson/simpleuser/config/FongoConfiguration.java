package com.henderson.simpleuser.config;

import com.github.fakemongo.Fongo;
import com.henderson.simpleuser.UserApplication;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@ComponentScan
public class FongoConfiguration extends AbstractMongoConfiguration {


    @Override
    protected String getDatabaseName() {
        return "demo-test";
    }

    @Bean
    public Mongo mongo() {
        return new Fongo("something").getMongo();
    }

    @Override
    protected String getMappingBasePackage() {
        return  UserApplication.class.getPackage().getName();
    }

}
