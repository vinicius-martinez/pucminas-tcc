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

import br.edu.puc.sca.domain.Lavra;

@ApplicationScoped
@Transactional(REQUIRED)
public class LavraAuditService {

    @Inject
    EntityManager entityManager;

    @Transactional(SUPPORTS)
    public List<Lavra> findAll(){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Lavra.class, true, true);
        List<Lavra> lavraList = query.getResultList();
        return lavraList;
    }

    @Transactional(SUPPORTS)
    public List<Lavra> findById(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(Lavra.class, true, true).add(AuditEntity.id().eq(id));
        List<Lavra> lavraList = query.getResultList();
        return lavraList;
    }
    
}