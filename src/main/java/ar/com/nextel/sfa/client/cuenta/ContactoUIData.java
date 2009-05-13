package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

public class ContactoUIData extends UIData {

	ListBox tipoDocumento = new ListBox();
	TextBox numeroDocumento = new TextBox();
	TextBox nombre = new TextBox();
	TextBox apellido = new TextBox();
	ListBox sexo = new ListBox();
	ListBox cargo = new ListBox();
	TelefonoTextBox telefonoPrincipal = new TelefonoTextBox();
	TelefonoTextBox telefonoCelular = new TelefonoTextBox();
	TelefonoTextBox telefonoAdicional = new TelefonoTextBox();
	TelefonoTextBox fax = new TelefonoTextBox();
	TextBox emailPersonal = new TextBox();
	TextBox emailLaboral = new TextBox();
	Label veraz = new Label();
	
	public void setContacto(/*ContactoDto contactoDto*/){
		/**Seteo los combo y textBox para editar el contacto*/
	}
	
//	public ContactoDto getContacto(){
//		/**Obtengo los datos del contacto para poder guardarlos*/
//	}

	public List<String> validarCampoObligatorio() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(nombre).required("El campo" + Sfa.constant().nombre() + "es obligatorio");
		validator.addTarget(apellido).required("El campo" + Sfa.constant().apellido() + "es obligatorio");
		validator.fillResult();
		return validator.getErrors();
	}
	
	public List<String> validarVeraz(){
		GwtValidator validator = new GwtValidator();
		if (numeroDocumento.getText().equals("")) {
			validator.addError(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));
		} else {
			validator.addTarget(numeroDocumento).numericPositive(Sfa.constant().ERR_FORMATO().replaceAll("\\{1\\}", Sfa.constant().numeroDocumento()));	
		}
		if (!validator.getErrors().isEmpty()) {
			ErrorDialog.getInstance().show(validator.getErrors());
		}
		validator.fillResult();
		return validator.getErrors();
	}
	
	public ContactoUIData() {
		fields.add(tipoDocumento);
		fields.add(numeroDocumento);
		fields.add(nombre);
		fields.add(apellido);
		fields.add(sexo);
		fields.add(cargo);
		fields.add(telefonoPrincipal);
		fields.add(telefonoCelular);
		fields.add(telefonoAdicional);
		fields.add(fax);
		fields.add(emailPersonal);
		fields.add(emailLaboral);
		
		tipoDocumento.setWidth("125px");
		sexo.setWidth("100px");
		cargo.setWidth("250px");
	}
	

	public ListBox getTipoDocumento() {
		return tipoDocumento;
	}

	public TextBox getNumeroDocumento() {
		return numeroDocumento;
	}

	public TextBox getNombre() {
		return nombre;
	}

	public TextBox getApellido() {
		return apellido;
	}

	public ListBox getSexo() {
		return sexo;
	}

	public ListBox getCargo() {
		return cargo;
	}

	public TelefonoTextBox getTelefonoPrincipal() {
		return telefonoPrincipal;
	}

	public TelefonoTextBox getTelefonoCelular() {
		return telefonoCelular;
	}

	public TelefonoTextBox getTelefonoAdicional() {
		return telefonoAdicional;
	}

	public TelefonoTextBox getFax() {
		return fax;
	}

	public TextBox getEmailPersonal() {
		return emailPersonal;
	}

	public TextBox getEmailLaboral() {
		return emailLaboral;
	}
	
	public Label getVeraz(){
		return veraz;
	}

}
