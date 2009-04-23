package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DocumentoDto implements IsSerializable {

    public TipoDocumentoDto tipoDocumento;
    public String numero;
    
    public DocumentoDto() {
    }
    
    public DocumentoDto(String numero, TipoDocumentoDto tipoDocumento) {
		super();
		this.numero = numero;
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public TipoDocumentoDto getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoDto tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
}
