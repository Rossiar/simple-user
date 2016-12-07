package com.henderson.simpleuser;

import com.henderson.simpleuser.domain.Country;
import com.henderson.simpleuser.domain.SimpleUser;
import com.henderson.simpleuser.repository.CountryRespository;
import com.henderson.simpleuser.repository.SimpleUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserApplication {


    @Bean
    public CommandLineRunner init(SimpleUserRepository userRepository, CountryRespository countryRespository) {
        return (evt) -> {
            /**
             * Loads some users into the database at runtime, obviously this is just an example and wouldn't be part
             * of a production system.
             */
            Country uk = new Country(1L, "United Kingdom");
            Country fr = new Country(2L, "France");
            Country sc = new Country(3L, "Scotland");

            countryRespository.save(uk);
            countryRespository.save(fr);
            countryRespository.save(sc);

            userRepository.save(new SimpleUser(1L, "Kelly", "Shaddock", "Kelly-087", "SPARTAN-087", "kelly@orion.com", uk));
            userRepository.save(new SimpleUser(2L, "Frederic", "Ellsworth", "Frederic-104", "SPARTAN-104", "fred@orion.com", fr));
            userRepository.save(new SimpleUser(3L, "Linda", "Pravdin", "Linda-058", "SPARTAN-058", "linda@orion.com", sc));
            userRepository.save(new SimpleUser(4L, "Kurt M.", "Trevelyan", "Kurt-051", "SPARTAN-051", "kurt@orion.com", fr));
            userRepository.save(new SimpleUser(5L, "Master", "Chief", "John-117", "SPARTAN-117", "john@orion.com", uk));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}