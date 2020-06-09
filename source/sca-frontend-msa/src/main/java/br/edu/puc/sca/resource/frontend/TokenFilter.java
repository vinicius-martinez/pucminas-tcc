package br.edu.puc.sca.resource.frontend;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import io.quarkus.oidc.AccessTokenCredential;

public class TokenFilter implements ClientRequestFilter {

    @Inject
    AccessTokenCredential credential;

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {   
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + credential.getToken());        
	}
    
}