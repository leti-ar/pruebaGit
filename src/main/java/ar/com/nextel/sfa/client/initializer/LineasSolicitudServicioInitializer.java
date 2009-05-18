package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.LocalidadDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineasSolicitudServicioInitializer implements IsSerializable {

	private List<TipoSolicitudDto> tiposSolicitudes;
	private List<TipoPlanDto> tiposPlanes;
	private List<LocalidadDto> localidades;

	public void setTiposSolicitudes(List<TipoSolicitudDto> tiposSolicitudes) {
		this.tiposSolicitudes = tiposSolicitudes;
	}

	public List<TipoSolicitudDto> getTiposSolicitudes() {
		return tiposSolicitudes;
	}

	public List<TipoPlanDto> getTiposPlanes() {
		return tiposPlanes;
	}

	public void setTiposPlanes(List<TipoPlanDto> tiposPlanes) {
		this.tiposPlanes = tiposPlanes;
	}

	public List<LocalidadDto> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<LocalidadDto> localidades) {
		this.localidades = localidades;
	}

}
