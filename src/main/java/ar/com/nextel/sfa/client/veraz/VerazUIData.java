package ar.com.nextel.sfa.client.veraz;

import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class VerazUIData {
	
	private ListBox tipoDocListBox;
	private TextBox numeroDocTextBox;
	private ListBox sexoListBox;
	private SimpleLink validarVerazLink;
	private SimpleLink agregarProspectLink;
	
	public VerazUIData() {
		tipoDocListBox = new ListBox();
		sexoListBox = new ListBox();
		numeroDocTextBox = new TextBox();
		validarVerazLink = new SimpleLink("Validar Veraz", "#", true);
		agregarProspectLink = new SimpleLink("Agregar Prospect", "#", true);
	}

	/** Getters de todos los Widgets **/
	public ListBox getTipoDocListBox() {
		return tipoDocListBox;
	}
	
	public ListBox getSexoListBox() {
		return sexoListBox;
	}

	public TextBox getNumeroDocTextBox() {
		return numeroDocTextBox;
	}

	public SimpleLink getValidarVerazLink() {
		return validarVerazLink;
	}

	public SimpleLink getAgregarProspectLink() {
		return agregarProspectLink;
	}
		
}
