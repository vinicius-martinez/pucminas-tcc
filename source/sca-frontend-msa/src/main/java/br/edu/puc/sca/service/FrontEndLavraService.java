package br.edu.puc.sca.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.edu.puc.sca.domain.Lavra;
import br.edu.puc.sca.resource.frontend.TokenFilter;

@ApplicationScoped
@RegisterRestClient
@RegisterProvider(TokenFilter.class)
public interface FrontEndLavraService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lavra> findAll();
    
}