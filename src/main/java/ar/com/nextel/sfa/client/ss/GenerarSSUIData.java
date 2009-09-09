package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioGeneracionDto;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GenerarSSUIData extends UIData implements ClickListener {

	private CheckBox laboral;
	private CheckBox personal;
	private CheckBox nuevo;
	private CheckBox scoring;
	private HTML emailLaboral;
	private HTML emailPersonal;
	private TextBox email;
	private SolicitudServicioGeneracionDto solicitudServicioGeneracion;

	public GenerarSSUIData() {
		fields.add(laboral = new CheckBox());
		fields.add(personal = new CheckBox());
		fields.add(nuevo = new CheckBox());
		fields.add(scoring = new CheckBox());
		fields.add(email = new RegexTextBox(RegularExpressionConstants.lazyEmail));
		fields.add(emailLaboral = new HTML());
		fields.add(emailPersonal = new HTML());

		laboral.addClickListener(this);
		personal.addClickListener(this);
		nuevo.addClickListener(this);
		email.setEnabled(false);
		email.setReadOnly(true);
		email.setWidth("300px");
	}

	public void onClick(Widget sender) {
		CheckBox senderCheckBox = (CheckBox) sender;
		if (senderCheckBox.isChecked()) {
			laboral.setChecked(false);
			personal.setChecked(false);
			nuevo.setChecked(false);
			senderCheckBox.setChecked(true);
		}
		if (nuevo.isChecked()) {
			email.setEnabled(true);
			email.setReadOnly(false);
		} else {
			email.setEnabled(false);
			email.setReadOnly(true);
			email.setText("");
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
		if (laboral.isEnabled()) {
			laboral.setChecked(true);
		} else if (personal.isEnabled()) {
			personal.setChecked(true);
		}
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

	public HTML getEmailLaboral() {
		return emailLaboral;
	}

	public HTML getEmailPersonal() {
		return emailPersonal;
	}

	public SolicitudServicioGeneracionDto getSolicitudServicioGeneracion() {
		solicitudServicioGeneracion.setEmailLaboralChecked(laboral.isChecked());
		solicitudServicioGeneracion.setEmailNuevoChecked(nuevo.isChecked());
		solicitudServicioGeneracion.setEmailPersonalChecked(personal.isChecked());
		solicitudServicioGeneracion.setScoringChecked(scoring.isChecked());
		
		solicitudServicioGeneracion.setEmailNuevo(null);
		solicitudServicioGeneracion.setEmailLicencia(null);
		if (laboral.isChecked())
			solicitudServicioGeneracion.setEmailLicencia(emailLaboral.getText());
		if (personal.isChecked())
			solicitudServicioGeneracion.setEmailLicencia(emailPersonal.getText());
		if (nuevo.isChecked())
			solicitudServicioGeneracion.setEmailNuevo(email.getText());
		return solicitudServicioGeneracion;
	}

	public void setSolicitudServicioGeneracion(SolicitudServicioGeneracionDto solicitudServicioGeneracion) {
		this.solicitudServicioGeneracion = solicitudServicioGeneracion;
		email.setText(solicitudServicioGeneracion.getEmailNuevo());
		laboral.setChecked(solicitudServicioGeneracion.isEmailLaboralChecked());
		personal.setChecked(solicitudServicioGeneracion.isEmailPersonalChecked());
		nuevo.setChecked(solicitudServicioGeneracion.isEmailNuevoChecked());
		scoring.setChecked(solicitudServicioGeneracion.isScoringChecked());
		email.setText(solicitudServicioGeneracion.getEmailNuevo());

	}

}
