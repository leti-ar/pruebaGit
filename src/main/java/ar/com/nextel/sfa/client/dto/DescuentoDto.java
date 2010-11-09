package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DescuentoDto implements IsSerializable, IdentifiableDto {
	
	private Long id;
	private Long idTipoDescuento;
	private List<DescuentoLineaDto> descuentosLinea = new ArrayList<DescuentoLineaDto>();
	private String codigoDescuento;
	private Long operand;
	private boolean sobreescribir;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<DescuentoLineaDto> getDescuentosLinea() {
		return descuentosLinea;
	}
	
	public void setDescuentosLinea(List<DescuentoLineaDto> descuentosLinea) {
		this.descuentosLinea = descuentosLinea;
	}

	public Long getIdTipoDescuento() {
		return idTipoDescuento;
	}
	
	public void setIdTipoDescuento(Long idTipoDescuento) {
		this.idTipoDescuento = idTipoDescuento;
	}
	
	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public void setCodigoDescuento(String codigoDescuento) {
		this.codigoDescuento = codigoDescuento;
	}

	public Long getOperand() {
		return operand;
	}

	public void setOperand(Long operand) {
		this.operand = operand;
	}

	public boolean isSobreescribir() {
		return sobreescribir;
	}

	public void setSobreescribir(boolean sobreescribir) {
		this.sobreescribir = sobreescribir;
	}

}
