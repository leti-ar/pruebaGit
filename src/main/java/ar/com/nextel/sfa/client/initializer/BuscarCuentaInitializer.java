package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.BusquedaPredefinidaDto;
import ar.com.nextel.sfa.client.dto.CategoriaCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BuscarCuentaInitializer implements IsSerializable {

	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<CategoriaCuentaDto> categorias = new ArrayList<CategoriaCuentaDto>();
	private List<BusquedaPredefinidaDto> busquedasPredefinidas = new ArrayList<BusquedaPredefinidaDto>();
	private List<String> cantidadesResultados = new ArrayList<String>();
	
	public BuscarCuentaInitializer() {		
	};  
	
	public BuscarCuentaInitializer(
			List<BusquedaPredefinidaDto> busquedasPredefinidas,
			List<String> cantidadesResultados, List<CategoriaCuentaDto> categorias,
			List<TipoDocumentoDto> tiposDocumento) {
		super();
		this.busquedasPredefinidas = busquedasPredefinidas;
		this.cantidadesResultados = cantidadesResultados;
		this.categorias = categorias;
		this.tiposDocumento = tiposDocumento;
	}

	public List<TipoDocumentoDto> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<CategoriaCuentaDto> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaCuentaDto> categorias) {
		this.categorias = categorias;
	}

	public List<BusquedaPredefinidaDto> getBusquedasPredefinidas() {
		return  busquedasPredefinidas;
	}

	public void setBusquedasPredefinidas(List<BusquedaPredefinidaDto> busquedasPredefinidas) {
		this.busquedasPredefinidas = busquedasPredefinidas;
	}

	public void setCantidadesResultados(List<String> cantidadesResultados) {
		this.cantidadesResultados = cantidadesResultados;
	}
	
	public List<String> getCantidadesResultados() {
		return cantidadesResultados;
	}
}
