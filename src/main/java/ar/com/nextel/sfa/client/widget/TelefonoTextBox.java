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
		numero.setWidth("80px");

		mainpanel.add(area);
		mainpanel.add(new InlineHTML("-"));
		mainpanel.add(numero);

		if (showInterno) {
			interno = new TextBox();
			interno.setWidth("35px");
			mainpanel.add(new InlineHTML("-"));
			mainpanel.add(interno);
		}
	}

	public void clean() {
		area.setText("");
		numero.setText("");
		interno.setText("");
	}
}
