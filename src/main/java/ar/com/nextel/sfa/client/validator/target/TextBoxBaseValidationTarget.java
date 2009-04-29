package ar.com.nextel.sfa.client.validator.target;

import java.util.Date;

import ar.com.nextel.sfa.client.validator.GwtValidationUtils;

import com.google.gwt.user.client.ui.TextBoxBase;

public class TextBoxBaseValidationTarget extends ValidationTarget {

	private String text;

	public TextBoxBaseValidationTarget(TextBoxBase widget) {
		this.text = widget.getText();
	}

	/**
	 * Valida que el texto sea requerido
	 */
	public TextBoxBaseValidationTarget required(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateRequired(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	/**
	 * Marca que todas las validaciones que vengan después de esto, solo se validen si el campo es requerido
	 */
	public TextBoxBaseValidationTarget conditionalRequired() {
		if (isOk()) {
			if (!GwtValidationUtils.validateRequired(text)) {
				this.conditionalState = true;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget booleanValidation(boolean validationResult, String errorMsg) {
		if (isOk()) {
			if (!validationResult) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget empty(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateEmpty(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget equals(Object value, String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateEquals(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget notEquals(String errorMsg, Object value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateNotEquals(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget length(int length, String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateLength(text, length)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget minLength(int length, String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateMinLength(text, length)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget maxLength(int length, String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateMaxLength(text, length)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget isInt(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateInteger(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget isLong(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateLong(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget isFloat(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateFloat(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget numeric(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateNumeric(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget numericPositive(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateNumericPositive(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget numericPositiveOrZero(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateNumericPositiveOrZero(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget alphabetic(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateAlphabetic(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget alphaNumeric(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateAlphaNumeric(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget smaller(String errorMsg, int value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateSmaller(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget smallerOrEqual(String errorMsg, int value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateSmallerOrEqual(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget greater(String errorMsg, int value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateGreater(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget greaterOrEqual(String errorMsg, int value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateGreaterOrEqual(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget smaller(String errorMsg, double value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateSmaller(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget smallerOrEqual(String errorMsg, double value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateSmallerOrEqual(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget greater(String errorMsg, double value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateGreater(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget greaterOrEqual(String errorMsg, double value) {
		if (isOk()) {
			if (!GwtValidationUtils.validateGreaterOrEqual(text, value)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget day(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDay(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget month(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateMonth(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget year(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateYear(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget yearToday(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateYearToday(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget yearTodaySmallerOrEqual(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateYearTodaySmallerOrEqual(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget yearSmallerOrEqual(String errorMsg, int year) {
		if (isOk()) {
			if (!GwtValidationUtils.validateYearSmallerOrEqual(text, year)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget date(String errorMsg, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDate(text, separator)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget date(String errorMsg) {
		date(errorMsg, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateSmaller(String errorMsg, Date date, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateSmaller(text, separator, date)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateSmaller(String errorMsg, Date date) {
		dateSmaller(errorMsg, date, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerOrEquals(String errorMsg, Date date, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateSmallerOrEquals(text, separator, date)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerOrEquals(String errorMsg, Date date) {
		dateSmallerOrEquals(errorMsg, date, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateGreater(String errorMsg, Date date, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateGreater(text, separator, date)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateGreater(String errorMsg, Date date) {
		dateGreater(errorMsg, date, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterOrEquals(String errorMsg, Date date, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateGreaterOrEquals(text, separator, date)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterOrEquals(String errorMsg, Date date) {
		dateGreaterOrEquals(errorMsg, date, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerToday(String errorMsg, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateSmallerToday(text, separator)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerToday(String errorMsg) {
		dateSmallerToday(errorMsg, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerOrEqualToday(String errorMsg, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateSmallerOrEqualToday(text, separator)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateSmallerOrEqualToday(String errorMsg) {
		dateSmallerOrEqualToday(errorMsg, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterToday(String errorMsg, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateGreaterToday(text, separator)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterToday(String errorMsg) {
		dateGreaterToday(errorMsg, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterOrEqualsToday(String errorMsg, String separator) {
		if (isOk()) {
			if (!GwtValidationUtils.validateDateGreaterOrEqualsToday(text, separator)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget dateGreaterOrEqualsToday(String errorMsg) {
		dateGreaterOrEqualsToday(errorMsg, GwtValidationUtils.DEFAULT_DATE_SEPARATOR);
		return this;
	}

	public TextBoxBaseValidationTarget mail(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateMail(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget regEx(String errorMsg, String regex) {
		if (isOk()) {
			if (!GwtValidationUtils.validateRegEx(text, regex)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	public TextBoxBaseValidationTarget cuil(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateCuil(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	/**
	 * Valida que el texto tenga al menos "numberCount" numeros
	 */
	public TextBoxBaseValidationTarget withNumbers(String errorMsg, int numberCount) {
		if (isOk()) {
			if (!GwtValidationUtils.validateWithNumbers(text, numberCount)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

	/**
	 * Valida que el texto no tenga espacios en blanco por ningún lado
	 */
	public TextBoxBaseValidationTarget withoutSpaces(String errorMsg) {
		if (isOk()) {
			if (!GwtValidationUtils.validateWithoutSpaces(text)) {
				this.errorMsg = errorMsg;
			}
		}
		return this;
	}

}
