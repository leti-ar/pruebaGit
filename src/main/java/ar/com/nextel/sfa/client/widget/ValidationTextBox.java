package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

public class ValidationTextBox extends RegexTextBox{

	private boolean excluyente;
	private boolean activo;

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public ValidationTextBox(String string) {
		super(string);
	}

	public boolean isExcluyente() {
		return excluyente;
	}

	public void setExcluyente(boolean excluyente) {
		this.excluyente = excluyente;
	}
	
}
