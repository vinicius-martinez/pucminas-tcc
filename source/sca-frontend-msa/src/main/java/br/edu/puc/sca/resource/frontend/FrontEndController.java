package br.edu.puc.sca.resource.frontend;

import java.util.ArrayList;
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
        List<LavraForm> lavraFormList = new ArrayList<LavraForm>(0);
        for (Lavra lavra : lavrasList) {
            LavraForm lavraForm = new LavraForm();
            lavraForm.setId(lavra.id);
            lavraForm.setNome(lavra.getDescricao());
            lavraForm.setDescricao(lavra.getDescricao());
            lavraForm.setMetodoLavra(lavra.getMetodoLavra().name());
            lavraForm.setStatus(lavra.getStatus().name());
            lavraFormList.add(lavraForm);
        }
        List<LavraForm> lavraFormAuditList = new ArrayList<LavraForm>(0);
        for (Lavra lavra : lavrasList) {
            LavraForm lavraFormAudit = new LavraForm();
            lavraFormAudit.setId(lavra.id);
            lavraFormAudit.setNome(lavra.getDescricao());
            lavraFormAudit.setDescricao(lavra.getDescricao());
            lavraFormAudit.setMetodoLavra(lavra.getMetodoLavra().name());
            lavraFormAudit.setStatus(lavra.getStatus().name());
            lavraFormAuditList.add(lavraFormAudit);
        }
        return lavras.data("lavras", lavraFormList)
            .data("lavrasAudit", lavraFormAuditList);
    }

    @GET
    @Path("equipamentos")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public TemplateInstance findAllEquipamento(){
        List<Equipamento> equipamentosList = frontEndEquipamentoService.findAll();
        List<EquipamentosForm> equipamentosFormsList = new ArrayList<EquipamentosForm>(0);
        for (Equipamento equipamento : equipamentosList) {
            EquipamentosForm equipamentosForm = new EquipamentosForm();
            equipamentosForm.setId(equipamento.id);
            equipamentosForm.setNome(equipamento.getNome());
            equipamentosForm.setDescricao(equipamento.getDescricao());
            equipamentosForm.setMetodoLavra(equipamento.getMetodoLavra().name());
            equipamentosForm.setStatus(equipamento.getStatus().name());
            equipamentosForm.setNomeLavra(equipamento.getLavra().getNome());
            equipamentosFormsList.add(equipamentosForm);
        }
        List<EquipamentosForm> equipamentosFormsAuditList = new ArrayList<EquipamentosForm>(0);
        List<Equipamento> equipamentosAuditList= frontEndEquipamentoAuditService.findAll();
        for (Equipamento equipamento : equipamentosAuditList) {
            EquipamentosForm equipamentosFormAudit = new EquipamentosForm();
            equipamentosFormAudit.setId(equipamento.id);
            equipamentosFormAudit.setNome(equipamento.getNome());
            equipamentosFormAudit.setDescricao(equipamento.getDescricao());
            equipamentosFormAudit.setMetodoLavra(equipamento.getMetodoLavra().name());
            equipamentosFormAudit.setStatus(equipamento.getStatus().name());
            equipamentosFormAudit.setNomeLavra(equipamento.getLavra().getNome());
            equipamentosFormsAuditList.add(equipamentosFormAudit);
        }
        return equipamentos.data("equipamentos", equipamentosFormsList)
            .data("equipamentosAudit", equipamentosFormsAuditList);
    }
    
}