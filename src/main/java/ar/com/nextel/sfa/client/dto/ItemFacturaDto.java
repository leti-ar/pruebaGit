package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * Items de factura
 * @author mrotger
 *
 */
public class ItemFacturaDto implements IsSerializable{

	private Long id;
	
	private String descripcion;

	private Long idFinancials;

	private Integer cantidad;
	
	private Float precioUnitario;
	
	private String serie;

    private String imei;

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getIdFinancials() {
		return idFinancials;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public Float getPrecioUnitario() {
		return precioUnitario;
	}

	public String getSerie() {
		return serie;
	}

	public String getImei() {
		return imei;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setIdFinancials(Long idFinancials) {
		this.idFinancials = idFinancials;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setPrecioUnitario(Float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
