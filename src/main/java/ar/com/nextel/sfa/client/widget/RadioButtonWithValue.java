package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonWithValue extends RadioButton {
	String value;
	public RadioButtonWithValue(String grupo, String label, String value) {
		super(grupo,label);
		this.value=value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
