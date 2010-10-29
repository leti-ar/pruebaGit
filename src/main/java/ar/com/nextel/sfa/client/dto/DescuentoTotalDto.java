package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DescuentoTotalDto implements IsSerializable, IdentifiableDto {

	private List<DescuentoDto> descuentos = new ArrayList<DescuentoDto>();
	private List<TipoDescuentoDto> tiposDescuento = new ArrayList<TipoDescuentoDto>();
	private Long idLinea;

	public List<DescuentoDto> getDescuentos() {
		return descuentos;
	}

	public void setDescuentos(List<DescuentoDto> descuentos) {
		this.descuentos = descuentos;
	}

	public List<TipoDescuentoDto> getTiposDescuento() {
		return tiposDescuento;
	}

	public void setTiposDescuento(List<TipoDescuentoDto> tiposDescuento) {
		this.tiposDescuento = tiposDescuento;
	}
	
	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	public Long getId() {
		return getIdLinea();
	}

}
