package ar.com.nextel.sfa.client.dto;

import java.io.Serializable;

public class ClienteNexusDto implements Serializable {

	private String customerCode;
	private String customerId;
	private String customerNexusId;
	private String idRegistroAtencion;
	private boolean vieneDeNexus;
	private boolean pinChequeadoEnNexus;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
//	public boolean isVieneDeNexus() {
//		return vieneDeNexus;
//	}
	
	public void setVieneDeNexus(boolean vieneDeNexus) {
		this.vieneDeNexus = vieneDeNexus;
	}

	public String getIdRegistroAtencion() {
		return idRegistroAtencion;
	}

	public void setIdRegistroAtencion(String idRegistroAtencion) {
		this.idRegistroAtencion = idRegistroAtencion;
	}

	public String getCustomerNexusId() {
		return customerNexusId;
	}

	public void setCustomerNexusId(String customerNexusId) {
		this.customerNexusId = customerNexusId;
	}

	public boolean isPinChequeadoEnNexus() {
		return pinChequeadoEnNexus;
	}

	public void setPinChequeadoEnNexus(boolean pinChequeadoEnNexus) {
		this.pinChequeadoEnNexus = pinChequeadoEnNexus;
	}

}
