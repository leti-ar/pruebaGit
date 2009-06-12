package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AvailableNumberDto implements IsSerializable{

	private long availableNumber;

	public long getAvailableNumber() {
		return availableNumber;
	}

	public void setAvailableNumber(long availableNumber) {
		this.availableNumber = availableNumber;
	}

}
