package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.ListBox;

public class ValidationListBox extends ListBox implements ExcluyenteWidget {

	private boolean excluyente;

	public ValidationListBox(String string) {
		super(string);
	}

	public ValidationListBox() {
		super();
	}

	public boolean isExcluyente() {
		return excluyente;
	}

	public void setExcluyente(boolean excluyente) {
		this.excluyente = excluyente;
	}
}