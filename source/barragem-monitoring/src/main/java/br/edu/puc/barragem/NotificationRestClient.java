package br.edu.puc.barragem;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.edu.puc.barragem.domain.Barragem;

@ApplicationScoped
@RegisterRestClient
public interface NotificationRestClient {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNotification(Barragem barragem);
}