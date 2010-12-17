package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalLineaTransfSolicitudServicioDto implements IsSerializable, IdentifiableDto, Cloneable{

	private Long id;
	private ServicioAdicionalDto servicioAdicional;
	private Double precioLista;
	private Double precioVenta;
	
	//private LineaTransfSolicitudServicio linea;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public ServicioAdicionalDto getServicioAdicional() {
		return servicioAdicional;
	}
	
	public void setServicioAdicional(ServicioAdicionalDto servicioAdicional) {
		this.servicioAdicional = servicioAdicional;
	}
	
	public Double getPrecioLista() {
		return precioLista;
	}
	
	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}
	
	public Double getPrecioVenta() {
		return precioVenta;
	}
	
	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}
}
