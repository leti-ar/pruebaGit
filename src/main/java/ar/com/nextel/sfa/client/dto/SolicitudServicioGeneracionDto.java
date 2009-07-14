package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolicitudServicioGeneracionDto implements IsSerializable {

	private boolean emailLaboralChecked;
	private boolean emailPersonalChecked;
	private boolean emailNuevoChecked;
	private boolean scoringChecked;
	private String emailNuevo;
	/** Puede ser el laboral o el personal (en ese orden) */
	private String emailLicencia;

	public SolicitudServicioGeneracionDto() {
		emailLaboralChecked = false;
		emailPersonalChecked = false;
		emailNuevoChecked = false;
		scoringChecked = false;
	}

	public boolean isEmailLaboralChecked() {
		return emailLaboralChecked;
	}

	public void setEmailLaboralChecked(boolean emailLaboralChecked) {
		this.emailLaboralChecked = emailLaboralChecked;
	}

	public boolean isEmailPersonalChecked() {
		return emailPersonalChecked;
	}

	public void setEmailPersonalChecked(boolean emailPersonalChecked) {
		this.emailPersonalChecked = emailPersonalChecked;
	}

	public boolean isEmailNuevoChecked() {
		return emailNuevoChecked;
	}

	public void setEmailNuevoChecked(boolean emailNuevoChecked) {
		this.emailNuevoChecked = emailNuevoChecked;
	}

	public boolean isScoringChecked() {
		return scoringChecked;
	}

	public void setScoringChecked(boolean scoringChecked) {
		this.scoringChecked = scoringChecked;
	}

	public String getEmailNuevo() {
		return emailNuevo;
	}

	public void setEmailNuevo(String emailNuevo) {
		this.emailNuevo = emailNuevo;
	}

	public String getEmailLicencia() {
		return emailLicencia;
	}

	public void setEmailLicencia(String emailLicencia) {
		this.emailLicencia = emailLicencia;
	}

}
