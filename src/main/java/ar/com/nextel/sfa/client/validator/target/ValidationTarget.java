package ar.com.nextel.sfa.client.validator.target;

import java.util.List;

public abstract class ValidationTarget {

	protected String errorMsg = null;
	protected boolean conditionalState = false;

	protected boolean isOk() {
		if (conditionalState) {
			return false;
		} else {
			return !hasErrorMsg();
		}
	}

	public boolean hasErrorMsg() {
		return this.errorMsg != null;
	}

	public void fillWithErrors(List<String> errors) {
		if (hasErrorMsg()) {
			errors.add(this.errorMsg);
		}
	}

}
