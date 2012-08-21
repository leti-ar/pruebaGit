package ar.com.nextel.sfa.client.ss;

import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class FullDocumentWidget extends Composite{

	private RegexTextBox nroDocumento;
	private ListBox tipoDocumento;
	
	public FullDocumentWidget() {
	}
	
	public FullDocumentWidget(ListBox lista , RegexTextBox nro){
		this.tipoDocumento = lista;
		this.nroDocumento = nro;
		HorizontalPanel panel = new HorizontalPanel();
	    panel.add(tipoDocumento);
	    panel.add(nro);
		initWidget(panel);
	}

	public RegexTextBox getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(RegexTextBox nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(ListBox tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
