package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoDomicilioDto  extends EnumDto implements IsSerializable {

	public EstadoDomicilioDto() {}
	
	public EstadoDomicilioDto(Long id, String desc) {
		super();
		this.id=id;
		this.descripcion=desc;
	}
	
}
