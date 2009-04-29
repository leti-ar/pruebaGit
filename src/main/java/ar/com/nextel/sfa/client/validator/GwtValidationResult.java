package ar.com.nextel.sfa.client.validator;

import java.util.List;

public class GwtValidationResult {

	private boolean valid;
	private List<String> errors;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
