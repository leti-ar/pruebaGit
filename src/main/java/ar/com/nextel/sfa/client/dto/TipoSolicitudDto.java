package ar.com.nextel.sfa.client.dto;

import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudDto extends EnumDto implements IsSerializable, ListBoxItem {

	private Long idTipoSolicitudBase;
	private String formaContratacion;
	private List<ListaPreciosDto> listasPrecios;

	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public boolean isVenta() {
		return "venta".equals(formaContratacion != null ? formaContratacion.toLowerCase() : "");
	}

	public boolean isAlquiler() {
		return "alquiler".equals(formaContratacion != null ? formaContratacion.toLowerCase() : "");
	}

	public boolean isActivacion() {
		return "activacion".equals(formaContratacion != null ? formaContratacion.toLowerCase() : "");
	}

	public Long getIdTipoSolicitudBase() {
		return idTipoSolicitudBase;
	}

	public void setIdTipoSolicitudBase(Long idTipoSolicitudBase) {
		this.idTipoSolicitudBase = idTipoSolicitudBase;
	}

	public String getFormaContratacion() {
		return formaContratacion;
	}

	public void setFormaContratacion(String formaContratacion) {
		this.formaContratacion = formaContratacion;
	}

	public List<ListaPreciosDto> getListasPrecios() {
		return listasPrecios;
	}

	public void setListasPrecios(List<ListaPreciosDto> listasPrecios) {
		this.listasPrecios = listasPrecios;
	}

}
