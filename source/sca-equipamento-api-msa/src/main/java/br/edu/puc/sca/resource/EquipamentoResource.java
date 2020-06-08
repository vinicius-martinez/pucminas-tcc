package br.edu.puc.sca.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.puc.sca.domain.Equipamento;
import br.edu.puc.sca.domain.Lavra;
import br.edu.puc.sca.domain.MetodoLavra;
import br.edu.puc.sca.domain.Status;
import br.edu.puc.sca.service.EquipamentoService;

@Path("/api/v1/equipamentos")
public class EquipamentoResource {

    @Inject
    EquipamentoService equipamentoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Equipamento> getAll() {
        List<Equipamento> equipamentos = equipamentoService.findAll();
        return equipamentos;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Equipamento entitity = equipamentoService.findById(id);
        if (entitity != null) {
            return Response.ok(entitity).build();
        }
        return Response.noContent().status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Equipamento equipamento) {        
        equipamentoService.persist(equipamento);
        return Response.ok(equipamento).status(Response.Status.CREATED).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Equipamento equipamento) {
        Equipamento entity = equipamentoService.update(equipamento);
        if (entity != null){
            return Response.ok(entity).status(Response.Status.OK).build();
        }
        return Response.noContent().status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        equipamentoService.delete(id);
        return Response.noContent().status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("load")
    public Response load(){
        List<Equipamento> equipamentoList = new ArrayList<Equipamento>(10);
        List<Lavra> lavraList = Lavra.listAll();
        for (int i = 0; i < 10; i++) {
            Equipamento equipamento = new Equipamento();
            equipamento.setNome("equipamento" + i);
            equipamento.setDescricao("equipamento Lavra" + i);
            equipamento.setMetodoLavra(MetodoLavra.values()[new Random().nextInt(MetodoLavra.values().length)]);
            equipamento.setStatus(Status.values()[new Random().nextInt(Status.values().length)]);
            equipamento.setLavra(lavraList.get(new Random().nextInt(lavraList.size() -1)));
            equipamentoService.persist(equipamento);
        }
        equipamentoList = equipamentoService.findAll();
        return Response.ok(equipamentoList).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("unload")
    @Transactional
    public Response unload(){
        Equipamento.deleteAll();
        return Response.noContent().status(Response.Status.OK).build();
    }

}
