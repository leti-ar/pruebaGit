package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioSearchDto implements IsSerializable {

	private String numeroSS;
	private String numeroCuenta;
	private String razonSocial;
	private Long cantidadEquipos;
	private String pataconex;
	private String firmas;
	private String fechaDesde;
	private String fechaHasta;
	private String estado;
	private int cantidadResultados;

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

	public String getFirmas() {
		return firmas;
	}

	public void setFirmas(String firmas) {
		this.firmas = firmas;
	}

	public int getCantidadResultados() {
		return cantidadResultados;
	}

	public void setCantidadResultados(int cantidadResultados) {
		this.cantidadResultados = cantidadResultados;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
