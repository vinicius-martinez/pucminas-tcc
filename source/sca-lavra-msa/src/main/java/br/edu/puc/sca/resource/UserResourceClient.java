package br.edu.puc.sca.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.edu.puc.sca.domain.User;

@RegisterRestClient
@Path("/api/v1")
public interface UserResourceClient {

    @GET
    @Path("/me")
    User get();
    
}