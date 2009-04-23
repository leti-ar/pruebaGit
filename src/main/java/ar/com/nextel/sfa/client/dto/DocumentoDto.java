package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DocumentoDto implements IsSerializable {

    private long serialVersionUID = 6783360349446430990L;
    public TipoDocumentoDto tipoDocumento;
    public String numero;
    
    public long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setSerialVersionUID(long serialVersionUID) {
		this.serialVersionUID = serialVersionUID;
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
