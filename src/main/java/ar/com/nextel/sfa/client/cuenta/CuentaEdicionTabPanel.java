package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.DualPanel;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;

public class CuentaEdicionTabPanel {

	private static CuentaEdicionTabPanel instance = new CuentaEdicionTabPanel();
	private FlexTable marco = new FlexTable();
	private TabPanel tabPanel;
	private DualPanel encabezado;
	private Label razonSocialLabel;
	private InlineLabel clienteLabel;
	
	private CuentaEdicionTabPanel() {
		init();
	}

	public static CuentaEdicionTabPanel getInstance() {
		return instance;
	}
	
	public void init() {
		marco = new FlexTable();
		marco.setWidth("100%");

		encabezado = new DualPanel();
		encabezado.addStyleName("layout");
		
		razonSocialLabel = new Label(Sfa.constant().razonSocial());
		clienteLabel = new InlineLabel(Sfa.constant().cliente());
		encabezado.setLeft(razonSocialLabel);
		encabezado.setRight(clienteLabel);
		
		marco.setWidget(0, 0, encabezado);
		tabPanel = new TabPanel();
		tabPanel.setWidth("100%");
		tabPanel.add(CuentaDatosForm.getInstance(), Sfa.constant().datos());
		tabPanel.add(CuentaDomicilioForm.getInstance(), Sfa.constant().domicilios());
		tabPanel.add(CuentaContactoForm.getInstance(), Sfa.constant().contactos());
		tabPanel.selectTab(0);
		marco.setWidget(1, 0, tabPanel);
		
	}
	
	public FlexTable getCuentaEdicionPanel() {
		return marco;
	}
	
	public void clean() {
		
	}
}
