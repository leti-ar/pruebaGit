package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;

public class CuentaEdicionTabPanel {

	private static CuentaEdicionTabPanel instance = new CuentaEdicionTabPanel();
	private FlexTable marco = new FlexTable();
	DualPanel razonSocialPanel = new DualPanel();
	private Label razonSocial = new Label();
	private Label cliente = new Label();
	private CuentaDatosForm     cuentaDatosForm     = CuentaDatosForm.getInstance();
	private CuentaDomiciliosForm cuentaDomiciliosForm = CuentaDomiciliosForm.getInstance();
	private CuentaContactoForm  cuentaContactoForm  = CuentaContactoForm.getInstance();

	
	private CuentaEdicionTabPanel() {
		init();
	}

	public static CuentaEdicionTabPanel getInstance() {
		return instance;
	}
	
	public void init() {


	
		
		
		marco = new FlexTable();
		marco.setWidth("100%");
		
		Label razonSocialLabel = new Label(Sfa.constant().razonSocial());
		razonSocialPanel.setLeft(razonSocialLabel);
		razonSocialPanel.setRight(razonSocial);

		DualPanel clientePanel = new DualPanel();
		Label clienteLabel = new Label(Sfa.constant().cliente());
		clientePanel.setLeft(clienteLabel);
		clientePanel.setRight(cliente);

		marco.setWidget(0, 0, razonSocialPanel);
		marco.setWidget(0, 1, clientePanel);
		TabPanel tabPanel = new TabPanel();
		tabPanel.setWidth("100%");
		tabPanel.add(cuentaDatosForm, Sfa.constant().datos());
		tabPanel.add(cuentaDomiciliosForm, Sfa.constant().domicilios());
		tabPanel.add(cuentaContactoForm, Sfa.constant().contactos());
		tabPanel.selectTab(0);
		marco.setWidget(1, 0, tabPanel);
		marco.getFlexCellFormatter().setColSpan(1, 0, 2);
		
	}
	
	public void clean() {
		cuentaDatosForm.reset();
		razonSocial.setText("");
		cliente.setText("");
	}

	public FlexTable getCuentaEdicionPanel() {
		return marco;
	}

	public String getRazonSocial() {
		return razonSocial.getText();
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial.setText(razonSocial);
	}

	public String getCliente() {
		return cliente.getText();
	}
	public void setCliente(String cliente) {
		this.cliente.setText(cliente);
	}

	public CuentaDatosForm getCuentaDatosForm() {
		return cuentaDatosForm;
	}
	public void setCuentaDatosForm(CuentaDatosForm cuentaDatosForm) {
		this.cuentaDatosForm = cuentaDatosForm;
	}

	public CuentaDomiciliosForm getCuentaDomicilioForm() {
		return cuentaDomiciliosForm;
	}
	public void setCuentaDomicilioForm(CuentaDomiciliosForm cuentaDomicilioForm) {
		this.cuentaDomiciliosForm = cuentaDomicilioForm;
	}

	public CuentaContactoForm getCuentaContactoForm() {
		return cuentaContactoForm;
	}
	public void setCuentaContactoForm(CuentaContactoForm cuentaContactoForm) {
		this.cuentaContactoForm = cuentaContactoForm;
	}
	
	
}
