package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class CerrarSSUIData extends UIData implements ClickHandler {

	private CheckBox laboral;
	private CheckBox personal;
	private CheckBox nuevo;
	private CheckBox scoring;
	private TextBox pin;
	private HTML emailLaboral;
	private HTML emailPersonal;
	private TextBox emailNuevo;
	private ListBox DAEListBox;
	private SolicitudServicioGeneracionDto solicitudServicioGeneracion;

	public CerrarSSUIData() {
		fields.add(laboral = new CheckBox());
		fields.add(personal = new CheckBox());
		fields.add(nuevo = new CheckBox());
		fields.add(scoring = new CheckBox());
		fields.add(pin = new PasswordTextBox());
		fields.add(emailNuevo = new RegexTextBox(RegularExpressionConstants.lazyEmail));
		fields.add(emailLaboral = new HTML());
		fields.add(emailPersonal = new HTML());
		fields.add(DAEListBox = new ListBox());

		pin.setMaxLength(6);
		pin.setWidth("70px");
		laboral.addClickHandler(this);
		personal.addClickHandler(this);
		nuevo.addClickHandler(this);
		emailNuevo.setEnabled(false);
		emailNuevo.setReadOnly(true);
		emailNuevo.setWidth("300px");
		
		SolicitudRpcService.Util.getInstance().getVendedoresDae(
				new DefaultWaitCallback<List<VendedorDto>>() {
					public void success(List<VendedorDto> result) {
 						for (VendedorDto vendedorDto : result) {
							DAEListBox.addItem(vendedorDto.getItemText());
						}
					}
			});
	}

	public void onClick(ClickEvent event) {
		CheckBox senderCheckBox = (CheckBox) event.getSource();
		if (senderCheckBox.getValue()) {
			laboral.setValue(false);
			personal.setValue(false);
			nuevo.setValue(false);
			senderCheckBox.setValue(true);
		}
		if (nuevo.getValue()) {
			emailNuevo.setEnabled(true);
			emailNuevo.setReadOnly(false);
		} else {
			emailNuevo.setEnabled(false);
			emailNuevo.setReadOnly(true);
			emailNuevo.setText("");
		}
	}

	public void setEmails(List<EmailDto> emails, SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		clean();
		setSolicitudServicioGeneracion(solicitudServicioGeneracion);
		personal.setEnabled(false);
		laboral.setEnabled(false);

		for (EmailDto email : emails) {
			Long idTipo = email.getTipoEmail().getId();
			if (TipoEmailEnum.PERSONAL.getTipo().equals(idTipo)) {
				emailPersonal.setText(email.getEmail());
				personal.setEnabled(true);
			}
			if (TipoEmailEnum.LABORAL.getTipo().equals(idTipo)) {
				emailLaboral.setText(email.getEmail());
				laboral.setEnabled(true);
			}
		}
		emailNuevo.setEnabled(nuevo.getValue());
		emailNuevo.setReadOnly(!nuevo.getValue());
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

	public TextBox getPin() {
		return pin;
	}

	public TextBox getEmail() {
		return emailNuevo;
	}

	public HTML getEmailLaboral() {
		return emailLaboral;
	}

	public HTML getEmailPersonal() {
		return emailPersonal;
	}
	
	public ListBox getDAEListBox() {
		return DAEListBox;
	}

	public void setDAEListBox(ListBox dAEListBox) {
		DAEListBox = dAEListBox;
	}

	public SolicitudServicioGeneracionDto getSolicitudServicioGeneracion() {
		solicitudServicioGeneracion.setEmailLaboralChecked(laboral.getValue());
		solicitudServicioGeneracion.setEmailNuevoChecked(nuevo.getValue());
		solicitudServicioGeneracion.setEmailPersonalChecked(personal.getValue());
		solicitudServicioGeneracion.setScoringChecked(scoring.getValue());

		solicitudServicioGeneracion.setEmailNuevo(null);
		solicitudServicioGeneracion.setEmailLicencia(null);
		if (laboral.getValue())
			solicitudServicioGeneracion.setEmailLicencia(emailLaboral.getText());
		if (personal.getValue())
			solicitudServicioGeneracion.setEmailLicencia(emailPersonal.getText());
		if (nuevo.getValue())
			solicitudServicioGeneracion.setEmailNuevo(emailNuevo.getText());
		return solicitudServicioGeneracion;
	}

	public void setSolicitudServicioGeneracion(SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		this.solicitudServicioGeneracion = solicitudServicioGeneracion;
		laboral.setValue(solicitudServicioGeneracion.isEmailLaboralChecked());
		personal.setValue(solicitudServicioGeneracion.isEmailPersonalChecked());
		nuevo.setValue(solicitudServicioGeneracion.isEmailNuevoChecked());
		scoring.setValue(solicitudServicioGeneracion.isScoringChecked());
		emailNuevo.setText(solicitudServicioGeneracion.getEmailNuevo());
	}
	
}
