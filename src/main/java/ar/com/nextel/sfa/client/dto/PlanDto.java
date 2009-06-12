package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlanDto extends EnumDto implements IsSerializable, ListBoxItem {

	private Double precio;
	private List<ModalidadCobroDto> modalidadesCobro;
	private TipoPlanDto tipoPlan;
	private TipoTelefoniaDto tipoTelefonia;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public List<ModalidadCobroDto> getModalidadesCobro() {
		return modalidadesCobro;
	}

	public void setModalidadesCobro(List<ModalidadCobroDto> modalidadesCobro) {
		this.modalidadesCobro = modalidadesCobro;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public TipoPlanDto getTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(TipoPlanDto tipoPlan) {
		this.tipoPlan = tipoPlan;
	}

	public TipoTelefoniaDto getTipoTelefonia() {
		return tipoTelefonia;
	}

	public void setTipoTelefonia(TipoTelefoniaDto tipoTelefonia) {
		this.tipoTelefonia = tipoTelefonia;
	}

}
