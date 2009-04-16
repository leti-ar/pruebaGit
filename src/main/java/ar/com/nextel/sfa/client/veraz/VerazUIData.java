package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.VerazSearchDto;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.dto.ListBoxItemImpl;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author mrial
 *
 */

public class VerazUIData extends UIData {
	
	private ListBox tipoDocListBox;
	private TextBox numeroDocTextBox;
	private ListBox sexoListBox;
	private SimpleLink validarVerazLink;
	private SimpleLink agregarProspectLink;
	
	public VerazUIData() {
		
		fields.add(tipoDocListBox = new ListBox(""));
		fields.add(sexoListBox = new ListBox(""));
		fields.add(numeroDocTextBox = new TextBox());
		numeroDocTextBox.setMaxLength(10);
		validarVerazLink = new SimpleLink("Validar Veraz", "#", true);
		agregarProspectLink = new SimpleLink("Agregar Prospect", "#", true);
	
	
	CuentaRpcService.Util.getInstance().getVerazInitializer(
			new DefaultWaitCallback<VerazInitializer>() {
				public void success(VerazInitializer result) {
					setCombos(result);
				}
			});
	}
		
	
	private void setCombos(VerazInitializer datos) {
		tipoDocListBox.addAllItems(datos.getTiposDocumento());
		sexoListBox.addAllItems(datos.getSexos());
	}
		
	
	public VerazSearchDto getVerazSearch() {
		VerazSearchDto verazSearchDto = new VerazSearchDto();
		verazSearchDto.setTipoDoc(tipoDocListBox.getSelectedItem().getItemText());
		verazSearchDto.setNroDoc(numeroDocTextBox.getText());
		verazSearchDto.setSexo(sexoListBox.getSelectedItem().getItemText());
		return verazSearchDto;
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
