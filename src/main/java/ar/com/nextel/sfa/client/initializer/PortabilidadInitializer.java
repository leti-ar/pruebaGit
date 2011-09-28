package ar.com.nextel.sfa.client.initializer;

import java.util.List;

import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.ProveedorDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PortabilidadInitializer implements IsSerializable{
	private PersonaDto persona;
	private List<TipoDocumentoDto> lstTipoDocumento;
	private List<ProveedorDto> lstProveedorAnterior;
	private List<TipoTelefoniaDto> lstTipoTelefonia;
	private List<ModalidadCobroDto> lstModalidadCobro;
	
	public PortabilidadInitializer(){
		
	}

	public PortabilidadInitializer(List<TipoDocumentoDto> unTipoDocumento, 
			List<ProveedorDto> unProveedorAnterior,
			List<TipoTelefoniaDto> unTipoTelefonia,
			List<ModalidadCobroDto> unModalidadCobro){
		this.lstTipoDocumento = unTipoDocumento;
		this.lstTipoTelefonia = unTipoTelefonia;
		this.lstModalidadCobro = unModalidadCobro;
		this.lstProveedorAnterior = unProveedorAnterior;
	}

	public List<TipoDocumentoDto> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<TipoDocumentoDto> lsTipoDocumento) {
		this.lstTipoDocumento = lsTipoDocumento;
	}

	public List<ProveedorDto> getLstProveedorAnterior() {
		return lstProveedorAnterior;
	}

	public void setLstProveedorAnterior(List<ProveedorDto> lstProveedorAnterior) {
		this.lstProveedorAnterior = lstProveedorAnterior;
	}

	public List<TipoTelefoniaDto> getLstTipoTelefonia() {
		return lstTipoTelefonia;
	}

	public void setLstTipoTelefonia(List<TipoTelefoniaDto> lstTipoTelefonia) {
		this.lstTipoTelefonia = lstTipoTelefonia;
	}

	public List<ModalidadCobroDto> getLstModalidadCobro() {
		return lstModalidadCobro;
	}

	public void setLstModalidadCobro(List<ModalidadCobroDto> lstModalidadCobro) {
		this.lstModalidadCobro = lstModalidadCobro;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
}
