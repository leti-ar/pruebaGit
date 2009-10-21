package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClaseCuentaDto extends EnumDto implements IsSerializable, ListBoxItem  {

	public static long ID_GOBIERNO = 6;
	public static long ID_GOB_BS_AS = 50;
	
	public ClaseCuentaDto() {
	}
	public ClaseCuentaDto(long id, String descripcion) {
		super();
		this.id=id;
		this.descripcion=descripcion;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id + "";
	}
}
