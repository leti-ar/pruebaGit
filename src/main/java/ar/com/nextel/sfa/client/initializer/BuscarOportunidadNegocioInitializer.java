package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.EstadoOportunidadDto;
import ar.com.nextel.sfa.client.dto.GrupoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author eSalvador 
 **/
public class BuscarOportunidadNegocioInitializer implements IsSerializable{
	
	private List<GrupoDocumentoDto> grupoDocumento = new ArrayList<GrupoDocumentoDto>();
	private List<EstadoOportunidadDto> estadoOPP = new ArrayList<EstadoOportunidadDto>();
	
	public BuscarOportunidadNegocioInitializer(){
	}

	public BuscarOportunidadNegocioInitializer(
			List<GrupoDocumentoDto> grupoDocumento,
			List<EstadoOportunidadDto> estadoOPP) {
		super();
		this.grupoDocumento = grupoDocumento;
		this.estadoOPP = estadoOPP;
	}

	public List<EstadoOportunidadDto> getEstadoOPP() {
		return estadoOPP;
	}

	public void setEstadoOPP(List<EstadoOportunidadDto> estadoOPP) {
		this.estadoOPP = estadoOPP;
	}

	public void setGrupoDocumento(List<GrupoDocumentoDto> grupoDocumento) {
		this.grupoDocumento = grupoDocumento;
	}

	public List<GrupoDocumentoDto> getGrupoDocumento() {
		return grupoDocumento;
	}
}