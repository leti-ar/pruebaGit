package ar.com.nextel.sfa.client.dto;

import ar.com.snoop.gwt.commons.client.dto.ListBoxItem;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OrigenSolicitudDto extends EnumDto implements ListBoxItem, IsSerializable {

	private Boolean usaNumeroSSWeb;//Mejoras Perfil Telemarketing. REQ#2 - N° de SS Web en la Solicitud de Servicio.
	
	public String getItemText() {
		return descripcion;
	}

	public String getItemValue() {
		return "" + id;
	}

	public void setUsaNumeroSSWeb(Boolean usaNumeroSSWeb) {
		this.usaNumeroSSWeb = usaNumeroSSWeb;
	}

	public Boolean getUsaNumeroSSWeb() {
		return usaNumeroSSWeb;
	}

}
