package ar.com.nextel.sfa.client.enums;

public enum TipoDocumentoEnum {
	DNI(1, "96"), CI(2, "00"), CUIT(3, "80"), CUIL(4, "1000"), LE(5, "89"), LC(6, "90"), CUIT_EXT(7, "99"), PAS(
			8, "94"), CUITCUIL(2, "");
	private long tipo;
	private String codigoVantive;

	TipoDocumentoEnum(long tipo, String codigoVantive) {
		this.tipo = tipo;
		this.codigoVantive = codigoVantive;
	}

	public long getTipo() {
		return tipo;
	}

	public String getCodigoVantive() {
		return codigoVantive;
	}

}
