package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GenerarSSUI extends NextelDialog {

	private GenerarSSUIData generarSSData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;

	private static GenerarSSUI generarSSUI = null;

	public static GenerarSSUI getInstance() {
		if (generarSSUI == null) {
			generarSSUI = new GenerarSSUI("SS - Generar SS");
		}
		return generarSSUI;
	}

	ClickListener listener = new ClickListener() {
		public void onClick(Widget sender) {
			if (sender == aceptar) {

			} else if (sender == cancelar) {
				hide();
			} else {
				if (((CheckBox)sender).isChecked()) {
					generarSSData.getEmail().setEnabled(true);
				} else {
					generarSSData.getEmail().setEnabled(false);
					generarSSData.getEmail().setText("");
				}
			}
		}
	};

	public GenerarSSUI(String title) {
		super(title);
		init();
	}

	private void init() {
		setWidth("740px");
		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(listener);
		cancelar = new SimpleLink(Sfa.constant().cancelar());
		cancelar.addClickListener(listener);
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFooterVisible(false);
		setFormButtonsVisible(true);

		generarSSData = new GenerarSSUIData();
		generarSSData.getNuevo().addClickListener(listener);
		FlexTable primeraTabla = new FlexTable();
		primeraTabla.setWidth("100%");
		primeraTabla.setCellSpacing(5);
//		primeraTabla.getFlexCellFormatter().setColSpan(1, 1, 2);
		Label titulo = new Label(Sfa.constant().emailPanelTitle());
		titulo.addStyleName("req");
		primeraTabla.setWidget(0, 0, titulo);
		primeraTabla.setWidget(1, 0, generarSSData.getLaboral());
		primeraTabla.setText(1, 1, Sfa.constant().laboral());
		primeraTabla.setWidget(2, 0, generarSSData.getPersonal());
		primeraTabla.setText(2, 1, Sfa.constant().personal());
		primeraTabla.setWidget(3, 0, generarSSData.getNuevo());
		primeraTabla.setText(3, 1, Sfa.constant().nuevo());
		primeraTabla.setText(3, 2, Sfa.constant().emailPanelTitle());
		primeraTabla.setWidget(3, 3, generarSSData.getEmail());
		primeraTabla.setText(4, 1, Sfa.constant().scoringTitle());
		primeraTabla.setWidget(4, 0, generarSSData.getScoring());
		add(primeraTabla);
	}
}
