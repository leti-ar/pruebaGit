package ar.com.nextel.sfa.client.dto;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioCerradaDto implements IsSerializable {
	
	private String numeroCuenta;
	private String numeroSS;
	private String razonSocial;
	private Long cantidadEquipos;
	private Boolean pataconex;
	private Boolean firmas;
	private Date fechaCierreDesde;
	private Date fechaCierreHasta;
	private String estado;
	private Long cantidadResultados;

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


//	public List<CambiosSolicitudServicioDto> getCambios() {
//		return cambios;
//	}
//
//	public void setCambios(List<CambiosSolicitudServicioDto> cambios) {
//		this.cambios = cambios;
//	}
	


	public Long getCantidadResultados() {
		return cantidadResultados;
	}

	public void setCantidadResultados(Long cantidadResultados) {
		this.cantidadResultados = cantidadResultados;
	}

	public String getEstado() {
		return estado;
	}

	public Date getFechaCierreDesde() {
		return fechaCierreDesde;
	}

	public void setFechaCierreDesde(Date fechaCierreDesde) {
		this.fechaCierreDesde = fechaCierreDesde;
	}

	public Date getFechaCierreHasta() {
		return fechaCierreHasta;
	}

	public void setFechaCierreHasta(Date fechaCierreHasta) {
		this.fechaCierreHasta = fechaCierreHasta;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setPataconex(Boolean pataconex) {
		this.pataconex = pataconex;
	}
	
}
