package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudDto implements IsSerializable, ListBoxItem {

	private Long id;
	private Long idTipoSolicitudBase;
	private String descripcion;
	private List<ListaPreciosDto> listasPrecios;

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

	public Long getIdTipoSolicitudBase() {
		return idTipoSolicitudBase;
	}

	public void setIdTipoSolicitudBase(Long idTipoSolicitudBase) {
		this.idTipoSolicitudBase = idTipoSolicitudBase;
	}

	public List<ListaPreciosDto> getListasPrecios() {
		return listasPrecios;
	}

	public void setListasPrecios(List<ListaPreciosDto> listasPrecios) {
		this.listasPrecios = listasPrecios;
	}

}
