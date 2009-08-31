package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonWithValue extends RadioButton {
	private String textValue;

	public RadioButtonWithValue(String grupo, String label, String value) {
		super(grupo, label);
		this.textValue = value;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
}
