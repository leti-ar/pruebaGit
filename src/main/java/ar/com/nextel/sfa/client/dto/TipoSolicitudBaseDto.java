package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudBaseDto extends EnumDto implements IsSerializable {

	private String formaContratacion;

	public String getFormaContratacion() {
		return formaContratacion;
	}

	public void setFormaContratacion(String formaContratacion) {
		this.formaContratacion = formaContratacion;
	}

}
