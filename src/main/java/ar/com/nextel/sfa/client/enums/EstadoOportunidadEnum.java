package ar.com.nextel.sfa.client.enums;

public enum EstadoOportunidadEnum {
	ACTIVO     (1L),
	NO_CERRADA (2L),
	CERRADA    (3L);
	private Long id;
	EstadoOportunidadEnum(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
}
