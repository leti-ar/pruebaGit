package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactosUI extends NextelDialog {

	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private ContactoUIData contactosData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private SimpleLink agregar;
	private SimpleLink veraz;

	private static ContactosUI cuentaCrearContactoPopUp = null;

	public static ContactosUI getInstance() {
		if (cuentaCrearContactoPopUp == null) {
			cuentaCrearContactoPopUp = new ContactosUI();
		}
		return cuentaCrearContactoPopUp;
	}
	
	 ClickListener listener = new ClickListener(){
		public void onClick(Widget sender){
		if(sender == aceptar){
			validarCampoObligatorio(true);
		}
		else if(sender == veraz){
			validarVeraz(true);
		}
		else if(sender == cancelar){
			hide();
		}
		else{
			DomicilioUI.getInstance().showAndCenter();
		}
	}};


	public ContactosUI() {
		super("Crear Contacto");
		init();
	}

	private void init() {
		setWidth("740px");
		contactosData = new ContactoUIData();
		TabPanel mainTabPanel = new TabPanel();
		mainTabPanel
				.addStyleDependentName("gwt-TabPanelBottom crearCuentaTabPanel");
		mainTabPanel.setWidth("98%");
		mainTabPanel.add(createDatosCuentaPanel(), "Datos");
		mainTabPanel.add(createDomicilioPanel(), "Domicilios");
		mainTabPanel.add(createTelefonoPanel(), "Telefonos");
		mainTabPanel.selectTab(0);
		add(mainTabPanel);

		aceptar = new SimpleLink("Aceptar");
		aceptar.addClickListener(listener);

		cancelar = new SimpleLink("Cerrar");
		cancelar.addClickListener(listener);
		
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);

	}

	public void cargaPopupNuevoContacto() {
		contactosData.clean();
		aceptar.setVisible(true);
		contactosData.enableFields();
		showAndCenter();
		// setDialogTitle("Crear Contacto");
	}

	public void cargarPopupEditarConatcto(/*
										 * ContactoDto contacto, boolean
										 * editable
										 */) {
		contactosData.setContacto(/* contacto */);
		// if (!editable){
		// contactosData.disableFields();
		// aceptar.setVisible(false);
		// }else{
		// contactosData.enableFields();
		// aceptar.setVisible(true);
		// }
		// setDialogTitle("Editar Contacto");
		showAndCenter();
	}

	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		datosCuentaTable.setWidth("100%");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		datosCuentaTable.setCellSpacing(5);
		
		veraz = new SimpleLink();
		veraz.addClickListener(listener);
		veraz.addStyleName("linkVeraz");

		FlowPanel datosCuentaPanel = new FlowPanel();
		datosCuentaPanel.addStyleName("gwt-TabPanelBottom content");
		datosCuentaTable.setText(0, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(0, 1, contactosData.getTipoDocumento());
		datosCuentaTable.setWidget(0, 3, new Label(Sfa.constant().numero()));
		datosCuentaTable.setWidget(0, 4, contactosData.getNumeroDocumento());
		datosCuentaTable.setWidget(2, 0, new Label(Sfa.constant().nombre()));
		datosCuentaTable.setWidget(2, 1, contactosData.getNombre());
		datosCuentaTable.setWidget(2, 3, new Label(Sfa.constant().apellido()));
		datosCuentaTable.setWidget(2, 4, contactosData.getApellido());
		datosCuentaTable.setText(3, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(3, 1, contactosData.getSexo());
		datosCuentaTable.setText(4, 0, Sfa.constant().cargo());
		datosCuentaTable.setWidget(4, 1, contactosData.getCargo());
		FlexTable verazTable = new FlexTable();
		verazTable.setWidget(0, 0, veraz);
		verazTable.setText(0, 1, Sfa.constant().veraz());
		verazTable.setWidget(0, 2, contactosData.getVeraz());

		datosCuentaPanel.add(datosCuentaTable);
		datosCuentaPanel.add(verazTable);
		return datosCuentaPanel;
	}

	private Widget createDomicilioPanel() {
		FlowPanel domicilioPanel = new FlowPanel();
		domicilioPanel.addStyleDependentName("gwt-TabPanelBottom content");
		agregar = new SimpleLink("Crear Nuevo");
		agregar.addStyleName("crearDomicilioButton");
		agregar.addClickListener(listener);
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

		
		return domicilioPanel;
	}

	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 4);
		telefonoTable.getFlexCellFormatter().setColSpan(4, 1, 4);
		telefonoTable.setWidth("100%");
		telefonoTable.setCellSpacing(6);
		FlowPanel telefonoPanel = new FlowPanel();
		telefonoPanel.addStyleDependentName("gwt-TabPanelBottom content");
		telefonoPanel.add(telefonoTable);

		telefonoTable.setWidget(0, 0, new Label(Sfa.constant().telefonos()));
		telefonoTable.setText(1, 0, Sfa.constant().principal());
		telefonoTable.setWidget(1, 1, contactosData.getTelefonoPrincipal());
		telefonoTable.setText(1, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(1, 3, contactosData.getTelefonoAdicional());
		telefonoTable.setText(2, 0, Sfa.constant().celular());
		telefonoTable.setWidget(2, 1, contactosData.getTelefonoCelular());
		telefonoTable.setText(2, 2, Sfa.constant().fax());
		telefonoTable.setWidget(2, 3, contactosData.getFax());
		telefonoTable.setWidget(3, 0, new Label(Sfa.constant()
				.emailPanelTitle()));
		telefonoTable.setText(4, 0, Sfa.constant().personal());
		telefonoTable.setWidget(4, 1, contactosData.getEmailPersonal());
		telefonoTable.setText(4, 2, Sfa.constant().laboral());
		telefonoTable.setWidget(4, 3, contactosData.getEmailLaboral());

		return telefonoPanel;
	}
	
	private void validarCampoObligatorio(boolean showErrorDialog) {
		List<String> errors = contactosData.validarCampoObligatorio();
		if (!errors.isEmpty()) {
			if (showErrorDialog) {
				ErrorDialog.getInstance().show(errors);
			}
		} else {
			
		}
	}
	
	private void validarVeraz(boolean showErrorDialog){
		List<String> errors = contactosData.validarVeraz();
		if (!errors.isEmpty()) {
			if (showErrorDialog) {
				ErrorDialog.getInstance().show(errors);
			}
		} else {
			
		}
	}
}
