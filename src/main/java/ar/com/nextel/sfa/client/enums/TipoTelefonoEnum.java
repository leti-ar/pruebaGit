package ar.com.nextel.sfa.client.enums;

public enum TipoTelefonoEnum {
	PARTICULAR(5), PRINCIPAL(1), FAX(3), CELULAR(4), ADICIONAL(6);
	private long tipo;
	TipoTelefonoEnum(long t) {
		tipo=t;
	}
	public long getTipo() {
		return tipo;
	}
}
