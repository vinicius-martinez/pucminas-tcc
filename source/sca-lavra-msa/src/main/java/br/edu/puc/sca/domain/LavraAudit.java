package br.edu.puc.sca.domain;

import javax.inject.Inject;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import br.edu.puc.sca.service.UserService;

//@Entity
//@RevisionEntity(LavraRevisionListener.class)
public class LavraAudit {

	@Inject
    @Transient
	UserService userService;

	@Id
	@GeneratedValue
	private Long id;
	
    private String userName;

    @RevisionNumber
    private Integer revisionNumber;

    @RevisionTimestamp  
    public Long revisionTimeStamp; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
    }
	public Integer getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(Integer revisionNumber) {
		this.revisionNumber = revisionNumber;
    }
	public Long getRevisionTimeStamp() {
		return revisionTimeStamp;
	}
	public void setRevisionTimeStamp(Long revisionTimeStamp) {
		this.revisionTimeStamp = revisionTimeStamp;
	}

}
