package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class AgregarCuentaInitializer implements IsSerializable {

	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<TipoContribuyenteDto> tiposContribuyentes = new ArrayList<TipoContribuyenteDto>();
	private List<RubroDto> rubros = new ArrayList<RubroDto>();
	
	public AgregarCuentaInitializer() {		
	};  
	
	public AgregarCuentaInitializer(
			List<TipoDocumentoDto> tiposDocumento,
			List<TipoContribuyenteDto> tiposContribuyentes,
			List<RubroDto> rubros) {
		super();
		this.tiposDocumento = tiposDocumento;
		this.tiposContribuyentes = tiposContribuyentes;
		this.rubros = rubros;
	}
	
	public List<TipoDocumentoDto> getTiposDocumento() {
		return tiposDocumento;
	}
	public void setTiposDocumento(List<TipoDocumentoDto> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}
	public List<TipoContribuyenteDto> getTiposContribuyentes() {
		return tiposContribuyentes;
	}
	public void setTiposContribuyentes(
			List<TipoContribuyenteDto> tiposContribuyentes) {
		this.tiposContribuyentes = tiposContribuyentes;
	}
	public List<RubroDto> getRubros() {
		return rubros;
	}
	public void setRubros(List<RubroDto> rubros) {
		this.rubros = rubros;
	}
	
}
