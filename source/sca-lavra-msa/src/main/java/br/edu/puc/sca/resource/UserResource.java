package br.edu.puc.sca.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import br.edu.puc.sca.domain.User;
import br.edu.puc.sca.service.UserService;

@Path("/api/v1/users")
public class UserResource {

    @Inject
    UserService userService;
    
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public User me() {
        return userService.get();
    }
}