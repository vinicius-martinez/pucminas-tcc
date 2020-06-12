package br.edu.puc.barragem.domain;

public enum Classe {
    CLASSE_A(5),
    CLASSE_B(4),
    CLASSE_C(3),
    CLASSE_D(2),
    CLASSE_E(1);

    public final Integer classe;

    private Classe(Integer classe){
        this.classe = classe;
    }

	public Integer getClasse() {
		return classe;
	}

}