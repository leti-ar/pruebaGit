package ar.com.nextel.sfa.client.enums;

public enum BuscoCuentaPorDniEnum {
	SI("1"),
	NO("0");
	private String condicion;
	BuscoCuentaPorDniEnum(String c) {
		condicion=c;
	}
	public String getCondicion() {
		return condicion;
	}
}
