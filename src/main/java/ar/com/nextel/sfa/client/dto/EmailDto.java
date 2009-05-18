package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EmailDto implements IsSerializable {

	private TipoEmailDto tipoEmail;
    private String email;
    private boolean enCarga = true;
	
    public EmailDto() {}
    
    public EmailDto(String email, boolean enCarga, TipoEmailDto tipoEmail) {
		super();
		this.email = email;
		this.enCarga = enCarga;
		this.tipoEmail = tipoEmail;
	}
    
	public TipoEmailDto getTipoEmail() {
		return tipoEmail;
	}
	public void setTipoEmail(TipoEmailDto tipoEmail) {
		this.tipoEmail = tipoEmail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnCarga() {
		return enCarga;
	}
	public void setEnCarga(boolean enCarga) {
		this.enCarga = enCarga;
	}
}
