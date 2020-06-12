package br.edu.puc.barragem.domain;

public enum NivelEmergencia {
    NIVEL_1(1),
    NIVEL_2(2),
    NIVEL_3(3);

    public final Integer nivel;

    private NivelEmergencia(Integer nivel){
        this.nivel = nivel;
    }

	public Integer getNivel() {
		return nivel;
    }
    
}