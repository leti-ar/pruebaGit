package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ListaPreciosDto extends EnumDto implements IsSerializable, ListBoxItem {

	private List<TerminoPagoValidoDto> terminosPagoValido;
	private List<ItemSolicitudTasadoDto> itemsListaPrecioVisibles;
	private List<AjustesDto> ajustes;
	private Boolean despacho;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
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

	public void setDespacho(Boolean despacho) {
		this.despacho = despacho;
	}

	public Boolean getDespacho() {
		return despacho;
	}
}
