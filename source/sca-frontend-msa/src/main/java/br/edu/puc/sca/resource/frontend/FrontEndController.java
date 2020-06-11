package br.edu.puc.sca.resource.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(FrontEndController.class);

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

    @ConfigProperty(name = "testingFault")
    boolean testingFault;

    @ConfigProperty(name = "isSleeping")
    boolean isSleeping;

    @ConfigProperty(name = "isRetry")
    boolean isRetry;
    final int quantidadeRetry = 5;
    int exceptionCount = quantidadeRetry - 1;
    int count = 0;

    @ConfigProperty(name = "isTimeout")
    boolean isTimeout;
    final int maxTimeout = 250;
    final int randonTimeout = 500;

    @GET
    @Path("lavras")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    //@Retry(maxRetries = quantidadeRetry, delay = 1, delayUnit = ChronoUnit.SECONDS)
    @Timeout(maxTimeout)
    @Fallback( fallbackMethod = "fallBack")
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
        List<Lavra> lavraAuditList = frontEndLavraAuditService.findAll();
        List<LavraForm> lavraFormAuditList = new ArrayList<LavraForm>(0);
        for (Lavra lavra : lavraAuditList) {
            LavraForm lavraFormAudit = new LavraForm();
            lavraFormAudit.setId(lavra.id);
            lavraFormAudit.setNome(lavra.getDescricao());
            lavraFormAudit.setDescricao(lavra.getDescricao());
            if (lavra.getMetodoLavra() != null){
                lavraFormAudit.setMetodoLavra(lavra.getMetodoLavra().name());
            }
            if (lavra.getStatus() != null){
                lavraFormAudit.setStatus(lavra.getStatus().name());
            }
            lavraFormAuditList.add(lavraFormAudit);
        }
        if (testingFault){
            executeFault();
        }
        count = 0;
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
        for (Equipamento equipamentoAudit : equipamentosAuditList) {
            EquipamentosForm equipamentosFormAudit = new EquipamentosForm();
            equipamentosFormAudit.setId(equipamentoAudit.id);
            equipamentosFormAudit.setNome(equipamentoAudit.getNome());
            equipamentosFormAudit.setDescricao(equipamentoAudit.getDescricao());
            if (equipamentoAudit.getMetodoLavra() != null){
                equipamentosFormAudit.setMetodoLavra(equipamentoAudit.getMetodoLavra().name());
            }
            if (equipamentoAudit.getStatus() != null){
                equipamentosFormAudit.setStatus(equipamentoAudit.getStatus().name());
            }
            if (equipamentoAudit.getLavra() != null){
                if (equipamentoAudit.getLavra().getNome() != null){
                    equipamentosFormAudit.setNomeLavra(equipamentoAudit.getLavra().getNome());
                }
            }
            equipamentosFormsAuditList.add(equipamentosFormAudit);
        }
        return equipamentos.data("equipamentos", equipamentosFormsList)
            .data("equipamentosAudit", equipamentosFormsAuditList);
    }
    
    public TemplateInstance fallBack(){
        List<LavraForm> lavraFormList = new ArrayList<LavraForm>(0);
        List<LavraForm> lavraFormAuditList = new ArrayList<LavraForm>(0);
        return lavras.data("lavras", lavraFormList).data("lavrasAudit", lavraFormAuditList);
    }

    private void executeFault() {
        if (isSleeping){
            try {
                Thread.sleep(1000);
                LOGGER.error("Thread Sleep: 1000");
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (isRetry){
            while ( count < exceptionCount){
                count++;
                LOGGER.error("Simulando Erro: " + count);
                throw new RuntimeException("Simulando Er∏ro: " + count);
            }
        }
        if (isTimeout){
            int timeout = new Random().nextInt(randonTimeout);
            try {
                LOGGER.error("Timeout: " + timeout);
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
                LOGGER.error("Timeout: " + timeout);
				e.printStackTrace();
			}
        }
    }
        
}