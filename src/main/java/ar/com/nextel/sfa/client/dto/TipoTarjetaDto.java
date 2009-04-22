package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoTarjetaDto implements IsSerializable {
    private String codigoVantive;
    private String descripcion;
    private Integer cantidadDigitos;
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
	public Integer getCantidadDigitos() {
		return cantidadDigitos;
	}
	public void setCantidadDigitos(Integer cantidadDigitos) {
		this.cantidadDigitos = cantidadDigitos;
	}
    
}
