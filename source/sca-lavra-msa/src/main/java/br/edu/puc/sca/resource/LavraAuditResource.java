package br.edu.puc.sca.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.puc.sca.domain.Lavra;
import br.edu.puc.sca.service.LavraAuditService;

@Path("/api/v1/lavras/audit")
public class LavraAuditResource {

    @Inject
    LavraAuditService lavraAuditService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Lavra> getAll() {
        List<Lavra> lavraList= lavraAuditService.findAll();
        return lavraList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public List<Lavra> get(@PathParam("id") Long id) {
        List<Lavra> lavraList = lavraAuditService.findById(id);
        return lavraList;
    }
    
}