package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResumenEquipoDto implements IsSerializable {

    private String razonSocial;
    private String numeroCliente;
    private String facturaNumero;
    private String flota;
    private String emision;
    private List<EquipoDto> equipos;
	
    
    public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNumeroCliente() {
		return numeroCliente;
	}
	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}
	public String getFacturaNumero() {
		return facturaNumero;
	}
	public void setFacturaNumero(String facturaNumero) {
		this.facturaNumero = facturaNumero;
	}
	public String getFlota() {
		return flota;
	}
	public void setFlota(String flota) {
		this.flota = flota;
	}
	public String getEmision() {
		return emision;
	}
	public void setEmision(String emision) {
		this.emision = emision;
	}
	public List<EquipoDto> getEquipos() {
		return equipos;
	}
	public void setEquipos(List<EquipoDto> equipos) {
		this.equipos = equipos;
	}
	
}
