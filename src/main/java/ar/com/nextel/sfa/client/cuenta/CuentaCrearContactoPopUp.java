package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class CuentaCrearContactoPopUp extends NextelDialog {
	
	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private CuentaUIData cuentaUIData;
	private SimpleLink aceptar;

	private static CuentaCrearContactoPopUp cuentaCrearContactoPopUp = null;
	
	public static CuentaCrearContactoPopUp getInstance(){
		if(cuentaCrearContactoPopUp == null){
			cuentaCrearContactoPopUp = new CuentaCrearContactoPopUp("Crear Contacto");
		}
		return cuentaCrearContactoPopUp;
	}
	
	public CuentaCrearContactoPopUp(String title) {
		super(title);
		setWidth("740px");
		cuentaUIData = new CuentaUIData();
		TabPanel mainTabPanel = new TabPanel();
		mainTabPanel.addStyleDependentName("gwt-TabPanelBottom crearCuentaTabPanel");
		mainTabPanel.setWidth("98%");
		mainTabPanel.add(createDatosCuentaPanel(), "Datos");
		mainTabPanel.add(createDomicilioPanel(),"Domicilios");
		mainTabPanel.add(createTelefonoPanel(), "Telefonos");
		mainTabPanel.selectTab(0);
		add(mainTabPanel);
		
		aceptar = new SimpleLink("Aceptar");
		SimpleLink cerrar = new SimpleLink("Cerrar");
		cerrar.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				hide();
				
			}});
		
		addFormButtons(aceptar);
		addFormButtons(cerrar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
	}
	

	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		datosCuentaTable.setWidth("100%");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		cuentaUIData.getRazonSocial().setWidth("100%");
		
		FlowPanel datosCuentaPanel = new FlowPanel();
		datosCuentaPanel.addStyleName("gwt-TabPanelBottom content");
		datosCuentaTable.setText(0, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(0, 1, cuentaUIData.getTipoDocumento());
		datosCuentaTable.setWidget(0, 3, new Label(Sfa.constant().numero()));
		datosCuentaTable.setWidget(0, 4, cuentaUIData.getNumeroDocumento());
		datosCuentaTable.setWidget(2, 0, new Label(Sfa.constant().nombre()));
		datosCuentaTable.setWidget(2, 1, cuentaUIData.getNombre());
		datosCuentaTable.setWidget(2, 3, new Label(Sfa.constant().apellido()));
		datosCuentaTable.setWidget(2, 4, cuentaUIData.getApellido());
		datosCuentaTable.setText(3, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(3, 1, cuentaUIData.getSexo());
		datosCuentaTable.setText(4, 0, Sfa.constant().cargo());
		datosCuentaTable.setWidget(4, 1, cuentaUIData.getCargo());
		datosCuentaTable.setText(5, 0, Sfa.constant().veraz());
		datosCuentaTable.setWidget(5, 1, cuentaUIData.getVeraz());

		datosCuentaPanel.add(datosCuentaTable);	
		return datosCuentaPanel;
	}
	
	private Widget createDomicilioPanel() {
		FlowPanel domicilioPanel = new FlowPanel();
		domicilioPanel.addStyleDependentName("gwt-TabPanelBottom content");
		Button agregar = new Button("Crear Nuevo");
		agregar.addStyleName("crearDomicilioButton");
		domicilioPanel.add(agregar);
		
		FlexTable domicilioTable = new FlexTable();
		String[] widths = { "24px", "100px" };
		for (int col = 0; col < widths.length; col++) {
			domicilioTable.getColumnFormatter().setWidth(col, widths[col]);
		}
		domicilioTable.getColumnFormatter().addStyleName(0, "alignCenter");
		domicilioTable.getColumnFormatter().addStyleName(1, "alignCenter");
		domicilioTable.setCellPadding(0);
		domicilioTable.setCellSpacing(0);
		domicilioTable.addStyleName("gwt-BuscarCuentaResultTable");
		domicilioTable.getRowFormatter().addStyleName(0, "header");
		domicilioTable.setWidth("100%");
		domicilioTable.setText(0, 0, Sfa.constant().whiteSpace());
		domicilioTable.setText(0, 1, Sfa.constant().domicilios());
		domicilioPanel.add(domicilioTable);
		
		agregar.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio();
				
			}});
		return domicilioPanel;
	}
	
	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		FlowPanel telefonoPanel = new FlowPanel();
		telefonoPanel.addStyleDependentName("gwt-TabPanelBottom content");
		telefonoPanel.add(telefonoTable);

		telefonoTable.setText(0, 0, Sfa.constant().principal());
		telefonoTable.setWidget(0, 1, new TelefonoTextBox());
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, new TelefonoTextBox());
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, new TelefonoTextBox(false));
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, new TelefonoTextBox());
		telefonoTable.setText(2, 0, Sfa.constant().observaciones());
		telefonoTable.setWidget(2, 1, cuentaUIData.getObservaciones());

		return telefonoPanel;
	}

}
