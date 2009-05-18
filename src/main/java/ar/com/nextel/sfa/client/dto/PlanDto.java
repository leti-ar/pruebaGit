package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PlanDto implements IsSerializable, ListBoxItem {
	private Long id;
	private String descripcion;
	private Double precio;
	private List<ModalidadCobroDto> modalidadesCobro;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

}
