package ar.com.nextel.sfa.client.enums;

public enum TipoDomicilioAsociadoEnum {
	ENTREGA     (1), 
	FACTURACION (4);
	private long id;
	TipoDomicilioAsociadoEnum(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
}
