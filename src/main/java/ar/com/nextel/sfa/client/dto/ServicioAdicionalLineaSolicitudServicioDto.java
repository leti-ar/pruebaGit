package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalLineaSolicitudServicioDto implements IsSerializable {

	private Double precioVenta;
	private ServicioAdicionalDto servicioAdicional;

	private Long ordenAparicion;
	private String codigoBSCS;
	private Double precioLista;
	private String descripcionServicioAdicional;
	private Boolean unicaVez;
	private Boolean esGarantia;
	private Boolean esAlquiler;
	private Boolean esWap;
	private Boolean esTethered;
	private Boolean requiereDAE;
	private Boolean checked;
	private Boolean obligatorio;

	public Long getOrdenAparicion() {
		return ordenAparicion;
	}

	public void setOrdenAparicion(Long ordenAparicion) {
		this.ordenAparicion = ordenAparicion;
	}

	public String getCodigoBSCS() {
		return codigoBSCS;
	}

	public void setCodigoBSCS(String codigoBSCS) {
		this.codigoBSCS = codigoBSCS;
	}

	public Double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}

	public String getDescripcionServicioAdicional() {
		return descripcionServicioAdicional;
	}

	public void setDescripcionServicioAdicional(String descripcionServicioAdicional) {
		this.descripcionServicioAdicional = descripcionServicioAdicional;
	}

	public Boolean getUnicaVez() {
		return unicaVez;
	}

	public void setUnicaVez(Boolean unicaVez) {
		this.unicaVez = unicaVez;
	}

	public Boolean getEsGarantia() {
		return esGarantia;
	}

	public void setEsGarantia(Boolean esGarantia) {
		this.esGarantia = esGarantia;
	}

	public Boolean getEsAlquiler() {
		return esAlquiler;
	}

	public void setEsAlquiler(Boolean esAlquiler) {
		this.esAlquiler = esAlquiler;
	}

	public Boolean getEsWap() {
		return esWap;
	}

	public void setEsWap(Boolean esWap) {
		this.esWap = esWap;
	}

	public Boolean getEsTethered() {
		return esTethered;
	}

	public void setEsTethered(Boolean esTethered) {
		this.esTethered = esTethered;
	}

	public Boolean getRequiereDAE() {
		return requiereDAE;
	}

	public void setRequiereDAE(Boolean requiereDAE) {
		this.requiereDAE = requiereDAE;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public ServicioAdicionalDto getServicioAdicional() {
		return servicioAdicional;
	}

	public void setServicioAdicional(ServicioAdicionalDto servicioAdicional) {
		this.servicioAdicional = servicioAdicional;
	}

}
