package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModeloDto extends EnumDto implements ListBoxItem, IsSerializable {

	private List<ItemSolicitudTasadoDto> items;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public List<ItemSolicitudTasadoDto> getItems() {
		return items;
	}

	public void setItems(List<ItemSolicitudTasadoDto> items) {
		this.items = items;
	}

}
