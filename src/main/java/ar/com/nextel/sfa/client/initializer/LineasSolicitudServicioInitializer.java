package ar.com.nextel.sfa.client.initializer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import ar.com.nextel.sfa.client.dto.LocalidadDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LineasSolicitudServicioInitializer implements IsSerializable {

	private Map<Long, List<TipoSolicitudDto>> tiposSolicitudPorGrupo;
	private List<TipoPlanDto> tiposPlanes;
	private List<LocalidadDto> localidades;

	public Map<Long, List<TipoSolicitudDto>> getTiposSolicitudPorGrupo() {
		return tiposSolicitudPorGrupo;
	}

	public void setTiposSolicitudPorGrupo(Map<Long, List<TipoSolicitudDto>> tiposSolicitudPorGrupo) {
		this.tiposSolicitudPorGrupo = tiposSolicitudPorGrupo;
	}

	public List<TipoPlanDto> getTiposPlanes() {
		return tiposPlanes;
	}

	public void setTiposPlanes(List<TipoPlanDto> tiposPlanes) {
		this.tiposPlanes = tiposPlanes;
	}

	public List<LocalidadDto> getLocalidades() {
		//MGR - #934 - La lista de localidades debe estar ordenada por orden alfabetico
		if(this.localidades != null && !this.localidades.isEmpty()){
			Collections.sort(this.localidades);
		}
		return localidades;
	}

	public void setLocalidades(List<LocalidadDto> localidades) {
		this.localidades = localidades;
	}

}
