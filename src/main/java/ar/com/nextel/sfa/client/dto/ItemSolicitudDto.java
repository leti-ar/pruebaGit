package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemSolicitudDto extends EnumDto implements IsSerializable {


	private Boolean sinModelo;

	public Boolean getSinModelo() {
		return sinModelo;
	}

	public void setSinModelo(Boolean sinModelo) {
		this.sinModelo = sinModelo;
	}

}
