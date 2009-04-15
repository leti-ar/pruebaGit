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
}
