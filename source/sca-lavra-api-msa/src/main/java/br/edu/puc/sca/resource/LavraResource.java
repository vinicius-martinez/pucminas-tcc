package br.edu.puc.sca.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.security.RolesAllowed;
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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.edu.puc.sca.domain.Lavra;
import br.edu.puc.sca.domain.MetodoLavra;
import br.edu.puc.sca.domain.Status;
import br.edu.puc.sca.service.LavraService;

@Path("/api/v1/lavras")
public class LavraResource {

    @Inject
    LavraService lavraService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Lavra> getAll() {
        final List<Lavra> lavraList = lavraService.findAll();
        return lavraList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response get(@PathParam("id") final Long id) {
        final Lavra entitity = lavraService.findById(id);
        if (entitity != null) {
            return Response.ok(entitity).build();
        }
        return Response.noContent().status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final Lavra lavra) {
        lavraService.persist(lavra);
        return Response.ok(lavra).status(Response.Status.CREATED).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final Lavra lavra) {
        final Lavra entity = lavraService.update(lavra);
        if (entity != null){
            return Response.ok(entity).status(Response.Status.OK).build();
        }
        return Response.noContent().status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response remove(@PathParam("id") final Long id) {
        lavraService.delete(id);
        return Response.noContent().status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("load")
    public Response load(){
            final List<Lavra> lavraList = new ArrayList<Lavra>(10);
        for (int i = 0; i < 10; i++) {
            final Lavra lavra = new Lavra();
            lavra.setNome("Lavra" + i);
            lavra.setDescricao("Descricao Lavra" + i);
            lavra.setMetodoLavra(MetodoLavra.values()[new Random().nextInt(MetodoLavra.values().length)]);
            lavra.setStatus(Status.values()[new Random().nextInt(Status.values().length)]);
            lavraService.persist(lavra);
            lavraList.add(lavra);
        }
        return Response.ok(lavraList).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("unload")
    @Transactional
    public Response unload(){
        Lavra.deleteAll();
        return Response.noContent().status(Response.Status.OK).build();
    }

}
