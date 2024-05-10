package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface
{
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    Hashing hashing;


    @Override
    public Token login(String email, String password)
    {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null || !hashing.compare(appUser.password, password))
            return null;

        Token token = tokenRepository.findByAppUser(appUser);
        if (token != null)
            return token;

        token = new Token();
        token.appUser = appUser;
        tokenRepository.save(token);
        return token;
    }

    @Override
    public AppUser authentication(String tokenId)
    {
        Optional<Token> token = tokenRepository.findById(tokenId);
        if(token.isEmpty())
            return null;
        return token.get().appUser;
    }

    @Override
    public ProfileResponse profile(AppUser appUser)
    {
        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile)
    {
        appUser.name = profile.name();
        appUser.role = profile.role();
        appUser.password = hashing.hash(profile.password());
        appUserRepository.save(appUser);
        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }
    public ProfileResponse profile(RegisterRequest register)
    {
        AppUser appUser = new AppUser();
        appUser.email = register.email();
        appUser.password = hashing.hash(register.password());
        appUser.role = register.role();
        appUser.name = register.name();
        appUserRepository.save(appUser);
        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }

    public void logout(String tokenId)
    {
        tokenRepository.deleteById(tokenId);
    }

    public void delete(AppUser appUser)
    {
        appUserRepository.delete(appUser);
    }
}
