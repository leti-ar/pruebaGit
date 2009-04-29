package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.EmailDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.TelefonoDto;
import ar.com.nextel.sfa.client.dto.TipoEmailDto;
import ar.com.nextel.sfa.client.dto.TipoTelefonoDto;
import ar.com.nextel.sfa.client.enums.TipoEmailEnum;
import ar.com.nextel.sfa.client.enums.TipoTelefonoEnum;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


public class CuentaDatosForm extends Composite {

	private static CuentaDatosForm instance = new CuentaDatosForm();
	private FlexTable mainPanel;
	private FlexTable datosCuentaTable;
	private FlexTable telefonoTable;
	private FlexTable emailTable;
	private FlexTable formaDePagoTable;
	private DualPanel fechaUsuarioTable;
	private FlexTable usuario;
	private FlexTable fechaCreacion;
	private CuentaUIData cuentaEditor;
	private FormButtonsBar footerBar;
	private TelefonoTextBox telPrincipalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telAdicionalTextBox = new TelefonoTextBox();
	private TelefonoTextBox telCelularTextBox   = new TelefonoTextBox(false);
	private TelefonoTextBox telFaxTextBox       = new TelefonoTextBox();
	
	public static CuentaDatosForm getInstance() {
		return instance;
	}
	
	private CuentaDatosForm() {
		cuentaEditor = new CuentaUIData();
		mainPanel    = new FlexTable();
		footerBar    = new FormButtonsBar();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("abmPanel2");
		mainPanel.setWidget(0,0,createDatosCuentaPanel());
		mainPanel.setWidget(1,0,createTelefonoPanel());
		mainPanel.setWidget(2,0,createEmailPanel());
		mainPanel.setWidget(3,0,createFormaDePagoPanel());
		mainPanel.setWidget(4,0,createFechaUsuarioPanel());
		//mainPanel.addStyleName("aButtons");
		
		cuentaEditor.getGuardar().setStyleName("link");
		cuentaEditor.getCrearSS().setStyleName("link");
		cuentaEditor.getAgregar().setStyleName("link");
		cuentaEditor.getCancelar().setStyleName("link");
		
		footerBar.addLink(cuentaEditor.getGuardar());
		footerBar.addLink(cuentaEditor.getCrearSS());
		footerBar.addLink(cuentaEditor.getAgregar());
		footerBar.addLink(cuentaEditor.getCancelar());		
		mainPanel.setWidget(5,0,footerBar);

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
		datosCuentaTable.setWidth("100%");
		
		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		cuentaEditor.getRazonSocial().setWidth("90%");
		
		TitledPanel datosCuentaPanel = new TitledPanel(Sfa.constant().cuentaPanelTitle());

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
		datosCuentaTable.setWidget(3, 4, cuentaEditor.getFechaNacimientoGrid());
		datosCuentaTable.setText(4, 0, Sfa.constant().contribuyente());
		datosCuentaTable.setWidget(4, 1, cuentaEditor.getContribuyente());
		datosCuentaTable.setText(5, 0, Sfa.constant().provedorAnterior());
		datosCuentaTable.setWidget(5, 1, cuentaEditor.getProveedorAnterior());
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

		datosCuentaTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		
//		datosCuentaPanel.addStyleName("abmPanel");
//		datosCuentaTable.addStyleName("abmPanelChild");
		datosCuentaPanel.add(datosCuentaTable);
		
		return datosCuentaPanel;
	}

	private Widget createTelefonoPanel() {
		telefonoTable = new FlexTable();
		telefonoTable.setWidth("95%");
		TitledPanel telefonoPanel = new TitledPanel(Sfa.constant().telefonoPanelTitle());
		telefonoPanel.add(telefonoTable);
		cuentaEditor.getObservaciones().addStyleName("obsTextAreaCuentaDatos");
		
		telefonoTable.setText(0, 0, Sfa.constant().principal());
		telefonoTable.setWidget(0, 1, telPrincipalTextBox);
		telefonoTable.setText(0, 2, Sfa.constant().adicional());
		telefonoTable.setWidget(0, 3, telAdicionalTextBox);
		telefonoTable.setText(1, 0, Sfa.constant().celular());
		telefonoTable.setWidget(1, 1, telCelularTextBox);
		telefonoTable.setText(1, 2, Sfa.constant().fax());
		telefonoTable.setWidget(1, 3, telFaxTextBox);
		telefonoTable.setText(2, 0, Sfa.constant().observaciones());
		telefonoTable.setWidget(2, 1, cuentaEditor.getObservaciones());
		telefonoTable.getFlexCellFormatter().setColSpan(2, 1, 3);
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
		formaDePagoTable.setWidget(0, 1, cuentaEditor.getFormaPago());		
		return formaDePagoPanel;
	}
	
