package ar.com.nextel.sfa.client.widget;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonGroup {

	String grupo = "";
	List<RadioButtonWithValue> radios = new ArrayList<RadioButtonWithValue>();

	public RadioButtonGroup() {
	}

	public RadioButtonGroup(String grupo) {
		this.grupo = grupo;
	}

	public void addRadio(RadioButtonWithValue radio) {
		this.radios.add(radio);
	}

	public void addRadio(String grupo, String label, String value) {
		this.radios.add(new RadioButtonWithValue(grupo, label, value));
	}

	public void addRadio(String label, String value) {
		this.radios.add(new RadioButtonWithValue(this.grupo, label, value));
	}

	public List<RadioButtonWithValue> getRadios() {
		return radios;
	}

	public void setRadios(List<RadioButtonWithValue> radios) {
		this.radios = radios;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getValueChecked() {
		return recuperarValorChecked("v");
	}

	public String getLabelChecked() {
		return recuperarValorChecked("l");
	}

	public void setValueChecked(String value) {
		for (RadioButtonWithValue radioBtn : radios) {
			if (radioBtn.getTextValue().equals(value)) {
				radioBtn.setValue(true);
				break;
			}
		}
	}

	private String recuperarValorChecked(String what) {
		String value = null;
		if (radios != null && radios.size() > 0) {
			for (RadioButtonWithValue radioBtn : radios) {
				if (radioBtn.getValue()) {
					value = what.equals("v") ? radioBtn.getTextValue() : radioBtn.getText();
					break;
				}
			}
		}
		return value;
	}

}
