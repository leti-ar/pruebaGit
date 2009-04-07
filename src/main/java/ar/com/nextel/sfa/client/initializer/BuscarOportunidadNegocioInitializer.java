package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class BuscarOportunidadNegocioInitializer implements IsSerializable{
	
	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<EstadoOportunidadDto> estadoOPP = new ArrayList<EstadoOportunidadDto>();
	
	public BuscarOportunidadNegocioInitializer(){
	}
	
	public BuscarOportunidadNegocioInitializer(
			List<EstadoOportunidadDto> estadoOPP,
			List<TipoDocumentoDto> tiposDocumento) {
		super();
		this.estadoOPP = estadoOPP;
		this.tiposDocumento = tiposDocumento;
	}

	public List<TipoDocumentoDto> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<EstadoOportunidadDto> getEstadoOPP() {
		return estadoOPP;
	}

	public void setEstadoOPP(List<EstadoOportunidadDto> estadoOPP) {
		this.estadoOPP = estadoOPP;
	}
}