package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PrioridadDto implements IsSerializable {
	Long id;
	String descripcion;
    public PrioridadDto() {}
    
    public PrioridadDto(Long id, String desc) {
    	this.id = id;
    	this.descripcion=desc;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
