package br.edu.puc.barragem.domain;

public class Barragem {

	private Integer id;
    private String nome;
    private String municipio;
    private CategoriaRisco categoriaRisco;
    private Classe classe;
	private NivelEmergencia nivelEmergencia;
	private Long quantidadeRejeito;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public CategoriaRisco getCategoriaRisco() {
		return categoriaRisco;
	}
	public void setCategoriaRisco(CategoriaRisco categoriaRisco) {
		this.categoriaRisco = categoriaRisco;
	}
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public NivelEmergencia getNivelEmergencia() {
		return nivelEmergencia;
	}
	public void setNivelEmergencia(NivelEmergencia nivelEmergencia) {
		this.nivelEmergencia = nivelEmergencia;
	}
	public Long getQuantidadeRejeito() {
		return quantidadeRejeito;
	}
	public void setQuantidadeRejeito(Long d) {
		this.quantidadeRejeito = d;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Barragem other = (Barragem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}