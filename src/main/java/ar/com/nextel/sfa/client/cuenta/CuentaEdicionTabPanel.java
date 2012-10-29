package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.enums.PrioridadEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.ss.LinksCrearSS;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class CuentaEdicionTabPanel {

	private static CuentaEdicionTabPanel instance = new CuentaEdicionTabPanel();
	private FlexTable marco = new FlexTable();
	public  DualPanel razonSocialPanel = new DualPanel();
	public  DualPanel clientePanel     = new DualPanel();
	public  DualPanel numeroCtaPotPanel= new DualPanel();
	
	public  Label razonSocialLabel = new Label(Sfa.constant().razonSocial()+":");
	public  Label clienteLabel     = new Label(Sfa.constant().cliente()+":");
	public  Label numeroCtaPotLabel= new Label(Sfa.constant().numero()+":");

	private Label razonSocial  = new Label();
	private Label cliente      = new Label();
	private Label numeroCtaPot = new Label();
	
	public  HTML redFlagIcon   = IconFactory.redFlag();
	public  HTML yellowFlagIcon= IconFactory.yellowFlag();
	public  HTML greenFlagIcon = IconFactory.greenFlag();
	
	private FlexTable marcoCliente = new FlexTable();
	
	private CuentaDto            cuenta2editDto;
	private CuentaDatosForm      cuentaDatosForm      = CuentaDatosForm.getInstance();
	private CuentaDomiciliosForm cuentaDomiciliosForm = CuentaDomiciliosForm.getInstance();
	private CuentaContactoForm   cuentaContactoForm   = CuentaContactoForm.getInstance();
	private CuentaInfocomForm    cuentaInfocomForm    = CuentaInfocomForm.getInstance();
	private CuentaNotasForm      cuentaNotasForm      = CuentaNotasForm.getInstance();
//	MGR - Para salir sin "Caratula" (24-07-2012)
//	private CuentaCaratulaForm   cuentaCaratulaForm   = CuentaCaratulaForm.getInstance();
	private TabPanel tabPanel;
	private FormButtonsBar footerBar;
	
	public Button validarCompletitudButton;
	public static final String VALIDAR_COMPLETITUD_FAIL_STYLE = "validarCompletitudFailButton";
	public static final String ID_CUENTA = "idCuenta";
	//MGR - Adm. Vtas. R2 -  Se agrego la pestaña "Caratulas" que es fija
//	MGR - Para salir sin "Caratula" (24-07-2012)
//	public static final int CANT_PESTANIAS_FIJAS = 4;
	public static final int CANT_PESTANIAS_FIJAS = 3;
	
	public static final Long DEFAULT_OPP_PRIORITY = 2L;
	private GwtValidator validator = new GwtValidator();
	
	public SimpleLink guardar;
	public SimpleLink crearSSButton;
	public SimpleLink agregarCuentaButton;
	public SimpleLink cancelar;
	public SimpleLink cerrar;
	public SimpleLink crearCuenta;
	
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
//	private SimpleLink  crearEquipos;
//	private SimpleLink  crearCDW;
//	private SimpleLink  crearMDS;
	private LinksCrearSS linksCrearSS;
	private SimpleLink  agregarDivision;
	private SimpleLink  agregarSuscriptor;
	
	List<String> erroresValidacion = new ArrayList<String>();
    private Command aceptarCommand;
    private Command cancelarCommand;

	public static CuentaEdicionTabPanel getInstance() {
		return instance;
	}
	
	private CuentaEdicionTabPanel() {
		init();
	}

	/**
	 * 	
	 */
	public void init() {
		initRazonSocialClientePanel();
		initValidarCompletitud();
		initTabPanel();
		initFooter();
		
		marco = new FlexTable();
		marco.setWidth("100%");
		marco.setWidget(0, 0, new HTML("<br/>"));
		
		//MGR - #961 
		if( (ClientContext.getInstance().vengoDeNexus() && !ClientContext.getInstance().soyClienteNexus())
				|| !ClientContext.getInstance().vengoDeNexus()){
			marco.setWidget(1, 0, marcoCliente);
		}
		marco.setWidget(2, 0, validarCompletitudButton);
		marco.setWidget(3, 0, tabPanel);
		marco.setWidget(4, 0, footerBar);
		
		cancelarCommand = new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
			}
		};
		aceptarCommand = new Command() {
			public void execute() {
                cancelar();
			}
		};
	}
	
    private void initValidarCompletitud() {
		validarCompletitudButton = new Button(Sfa.constant().validarCompletitud());
		validarCompletitudButton.addStyleName("validarCompletitudButton");
		validarCompletitudButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				validarCompletitud();
			}
		});
    }
	
	/**
	 * 
	 */
	private void initTabPanel() {
		tabPanel = new TabPanel();
		tabPanel.setWidth("100%");
		tabPanel.add(cuentaDatosForm, Sfa.constant().datos());
		tabPanel.add(cuentaDomiciliosForm, Sfa.constant().domicilios());
		tabPanel.add(cuentaContactoForm, Sfa.constant().contactos());
		
//		MGR - Para salir sin "Caratula" (24-07-2012)
//		if (ClientContext.getInstance().getVendedor().isADMCreditos()) {
//			tabPanel.add(cuentaCaratulaForm, Sfa.constant().caratula());
//		}
		
		tabPanel.add(cuentaInfocomForm, Sfa.constant().infocom());
		if (!ClientContext.getInstance().getVendedor().isADMCreditos()) {
			tabPanel.add(cuentaNotasForm, Sfa.constant().notas());
		}
		tabPanel.selectTab(0);
		tabPanel.addTabListener(new TabListener(){
			public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1) {
				return true;
			}
			public void onTabSelected(SourcesTabEvents arg0, int arg1) {
				//MGR - Adm. Vtas. R2 - La 3 ahora es la pasteña de caratulas
//				MGR - Para salir sin "Caratula" (24-07-2012)
//				if(arg1 == 4){
				if(arg1 == 3){
					//cuentaInfocomForm.setIdCuenta(cliente.getText());
					cuentaInfocomForm.load();					
				}
			}
		});
	}

	/**
	 * 
	 */
	public void initRazonSocialClientePanel() {
		FlexTable marcoPriority = new FlexTable();
		marcoPriority.setWidget(0, 0, redFlagIcon);
		marcoPriority.setWidget(0, 1, yellowFlagIcon);
		marcoPriority.setWidget(0, 2, greenFlagIcon);

		razonSocialPanel.setLeft(razonSocialLabel);
		razonSocialPanel.setRight(razonSocial);
		razonSocialPanel.getRight().addStyleName("fontNormalGris");
		
		clientePanel.setLeft(clienteLabel);
		clientePanel.setRight(cliente);		
		clientePanel.getRight().addStyleName("fontNormalGris");
		
		numeroCtaPotPanel.setLeft(numeroCtaPotLabel);
		numeroCtaPotPanel.setRight(numeroCtaPot);		
		numeroCtaPotPanel.getRight().addStyleName("fontNormalGris");

		marcoCliente.setWidth("100%");
		marcoCliente.setWidget(0, 0, razonSocialPanel);
		marcoCliente.setWidget(0, 1, null);
		marcoCliente.setWidget(0, 2, clientePanel);
		marcoCliente.setWidget(0, 3, numeroCtaPotPanel);
		marcoCliente.setWidget(0, 4, marcoPriority);
		
		marcoCliente.getCellFormatter().setWidth(0, 0, "40%");
		marcoCliente.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		marcoCliente.getCellFormatter().setWidth(0, 1, "20%");
		marcoCliente.getCellFormatter().setWidth(0, 2, "20%");
		marcoCliente.getCellFormatter().setWidth(0, 3, "20%");
		marcoCliente.getCellFormatter().setWidth(0, 4, "5");
	}
	
	/**
	 * 
	 */	
	private void initFooter() {
		footerBar = new FormButtonsBar();
		guardar   = new SimpleLink(Sfa.constant().guardar() , "#", true);
		guardar.setStyleName("link");

		crearSSButton = new SimpleLink(Sfa.constant().crearSS(), "#", true);
		agregarCuentaButton = new SimpleLink(Sfa.constant().agregarDivSusc(), "#", true);

		popupCrearSS = new PopupPanel(true);
		popupAgregarCuenta = new PopupPanel(true);
		popupCrearSS.addStyleName("dropUpStyle");
		popupAgregarCuenta.addStyleName("dropUpStyle");
		
		//MGR - #873 - Se indica el Vendedor
		linksCrearSS = new LinksCrearSS(ClientContext.getInstance().getVendedor());
		popupCrearSS.setWidget(linksCrearSS);
		
		FlowPanel linksAgregarCuenta = new FlowPanel();
		linksAgregarCuenta.add(agregarDivision = new SimpleLink("<div>"+Sfa.constant().division()+"</div>"));
		linksAgregarCuenta.add(agregarSuscriptor = new SimpleLink("<div>"+Sfa.constant().suscriptor()+"</div>"));
		popupAgregarCuenta.setWidget(linksAgregarCuenta);
		
		cancelar = new SimpleLink(Sfa.constant().cancelar(), "#", true);
		cancelar.setStyleName("link");
		
		crearCuenta = new SimpleLink(Sfa.constant().crearCuenta(), "#", true);
		cerrar      = new SimpleLink(Sfa.constant().cerrar(), "#", true);
		
		footerBar.addLink(guardar);
		footerBar.addLink(crearCuenta);
		
		//MGR - Integracion
		if(!ClientContext.getInstance().soyClienteNexus())
		{
			footerBar.addLink(crearSSButton);
		}
		
		
		footerBar.addLink(agregarCuentaButton);
		footerBar.addLink(cancelar);
		footerBar.addLink(cerrar);

		guardar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (!editorDirty()) {
					if (cuentaDatosForm.evaluarFacturaElectronicaPanel()) {
						MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().NO_INGRESO_FACTURA_ELECTRONICA(), cancelarCommand);
					} else {
						MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().MSG_NO_HAY_DATOS_NUEVOS(), cancelarCommand);
					}
				} else if (validarCamposTabDatos()) {
					guardar();
				} 
			}
		});
		crearSSButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				pageSolicitudServicio();
			}
		});
		agregarCuentaButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (isFormCompletoYguardado()) {
					popupAgregarCuenta.show();
					popupAgregarCuenta.setPopupPosition(agregarCuentaButton.getAbsoluteLeft(), agregarCuentaButton.getAbsoluteTop() - 35);
				}
			}
		});
