package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.FacturaElectronicaDto;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;

public class FacturaElectronicaUI extends NextelDialog implements ClickHandler {

	private CheckBox facturaElectronicaHabilitada;
	private RegexTextBox email;
	private InlineHTML emailLabel;
	private Grid layout;
	private SimpleLink aceptar;
	private SimpleLink cerrar;
	private static final String v1 = "\\{1\\}";
	private Command aceptarCommand;
	private boolean dirty;
	private FacturaElectronicaDto facturaElectronicaOriginal;

	public FacturaElectronicaUI() {
		super("Factura Electrónica");

		facturaElectronicaHabilitada = new CheckBox();
		email = new RegexTextBox(RegularExpressionConstants.lazyEmail);
		emailLabel = new InlineHTML(Sfa.constant().email());
		layout = new Grid(2, 2);
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		layout.addStyleName("m10");

		layout.setHTML(0, 0, Sfa.constant().habilitar());
		layout.setWidget(0, 1, facturaElectronicaHabilitada);
		layout.setWidget(1, 0, emailLabel);
		layout.setWidget(1, 1, email);

		centralPanel.add(layout);

		addFormButtons(aceptar);
		addFormButtons(cerrar);

		setFooterVisible(false);
		setFormButtonsVisible(true);

		ClickListener clickListener = new ClickListener() {
			public void onClick(Widget sender) {
				if (sender == aceptar) {
					List<String> errors = validate();
					if (errors.isEmpty()) {
						dirty = facturaElectronicaOriginal == null && facturaElectronicaHabilitada.getValue();
						dirty = facturaElectronicaOriginal != null && facturaElectronicaHabilitada.getValue()
								&& facturaElectronicaOriginal.getEmail().equals(getEmail()) || dirty;
						aceptarCommand.execute();
						hide();
					} else {
						ErrorDialog.getInstance().show(errors);
					}
				} else {
					hide();
				}
			}
		};

		aceptar.addClickListener(clickListener);
		cerrar.addClickListener(clickListener);
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

	private List<String> validate() {
		GwtValidator validator = new GwtValidator();
		if (facturaElectronicaHabilitada.getValue()) {
			validator.addTarget(email).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().email())).mail(
					Sfa.constant().ERR_EMAIL_NO_VALIDO().replaceAll(v1, Sfa.constant().email()));
		}
		return validator.fillResult().getErrors();
	}

	public void setOnAceptar(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public boolean getFacturaElectronicaHabilitada() {
		return facturaElectronicaHabilitada.getValue();
	}

	// public void setFacturaElectronicaHabilitada(boolean facturaElectronicaHabilitada) {
	// this.facturaElectronicaHabilitada.setValue(facturaElectronicaHabilitada);
	// enableEmail();
	// }

	public void setFacturaElectronicaOriginal(FacturaElectronicaDto facturaElectronica) {
		clearFields();
		this.facturaElectronicaOriginal = facturaElectronica;
		if (facturaElectronica != null) {
			facturaElectronicaHabilitada.setValue(true);
			email.setText(facturaElectronica.getEmail());
			enableEmail();
			setEnabled(!facturaElectronica.isCargadaEnVantive());
		}
	}

	public String getEmail() {
		return email.getText();
	}

	//
	// public void setEmail(String email) {
	// this.email.setText(email);
	// }

	public void clearFields() {
		facturaElectronicaHabilitada.setValue(false);
		email.setText("");
		enableEmail();
		dirty = false;
		setEnabled(true);
	}

	public void setEnabled(boolean enabled) {
		facturaElectronicaHabilitada.setEnabled(enabled);
		email.setEnabled(enabled);
		email.setReadOnly(!enabled);
	}

	public boolean isDirty() {
		return dirty;
	}
}
