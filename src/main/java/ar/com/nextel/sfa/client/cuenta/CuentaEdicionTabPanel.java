package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.enums.PrioridadEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.UILoader;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
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
	private TabPanel tabPanel;
	private FormButtonsBar footerBar;
	
	public Button validarCompletitudButton;
	public static final String VALIDAR_COMPLETITUD_FAIL_STYLE = "validarCompletitudFailButton";
	public static final String ID_CUENTA = "idCuenta";
	public static final int CANT_PESTANIAS_FIJAS = 3;
	private GwtValidator validator = new GwtValidator();
	
	public SimpleLink guardar;
	public SimpleLink crearSSButton;
	public SimpleLink agregarCuentaButton;
	public SimpleLink cancelar;
	public SimpleLink cerrar;
	public SimpleLink crearCuenta;
	
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
	private Hyperlink  crearEquipos;
	private Hyperlink  crearCDW;
	// private Hyperlink  crearMDS;
	private Hyperlink  agregarDivision;
	private Hyperlink  agregarSuscriptor;
	
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
		
		marco.setWidget(1, 0, marcoCliente);
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
		tabPanel.selectTab(0);
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
		
		marcoCliente.getCellFormatter().setWidth(0, 0, "30%");
		marcoCliente.getCellFormatter().setWidth(0, 1, "30%");
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

		FlowPanel linksCrearSS = new FlowPanel();
		linksCrearSS.add(crearEquipos = new Hyperlink("Equipos/Accesorios", "" + UILoader.BUSCAR_CUENTA));
		linksCrearSS.add(crearCDW = new Hyperlink("CDW", "" + UILoader.BUSCAR_CUENTA));
		// linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
		popupCrearSS.setWidget(linksCrearSS);
		
		FlowPanel linksAgregarCuenta = new FlowPanel();
		linksAgregarCuenta.add(agregarDivision = new Hyperlink(Sfa.constant().division(), "" + UILoader.BUSCAR_CUENTA));
		linksAgregarCuenta.add(agregarSuscriptor = new Hyperlink(Sfa.constant().suscriptor(), "" + UILoader.BUSCAR_CUENTA));
		popupAgregarCuenta.setWidget(linksAgregarCuenta);
		
		cancelar = new SimpleLink(Sfa.constant().cancelar(), "#", true);
		cancelar.setStyleName("link");
		
		crearCuenta = new SimpleLink(Sfa.constant().crearCuenta(), "#", true);
		cerrar      = new SimpleLink(Sfa.constant().cerrar(), "#", true);
		
		footerBar.addLink(guardar);
		footerBar.addLink(crearCuenta);
		footerBar.addLink(crearSSButton);
		footerBar.addLink(agregarCuentaButton);
		footerBar.addLink(cancelar);
		footerBar.addLink(cerrar);

		guardar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (!editorDirty()) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().MSG_NO_HAY_DATOS_NUEVOS(), cancelarCommand);
				} else if (validarCamposTabDatos()) {
					guardar();
				} 
			}
		});
		crearSSButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				
				if (editorDirty()) {
					MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), Sfa.constant().ERR_CREAR_SS_EDITOR_CUENTA_DIRTY(), cancelarCommand);
				} else {
					Long idCuenta = cuenta2editDto.getId();
					if (idCuenta != null) {
						String targetHistoryToken = UILoader.AGREGAR_SOLICITUD + "?" + ID_CUENTA + "=" + idCuenta;
						crearEquipos.setTargetHistoryToken(targetHistoryToken + "&" + EditarSSUI.ID_GRUPO_SS
								+ "=" + GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS);
						crearCDW.setTargetHistoryToken(targetHistoryToken + "&" + EditarSSUI.ID_GRUPO_SS
								+ "=" + GrupoSolicitudDto.ID_CDW);
						// crearMDS.setTargetHistoryToken(targetHistoryToken);
						popupCrearSS.show();
						popupCrearSS.setPopupPosition(crearSSButton.getAbsoluteLeft() - 10, crearSSButton
								.getAbsoluteTop() - 50);
					} else {
						MessageDialog.getInstance().showAceptar("Error", Sfa.constant().ERR_NO_CUENTA_SELECTED(),
								MessageDialog.getCloseCommand());
					}
				}
			}
		});
		agregarCuentaButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupAgregarCuenta.show();
				popupAgregarCuenta.setPopupPosition(agregarCuentaButton.getAbsoluteLeft(), agregarCuentaButton.getAbsoluteTop() - 35);
			}
		});
		crearEquipos.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupCrearSS.hide();
			}
		});
		crearCDW.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupCrearSS.hide();
			}
		});
		// crearMDS.addClickListener(new ClickListener() {
		// 	public void onClick(Widget arg0) {
		// 		popupCrearSS.hide();
		// 	}
		// });
		agregarDivision.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				agregarDivision.setTargetHistoryToken(UILoader.EDITAR_CUENTA  + "?cuenta_id=" + cuenta2editDto.getId() + "&div="+"TRUE");
				popupAgregarCuenta.hide();
			}
		});
		agregarSuscriptor.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				agregarSuscriptor.setTargetHistoryToken(UILoader.EDITAR_CUENTA  + "?cuenta_id=" + cuenta2editDto.getId() + "&sus="+"TRUE");
				popupAgregarCuenta.hide();
			}
		});
		crearCuenta.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				crearCuenta.setTargetHistoryToken(UILoader.AGREGAR_CUENTA+"");
				popupAgregarCuenta.hide();
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
		if (!RegularExpressionConstants.isVancuc(cuenta2editDto.getCodigoVantive()))
			tabPanel.add(cuentaInfocomForm, Sfa.constant().infocom());
		//si viene de opp agrega notas
		if (!editorCuenta ) 
			tabPanel.add(cuentaNotasForm, Sfa.constant().notas());

		validarCompletitudButton.setVisible(editorCuenta);
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
		ctaDto.getPersona().setDomicilios(CuentaDomiciliosForm.getInstance().cuentaDto.getPersona().getDomicilios());
		
		CuentaRpcService.Util.getInstance().saveCuenta(ctaDto,new DefaultWaitCallback<CuentaDto>() {
			public void success(CuentaDto cuentaDto) {
				CuentaEdicionTabPanel.getInstance().setCuenta2editDto(cuentaDto);
				cuentaDatosForm.ponerDatosBusquedaEnFormulario(cuentaDto);
				razonSocial.setText(cuentaDto.getPersona().getRazonSocial());
				MessageDialog.getInstance().showAceptar("", "      "+Sfa.constant().MSG_CUENTA_GUARDADA_OK()+"     ", MessageDialog.getCloseCommand());
				cuentaDomiciliosForm.setHuboCambios(false);
				CuentaContactoForm.getInstance().setFormDirty(false);
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
				ErrorDialog.getInstance().show(erroresValidacion);
		} else {
			validarCompletitudButton.removeStyleName(VALIDAR_COMPLETITUD_FAIL_STYLE);
		}
	}
	
	public void validarCompletitud() {
		validarCompletitud(true);
	}
	
	private boolean validarCamposTabDatos() {
		erroresValidacion.clear();
		erroresValidacion.addAll(cuentaDatosForm.validarCompletitud());
		erroresValidacion.addAll(cuentaDatosForm.validarCamposTabDatos());
		if (!erroresValidacion.isEmpty()) {
			ErrorDialog.getInstance().show(erroresValidacion);
		}
		return erroresValidacion.isEmpty(); 
	}
	
	private boolean editorDirty() {
		return cuentaDatosForm.formularioDatosDirty() 
		    || cuentaDomiciliosForm.formularioDatosDirty() 
		    || cuentaContactoForm.formContactosDirty();
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
	public GwtValidator getValidator() {
		return validator;
	}
	public TabPanel getTabPanel() {
		return tabPanel;
	}
	
}
