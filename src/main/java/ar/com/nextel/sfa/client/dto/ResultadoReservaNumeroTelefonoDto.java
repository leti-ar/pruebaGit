package ar.com.nextel.sfa.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultadoReservaNumeroTelefonoDto implements IsSerializable {

	private long reservedNumber;
	private List<AvailableNumberDto> availableNumbers = new ArrayList();

	public long getReservedNumber() {
		return reservedNumber;
	}

	public void setReservedNumber(long reservedNumber) {
		this.reservedNumber = reservedNumber;
	}

	public List<AvailableNumberDto> getAvailableNumbers() {
		return availableNumbers;
	}

	public void setAvailableNumbers(List<AvailableNumberDto> availableNumbers) {
		this.availableNumbers = availableNumbers;
	}

}
