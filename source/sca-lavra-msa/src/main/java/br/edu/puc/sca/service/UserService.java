package br.edu.puc.sca.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.puc.sca.domain.User;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
public class UserService {

    @Inject
    SecurityIdentity identity;
    
    public User get(){
        User user = new User(identity);
        return user;        
    }
    
}