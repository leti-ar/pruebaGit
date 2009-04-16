package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

/**
 * 
 * @author mrial
 *
 */

public class VerazInitializer implements IsSerializable {

	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<SexoDto> sexos = new ArrayList<SexoDto>();

	public VerazInitializer() {
		
	}
	
	public VerazInitializer(
			List<TipoDocumentoDto> tiposDocumento,
			List<SexoDto> sexos) {
		this.tiposDocumento = tiposDocumento;
		this.sexos = sexos;
	}

	public List<TipoDocumentoDto> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<SexoDto> getSexos() {
		return sexos;
	}

	public void setSexos(List<SexoDto> sexos) {
		this.sexos = sexos;
	}

}
