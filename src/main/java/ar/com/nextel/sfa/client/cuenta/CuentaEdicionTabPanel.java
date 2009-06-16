package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DivisionDto;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
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
	DualPanel razonSocialPanel = new DualPanel();
	DualPanel clientePanel = new DualPanel();
	private Label razonSocial = new Label();
	private Label cliente = new Label();
	private CuentaDto cuenta2editDto = new CuentaDto();
	private CuentaDatosForm      cuentaDatosForm      = CuentaDatosForm.getInstance();
	private CuentaDomiciliosForm cuentaDomiciliosForm = CuentaDomiciliosForm.getInstance();
	private CuentaContactoForm   cuentaContactoForm   = CuentaContactoForm.getInstance();
	private TabPanel tabPanel;
	private FormButtonsBar footerBar;
	
	public Button validarCompletitudButton;
	public static final String VALIDAR_COMPLETITUD_FAIL_STYLE = "validarCompletitudFailButton";
	public static final String ID_CUENTA = "idCuenta";
	private GwtValidator validator = new GwtValidator();
	
	private SimpleLink guardar;
	private SimpleLink crearSSButton;
	private SimpleLink agregarCuentaButton;
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
	private Hyperlink  crearEquipos;
	private Hyperlink  crearCDW;
	private Hyperlink  crearMDS;
	private Hyperlink  agregarDivision;
	private Hyperlink  agregarSuscriptor;
	private SimpleLink cancelar;

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
		marco.setWidget(1, 0, razonSocialPanel);
		marco.setWidget(1, 1, clientePanel);
		marco.setWidget(2, 0, validarCompletitudButton);
		marco.setWidget(3, 0, tabPanel);
		marco.setWidget(4, 0, footerBar);
		marco.getFlexCellFormatter().setColSpan(2, 0, 2);
		marco.getFlexCellFormatter().setColSpan(3, 0, 2);
		marco.getFlexCellFormatter().setColSpan(4, 0, 2);
		
		marco.getFlexCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_LEFT);
		marco.getFlexCellFormatter().setHorizontalAlignment(1, 1, HorizontalPanel.ALIGN_RIGHT);
		
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
		validarCompletitudButton = new Button("Validar Completitud");
		validarCompletitudButton.addStyleName("validarCompletitudButton");
		validarCompletitudButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
			    if (validarCompletitud()) {
					ErrorDialog.getInstance().show("COMPLETITUD OK");
				}
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
	private void initRazonSocialClientePanel() {
		Label razonSocialLabel = new Label(Sfa.constant().razonSocial()+":");
		Label clienteLabel     = new Label(Sfa.constant().cliente()+":");
		razonSocialPanel.setLeft(razonSocialLabel);
		razonSocialPanel.setRight(razonSocial);
		razonSocialPanel.getRight().addStyleName("fontNormalGris");
		razonSocialPanel.setWidth("50%");
		
		clientePanel.setLeft(clienteLabel);
		clientePanel.setRight(cliente);		
		clientePanel.getRight().addStyleName("fontNormalGris");
		clientePanel.setWidth("50%");
	}
	
	/**
	 * 
	 */	
	private void initFooter() {
		footerBar    = new FormButtonsBar();
		guardar  = new SimpleLink(Sfa.constant().guardar() , "#", true);
		guardar.setStyleName("link");

		crearSSButton = new SimpleLink("^Crear SS", "#", true);
		agregarCuentaButton = new SimpleLink("^Agregar", "#", true);

		popupCrearSS = new PopupPanel(true);
		popupAgregarCuenta = new PopupPanel(true);
		popupCrearSS.addStyleName("dropUpStyle");
		popupAgregarCuenta.addStyleName("dropUpStyle");

		FlowPanel linksCrearSS = new FlowPanel();
		linksCrearSS.add(crearEquipos = new Hyperlink("Equipos/Accesorios", "" + UILoader.BUSCAR_CUENTA));
		linksCrearSS.add(crearCDW = new Hyperlink("CDW", "" + UILoader.BUSCAR_CUENTA));
		linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
		popupCrearSS.setWidget(linksCrearSS);
		
		FlowPanel linksAgregarCuenta = new FlowPanel();
		linksAgregarCuenta.add(agregarDivision = new Hyperlink("División", "" + UILoader.BUSCAR_CUENTA));
		linksAgregarCuenta.add(agregarSuscriptor = new Hyperlink("Suscriptor", "" + UILoader.BUSCAR_CUENTA));
		popupAgregarCuenta.setWidget(linksAgregarCuenta);
		
		cancelar = new SimpleLink(Sfa.constant().cancelar(), "#", true);
		cancelar.setStyleName("link");
		
		footerBar.addLink(guardar);
		footerBar.addLink(crearSSButton);
		footerBar.addLink(agregarCuentaButton);
		footerBar.addLink(cancelar);		

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
						crearEquipos.setTargetHistoryToken(targetHistoryToken);
						// crearCDW.setTargetHistoryToken(targetHistoryToken);
						// crearMDS.setTargetHistoryToken(targetHistoryToken);
						popupCrearSS.show();
						popupCrearSS.setPopupPosition(crearSSButton.getAbsoluteLeft() - 10, crearSSButton
								.getAbsoluteTop() - 50);
					} else {
						MessageDialog.getInstance().showAceptar("Error", "Debe seleccionar una Cuenta",
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
		crearMDS.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupCrearSS.hide();
			}
		});
		agregarDivision.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupCrearSS.hide();
			}
		});
		agregarSuscriptor.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				popupCrearSS.hide();
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
	}

	/**
	 * 
	 */
	public void clean() {
		razonSocial.setText("");
		cliente.setText("");
		cuentaDatosForm.reset();
	}
	
	private void guardar() {
		CuentaDto ctaDto = cuentaDatosForm.getCuentaDtoFromEditor();
		if ("GRAN CUENTA".equals(ctaDto.getCategoriaCuenta().getDescripcion())){
			guardaGranCuenta();
		}else if ("SUSCRIPTOR".equals(ctaDto.getCategoriaCuenta())){
			guardaSuscriptor();
		}else if ("DIVISION".equals(ctaDto.getCategoriaCuenta())){
			guardaDivision();
		}
	}

	/**
	 *@author eSalvador
	 * TODO:Terminar en la segunda iteracion del proyecto.  
	 **/
	private void guardaSuscriptor(){
		
	}

	private void guardaGranCuenta(){
		GranCuentaDto ctaDto = (GranCuentaDto)cuentaDatosForm.getCuentaDtoFromEditor();
        //agrego domicilios
		ctaDto.getPersona().setDomicilios(CuentaDomiciliosForm.getInstance().cuentaDto.getPersona().getDomicilios());
		
		CuentaRpcService.Util.getInstance().saveGranCuenta(ctaDto,new DefaultWaitCallback() {
			public void success(Object result) {
				CuentaEdicionTabPanel.getInstance().setCuenta2editDto((CuentaDto)result);
				cuentaDatosForm.ponerDatosBusquedaEnFormulario((CuentaDto) result);
				razonSocial.setText(((CuentaDto) result).getPersona().getRazonSocial());
				MessageDialog.getInstance().showAceptar("", "      La cuenta se guardó con exito     ", MessageDialog.getCloseCommand());
				cuentaDomiciliosForm.setHuboCambios(false);
			}
		});
	}
	
	/**
	 *@author eSalvador
	 * TODO:Terminar en la segunda iteracion del proyecto.  
	 **/
	private void guardaDivision(){
		
	}
	
	private void crearSS() {
		ErrorDialog.getInstance().show("OK PARA CREAR SS (@TODO)");
	}
	private void agregar(PersonaDto personaDto) {
		ErrorDialog.getInstance().show("OK PARA AGREGAR DIVISIO/SUSCRIPTOR (@TODO)");
	}
	private void cancelar() {
		MessageDialog.getInstance().hide();
		History.newItem("");
	}
	
	private boolean validarCompletitud() {
		erroresValidacion.clear();
		erroresValidacion.addAll(cuentaDatosForm.validarCompletitud());
		erroresValidacion.addAll(CuentaDomiciliosForm.getInstance().validarCompletitud());
		if (!erroresValidacion.isEmpty()) {
			validarCompletitudButton.addStyleName(VALIDAR_COMPLETITUD_FAIL_STYLE);
			ErrorDialog.getInstance().show(erroresValidacion);
		} else {
			validarCompletitudButton.removeStyleName(VALIDAR_COMPLETITUD_FAIL_STYLE);
		}
		return erroresValidacion.isEmpty(); 
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
		return cuentaDatosForm.formularioDatosDirty() || cuentaDomiciliosForm.formularioDatosDirty();
	}
	
	///////////////
	public CuentaDto getCuenta2editDto() {
		return cuenta2editDto;
	}
	
	public void setCuenta2editDto(CuentaDto ctaDto) {
		/**Esto va porque si se accede a este metodo desde la Creacion de una cuenta, aun no se tiene una categoria.
		 * MODIFICAR cuando se agregue la creacion de Division y Suscriptor.*/
		if (ctaDto != null){
			String categoriaCuenta = ctaDto.getCategoriaCuenta().getDescripcion();
			if ("SUSCRIPTOR".equals(categoriaCuenta)){
				SuscriptorDto suscriptor = new SuscriptorDto();
				suscriptor.setGranCuenta(ctaDto);
				GranCuentaDto granCuenta = new GranCuentaDto();
				granCuenta.addSuscriptor(suscriptor);
				this.cuenta2editDto = (CuentaDto)granCuenta;
			}else if("GRAN CUENTA".equals(categoriaCuenta)){
				this.cuenta2editDto = ctaDto;
			}else if("DIVISION".equals(categoriaCuenta)){
				((GranCuentaDto)this.cuenta2editDto).addDivision((DivisionDto)ctaDto);
			}
		}
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
	public GwtValidator getValidator() {
		return validator;
	}
	public TabPanel getTabPanel() {
		return tabPanel;
	}
	
}
