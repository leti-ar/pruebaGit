package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModeloDto extends EnumDto implements ListBoxItem, IsSerializable {

	private List<ItemSolicitudTasadoDto> items;
	private boolean esBlackberry;
	private Boolean hasSim;

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

	public boolean isEsBlackberry() {
		return esBlackberry;
	}

	public void setEsBlackberry(boolean esBlackberry) {
		this.esBlackberry = esBlackberry;
	}

	public Boolean getHasSim() {
		return hasSim;
	}

	public void setHasSim(Boolean hasSim) {
		this.hasSim = hasSim;
	}

}
