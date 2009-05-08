package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineasSolicitudServicioInitializer implements IsSerializable {

	private List<TipoSolicitudDto> tiposSolicitudes;
	private List<ListaPreciosDto> listasPrecios;

	public void setTiposSolicitudes(List<TipoSolicitudDto> tiposSolicitudes) {
		this.tiposSolicitudes = tiposSolicitudes;
	}

	public List<TipoSolicitudDto> getTiposSolicitudes() {
		return tiposSolicitudes;
	}

	public List<ListaPreciosDto> getListasPrecios() {
		return listasPrecios;
	}

	public void setListasPrecios(List<ListaPreciosDto> listasPrecios) {
		this.listasPrecios = listasPrecios;
	}

}
