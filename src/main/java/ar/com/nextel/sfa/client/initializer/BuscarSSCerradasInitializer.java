package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BuscarSSCerradasInitializer implements IsSerializable {

	private List<String> opcionesFirmas = new ArrayList<String>();
	private List<String> opcionesPatacones = new ArrayList<String>();
	private List<String> opcionesEstado = new ArrayList<String>();
	private List<String> cantidadesResultados = new ArrayList<String>();
	
	public BuscarSSCerradasInitializer() {		
	};  
	
	public BuscarSSCerradasInitializer(
			List<String> cantidadesResultados,
			List<String> opcionesFirmas, List<String> opcionesPatacones,
			List<String> opcionesEstado) {
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

	public List<String> getOpcionesEstado() {
		return opcionesEstado;
	}

	public void setOpcionesEstado(List<String> opcionesEstado) {
		this.opcionesEstado = opcionesEstado;
	}

	public void setCantidadesResultados(List<String> cantidadesResultados) {
		this.cantidadesResultados = cantidadesResultados;
	}
	
	public List<String> getCantidadesResultados() {
		return cantidadesResultados;
	}
}
