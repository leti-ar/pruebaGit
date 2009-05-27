package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class TipoDomicilioDto extends EnumDto implements IsSerializable{

	public TipoDomicilioDto() {
	}
	
	public TipoDomicilioDto(long id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
}
