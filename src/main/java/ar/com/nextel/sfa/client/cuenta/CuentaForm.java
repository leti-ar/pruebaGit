package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public class CuentaForm extends Composite {

	private FlowPanel mainPanel;
	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private FlexTable emailTable;
	private FlexTable formaDePagoTable;
	private FlexTable fechaUsuarioTable;
	private CuentaUIData cuentaEditor;
	private FormButtonsBar footerBar;
	

	public CuentaForm() {
		cuentaEditor = new CuentaUIData();
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		initWidget(mainPanel);
		
		mainPanel.add(createDatosCuentaPanel());
		mainPanel.add(createTelefonoPanel());
		mainPanel.add(createEmailPanel());
		mainPanel.add(createFormaDePagoPanel());
		mainPanel.add(createFechaUsuarioPanel());
			
		footerBar.addLink(cuentaEditor.getGuardar());
		footerBar.addLink(cuentaEditor.getCrearSS());
		footerBar.addLink(cuentaEditor.getAgregar());
		footerBar.addLink(cuentaEditor.getCancelar());		
		mainPanel.add(footerBar);

		cuentaEditor.getAgregar().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				validarYAgregar(cuentaEditor.getPersona());
			}
		});
		
	}
	
	private void validarYAgregar(PersonaDto personaDto) {
		CuentaRpcService.Util.getInstance().saveCuenta(personaDto,
				new DefaultWaitCallback() {
					public void success(Object result) {
						PersonaDto personaDto = (PersonaDto) result;
						System.out.println("Apretaste Agregar");
					}
				});
	}

	private Widget createDatosCuentaPanel() {
		datosCuentaTable = new FlexTable();
		TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());
		datosCuentaPanel.add(datosCuentaTable);

		datosCuentaTable.setText(0, 0, Sfa.constant().tipoDocumento());
		datosCuentaTable.setWidget(0, 1, cuentaEditor.getTipoDocumento());
		datosCuentaTable.setText(0, 3, Sfa.constant().numero());
		datosCuentaTable.setWidget(0, 4, cuentaEditor.getNumeroDocumento());
		datosCuentaTable.setText(1, 0, Sfa.constant().razonSocial());
		datosCuentaTable.setWidget(1, 1, cuentaEditor.getRazonSocial());
		datosCuentaTable.setText(2, 0, Sfa.constant().nombre());
		datosCuentaTable.setWidget(2, 1, cuentaEditor.getNombre());
		datosCuentaTable.setText(2, 3, Sfa.constant().apellido());
		datosCuentaTable.setWidget(2, 4, cuentaEditor.getApellido());
		datosCuentaTable.setText(3, 0, Sfa.constant().sexo());
		datosCuentaTable.setWidget(3, 1, cuentaEditor.getSexo());
		datosCuentaTable.setText(3, 3, Sfa.constant().fechaNacimiento());
		datosCuentaTable.setWidget(3, 4, cuentaEditor.getFechaNacimiento());
		datosCuentaTable.setText(4, 0, Sfa.constant().contribuyente());
		datosCuentaTable.setWidget(4, 1, cuentaEditor.getContribuyente());
		datosCuentaTable.setText(5, 0, Sfa.constant().provedorAnterior());
		datosCuentaTable.setWidget(5, 1, cuentaEditor.getProvedorAnterior());
		datosCuentaTable.setText(5, 3, Sfa.constant().rubro());
		datosCuentaTable.setWidget(5, 4, cuentaEditor.getRubro());
		datosCuentaTable.setText(6, 0, Sfa.constant().claseCliente());
		datosCuentaTable.setWidget(6, 1, cuentaEditor.getClaseCliente());
		datosCuentaTable.setText(6, 3, Sfa.constant().categoria());
		datosCuentaTable.setWidget(6, 4, cuentaEditor.getCategoria());
		datosCuentaTable.setText(7, 0, Sfa.constant().cicloFacturacion());
		datosCuentaTable.setWidget(7, 1, cuentaEditor.getCicloFacturacion());
		datosCuentaTable.setText(7, 3, Sfa.constant().veraz());
		datosCuentaTable.setWidget(7, 4, cuentaEditor.getVeraz());

		datosCuentaTable.addStyleName("gwt-Label");
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		cuentaEditor.getRazonSocial().setWidth("100%");

		return datosCuentaPanel;
	}

	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		TitledPanel telefonoPanel = new TitledPanel(Sfa.constant().telefonoPanelTitle());
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
		telefonoTable.setWidget(2, 1, cuentaEditor.getObservaciones());

		return telefonoPanel;
	}

	private Widget createEmailPanel() {
		emailTable = new FlexTable();
		TitledPanel emailPanel = new TitledPanel(Sfa.constant().emailPanelTitle());
		emailPanel.add(emailTable);
		
		emailTable.setText(0, 0, Sfa.constant().personal());
		emailTable.setWidget(0, 1, cuentaEditor.getEmailPersonal());
		emailTable.setText(0, 2, Sfa.constant().laboral());
		emailTable.setWidget(0, 3, cuentaEditor.getEmailLaboral());

		return emailPanel;
	}

	private Widget createFormaDePagoPanel() {
		formaDePagoTable = new FlexTable();
		TitledPanel formaDePagoPanel = new TitledPanel(Sfa.constant().formaDePagoPanelTitle());
		formaDePagoPanel.add(formaDePagoTable);
		
		formaDePagoTable.setText(0, 0, Sfa.constant().modalidad());
		formaDePagoTable.setWidget(0, 1, cuentaEditor.getModalidadPago());		
		return formaDePagoPanel;
	}
	
	private Widget createFechaUsuarioPanel() {
		fechaUsuarioTable = new FlexTable();
				
		fechaUsuarioTable.setText(0, 0, Sfa.constant().usuario());
		fechaUsuarioTable.setWidget(0, 1, cuentaEditor.getUsuario());		
		fechaUsuarioTable.setText(0, 2, Sfa.constant().fechaCreacion());
		fechaUsuarioTable.setWidget(0, 3, cuentaEditor.getFechaCreacion());	
		
		return fechaUsuarioTable;
	}
	
}
