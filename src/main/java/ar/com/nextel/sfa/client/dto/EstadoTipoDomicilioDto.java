package ar.com.nextel.sfa.client.dto;

public enum EstadoTipoDomicilioDto {
	PRINCIPAL(2L, "Principal"), SI(0L, "Si"), NO(1L, "No");
	
	private Long id;
	private String descripcion;
		
	private EstadoTipoDomicilioDto(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
