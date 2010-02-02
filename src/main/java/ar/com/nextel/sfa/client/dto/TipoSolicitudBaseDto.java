package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TipoSolicitudBaseDto extends EnumDto implements IsSerializable {

	public static final Long ID_VENTA_CDW = new Long(3);
	public static final Long VENTA_EQUIPOS_NUEVOS_G4 = new Long(10);
	public static final Long ALQUILER_EQUIPOS_NUEVOS_G4 = new Long(11);
	public static final Long VENTA_EQUIPOS_USADOS_G4 = new Long(14);
	public static final Long ALQUILER_EQUIPOS_USADOS_G4 = new Long(15);
	private String formaContratacion;

	public String getFormaContratacion() {
		return formaContratacion;
	}

	public void setFormaContratacion(String formaContratacion) {
		this.formaContratacion = formaContratacion;
	}

}
