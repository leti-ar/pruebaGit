package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaDto implements IsSerializable {

	private List<CambiosSolicitudServicioDto> cambios;
	private String numeroSS;
	private String numeroCuenta;
	private String razonSocial;
	private Long cantidadEquipos;
	private Boolean pataconex;
	private Boolean firmas;
	private Date fechaDesde;
	private Date fechaHasta;
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

	public Boolean getPataconex() {
		return pataconex;
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

	public List<CambiosSolicitudServicioDto> getCambios() {
		return cambios;
	}

	public void setCambios(List<CambiosSolicitudServicioDto> cambios) {
		this.cambios = cambios;
	}
	
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setPataconex(Boolean pataconex) {
		this.pataconex = pataconex;
	}
	
}
