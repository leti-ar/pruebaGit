package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DescuentoLineaDto implements IsSerializable, IdentifiableDto {
	
	private Long id;
	private Long idDescuento;
	private Long idLinea;
	private Double monto;
	private Double porcentaje;

	private String descripcionTipoDescuento;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdDescuento() {
		return idDescuento;
	}
	
	public void setIdDescuento(Long idDescuento) {
		this.idDescuento = idDescuento;
	}
	
	public Long getIdLinea() {
		return idLinea;
	}
	
	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}
	
	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getDescripcionTipoDescuento() {
		return descripcionTipoDescuento;
	}

	public void setDescripcionTipoDescuento(String descripcionTipoDescuento) {
		this.descripcionTipoDescuento = descripcionTipoDescuento;
	}

}
