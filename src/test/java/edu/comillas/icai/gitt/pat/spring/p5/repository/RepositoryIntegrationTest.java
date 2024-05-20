package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.OrderDetail;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Orders;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    @Test void saveTest()
    {
        // Given ...
        AppUser user = new AppUser();
        user.email = "pepito@email.com";
        user.password = "aaABCaa120";
        user.role = Role.RESTAURANTE;
        user.name = "pepito";

        Token token = new Token();

        token.appUser = user;

        // When ...
        appUserRepository.save(user);
        tokenRepository.save(token);

        AppUser userInDB = appUserRepository.findByEmail("pepito@email.com");
        Token tokenInDB = tokenRepository.findByAppUser(userInDB);


        // Then ...
        Assertions.assertNotNull(userInDB);
        Assertions.assertNotNull(tokenInDB);
    }

    @Test void deleteCascadeTest()
    {
        // Given ...
        AppUser user = new AppUser();
        user.email = "pepito@email.com";
        user.password = "aaABCaa120";
        user.role = Role.RESTAURANTE;
        user.name = "pepito";

        Token token = new Token();

        token.appUser = user;

        // When ...
        appUserRepository.save(user);
        tokenRepository.save(token);
        appUserRepository.delete(user);

        // Then ...
        Assertions.assertEquals(9, appUserRepository.count());
        Assertions.assertEquals(0, tokenRepository.count());
    }
}