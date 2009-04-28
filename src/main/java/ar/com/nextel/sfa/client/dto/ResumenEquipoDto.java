package ar.com.nextel.sfa.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResumenEquipoDto implements IsSerializable {

	private String razon;
	private String cliente;
	private String factura;
	private String flota;
	private String emision;
	private List<EquipoDto> equipos;

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
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
