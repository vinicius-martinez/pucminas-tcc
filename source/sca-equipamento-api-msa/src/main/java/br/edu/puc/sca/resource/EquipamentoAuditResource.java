package br.edu.puc.sca.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.puc.sca.domain.Equipamento;
import br.edu.puc.sca.service.EquipamentoAuditService;

@Path("/api/v1/equipamentos/audit")
public class EquipamentoAuditResource {

    @Inject
    EquipamentoAuditService equipamentoAuditService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Equipamento> getAll() {
        List<Equipamento> equipamentoList= equipamentoAuditService.findAll();
        return equipamentoList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public List<Equipamento> get(@PathParam("id") Long id) {
        List<Equipamento> equipamentoList = equipamentoAuditService.findById(id);
        return equipamentoList;
 
    }
}