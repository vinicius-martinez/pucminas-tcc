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
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;

@Path("/frontend")
@ApplicationScoped
@Authenticated
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

    @Inject
    Template lavras;

    @Inject
    Template equipamentos;

    @GET
    @Path("lavras")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public TemplateInstance findAllLavra(){
        List<Lavra> lavrasList = frontEndLavraService.findAll();
        List<Lavra> lavrasAudit = frontEndLavraAuditService.findAll();
        return lavras.data("lavras", lavrasList).data("lavrasAudit", lavrasAudit);
    }

    @GET
    @Path("equipamentos")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public TemplateInstance findAllEquipamento(){
        List<Equipamento> equipamentosList = frontEndEquipamentoService.findAll();
        List<Equipamento> equipamentosAuditList= frontEndEquipamentoAuditService.findAll();
        return equipamentos.data("equipamentos", equipamentosList).data("equipamentosAudit", equipamentosAuditList);
    }
    
}