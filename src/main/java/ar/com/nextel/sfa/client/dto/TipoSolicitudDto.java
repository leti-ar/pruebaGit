package ar.com.nextel.sfa.client.dto;

import java.util.Collections;
import java.util.List;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudDto extends EnumDto implements IsSerializable, ListBoxItem {

	private TipoSolicitudBaseDto tipoSolicitudBase;
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

	public TipoSolicitudBaseDto getTipoSolicitudBase() {
		return tipoSolicitudBase;
	}

	public void setTipoSolicitudBase(TipoSolicitudBaseDto tipoSolicitudBase) {
		this.tipoSolicitudBase = tipoSolicitudBase;
	}

	public String getFormaContratacion() {
		return formaContratacion;
	}

	public void setFormaContratacion(String formaContratacion) {
		this.formaContratacion = formaContratacion;
	}

	public List<ListaPreciosDto> getListasPrecios() {
		//MGR - #998 - La lista de precio debe estar ordenada por el campo Orden_Aparicion
		if(this.listasPrecios != null && !this.listasPrecios.isEmpty()){
			Collections.sort(this.listasPrecios);
		}
		return listasPrecios;
	}

	public void setListasPrecios(List<ListaPreciosDto> listasPrecios) {
		this.listasPrecios = listasPrecios;
	}

}
