package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoDescuentoDto implements IsSerializable, IdentifiableDto {

	private Long id;
	private List<DescuentoDto> descuentos = new ArrayList<DescuentoDto>();
	private String descripcion;
	private String codigoFNCL;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<DescuentoDto> getDescuentos() {
		return descuentos;
	}

	public void setDescuentos(List<DescuentoDto> descuentos) {
		this.descuentos = descuentos;
	}
	
	public String getCodigoFNCL() {
		return codigoFNCL;
	}

	public void setCodigoFNCL(String codigoFNCL) {
		this.codigoFNCL = codigoFNCL;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String toString() {
		return this.getDescripcion();
	}

}
