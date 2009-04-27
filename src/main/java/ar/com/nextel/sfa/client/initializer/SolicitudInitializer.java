package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.OrigenSolicitudDto;
import ar.com.nextel.sfa.client.dto.TipoAnticipoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudInitializer implements IsSerializable {

	private List<OrigenSolicitudDto> origenesSolicitud;
	private List<TipoAnticipoDto> tiposAnticipo;

	public void setOrigenesSolicitud(List<OrigenSolicitudDto> origenesSolicitud) {
		this.origenesSolicitud = origenesSolicitud;
	}

	public List<OrigenSolicitudDto> getOrigenesSolicitud() {
		return origenesSolicitud;
	}

	public void setTiposAnticipo(List<TipoAnticipoDto> tiposAnticipo) {
		this.tiposAnticipo = tiposAnticipo;
	}

	public List<TipoAnticipoDto> getTiposAnticipo() {
		return tiposAnticipo;
	}
}
