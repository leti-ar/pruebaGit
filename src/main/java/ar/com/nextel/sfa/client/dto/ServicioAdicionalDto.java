package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServicioAdicionalDto extends EnumDto implements IsSerializable {

	private Boolean esCargoAdministrativo;
	private Boolean esCDT;

	public Boolean getEsCargoAdministrativo() {
		return esCargoAdministrativo;
	}

	public void setEsCargoAdministrativo(Boolean esCargoAdministrativo) {
		this.esCargoAdministrativo = esCargoAdministrativo;
	}

	public Boolean getEsCDT() {
		return esCDT;
	}

	public void setEsCDT(Boolean esCDT) {
		this.esCDT = esCDT;
	}

}
