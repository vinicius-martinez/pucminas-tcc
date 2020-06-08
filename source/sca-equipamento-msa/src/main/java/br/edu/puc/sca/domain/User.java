package br.edu.puc.sca.domain;

import javax.inject.Inject;

import io.quarkus.security.identity.SecurityIdentity;

public class User {
    
    @Inject
    SecurityIdentity identity;

    private final String userName;

    public User(SecurityIdentity identity) {
        this.userName = identity.getPrincipal().getName();
    }

    public String getUserName() {
        return userName;
    }
}