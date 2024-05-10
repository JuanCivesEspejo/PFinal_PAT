package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    /**
     * TODO#9
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */
    @Test void saveTest()
    {
        // Given ...
        AppUser user = new AppUser();
        user.email = "pepito@email.com";
        user.password = "aaABCaa120";
        user.role = Role.USER;
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

    /**
     * TODO#10
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */
    @Test void deleteCascadeTest()
    {
        // Given ...
        AppUser user = new AppUser();
        user.email = "pepito@email.com";
        user.password = "aaABCaa120";
        user.role = Role.USER;
        user.name = "pepito";

        Token token = new Token();

        token.appUser = user;

        // When ...
        appUserRepository.save(user);
        tokenRepository.save(token);
        appUserRepository.delete(user);

        // Then ...
        Assertions.assertEquals(0, appUserRepository.count());
        Assertions.assertEquals(0, tokenRepository.count());
    }
}