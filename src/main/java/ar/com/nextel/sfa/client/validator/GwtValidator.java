package ar.com.nextel.sfa.client.validator;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.validator.target.ListBoxValidationTarget;
import ar.com.nextel.sfa.client.validator.target.TextBoxBaseValidationTarget;
import ar.com.nextel.sfa.client.validator.target.ValidationTarget;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * This tool can be used for simple validations
 */
public class GwtValidator {

	private List<ValidationTarget> targets;
	private List<String> errors;

	// aux
	private TextBox textBoxAux;

	/**
	 * Creates a new validator. It must be done before every session of validation
	 */
	public GwtValidator() {
		this.targets = new ArrayList<ValidationTarget>();
		this.errors = new ArrayList<String>();
	}

	public TextBoxBaseValidationTarget addTarget(String text) {
		textBoxAux = new TextBox();
		textBoxAux.setText(text);
		return this.addTarget(textBoxAux);
	}

	/**
	 * Add a new target to validate
	 * 
	 * @param widget
	 * @return
	 */
	public TextBoxBaseValidationTarget addTarget(TextBoxBase widget) {
		TextBoxBaseValidationTarget target = new TextBoxBaseValidationTarget(widget);
		this.targets.add(target);
		return target;
	}

	/**
	 * Add a new target to validate
	 * 
	 * @param listBox
	 * @return
	 */
	public ListBoxValidationTarget addTarget(ListBox listBox) {
		ListBoxValidationTarget target = new ListBoxValidationTarget(listBox);
		this.targets.add(target);
		return target;
	}

	/**
	 * Executes all validations
	 * 
	 * @return the result of the validation. If one or more failed, then it returns false.
	 */
	public boolean validateAll() {
		ValidationTarget target;
		for (int i = 0; i < this.targets.size(); i++) {
			target = (ValidationTarget) this.targets.get(i);
			target.fillWithErrors(this.errors);
		}
		if (this.errors.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Executes all validations and fills a GwtValidationResult object with the validations results
	 * 
	 * @return a GwtValidationResult object with the validations results
	 */
	public GwtValidationResult fillResult() {
		GwtValidationResult result = new GwtValidationResult();
		result.setValid(this.validateAll());
		result.setErrors(this.errors);
		return result;
	}

	/**
	 * List of errors. It must be asked after executing validateAll()
	 * 
	 * @return list of errors. It could be empty.
	 */
	public List<String> getErrors() {
		return this.errors;
	}

	/**
	 * Add a new error to the errors list
	 * 
	 * @param errorText
	 */
	public void addError(String errorText) {
		this.errors.add(errorText);
	}

	/**
	 * Add a list of errors to the errors list
	 * 
	 * @param errors
	 */
	public void addErrors(List<String> errors) {
		this.errors.addAll(errors);
	}

	/**
	 * Append a validation result to this validator
	 * 
	 * @param result
	 */
	public void addResult(GwtValidationResult result) {
		if (!result.isValid()) {
			this.errors.addAll(result.getErrors());
		}
	}

}
