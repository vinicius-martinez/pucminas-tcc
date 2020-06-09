package br.edu.puc.sca.service;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import br.edu.puc.sca.domain.Equipamento;
import br.edu.puc.sca.domain.Lavra;

@ApplicationScoped
@Transactional(REQUIRED)
public class EquipamentoService {

    @Transactional(SUPPORTS)
    public List<Equipamento> findAll() {
        return Equipamento.listAll();
    }

    @Transactional(SUPPORTS)
    public Equipamento findById(Long id) {
        return Equipamento.findById(id);
    }

    @Transactional
    public Equipamento persist(Equipamento equipamento) {
        Lavra lavra = Lavra.findById(equipamento.getLavra().id);
        equipamento.setLavra(lavra);
        equipamento.setDataCriacao(new Date());
        equipamento.setDataUltimaAtualizacao(new Date());
        Equipamento.persist(equipamento);
        return equipamento;
    }

    @Transactional
    public Equipamento update(Equipamento equipamento) {
        Equipamento entity = Equipamento.findById(equipamento.id);
        if (entity != null) {
            Lavra lavra = Lavra.findById(equipamento.getLavra().id);
            if (lavra != null){
                entity.setLavra(lavra);
            }
            entity.setNome(equipamento.getNome());
            entity.setDescricao(equipamento.getDescricao());
            entity.setMetodoLavra(equipamento.getMetodoLavra());
            entity.setStatus(equipamento.getStatus());
            entity.setDataUltimaAtualizacao(new Date());
            entity.persistAndFlush();
        }
        return entity;
    }

    public void delete(Long id) {
        Equipamento equipamento = Equipamento.findById(id);
        equipamento.delete();
    }
 
}