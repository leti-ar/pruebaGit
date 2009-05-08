package ar.com.nextel.sfa.client.enums;

public enum TipoDocumentoEnum {
	DNI     (1), 
	CI      (2),
	CUIT    (3),
	CUIL    (4),
	LE      (5),
	LC      (6),
	CUIT_EXT(7),
	PAS     (8);
	private long tipo;
	TipoDocumentoEnum(long t) {
		tipo=t;
	}
	public long getTipo() {
		return tipo;
	}
}
