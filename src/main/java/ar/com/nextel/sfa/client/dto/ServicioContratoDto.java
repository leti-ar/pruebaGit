package ar.com.nextel.sfa.client.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioContratoDto implements IsSerializable, Comparable {

	private Long id;
	private Long idContrato;
	private String servicio;
	private String msisdn;
	private Date fechaEstado;
	private Double tarifa;
	private String ajuste;
	private Date ultimaFactura;
	private int periodosPendientes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public Double getTarifa() {
		return tarifa;
	}
	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}
	public String getAjuste() {
		return ajuste;
	}
	public void setAjuste(String ajuste) {
		this.ajuste = ajuste;
	}
	public Date getUltimaFactura() {
		return ultimaFactura;
	}
	public void setUltimaFactura(Date ultimaFactura) {
		this.ultimaFactura = ultimaFactura;
	}
	public int getPeriodosPendientes() {
		return periodosPendientes;
	}
	public void setPeriodosPendientes(int periodosPendientes) {
		this.periodosPendientes = periodosPendientes;
	}

	public int compareTo(Object o) {
		ServicioContratoDto servicioContrato = (ServicioContratoDto) o;
		if (this.tarifa.compareTo(servicioContrato.tarifa) == 0) {
			return this.servicio.compareToIgnoreCase(servicioContrato.servicio);
		} else {
			return servicioContrato.tarifa.compareTo(this.tarifa);
		}
	}
	
}