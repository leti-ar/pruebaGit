package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.EstadoSolicitudDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BuscarSSCerradasInitializer implements IsSerializable {

	private List<String> opcionesFirmas = new ArrayList<String>();
	private List<String> opcionesPatacones = new ArrayList<String>();
	private List<EstadoSolicitudDto> opcionesEstado = new ArrayList<EstadoSolicitudDto>();
	private List<String> cantidadesResultados = new ArrayList<String>();
	private List<TipoDocumentoDto> documentos = new ArrayList<TipoDocumentoDto>();
	
	public BuscarSSCerradasInitializer() {		
	}
	
	public BuscarSSCerradasInitializer(
			List<String> cantidadesResultados,
			List<String> opcionesFirmas, List<String> opcionesPatacones,
			List<EstadoSolicitudDto> opcionesEstado) {
		super();
		this.cantidadesResultados = cantidadesResultados;
		this.opcionesFirmas = opcionesFirmas;
		this.opcionesPatacones = opcionesPatacones;
		this.opcionesEstado = opcionesEstado;
	}

	public List<String> getOpcionesFirmas() {
		return opcionesFirmas;
	}

	public void setOpcionesFirmas(List<String> opcionesFirmas) {
		this.opcionesFirmas = opcionesFirmas;
	}

	public List<String> getOpcionesPatacones() {
		return opcionesPatacones;
	}

	public void setOpcionesPatacones(List<String> opcionesPatacones) {
		this.opcionesPatacones = opcionesPatacones;
	}

	public List<EstadoSolicitudDto> getOpcionesEstado() {
		return opcionesEstado;
	}

	public void setOpcionesEstado(List<EstadoSolicitudDto> opcionesEstado) {
		this.opcionesEstado = opcionesEstado;
	}

	public void setCantidadesResultados(List<String> cantidadesResultados) {
		this.cantidadesResultados = cantidadesResultados;
	}
	
	public List<String> getCantidadesResultados() {
		return cantidadesResultados;
	}

	public List<TipoDocumentoDto> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<TipoDocumentoDto> documentos) {
		this.documentos = documentos;
	}
	
	
}
