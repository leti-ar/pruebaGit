package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalLineaSolicitudServicioDto implements IsSerializable, IdentifiableDto {

	private Long id;
	private Double precioVenta;
	private ServicioAdicionalDto servicioAdicional;

	private Long ordenAparicion;
	private String codigoBSCS;
	private Double precioLista;
	private String descripcionServicioAdicional;
	private boolean unicaVez = false;
	private boolean esGarantia = false;
	private boolean esAlquiler = false;
	private boolean esWap = false;
	private boolean esTethered = false;
	private boolean requiereDAE = false;
	private boolean checked = false;
	private boolean obligatorio = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public boolean isUnicaVez() {
		return unicaVez;
	}

	public void setUnicaVez(boolean unicaVez) {
		this.unicaVez = unicaVez;
	}

	public boolean isEsGarantia() {
		return esGarantia;
	}

	public void setEsGarantia(boolean esGarantia) {
		this.esGarantia = esGarantia;
	}

	public boolean isEsAlquiler() {
		return esAlquiler;
	}

	public void setEsAlquiler(boolean esAlquiler) {
		this.esAlquiler = esAlquiler;
	}

	public boolean isEsWap() {
		return esWap;
	}

	public void setEsWap(boolean esWap) {
		this.esWap = esWap;
	}

	public boolean isEsTethered() {
		return esTethered;
	}

	public void setEsTethered(boolean esTethered) {
		this.esTethered = esTethered;
	}

	public boolean isRequiereDAE() {
		return requiereDAE;
	}

	public void setRequiereDAE(boolean requiereDAE) {
		this.requiereDAE = requiereDAE;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((servicioAdicional.getId() == null) ? 0 : servicioAdicional.getId().hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		ServicioAdicionalLineaSolicitudServicioDto other = (ServicioAdicionalLineaSolicitudServicioDto) obj;
		if (servicioAdicional.getId() == null) {
			if (other.servicioAdicional.getId() != null)
				return false;
		} else if (!servicioAdicional.getId().equals(other.servicioAdicional.getId()))
			return false;
		return true;
	}

}
