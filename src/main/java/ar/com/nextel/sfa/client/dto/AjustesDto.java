package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AjustesDto implements IsSerializable {

	private Long id;
	private Double porcentaje;
	private String descripcion;
	private TerminoPagoDto terminoPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TerminoPagoDto getTerminoPago() {
		return terminoPago;
	}

	public void setTerminoPago(TerminoPagoDto terminoPago) {
		this.terminoPago = terminoPago;
	}

}
