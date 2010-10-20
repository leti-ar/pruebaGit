package ar.com.nextel.sfa.client.dto;

import java.io.Serializable;

public class ClienteNexusDto implements Serializable {

	private String customerCode;
	private String customerId;
	private boolean vieneDeNexus;

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

}
