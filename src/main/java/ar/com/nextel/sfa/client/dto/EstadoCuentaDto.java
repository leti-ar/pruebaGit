package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EstadoCuentaDto extends EnumDto implements IsSerializable {

	public EstadoCuentaDto() { }
	
	 public EstadoCuentaDto(long id, String desc) {
		 super();
		 this.id=id;
		 this.descripcion=desc;
	 }
	
}
