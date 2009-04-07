package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioDto implements IsSerializable {

	private String numeroSS;
	private String numeroCuenta;
	private String razonSocial;
	private Long cantidadEquipos;
	private String pataconex;
	private Boolean firmas;
	private int cantidadResultados;
	private int offset;

	public String getNumeroSS() {
		return numeroSS;
	}

	public void setNumeroSS(String numeroSS) {
		this.numeroSS = numeroSS;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getCantidadEquipos() {
		return cantidadEquipos;
	}

	public void setCantidadEquipos(Long cantidadEquipos) {
		this.cantidadEquipos = cantidadEquipos;
	}

	public String getPataconex() {
		return pataconex;
	}

	public void setPataconex(String pataconex) {
		this.pataconex = pataconex;
	}

	public Boolean getFirmas() {
		return firmas;
	}

	public void setFirmas(Boolean firmas) {
		this.firmas = firmas;
	}

	public int getCantidadResultados() {
		return cantidadResultados;
	}

	public void setCantidadResultados(int cantidadResultados) {
		this.cantidadResultados = cantidadResultados;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
