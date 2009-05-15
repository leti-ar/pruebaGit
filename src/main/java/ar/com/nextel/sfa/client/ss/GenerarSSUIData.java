package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.UIData;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;

public class GenerarSSUIData extends UIData {
	
	private CheckBox laboral;
	private CheckBox personal;
	private CheckBox nuevo;
	private CheckBox scoring;
	private TextBox email;
	
	public GenerarSSUIData(){
		fields.add(laboral = new CheckBox());
		fields.add(personal = new CheckBox());
		fields.add(nuevo = new CheckBox());
		fields.add(scoring = new CheckBox());
		fields.add(email = new TextBox());
		email.setEnabled(false);
	}

	public CheckBox getLaboral() {
		return laboral;
	}

	public CheckBox getPersonal() {
		return personal;
	}

	public CheckBox getNuevo() {
		return nuevo;
	}

	public CheckBox getScoring() {
		return scoring;
	}

	public TextBox getEmail() {
		return email;
	}
}
