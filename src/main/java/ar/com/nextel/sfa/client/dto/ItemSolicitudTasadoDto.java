package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudTasadoDto implements IsSerializable, ListBoxItem {

	private Long id;
	private Double precioLista;
	private ItemSolicitudDto item;

	public String getItemText() {
		return item.getDescripcion();
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

	public Double getPrecioLista() {
		return precioLista;
	}

	public void setPrecioLista(Double precioLista) {
		this.precioLista = precioLista;
	}

	public ItemSolicitudDto getItem() {
		return item;
	}

	public void setItem(ItemSolicitudDto item) {
		this.item = item;
	}

}
