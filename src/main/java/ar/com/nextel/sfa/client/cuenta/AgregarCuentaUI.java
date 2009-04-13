package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.UILoader;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class AgregarCuentaUI extends ApplicationUI {

	private TabPanel tabPanel;
	protected boolean firstLoad = true;
	private CuentaForm agregarCuentaForm;
	private DualPanel encabezado;
	private Label razonSocialLabel;
	private InlineLabel clienteLabel;


	public AgregarCuentaUI() {
		super();
	}

	public void load() {

		if (firstLoad) {
			NextelDialog buscadorDocumentoPopup = new BuscadorDocumentoPopup("Agregar");
			buscadorDocumentoPopup.showAndCenter();
			agregarCuentaForm = new CuentaForm();
			firstLoad = false;
			encabezado = new DualPanel();
			razonSocialLabel = new Label("Razón Social:");
			razonSocialLabel.addStyleName("razonSocial");
			encabezado.setLeft(razonSocialLabel);
			clienteLabel = new InlineLabel("Cliente:");
			clienteLabel.addStyleName("numeroCliente");
			encabezado.setRight(clienteLabel);
			mainPanel.add(encabezado);
			
			tabPanel = new TabPanel();
			tabPanel.add(new CuentaForm(), "Datos");
			tabPanel.addStyleName("gwt-TabPanelBottom");
			tabPanel.addStyleName("gwt-TabBarItem-selected");
			
			
			mainPanel.add(tabPanel);
			mainPanel.add(agregarCuentaForm);
			
			Button aceptar = new Button("Aceptar", new ClickListener() {
				public void onClick(Widget sender) {
					UILoader.getInstance().setPage(UILoader.SOLO_MENU);
				}
			});
			//mainPanel.add(aceptar);
		}
	}

	public void unload() {

	}

}
