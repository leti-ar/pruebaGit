package ar.com.nextel.sfa.client.veraz;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
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
	private ValidationTextBox numeroDocTextBox;
	private ListBox sexoListBox;
	private SimpleLink validarVerazLink;
	private SimpleLink agregarProspectLink;
	private List<String> errorList = new ArrayList();
	
	public VerazUIData() {
		
		fields.add(tipoDocListBox = new ListBox(""));
		fields.add(sexoListBox = new ListBox(""));
		fields.add(numeroDocTextBox = new ValidationTextBox("[0-9]*[\\-]{0,1}[0-9]*[﻿\\-]{0,1}[0-9]*"));
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

		
	public List<String> validarFormatoYCompletitud() {		
		if ((tipoDocListBox.getSelectedItem() == null) || ("".equals(numeroDocTextBox.getText())) || (sexoListBox.getSelectedItem() == null)) {
			errorList.add("Deben estar completos los campos tipo, número de documento y sexo para realizar la consulta a veraz");
		}else if(("3".equals(tipoDocListBox.getSelectedItem().getItemValue())) || 
				("4".equals(tipoDocListBox.getSelectedItem().getItemValue())) || 
				("7".equals(tipoDocListBox.getSelectedItem().getItemValue()))) {
			if (!validarTipoDoc(numeroDocTextBox)) {
				errorList.add("El formato del número de CUIT/CUIL es incorrecto. Debe ser xx-xxxxxxxx-x");
			}

		} else if (numeroDocTextBox.getText().length() > 10) {
			errorList.add("El número de documento debe tener 10 dígitos como máximo");
		}
		return errorList;
	}

		
	private boolean validarTipoDoc(ValidationTextBox numeroDocTextBox) {
		String cuit = new String("[0-9]{2,2}-[0-9]{8,8}-[0-9]{1,1}");
		return numeroDocTextBox.validatePattern(cuit);
	}
	
	public PersonaDto getVerazSearch() {
		PersonaDto personaDto = new PersonaDto();
		DocumentoDto documentoDto = new DocumentoDto(numeroDocTextBox.getText(), (TipoDocumentoDto) tipoDocListBox.getSelectedItem());
		personaDto.setDocumento(documentoDto);
		personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
		personaDto.setSexo((SexoDto) sexoListBox.getSelectedItem());
		return personaDto;
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
