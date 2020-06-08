package br.edu.puc.sca.service;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import br.edu.puc.sca.domain.Lavra;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Date;
import java.util.List;

@ApplicationScoped
@Transactional(REQUIRED)
public class LavraService {

    @Transactional(SUPPORTS)
    public List<Lavra> findAll() {
        return Lavra.listAll();
    }

    @Transactional(SUPPORTS)
    public Lavra findById(Long id) {
        return Lavra.findById(id);
    }

    public Lavra persist(Lavra lavra) {
        lavra.setDataCriacao(new Date());
        lavra.setDataUltimaAtualizacao(new Date());
        Lavra.persist(lavra);
        return lavra;
    }

    public Lavra update(Lavra lavra) {
        Lavra entity = Lavra.findById(lavra.id);
        if (entity != null) {
            entity.setNome(lavra.getNome());
            entity.setDescricao(lavra.getDescricao());
            entity.setMetodoLavra(lavra.getMetodoLavra());
            entity.setStatus(lavra.getStatus());
            entity.setDataUltimaAtualizacao(new Date());
        }
        return entity;
    }

    public void delete(Long id) {
        Lavra lavra = Lavra.findById(id);
        lavra.delete();
    }
    
}