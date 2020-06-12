package br.edu.puc.barragem.domain;

public enum CategoriaRisco {
    ALTA(3),
    MEDIA(2),
    BAIXA(1);

    public final Integer risco;

    private CategoriaRisco(Integer risco){
        this.risco = risco;
    }

	public Integer getRisco() {
		return risco;
	}
}