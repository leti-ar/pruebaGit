package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoEmailDto implements IsSerializable {
	private long id;
	private String codigoVantive;
    private String descripcion;
//    public TipoEmailDto() {}
//    public TipoEmailDto(long id, String desc) {
//    	super();
//    	this.id=id;
//    	this.descripcion=desc;
//    }
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigoVantive() {
		return codigoVantive;
	}
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
