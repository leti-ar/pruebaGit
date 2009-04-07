package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class AgregarCuentaUI extends ApplicationUI {

	private FlowPanel header;
	private TabPanel tabPanel;
	protected boolean firstLoad = true;
	private CuentaForm agregarCuentaForm;


	public AgregarCuentaUI() {
		super();
	}

	public void load() {

		if (firstLoad) {
			NextelDialog buscadorDocumentoPopup = new BuscadorDocumentoPopup("Agregar");
			buscadorDocumentoPopup.showAndCenter();
			agregarCuentaForm = new CuentaForm();
			firstLoad = false;
			header = new FlowPanel();
			header.add(new InlineLabel("Razón Social:"));
			
			tabPanel = new TabPanel();
			tabPanel.add(new CuentaForm(), "Datos");
			tabPanel.addStyleName("gwt-TabBarItem-selected");
			
			mainPanel.add(tabPanel);
			mainPanel.add(agregarCuentaForm);
			
			Button aceptar = new Button("Aceptar", new ClickListener() {
				public void onClick(Widget sender) {
					UILoader.getInstance().setPage(UILoader.SOLO_MENU);
				}
			});
			mainPanel.add(aceptar);
		}
	}

	public void unload() {

	}

}
