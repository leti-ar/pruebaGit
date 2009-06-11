package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;

public class TelefonoTextBox extends Composite {

	private FlowPanel mainpanel;
	private TextBox area; 
	private TextBox numero; 
	private TextBox interno; 

	public TelefonoTextBox() {
		this(true);
	}

	public TelefonoTextBox(boolean showInterno) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		area = new TextBox();
		numero = new TextBox();

		area.setWidth("40px");
		area.setMaxLength(5);
		numero.setWidth("80px");
		numero.setMaxLength(8);

		mainpanel.add(area);
		mainpanel.add(new InlineHTML("-"));
		mainpanel.add(numero);

		if (showInterno) {
			interno = new TextBox();
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

	public void setArea(TextBox area) {
		this.area = area;
	}

	public TextBox getNumero() {
		return numero;
	}

	public void setNumero(TextBox numero) {
		this.numero = numero;
	}

	public TextBox getInterno() {
		return interno;
	}

	public void setInterno(TextBox interno) {
		this.interno = interno;
	}

	public void clean() {
		area.setText("");
		numero.setText("");
		if (interno != null) { 
			interno.setText("");
		}
	}
}
