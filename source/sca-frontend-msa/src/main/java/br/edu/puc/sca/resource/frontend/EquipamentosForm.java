package br.edu.puc.sca.resource.frontend;

public class EquipamentosForm {

    private Long id;
    private String nome;
    private String descricao;
    private String status;
    private String metodoLavra;
    private String nomeLavra;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMetodoLavra() {
		return metodoLavra;
	}
	public void setMetodoLavra(String metodoLavra) {
		this.metodoLavra = metodoLavra;
	}
	public String getNomeLavra() {
		return nomeLavra;
	}
	public void setNomeLavra(String nomeLavra) {
		this.nomeLavra = nomeLavra;
	}

    
    
}