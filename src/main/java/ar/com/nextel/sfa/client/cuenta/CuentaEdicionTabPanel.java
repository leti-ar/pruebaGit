package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GranCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class CuentaEdicionTabPanel {

	private static CuentaEdicionTabPanel instance = new CuentaEdicionTabPanel();
	private FlexTable marco = new FlexTable();
	DualPanel razonSocialPanel = new DualPanel();
	DualPanel clientePanel = new DualPanel();
	private Label razonSocial = new Label();
	private Label cliente = new Label();
	private GranCuentaDto cuenta2editDto = new GranCuentaDto();
	private CuentaDatosForm      cuentaDatosForm      = CuentaDatosForm.getInstance();
	private CuentaDomiciliosForm cuentaDomiciliosForm = CuentaDomiciliosForm.getInstance();
	private CuentaContactoForm   cuentaContactoForm   = CuentaContactoForm.getInstance();
	private TabPanel tabPanel;
	private FormButtonsBar footerBar;
	
	public Button validarCompletitudButton;
	public static final String VALIDAR_COMPLETITUD_FAIL_STYLE = "validarCompletitudFailButton";
	private GwtValidator validator = new GwtValidator();
	
	private SimpleLink guardar  = new SimpleLink(Sfa.constant().guardar() , "#", true);
	private SimpleLink crearSS  = new SimpleLink(Sfa.constant().crear()+Sfa.constant().whiteSpace()+Sfa.constant().ss(), "#", true);
	private SimpleLink agregar  = new SimpleLink(Sfa.constant().agregar() , "#", true);
	private SimpleLink cancelar = new SimpleLink(Sfa.constant().cancelar(), "#", true);
	List<String> erroresValidacion = new ArrayList<String>();


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
		getGuardar().setStyleName("link");
		getCrearSS().setStyleName("link");
		getAgregar().setStyleName("link");
		getCancelar().setStyleName("link");
		
		footerBar.addLink(getGuardar());
		footerBar.addLink(getCrearSS());
		footerBar.addLink(getAgregar());
		footerBar.addLink(getCancelar());		

		getGuardar().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (!cuentaDatosForm.formularioDatosDirty() && (!cuentaDomiciliosForm.formularioDatosDirty())) {
					ErrorDialog.getInstance().show("NO HAY DATOS NUEVOS PARA GUARDAR");
				} else if (validarCamposTabDatos()) {
					//ErrorDialog.getInstance().show("HAY CAMPOS OBLIGATORIOS SIN COMPLETAR");
					guardar();
				} 
			}
		});
		getCrearSS().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (cuentaDatosForm.formularioDatosDirty()) {
					ErrorDialog.getInstance().show(Sfa.constant().ERR_FORMULARIO_DIRTY());
				} else { 
					crearSS();
				}
			}
		});
		getAgregar().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (cuentaDatosForm.formularioDatosDirty()) {
					ErrorDialog.getInstance().show(Sfa.constant().ERR_FORMULARIO_DIRTY());
				} else {
					agregar(null/*PersonaDto*/);
				}
			}
		});
		getCancelar().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				if (cuentaDatosForm.formularioDatosDirty()) {
					ErrorDialog.getInstance().show(Sfa.constant().ERR_FORMULARIO_DIRTY());
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
		cuentaDatosForm.reset();
		razonSocial.setText("");
		cliente.setText("");
	}
	
	private void guardar() {
		GranCuentaDto ctaDto = cuentaDatosForm.getCuentaDtoFromEditor();
        //agrego domicilios
		ctaDto.getPersona().setDomicilios(CuentaDomiciliosForm.getInstance().cuentaDto.getPersona().getDomicilios());
		//agrego contactos
		ctaDto.setContactos(CuentaContactoForm.getInstance().getListaContactos());
		
		CuentaRpcService.Util.getInstance().saveCuenta(ctaDto, new DefaultWaitCallback() {
			public void success(Object result) {
				CuentaEdicionTabPanel.getInstance().setCuenta2editDto((GranCuentaDto) result);
				cuentaDatosForm.ponerDatosBusquedaEnFormulario((GranCuentaDto) result);
				razonSocial.setText(((GranCuentaDto) result).getPersona().getRazonSocial());
				MessageDialog.getInstance().showAceptar("", "      La cuenta se guardó con exito     ", MessageDialog.getCloseCommand());
				cuentaDomiciliosForm.setHuboCambios(false);
			}
		});
	}
	
	private void crearSS() {
		ErrorDialog.getInstance().show("OK PARA CREAR SS (@TODO)");
	}
	private void agregar(PersonaDto personaDto) {
		ErrorDialog.getInstance().show("OK PARA AGREGAR DIVISIO/SUSCRIPTOR (@TODO)");
	}
	private void cancelar() {
		ErrorDialog.getInstance().show("OK PARA CANCELAR");
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
	
	///////////////
	public GranCuentaDto getCuenta2editDto() {
		return cuenta2editDto;
	}
	public void setCuenta2editDto(GranCuentaDto ctaDto) {
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
	public void setGuardar(SimpleLink guardar) {
		this.guardar = guardar;
	}
	public SimpleLink getGuardar() {
		return guardar;
	}
	public void setCrearSS(SimpleLink crearSS) {
		this.crearSS = crearSS;
	}
	public SimpleLink getCrearSS() {
		return crearSS;
	}
	public void setAgregar(SimpleLink agregar) {
		this.agregar = agregar;
	}
	public SimpleLink getAgregar() {
		return agregar;
	}
	public void setCancelar(SimpleLink cancelar) {
		this.cancelar = cancelar;
	}
	public SimpleLink getCancelar() {
		return cancelar;
	}
	public GwtValidator getValidator() {
		return validator;
	}
	
}
