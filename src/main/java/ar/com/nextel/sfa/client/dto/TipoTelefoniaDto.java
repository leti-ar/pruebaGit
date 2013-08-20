package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoTelefoniaDto extends EnumDto implements ListBoxItem,IsSerializable {

	public static final TipoTelefoniaDto TIPO_PREPAGO = new TipoTelefoniaDto(1, "PREPAGO");

	public TipoTelefoniaDto() {
	}

	public TipoTelefoniaDto(long id, String desc) {
		super();
		this.id = id;
		this.descripcion = desc;
	}

	public String getItemText() {
		// TODO Auto-generated method stub
		return this.descripcion;
	}

	public String getItemValue() {
		// TODO Auto-generated method stub
		return this.id + "";
	}

}
