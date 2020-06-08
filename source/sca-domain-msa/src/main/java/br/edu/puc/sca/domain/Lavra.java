package br.edu.puc.sca.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Audited
public class Lavra extends PanacheEntity {
 
    private String nome;
    private String descricao;
    private Status status;
	private MetodoLavra metodoLavra;
	@Version
	private Integer version;
	private Date dataCriacao;
	private Date dataUltimaAtualizacao;
	//@OneToMany(mappedBy = "lavra", orphanRemoval = true)
	//private List<Equipamento> equipamentos = new ArrayList<Equipamento>(0);

    public Lavra(){}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public MetodoLavra getMetodoLavra() {
		return metodoLavra;
	}
	public void setMetodoLavra(MetodoLavra metodoLavra) {
		this.metodoLavra = metodoLavra;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

}