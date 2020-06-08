package br.edu.puc.sca.resource;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.puc.sca.domain.Equipamento;
import br.edu.puc.sca.domain.Lavra;

@Path("/api/v1/equipamentos")
public class EquipamentoResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Equipamento find() {
        Equipamento equipamento = new Equipamento();
        Lavra lavra = Lavra.findById(1L);
        equipamento.setLavra(lavra);
        Equipamento.persist(equipamento);
        return equipamento;
    }


}