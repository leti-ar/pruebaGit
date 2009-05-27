package ar.com.nextel.sfa.client.enums;

public enum EstadoDomicilioEnum {
	ACTIVO   (1L), 
	INACTIVO (2L);
	private Long id;
	EstadoDomicilioEnum(Long id) {
		this.id=id;
	}
	public Long getId() {
		return id;
	}
}