//		crearEquipos.addClickListener(new ClickListener() {
//			public void onClick(Widget arg0) {
//				String targetHistoryToken = UILoader.AGREGAR_SOLICITUD + "?" + ID_CUENTA + "=" + cuenta2editDto.getId();
//				History.newItem(targetHistoryToken + "&" + EditarSSUI.ID_GRUPO_SS + "=" + GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS);
//				popupCrearSS.hide();
//			}
//		});
//		crearCDW.addClickListener(new ClickListener() {
//			public void onClick(Widget arg0) {
//				String targetHistoryToken = UILoader.AGREGAR_SOLICITUD + "?" + ID_CUENTA + "=" + cuenta2editDto.getId();
//				History.newItem(targetHistoryToken + "&" + EditarSSUI.ID_GRUPO_SS + "=" + GrupoSolicitudDto.ID_CDW);
//				popupCrearSS.hide();
//			}
//		});
//		crearMDS.addClickListener(new ClickListener() {
//			public void onClick(Widget arg0) {
////				if (isFormCleanAndSave()) {
//					String targetHistoryToken = UILoader.AGREGAR_SOLICITUD + "?" + ID_CUENTA + "=" + cuenta2editDto.getId();
//					History.newItem(targetHistoryToken + "&" + EditarSSUI.ID_GRUPO_SS + "=" + GrupoSolicitudDto.ID_MDS);
//					//History.newItem(targetHistoryToken);
//					popupCrearSS.hide();
////				}
//			}
//		});
		agregarDivision.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupAgregarCuenta.hide();
				CuentaClientService.crearDivision(cuenta2editDto.getId());
			}
		});
		agregarSuscriptor.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupAgregarCuenta.hide();
				CuentaClientService.crearSuscriptor(cuenta2editDto.getId());
			}
		});
		crearCuenta.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				AgregarCuentaUI.getInstance().load();
			}
		});
		cancelar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (editorDirty()) {
					MessageDialog.getInstance().showAceptarCancelar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().ERR_FORMULARIO_DIRTY(), aceptarCommand,cancelarCommand);
				} else {
					cancelar();
				}
			}
		});
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				cancelar();
			}
		});
	}

	/**
	 * Muestra tab infocom o notas se esté editando una cuento o mostrando opp
	 * @param editorCuenta
	 */
	public void setTabsTipoEditorCuenta(boolean editorCuenta)  {
        //saca pestañas que no son fijas
		if(tabPanel.getTabBar().getTabCount()>CANT_PESTANIAS_FIJAS) {
			for (int i=CANT_PESTANIAS_FIJAS; i<tabPanel.getTabBar().getTabCount();i++)  
			    tabPanel.remove(i);			
		}
		//si no es prospect agrega tab infocom
//		MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing. 
		//Si no es cliente, es prospect o prospect en carga
//		if (!RegularExpressionConstants.isVancuc(cuenta2editDto.getCodigoVantive()) &&  
		if (cuenta2editDto.isCustomer() && 
				(ClientContext.getInstance().getUsuario().getUserName().
						equals(cuenta2editDto.getVendedor().getUsuarioDto().getUserName())))
			tabPanel.add(cuentaInfocomForm, Sfa.constant().infocom());

		//si viene de opp agrega notas (Dejar comentado)
		if (!editorCuenta && !ClientContext.getInstance().getVendedor().isADMCreditos()) { 
			tabPanel.add(cuentaNotasForm, Sfa.constant().notas());
		}
		
		validarCompletitudButton.setVisible(editorCuenta && !EditarCuentaUI.edicionReadOnly);
		numeroCtaPotPanel.setVisible(!editorCuenta);
		
		guardar.setVisible(editorCuenta);
		crearSSButton.setVisible(true);
		agregarCuentaButton.setVisible(editorCuenta);
		cancelar.setVisible(editorCuenta);
		cerrar.setVisible(!editorCuenta);
		crearCuenta.setVisible(!editorCuenta);
	}
	
	/**
	 * 
	 * @param prioridad
	 */
	public void setPriorityFlag(Long prioridad) {
		if (prioridad==null) 
			prioridad=DEFAULT_OPP_PRIORITY;
		redFlagIcon.setVisible(PrioridadEnum.ALTA.getId()==prioridad.longValue());
		yellowFlagIcon.setVisible(PrioridadEnum.MEDIA.getId()==prioridad.longValue());
		greenFlagIcon.setVisible(PrioridadEnum.BAJA.getId()==prioridad.longValue());
	}
	
	/**
	 * 
	 */
	public void clean() {
		razonSocial.setText("");
		cliente.setText("");
		cuentaDatosForm.reset();
	}
	
	private void guardar(){
		CuentaDto ctaDto = (CuentaDto)cuentaDatosForm.getCuentaDtoFromEditor();
        //agrego domicilios
		ctaDto.getPersona().setDomicilios(CuentaDomiciliosForm.getInstance().getCuenta().getPersona().getDomicilios());
		//asegura que los contatos esten cargados en la granCuenta (para divisiones y suscriptores)
		agregarContactos(ctaDto);
		
		//Agrego Caratulas
//		MGR - Para salir sin "Caratula" (24-07-2012)
//		ctaDto.setCaratulas(cuentaCaratulaForm.getCaratulas());
		
		//solo para actualizar imagen (sin mensaje de error).
		validarCompletitud(false);
		
		CuentaRpcService.Util.getInstance().saveCuenta(ctaDto,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto cuentaDto) {
				CuentaEdicionTabPanel.getInstance().setCuenta2editDto(cuentaDto);
				//actualiza pestaña datos
				cuentaDatosForm.ponerDatosBusquedaEnFormulario(cuentaDto, true);
				razonSocial.setText(cuentaDto.getPersona().getRazonSocial());
				//actualiza pestaña domicilios
				cuentaDomiciliosForm.cargaTablaDomicilios(cuentaDto);
				cuentaDomiciliosForm.setHuboCambios(false);
				//actualiza pestaña contactos
				cuentaContactoForm.cargarTablaContactos(cuentaDto);
				CuentaContactoForm.getInstance().setFormDirty(false);
				//actualiza pestaña caratula
//				MGR - Para salir sin "Caratula" (24-07-2012)
//				cuentaCaratulaForm.cargaTablaCaratula(cuentaDto);
//				cuentaCaratulaForm.setHuboCambios(false);
				
				
//				MessageDialog.getInstance().showAceptar("", Sfa.constant().MSG_CUENTA_GUARDADA_OK(), MessageDialog.getCloseCommand());
			}
		});	
	}
	
	private void cancelar() {
		MessageDialog.getInstance().hide();
		History.newItem("");
	}
	
	public void validarCompletitud(boolean showMsg) {
		erroresValidacion.clear();
		erroresValidacion.addAll(cuentaDatosForm.validarCompletitud());
		erroresValidacion.addAll(CuentaDomiciliosForm.getInstance().validarCompletitud());
		if (!erroresValidacion.isEmpty()) {
			validarCompletitudButton.addStyleName(VALIDAR_COMPLETITUD_FAIL_STYLE);
			if(showMsg)
				ErrorDialog.getInstance().show(erroresValidacion,false);
		} else {
			validarCompletitudButton.removeStyleName(VALIDAR_COMPLETITUD_FAIL_STYLE);
		}
	}
	
	public void validarCompletitud() {
		validarCompletitud(true);
	}
	
	public void dejarSoloInfocom() {
		tabPanel.remove(0);
		
	}
	
	public boolean isFormCompletoYguardado() {
		if (!cuentaDatosForm.validarCompletitud().isEmpty()) {
			ErrorDialog.getInstance().show(cuentaDatosForm.validarCompletitud(),false);
			return false;
		}
		if (editorDirty()){ 
			MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().ERR_CREAR_SS_EDITOR_CUENTA_DIRTY(), cancelarCommand);
			return false; 
		}
		return true; 		
	}
	
	private boolean validarCamposTabDatos() {
		erroresValidacion.clear();
		erroresValidacion.addAll(cuentaDatosForm.validarCompletitud());
		erroresValidacion.addAll(cuentaDatosForm.validarCamposTabDatos());
		if (!erroresValidacion.isEmpty()) {
			ErrorDialog.getInstance().show(erroresValidacion,false);
		}
		return erroresValidacion.isEmpty(); 
	}
	
	private boolean editorDirty() {
		return cuentaDatosForm.formularioDatosDirty() 
		    || cuentaDomiciliosForm.formularioDatosDirty() 
		    || cuentaContactoForm.formContactosDirty();
//		    MGR - Para salir sin "Caratula" (24-07-2012)
//		    || cuentaCaratulaForm.formularioCaratulaDirty();
	}
	
	private void agregarContactos(CuentaDto ctaDto) {
		if(ctaDto instanceof GranCuentaDto) {
			((GranCuentaDto)ctaDto).setContactos(CuentaContactoForm.getInstance().getListaContactos());
		} else if(ctaDto instanceof SuscriptorDto) {
			if(((SuscriptorDto)ctaDto).getDivision()!=null) {
				((SuscriptorDto)ctaDto).getDivision().getGranCuenta().setContactos(CuentaContactoForm.getInstance().getListaContactos());
			} else {
				((SuscriptorDto)ctaDto).getGranCuenta().setContactos(CuentaContactoForm.getInstance().getListaContactos());
			}
		} else if(ctaDto instanceof DivisionDto) {
			((DivisionDto)ctaDto).getGranCuenta().setContactos(CuentaContactoForm.getInstance().getListaContactos());
		}
	}
	
	public void pageSolicitudServicio(){
		if(!cuenta2editDto.getResponsablePago()) {
			MessageDialog.getInstance().showAceptar(Sfa.constant().ERR_DIALOG_TITLE(),
					Sfa.constant().ERR_NO_ACCESO_NO_ES_RESP_PAGO().replaceAll("\\{1\\}", cuenta2editDto.getCodigoVantive()), MessageDialog.getCloseCommand());
		} else {
			if (isFormCompletoYguardado()) {
				linksCrearSS.setHistoryToken(cuenta2editDto.getId());
				popupCrearSS.show();
				popupCrearSS.setPopupPosition(crearSSButton.getAbsoluteLeft() - 10, crearSSButton.getAbsoluteTop() - popupCrearSS.getOffsetHeight());
			}
		}
	}

	
	///////////////
	public CuentaDto getCuenta2editDto() {
		return cuenta2editDto;
	}
	public void setCuenta2editDto(CuentaDto ctaDto) {
        this.cuenta2editDto = ctaDto;		
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
	public void setNumeroCtaPot(String numero) {
		this.numeroCtaPot.setText(numero);
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
	public CuentaInfocomForm getCuentaInfocomForm() {
		return cuentaInfocomForm;
	}
	public void setCuentaInfocomForm(CuentaInfocomForm cuentaInfocomForm) {
		this.cuentaInfocomForm = cuentaInfocomForm;
	}	
	public CuentaNotasForm getCuentaNotasForm() {
		return cuentaNotasForm;
	}
	public void setCuentaNotasForm(CuentaNotasForm cuentaNotasForm) {
		this.cuentaNotasForm = cuentaNotasForm;
	}
	
//	MGR - Para salir sin "Caratula" (24-07-2012)
//	public CuentaCaratulaForm getCuentaCaratulaForm() {
//		return cuentaCaratulaForm;
//	}
//	
//	public void setCuentaCaratulaForm(CuentaCaratulaForm cuentaCaratulaForm) {
//		this.cuentaCaratulaForm = cuentaCaratulaForm;
//	}

	public GwtValidator getValidator() {
		return validator;
	}
	public TabPanel getTabPanel() {
		return tabPanel;
	}
}
