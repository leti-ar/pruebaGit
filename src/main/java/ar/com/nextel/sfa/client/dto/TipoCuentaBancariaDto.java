package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoCuentaBancariaDto extends EnumDto implements ListBoxItem, IsSerializable {
    
	private Long id;
	private String codigoVantive;
    private String descripcion;

    public TipoCuentaBancariaDto() { }
    
    public TipoCuentaBancariaDto(long id,String codigoVantive, String descripcion) {
		super();
		this.codigoVantive = codigoVantive;
		this.descripcion = descripcion;
		this.id = id;
	}
	public String getItemText() {
		return descripcion;
	}
	public String getItemValue() {
		return id + "";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
