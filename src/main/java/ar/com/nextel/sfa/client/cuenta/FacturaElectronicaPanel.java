package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;

public class FacturaElectronicaPanel extends Composite implements ClickHandler {

	private Grid layout = new Grid(1, 4);
	private CheckBox facturaElectronicaHabilitada;
	private RegexTextBox email;
	private InlineHTML emailLabel;

	public FacturaElectronicaPanel() {
		facturaElectronicaHabilitada = new CheckBox();
		email = new RegexTextBox(RegularExpressionConstants.lazyEmail);
		emailLabel = new InlineHTML(Sfa.constant().email());
		layout.addStyleName("m10");

		layout.setHTML(0, 0, Sfa.constant().habilitarFacturaElectronica());
		layout.setWidget(0, 1, facturaElectronicaHabilitada);
//		layout.setWidget(0, 2, emailLabel);
		layout.setWidget(0, 3, email);

		this.initWidget(layout);
		facturaElectronicaHabilitada.addClickHandler(this);

		enableEmail();
	}

	public void onClick(ClickEvent event) {
		enableEmail();
	}

	private void enableEmail() {
		email.setVisible(facturaElectronicaHabilitada.getValue());
		emailLabel.setVisible(facturaElectronicaHabilitada.getValue());
	}

	public String getText() {
		if (!facturaElectronicaHabilitada.getValue())
			return "";
		return email.getText();
	}

	public void setText(String text) {
		if (text == null || text.equals("")) {
			facturaElectronicaHabilitada.setValue(false);
			email.setText(text);
			email.setVisible(false);
		} else {
			facturaElectronicaHabilitada.setValue(true);
			email.setText(text);
			email.setVisible(true);
		}
	}

	public void setEnabled(boolean val) {

		facturaElectronicaHabilitada.setEnabled(val);
		email.setEnabled(val);
	}

	public RegexTextBox getEmail() {
//		if (!facturaElectronicaHabilitada.getValue())
//			return null;
		return email;
	}

	public boolean isDirty(String oldEmail) {
		if (oldEmail == null || oldEmail.equals("")) {
			return !getText().equals("");
		} else {
			return !getText().equalsIgnoreCase(oldEmail);
		}

	}

	public void setFacturaElectronicaHabilitada(boolean val) {
		facturaElectronicaHabilitada.setValue(val);
		email.setVisible(val);
	}
	
	public void setFacturaElectronicaObligatoria(boolean val) {
		setFacturaElectronicaHabilitada(true);
		facturaElectronicaHabilitada.setEnabled(false);
	}
	
	public boolean isFacturaElectronicaChecked(){
		return facturaElectronicaHabilitada.getValue();
	}

	public InlineHTML getEmailLabel() {
		return emailLabel;
	}

	public void setEmailLabel(InlineHTML emailLabel) {
		this.emailLabel = emailLabel;
	}

	public boolean isEnabled(){
		return facturaElectronicaHabilitada.isEnabled() && email.isEnabled(); 

	}
	
	public CheckBox getFacturaElectronicaHabilitada() {
		return facturaElectronicaHabilitada;
	}
	
}
