package br.edu.puc.sca.service;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import br.edu.puc.sca.domain.Equipamento;

@ApplicationScoped
@Transactional(REQUIRED)
public class EquipamentoAuditService {
    
    @Inject
    EntityManager entityManager;

    @Transactional(SUPPORTS)
    public List<Equipamento> findAll(){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Equipamento.class, true, true);
        List<Equipamento> equipamentoList = query.getResultList();
        return equipamentoList;
    }

    @Transactional(SUPPORTS)
    public List<Equipamento> findById(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Equipamento.class, true, true).add(AuditEntity.id().eq(id));
        List<Equipamento> equipamentoList = query.getResultList();
        return equipamentoList;
    }
}