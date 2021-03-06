package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.util.RegularExpressionConstants;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;

public class TelefonoTextBox extends Composite {

	private FlowPanel mainpanel;
	private ValidationTextBox area;
	private ValidationTextBox numero;
	private ValidationTextBox interno;

	public TelefonoTextBox() {
		this(true);
	}

	public TelefonoTextBox(boolean showInterno) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		area = new ValidationTextBox(RegularExpressionConstants.getNumerosLimitado(5));
		numero = new ValidationTextBox(RegularExpressionConstants.getNumerosLimitado(8));

		area.setWidth("40px");
		area.setMaxLength(5);
		numero.setWidth("80px");
		numero.setMaxLength(8);

		mainpanel.add(area);
		mainpanel.add(new InlineHTML("-"));
		mainpanel.add(numero);

		if (showInterno) {
			interno = new ValidationTextBox(RegularExpressionConstants.getNumerosLimitado(4));
			interno.setWidth("35px");
			interno.setMaxLength(4);
			mainpanel.add(new InlineHTML("-"));
			mainpanel.add(interno);
		}
	}

	public FlowPanel getMainpanel() {
		return mainpanel;
	}

	public void setMainpanel(FlowPanel mainpanel) {
		this.mainpanel = mainpanel;
	}

	public TextBox getArea() {
		return area;
	}

	public TextBox getNumero() {
		return numero;
	}

	public TextBox getInterno() {
		return interno;
	}

	public void setArea(ValidationTextBox area) {
		this.area = area;
	}

	public void setNumero(ValidationTextBox numero) {
		this.numero = numero;
	}

	public void setInterno(ValidationTextBox interno) {
		this.interno = interno;
	}

	public void clean() {
		area.setText("");
		area.setReadOnly(false);
		numero.setText("");
		numero.setReadOnly(false);
		if (interno != null) {
			interno.setText("");
			interno.setReadOnly(false);
		}
	}
}
