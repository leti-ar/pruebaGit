package ar.com.nextel.sfa.client.initializer;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.ClaseCuentaDto;
import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.RubroDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;

import com.google.gwt.user.client.rpc.IsSerializable;


public class AgregarCuentaInitializer implements IsSerializable {

	private List<TipoDocumentoDto> tiposDocumento = new ArrayList<TipoDocumentoDto>();
	private List<TipoContribuyenteDto> tiposContribuyentes = new ArrayList<TipoContribuyenteDto>();
	private List<SexoDto> sexo = new ArrayList<SexoDto>();
	private List<ModalidadCobroDto> modalidadCobro = new ArrayList<ModalidadCobroDto>();
	private List<RubroDto> rubro = new ArrayList<RubroDto>();
	private List<ClaseCuentaDto> claseCliente = new ArrayList<ClaseCuentaDto>();
	
	public AgregarCuentaInitializer() {	};  
	
	public AgregarCuentaInitializer(
			List<TipoDocumentoDto> tiposDocumento,
			List<TipoContribuyenteDto> tiposContribuyentes,
			List<SexoDto> sexo,
			List<ModalidadCobroDto> modalidadCobro,
			List<RubroDto> rubro,
			List<ClaseCuentaDto> claseCliente) {
		super();
		this.tiposDocumento = tiposDocumento;
		this.tiposContribuyentes = tiposContribuyentes;
		this.sexo=sexo;
		this.modalidadCobro = modalidadCobro;
		this.rubro = rubro;
		this.claseCliente=claseCliente;
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
	public List<RubroDto> getRubro() {
		return rubro;
	}
	public void setRubro(List<RubroDto> rubro) {
		this.rubro = rubro;
	}

	public List<SexoDto> getSexo() {
		return sexo;
	}

	public void setSexo(List<SexoDto> sexo) {
		this.sexo = sexo;
	}



	public List<ModalidadCobroDto> getModalidadCobro() {
		return modalidadCobro;
	}

	public void setModalidadCobro(List<ModalidadCobroDto> modalidadCobro) {
		this.modalidadCobro = modalidadCobro;
	}

	public List<ClaseCuentaDto> getClaseCliente() {
		return claseCliente;
	}

	public void setClaseCliente(List<ClaseCuentaDto> claseCliente) {
		this.claseCliente = claseCliente;
	}
}
