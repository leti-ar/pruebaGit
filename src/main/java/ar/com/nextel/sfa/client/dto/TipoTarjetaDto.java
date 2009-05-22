package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoTarjetaDto implements ListBoxItem, IsSerializable {
    private long id;
	private String codigoVantive;
    private String descripcion;
    private Integer cantidadDigitos;
    
    public TipoTarjetaDto() { }

    public TipoTarjetaDto(long id, String descripcion) {
		super();
		this.descripcion = descripcion;
		this.id = id;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id + "";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigoVantive() {
		//return codigoVantive;
		return descripcion;
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
	public Integer getCantidadDigitos() {
		return cantidadDigitos;
	}
	public void setCantidadDigitos(Integer cantidadDigitos) {
		this.cantidadDigitos = cantidadDigitos;
	}
}
