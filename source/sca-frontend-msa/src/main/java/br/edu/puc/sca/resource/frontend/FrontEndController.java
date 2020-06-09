package br.edu.puc.sca.resource.frontend;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.edu.puc.sca.domain.Equipamento;
import br.edu.puc.sca.domain.Lavra;
import br.edu.puc.sca.service.FrontEndEquipamentoService;
import br.edu.puc.sca.service.FrontEndLavraAuditService;
import br.edu.puc.sca.service.FrontEndLavraService;

@Path("/frontend")
@ApplicationScoped
public class FrontEndController {

    @Inject
    @RestClient
    FrontEndLavraService frontEndLavraService;

    @Inject
    @RestClient
    FrontEndLavraAuditService frontEndLavraAuditService;

    @Inject
    @RestClient
    FrontEndEquipamentoService frontEndEquipamentoService;

    @Inject
    @RestClient
    FrontEndEquipamentoService frontEndEquipamentoAuditService;

    @GET
    @Path("lavras")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Lavra> findAllLavra(){
        List<Lavra> lavras = frontEndLavraService.findAll();
        lavras = frontEndLavraAuditService.findAll();
        return lavras;
    }

    @GET
    @Path("equipamentos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Equipamento> findAllEquipamento(){
        List<Equipamento> equipamentos = frontEndEquipamentoService.findAll();
        equipamentos = frontEndEquipamentoAuditService.findAll();
        return equipamentos;
    }
    
}