package br.edu.puc.sca.domain;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hibernate.envers.RevisionListener;

import br.edu.puc.sca.resource.UserResourceClient;

public class LavraRevisionListener implements RevisionListener {

    @Inject
    @RestClient
    UserResourceClient client;
    
    @Override
    public void newRevision(Object revisionEntity) {
        LavraAudit lavraAudit = (LavraAudit)revisionEntity;
        //lavraAudit.setUserName(client.get().getUserName());   
        lavraAudit.setUserName("user2");
    }

}
