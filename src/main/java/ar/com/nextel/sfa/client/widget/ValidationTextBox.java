package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

public class ValidationTextBox extends RegexTextBox implements ExcluyenteWidget{

	private boolean excluyente;

	public ValidationTextBox(String string) {
		super(string);
	}

	public boolean isExcluyente() {
		return excluyente;
	}

	public void setExcluyente(boolean excluyente) {
		this.excluyente = excluyente;
	}
	
	/**
	 * @author esalvador: Metodo creado especificamente para validar un String contra un pattern RegEx especifico. 
	 **/
	public boolean validatePattern(String pattern) {
		return pattern != null ? this.getText().matches(pattern) : true;
	}
	
}
