package ar.com.nextel.sfa.client.dto;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Clase: CuentaPotencialDto
 * @author eSalvador
 */

public class CuentaPotencialDto	implements IsSerializable {

	private String codigoVantive;
    private String numero;
    private String razonSocial;
    private Set<TelefonoDto> telefono;

    public String getCodigoVantive() {
		return codigoVantive;
	}
    
	public void setCodigoVantive(String codigoVantive) {
		this.codigoVantive = codigoVantive;
	}
	
	public String getNumero() {
		return numero;
	}	
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
    public Set<TelefonoDto> getTelefono() {
		return telefono;
	}
	
	public String getRazonSocial(){
		return this.razonSocial;
	}

	/**
	 * Se invoca desde OperacionesRpcServiceImpl 
	 * @param: persona
	 **/
	public void setRazonSocial(String razonSocial){
		this.razonSocial = razonSocial;
	}

	/**
	 * Se invoca desde OperacionesRpcServiceImpl 
	 * @param: persona
	 **/
	public void setTelefono(Set<TelefonoDto> telefono) {
		this.telefono = telefono;
	}
}

