package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.TipoPlanDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContratoViewInitializer implements IsSerializable {

	private List<TipoPlanDto> tiposPlanes;

	public List<TipoPlanDto> getTiposPlanes() {
		return tiposPlanes;
	}

	public void setTiposPlanes(List<TipoPlanDto> tiposPlanes) {
		this.tiposPlanes = tiposPlanes;
	}

}
