package ar.com.nextel.sfa.client.dto;

public class DocumentoDto {

    private long serialVersionUID = 6783360349446430990L;
    public String idTipo;
    public String descripcionTipo;    
    public String numero;
	
    
    public long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setSerialVersionUID(long serialVersionUID) {
		this.serialVersionUID = serialVersionUID;
	}
	public String getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
