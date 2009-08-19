package ar.com.nextel.sfa.client.veraz;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SexoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.initializer.VerazInitializer;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.ValidationTextBox;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

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
	private List<String> errorList = new ArrayList<String>();
	
	public VerazUIData() {

		fields.add(tipoDocListBox = new ListBox());
		fields.add(sexoListBox = new ListBox());
		fields.add(numeroDocTextBox = new ValidationTextBox("[0-9]*[\\-]{0,1}[0-9]*[﻿\\-]{0,1}[0-9]*"));
		validarVerazLink = new SimpleLink("Validar Veraz", "#", true);
		agregarProspectLink = new SimpleLink("Agregar Prospect", "#", true);

		agregarProspectLink.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (validarDatosParaCrearProspect()) {
					CuentaClientService.reservaCreacionCuenta(new Long(tipoDocListBox.getSelectedItemId()),numeroDocTextBox.getText() , null);
				}
			}			
		});
		
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
		sexoListBox.setSelectedIndex(2);
	}


	public List<String> validarFormatoYCompletitud() {		
		errorList.clear();
		if ((tipoDocListBox.getSelectedItem() == null) || ("".equals(numeroDocTextBox.getText())) || (sexoListBox.getSelectedItem() == null)) {
			errorList.add("Deben estar completos los campos tipo, número de documento y sexo para realizar la consulta a veraz");
		} else if (("3".equals(tipoDocListBox.getSelectedItem().getItemValue())) || 
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

	public PersonaDto getVerazSearch(TextBox numDoc, ListBox tipoDoc, ListBox sexo) {
		//if ((numDoc!=null) && (tipoDoc!=null) && (sexo!=null)) {
		PersonaDto personaDto = new PersonaDto();
		DocumentoDto documentoDto = new DocumentoDto(numDoc.getText(), (TipoDocumentoDto) tipoDoc.getSelectedItem());
		personaDto.setDocumento(documentoDto);
		personaDto.setIdTipoDocumento(documentoDto.getTipoDocumento().getId());
		personaDto.setSexo((SexoDto) sexo.getSelectedItem());
		//} 
		return personaDto;
	}

	private boolean validarDatosParaCrearProspect() {
		GwtValidator validator = new GwtValidator();
		if (numeroDocTextBox.getText().equals("")) {
			validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
		} else {
			if (tipoDocListBox.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIL.getTipo())) ||
					tipoDocListBox.getSelectedItemId().equals(Long.toString(TipoDocumentoEnum.CUIT.getTipo()))) {
				validator.addTarget(numeroDocTextBox).cuil(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
			} else {
				if (numeroDocTextBox.getText().length() > 8 ||numeroDocTextBox.getText().length()<7) {
					validator.addError(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
				}
				validator.addTarget(numeroDocTextBox).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
			}
		}
		validator.fillResult();
		if (!validator.getErrors().isEmpty()) {
			ErrorDialog.getInstance().show(validator.getErrors());
		}	
		return validator.getErrors().isEmpty();
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
