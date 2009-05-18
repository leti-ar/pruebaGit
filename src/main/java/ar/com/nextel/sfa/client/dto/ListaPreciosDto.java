package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ListaPreciosDto implements IsSerializable, ListBoxItem {

	private Long id;
	private String descripcion;
	private List<TerminoPagoValidoDto> terminosPagoValido;
	private List<ItemSolicitudTasadoDto> itemsListaPrecioVisibles;
	private List<AjustesDto> ajustes;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<TerminoPagoValidoDto> getTerminosPagoValido() {
		return terminosPagoValido;
	}

	public void setTerminosPagoValido(List<TerminoPagoValidoDto> terminosPagoValido) {
		this.terminosPagoValido = terminosPagoValido;
	}

	public List<ItemSolicitudTasadoDto> getItemsListaPrecioVisibles() {
		return itemsListaPrecioVisibles;
	}

	public void setItemsListaPrecioVisibles(List<ItemSolicitudTasadoDto> itemsListaPrecioVisibles) {
		this.itemsListaPrecioVisibles = itemsListaPrecioVisibles;
	}

	public List<AjustesDto> getAjustes() {
		return ajustes;
	}

	public void setAjustes(List<AjustesDto> ajustes) {
		this.ajustes = ajustes;
	}

}
