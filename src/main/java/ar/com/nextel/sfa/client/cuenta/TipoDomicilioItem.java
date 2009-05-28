package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.TipoDomicilioDto;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;

/**
 *@author eSalvador
 **/
public class TipoDomicilioItem extends ListBoxItemImpl{

	private String text;
	private String value;
	private boolean principal;
	private String textoAMostrar;
	
	public TipoDomicilioItem(TipoDomicilioDto tipoDomicilio, boolean principal, String texto){
		super();
		if(tipoDomicilio != null){
			value = tipoDomicilio.getId().toString();
			text = tipoDomicilio.getDescripcion();
			textoAMostrar = texto;
		}else{
			value = "null";
			text = "null";
			textoAMostrar = "No";
		}
		this.principal = principal;
	}
	
//	public void apply(DomiciliosCuentaDto domicilio){
//		/**TODO: Logica de cuando aprete el boton Guardar! */
//		TipoDomicilioDto tipoDomicilioDto = new TipoDomicilioDto();
//		TipoDomicilioAsociadoDto tipoDomicilioAsociado = domicilio.getTipoDomicilioAsociado(tipoDomicilioDto);
//	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getItemText() {
		return textoAMostrar;
	}
	
	public String getTextoNo() {
		return text;
	}
	
	public String getItemValue() {
		return value;
	}

	public String getTextoAMostrar() {
		return textoAMostrar;
	}

	public void setTextoAMostrar(String textoAMostrar) {
		this.textoAMostrar = textoAMostrar;
	}
}
