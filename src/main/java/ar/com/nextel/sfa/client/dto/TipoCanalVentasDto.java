package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoCanalVentasDto implements IsSerializable{
    private String codigoVantive;
    private String descripcion;
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
