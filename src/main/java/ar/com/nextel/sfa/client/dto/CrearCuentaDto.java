package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CrearCuentaDto implements IsSerializable {

	DocumentoDto documento;
	Long idOportunidadNegocio;
	
	public CrearCuentaDto() {}
	
	public CrearCuentaDto(DocumentoDto documento, Long idOportunidadNegocio) {
		super();
		this.documento = documento;
		this.idOportunidadNegocio = idOportunidadNegocio;
	}
	
	public DocumentoDto getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoDto documento) {
		this.documento = documento;
	}
	public Long getIdOportunidadNegocio() {
		return idOportunidadNegocio;
	}
	public void setIdOportunidadNegocio(Long idOportunidadNegocio) {
		this.idOportunidadNegocio = idOportunidadNegocio;
	}
	
	
}
