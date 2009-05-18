package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoTelefonoDto extends EnumDto implements IsSerializable {

	public TipoTelefonoDto() {
	}
	
	public TipoTelefonoDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
}