	private Widget createFechaUsuarioPanel() {
		fechaUsuarioTable = new DualPanel();
		usuario = new FlexTable();
		fechaCreacion = new FlexTable();
		usuario.setText(0, 0, Sfa.constant().usuario());
		usuario.setWidget(0, 1, cuentaEditor.getUsuario());
		fechaCreacion.setText(0, 0, Sfa.constant().fechaCreacion());
		fechaCreacion.setWidget(0, 1, cuentaEditor.getFechaCreacion());

		fechaUsuarioTable.setLeft(usuario);
		fechaUsuarioTable.setRight(fechaCreacion);

		return fechaUsuarioTable;
	}

	public void reset() {
		cuentaEditor.clean();
	}

	public CuentaUIData getCuentaEditor() {
		return cuentaEditor;
	}

	public void setCuentaEditor(CuentaUIData cuentaEditor) {
		this.cuentaEditor = cuentaEditor;
	}

	public void cargarDatos(CuentaDto cuentaDto) {
		if (cuentaDto.getPersona()!=null) {
			//area datos
			if (cuentaDto.getPersona().getDocumento()!=null) {
				cuentaEditor.getTipoDocumento().setSelectedItem(cuentaDto.getPersona().getDocumento().tipoDocumento) ;
				cuentaEditor.getNumeroDocumento().setText(cuentaDto.getPersona().getDocumento().getNumero());
			}
			cuentaEditor.getRazonSocial().setText(cuentaDto.getPersona().getRazonSocial());
			cuentaEditor.getNombre().setText(cuentaDto.getPersona().getNombre());
			cuentaEditor.getApellido().setText(cuentaDto.getPersona().getApellido());
			cuentaEditor.getSexo().setSelectedItem(cuentaDto.getPersona().getSexo());
			cuentaEditor.getFechaNacimiento().setSelectedDate(cuentaDto.getPersona().getFechaNacimiento());
			cuentaEditor.getContribuyente().setSelectedItem(cuentaDto.getTipoContribuyente());
			cuentaEditor.getProveedorAnterior().setSelectedItem(cuentaDto.getProveedorInicial());
			cuentaEditor.getCategoria().setText(cuentaDto.getCategoriaCuenta().getDescripcion());
			cuentaEditor.getClaseCliente().setSelectedItem(cuentaDto.getClaseCuenta());
			cuentaEditor.getCicloFacturacion().setText(cuentaDto.getCicloFacturacion().getCodigoFNCL());

			//area telefono
			for (int i=0;i<cuentaDto.getPersona().getTelefonos().size();i++) {
				TelefonoDto telefono = (TelefonoDto)cuentaDto.getPersona().getTelefonos().get(i);
				TipoTelefonoDto tipoTelefono = telefono.getTipoTelefono();

				if ( tipoTelefono.getId()==TipoTelefonoEnum.PRINCIPAL.getTipo()) {
					telPrincipalTextBox.getArea().setText(telefono.getArea());
					telPrincipalTextBox.getNumero().setText(telefono.getNumeroLocal());
					telPrincipalTextBox.getInterno().setText(telefono.getInterno());
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.PARTICULAR.getTipo() ||
 					 tipoTelefono.getId()==TipoTelefonoEnum.ADICIONAL.getTipo()) {
					telAdicionalTextBox.getArea().setText(telefono.getArea());
					telAdicionalTextBox.getNumero().setText(telefono.getNumeroLocal());
					telAdicionalTextBox.getInterno().setText(telefono.getInterno());
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.CELULAR.getTipo()) {
					telCelularTextBox.getArea().setText(telefono.getArea());
					telCelularTextBox.getNumero().setText(telefono.getNumeroLocal());
				}
				if ( tipoTelefono.getId()==TipoTelefonoEnum.FAX.getTipo()) {
					telFaxTextBox.getArea().setText(telefono.getArea());
					telFaxTextBox.getNumero().setText(telefono.getNumeroLocal());
					telFaxTextBox.getInterno().setText(telefono.getInterno());
				}
			}
			cuentaEditor.getObservaciones().setText(cuentaDto.getObservacionesTelMail());
			//EMail
			for (int i=0;i<cuentaDto.getPersona().getEmails().size();i++) {
			    EmailDto email = (EmailDto) cuentaDto.getPersona().getEmails().get(i);
			    TipoEmailDto tipoEmail = email.getTipoEmail();
			    if (tipoEmail.getId()==TipoEmailEnum.PERSONAL.getTipo()) {
			    	cuentaEditor.getEmailPersonal().setText(email.getEmail());
			    }
			    if (tipoEmail.getId()==TipoEmailEnum.LABORAL.getTipo()) {
			    	cuentaEditor.getEmailLaboral().setText(email.getEmail());
			    }
			}
			    
			//Forma de Pago

			//Vendedor
			
		}
	}
}
