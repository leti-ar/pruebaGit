package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

public class RegistroVendedorDto implements IsSerializable, ListBoxItem {

	private Long id;
	private String userName;
	private PuntoVentaDto puntoVenta;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PuntoVentaDto getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVentaDto puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	//el combo PDV se carga con el userName + la descripción del punto de venta (si la tuviera)
	public String getItemText() {
		return puntoVenta != null ? userName + " " + puntoVenta.getDescripcion() : userName;
	}

	public String getItemValue() {
		return puntoVenta != null ? puntoVenta.getId() + "" : "";
	}

}
