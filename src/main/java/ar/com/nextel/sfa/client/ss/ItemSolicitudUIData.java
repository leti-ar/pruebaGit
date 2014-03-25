package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.nextel.sfa.client.StockRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.ItemSolicitudTasadoDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.LocalidadDto;
import ar.com.nextel.sfa.client.dto.ModalidadCobroDto;
import ar.com.nextel.sfa.client.dto.ModeloDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ResultadoReservaNumeroTelefonoDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.SubsidiosDto;
import ar.com.nextel.sfa.client.dto.TerminoPagoValidoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.TipoVendedorDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.FormUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MensajeRegex;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.nextel.sfa.client.widget.VerificationRegexTextBox;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudUIData extends UIData implements ChangeListener, ClickHandler {

	private ListBox listaPrecio;
	private CheckBox fullPrice;
	private TextBox cantidad;
	private ListBox tipoOrden;
	private ListBox tipoPlan;
	private ListBox plan;
	private SimpleLabel precioListaItem;
	private SimpleLabel precioListaPlan;
	private ListBox localidad;
	private ListBox modalidadCobro;
	private TextBox alias;
	private TextBox reservarHidden;
	private TextBox reservar;
	private CheckBox ddn;
	private CheckBox ddi;
	private CheckBox roaming;
    
	// portabilidad
	private CheckBox portabilidad;
	private PortabilidadUIData portabilidadPanel = new PortabilidadUIData();
	private Command cmndAceptar;
	private Command cmndCancelar;
	boolean actuaXmodal = false;


	private TextBox imei;
	private MensajeRegex imeiMensajeRegex;
	private TextBox imeiRetiroEnSucursal;
	private ListBox modeloEq;
	private ListBox item;
	private ListBox terminoPago;
	private TextBox sim;
	private MensajeRegex simMensajeRegex;
	private TextBox simRetiroEnSucursal;
	private TextBox serie;
	private TextBox pin;
	private Button confirmarReserva;
	private Button desreservar;
	private InlineHTML totalLabel;

	private List idsTipoSolicitudBaseItemYPlan;
	private List idsTipoSolicitudBaseItem;
	private List idsTipoSolicitudBaseActivacion;
	private List idsTipoSolicitudBaseCDW;
	private List idsTipoSolicitudBaseVentaSim;
	private List<SubsidiosDto> subsidios = new ArrayList<SubsidiosDto>();

	private LineaSolicitudServicioDto lineaSolicitudServicio;
	private EditarSSUIController controller;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private String nombreMovil;
	private ReservarNumeroDialog reservarNumeroDialog;
	private Command reservarCommnad = null;
	private HTML verificarImeiWrapper;
	private HTML verificarSimWrapper;
	private InlineHTML pinLabel = new InlineHTML(Sfa.constant().nPinReq());
	private InlineHTML serieLabel = new InlineHTML(Sfa.constant().serie());

	private Long idPlanAnterior;
	private Long idItemAnterior;
	private Boolean fullPriceAnterior;

	public static final int SOLO_ITEM = 0;
	public static final int ITEM_PLAN = 1;
	public static final int ACTIVACION = 2;
	public static final int VENTA_CDW = 3;
	public static final int SOLO_SIN = 4;
	public static final int VENTA_SIM = 5;
	private static final long CUENTA_CORRIENTE_VENC_CICLO_ID = 33;
	private static final String v1 = "\\{1\\}";
	private static final String v2 = "\\{2\\}";
	private static final String WARNING = "Advertencia";
	private int tipoEdicion;
	private ItemSolicitudDialog dialog;
	private boolean activacionOnline;
	private boolean permanencia;
	private Long idTipoSolicitudBaseActivacionOnline;
	private Long idsTipoSolicitudBaseItemYPlanPermanencia;
//	MGR - #3462
	private boolean activacion = false;
	private Long idTipoSolicitudBaseActivacion = 0L;
	private ItemSolicitudTasadoDto itemActivacionOnline = null;
	private static final String LUGAR_DE_LLAMADA_DE_VALIDACION_STOCK="agregarItem";
	
	public ItemSolicitudUIData(EditarSSUIController controller) {
		
		// Oculta las opciones de portabilidad
		portabilidadPanel.setVisible(false);
		portabilidadPanel.setSolicitudServicio(controller.getEditarSSUIData().getSolicitudServicio());
		this.controller = controller;

		fields = new ArrayList<Widget>();
		fields.add(tipoOrden = new ListBox());
		fields.add(listaPrecio = new ListBox());
		fields.add(fullPrice = new CheckBox());
		fields.add(cantidad = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(4)));
		fields.add(tipoPlan = new ListBox());
		fields.add(plan = new ListBox(" "));
		fields.add(precioListaItem = new SimpleLabel());
		fields.add(precioListaPlan = new SimpleLabel());
		fields.add(localidad = new ListBox());
		fields.add(modalidadCobro = new ListBox());
		fields.add(alias = new TextBox());
		fields.add(reservarHidden = new TextBox());
		fields.add(reservar = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(4)));
		fields.add(imei = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(15)));
		this.imeiMensajeRegex = new MensajeRegex(RegularExpressionConstants.getCantidadNumerosFijo(15),Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"));
		fields.add(imeiRetiroEnSucursal = new VerificationRegexTextBox(RegularExpressionConstants.getNumerosLimitado(15),this.imeiMensajeRegex));
		fields.add(modeloEq = new ListBox());
		fields.add(item = new ListBox()); //#0006691
		fields.add(terminoPago = new ListBox());
		fields.add(sim = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(15)));
		this.simMensajeRegex = new MensajeRegex(RegularExpressionConstants.getCantidadNumerosFijo(15),Sfa.constant().ERR_LENGHT().replaceAll(v1, "SIM").replaceAll(v2, "15"));
		fields.add(simRetiroEnSucursal = new VerificationRegexTextBox(RegularExpressionConstants.getNumerosLimitado(15),simMensajeRegex));
		fields.add(serie = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(10)));
		fields.add(pin = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(8)));
		fields.add(ddn = new CheckBox());
		fields.add(ddi = new CheckBox());
		fields.add(roaming = new CheckBox());
		fields.add(portabilidad = new CheckBox());

		totalLabel = new InlineHTML(currencyFormat.format(0d));
		confirmarReserva = new Button("Ok");
		desreservar = new Button();
		confirmarReserva.setTitle("Reservar");
		desreservar.setTitle("Liberar reserva");
		verificarImeiWrapper = new HTML();
		verificarSimWrapper = new HTML();

		listaPrecio.setWidth("400px");
		fullPrice.setText("Full Price");
		item.setWidth("400px");
		cantidad.setWidth("70px");
		terminoPago.setWidth("400px");
		tipoPlan.setWidth("400px");
		imei.setWidth("370px");
		imei.setMaxLength(15);
		imeiRetiroEnSucursal.setMaxLength(15);
		sim.setMaxLength(15);
		simRetiroEnSucursal.setMaxLength(15);
		serie.setMaxLength(10);
		pin.setMaxLength(8);
		modeloEq.setWidth("400px");
		plan.setWidth("400px");
		localidad.setWidth("400px");
		alias.setWidth("70px");
		alias.setMaxLength(15);
		reservarHidden.setWidth("70px");
		reservarHidden.setEnabled(false);
		reservarHidden.setReadOnly(true);
		reservarHidden.addStyleName("m0p0");
		reservar.setWidth("50px");
		reservar.addStyleName("m0p0");
		reservar.setMaxLength(4);
		confirmarReserva.addStyleName("botonReservarTel");
		desreservar.addStyleName("botonLiberarTel");
		desreservar.setVisible(false);
		ddn.setText(Sfa.constant().ddn());
		ddi.setText(Sfa.constant().ddi());
		roaming.setText(Sfa.constant().roaming());
		portabilidad.setText(Sfa.constant().portabilidad());
		resetIMEICheck();
		verificarSimWrapper.addStyleName("pl10");

		// Debug Labels
		item.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM);
		cantidad.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD);
		tipoOrden.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN);
		imei.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_IMEI);
		sim.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_SIM);
		pin.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_PIN);
		tipoPlan.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_PLAN);
		plan.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN);
		modeloEq.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_MODELO);

		listaPrecio.addChangeListener(this);
		fullPrice.addClickHandler(this);
		item.addChangeListener(this);
		plan.addChangeListener(this);
		tipoPlan.addChangeListener(this);
		confirmarReserva.addClickHandler(this);
		desreservar.addClickHandler(this);
		cantidad.addChangeListener(this);
		modeloEq.addChangeListener(this);
		verificarImeiWrapper.addClickHandler(this);
		verificarSimWrapper.addClickHandler(this);
		// SB - #0004558
		// roaming.addClickHandler(this);
		imei.addChangeListener(this);
//		imeiRetiroEnSucursal.addBlurHandler(new BlurHandler() {
//			
//			public void onBlur(BlurEvent event) {
//				if (imeiRetiroEnSucursal.getText().length() > 0 && imeiRetiroEnSucursal.getText().length() < 15) {
//					ErrorDialog.getInstance().show(
//							Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"), false);
//				}
//			}
//		});
		
		initIdsTipoSolicitudBase();
		
		// TODO: portabilidad
		cantidad.addValueChangeHandler(valueChangeHandler_string);
		portabilidad.addValueChangeHandler(valueChangeHandler_boolean);

		portabilidadPanel.setCHKportabilidad(portabilidad);
		portabilidadPanel.setReserva(confirmarReserva,reservar);

		cmndAceptar = new Command() {
			public void execute() {
				if(!portabilidad.getValue()){
					// Elimina la portabilidad
					for(ServicioAdicionalLineaSolicitudServicioDto servicioAdicional : lineaSolicitudServicio.getServiciosAdicionales()){
						if(servicioAdicional.getServicioAdicional().getEsPortabilidad() && servicioAdicional.isChecked()) servicioAdicional.setChecked(false);
					}
					portabilidadPanel.setSolicitudPortabilidad(null);
					portabilidadPanel.resetearPortabilidad();
					reservar.setEnabled(true);
					confirmarReserva.setEnabled(true);
					portabilidadPanel.setVisible(false);
					dialog.center();
				}else{
					// Elimina la Reserva
					desreservar();
					reservar.setEnabled(false);
					reservar.setText("");
					confirmarReserva.setEnabled(false);
					portabilidadPanel.setVisible(true);
					portabilidadPanel.loadSolicitudPortabilidad(new SolicitudPortabilidadDto(),true);
					dialog.center();
				}
			
				ModalMessageDialog.getInstance().hide();
			}
		};
		
		cmndCancelar = new Command() {
			public void execute() {
				actuaXmodal = true;
				
				if(!portabilidad.getValue()) portabilidad.setValue(true);
				else portabilidad.setValue(false);
				
				actuaXmodal = false;
				ModalMessageDialog.getInstance().hide();
			}
		};
	}

	// TODO: portabilidad
	// Objeto manejador del evento de cambio de valor tipo string
	private ValueChangeHandler<String> valueChangeHandler_string = new ValueChangeHandler<String>() {
		public void onValueChange(ValueChangeEvent<String> event) {
			refreshTotalLabel();
			enableAliasYReserva(isCantiadIgualNadaOUno());
			
			if(Integer.valueOf(cantidad.getText()).intValue() > 1){
				// No puede haber portabilidad si la cantidad excede a 1
				if(portabilidadPanel.isVisible()){
					reservar.setEnabled(true);
					confirmarReserva.setEnabled(true);
					portabilidadPanel.resetearPortabilidad();
					portabilidadPanel.setVisible(false);
					portabilidad.setValue(false);
					portabilidad.setEnabled(false);
				}else portabilidad.setEnabled(false);
			}else portabilidad.setEnabled(true);
		}
	};

	// TODO: portabilidad
	// Objeto manejador del evento de cambio de valor tipo boolean
	private ValueChangeHandler<Boolean> valueChangeHandler_boolean = new ValueChangeHandler<Boolean>() {
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			if(!actuaXmodal){
				if(!portabilidad.getValue()){
					ModalMessageDialog.getInstance().showAceptarCancelar(
							WARNING,"Se eliminaran los datos correspondientes a Portabilidad",cmndAceptar, cmndCancelar);
				}else{ 
					if(cantidad.getText().length() < 1 || Integer.valueOf(cantidad.getText()) < 1) cantidad.setText("1");

					if(lineaSolicitudServicio.getNumeroReserva() != null && lineaSolicitudServicio.getNumeroReserva().length() > 0 ){
						ModalMessageDialog.getInstance().showAceptarCancelar(
								WARNING,"Se eliminara la reserva de numero, desea continuar",cmndAceptar, cmndCancelar);
					}else{
						reservar.setEnabled(false);
						confirmarReserva.setEnabled(false);
						portabilidadPanel.setVisible(true);
						portabilidadPanel.loadSolicitudPortabilidad(new SolicitudPortabilidadDto(),true);
					}
				}
			}
			dialog.center();
		}
	};

	public void setItemSolicitudDialog(ItemSolicitudDialog dialog){
		this.dialog = dialog;
	}
	
	private void initIdsTipoSolicitudBase() {
		idsTipoSolicitudBaseItemYPlan = new ArrayList<Long>();
		idsTipoSolicitudBaseItem = new ArrayList<Long>();
		idsTipoSolicitudBaseActivacion = new ArrayList<Long>();
		idsTipoSolicitudBaseCDW = new ArrayList<Long>();
		idsTipoSolicitudBaseVentaSim = new ArrayList<Long>();
		setIdsTipoSolicitudBaseItemYPlanPermanencia(Long.valueOf(1)); // 1-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS
		
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(getIdsTipoSolicitudBaseItemYPlanPermanencia())); // 1-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(7)); // 7-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(10)); // 10-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(14)); // 14-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_USADOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(11)); // 11-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(15)); // 15-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_USADOS_G4

		idsTipoSolicitudBaseItem.add(Long.valueOf(8)); // 8-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS
		idsTipoSolicitudBaseItem.add(Long.valueOf(16)); // 16-TIPO_SOLICITUD_BASE_VTA_LICENCIAS_BB
		idsTipoSolicitudBaseItem.add(Long.valueOf(12)); // 12-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS_G4

		setIdTipoSolicitudBaseActivacionOnline(Long.valueOf(17)); // 17-TIPO_SOLICITUD_BASE_ACTIVACION_ONLINE
		
//		MGR - #3462
//		idsTipoSolicitudBaseActivacion.add(Long.valueOf(9)); // 9-TIPO_SOLICITUD_BASE_ACTIVACION
		setIdTipoSolicitudBaseActivacion(Long.valueOf(9)); 
		idsTipoSolicitudBaseActivacion.add(getIdTipoSolicitudBaseActivacion()); // 17-TIPO_SOLICITUD_BASE_ACTIVACION_ONLINE
		
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(13)); // 13-TIPO_SOLICITUD_BASE_ACTIVACION_G4
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(getIdTipoSolicitudBaseActivacionOnline())); // 17-TIPO_SOLICITUD_BASE_ACTIVACION_ONLINE
		
		idsTipoSolicitudBaseCDW.add(Long.valueOf(3)); // 3-TIPO_SOLICITUD_BASE_VENTA_CDW
		
		idsTipoSolicitudBaseVentaSim.add(Long.valueOf(18)); //18-TIPO_SOLICITUD_BASE_VENTA_SIMCARD_SERVICIO
		idsTipoSolicitudBaseVentaSim.add(Long.valueOf(19)); //19-TIPO_SOLICITUD_BASE_Venta_SIMCARD
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == confirmarReserva) {
			reservar();
		} else if (sender == desreservar) {
			desreservar();
		} else if (sender == verificarImeiWrapper) {
			verificarImei();
		} else if (sender == verificarSimWrapper) {
			verificarSim();
		} else if (sender == fullPrice) {
			verificarSubsidio();
		} 
		
		// SB - #0004558
		
//		else if (sender == roaming) {
//			if (roaming.getValue()) {
//				ddi.setValue(true);
//				ddi.setEnabled(false);
//			} else {
//				ddi.setEnabled(true);
//			}
//		}
	}

	public boolean reservar() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(plan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Plan"));
		validator.addTarget(modalidadCobro).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "CPP/MPP"));
		validator.addTarget(localidad).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Localidad"));
		if (tipoEdicion != ACTIVACION) {
			validator.addTarget(cantidad).equals("1", Sfa.constant().ERR_CANT_UNO());
		}
		validator.addTarget(reservar).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Reservar Nº")).length(4,
				Sfa.constant().ERR_NUMERO_RESERVA());

		if (validator.fillResult().getErrors().isEmpty()) {
			reservarServiceCall(Long.parseLong(reservar.getText()));
		} else {
			ErrorDialog.getInstance().show(validator.getErrors(), false);
		}
		return validator.getErrors().isEmpty();
	}

	private void reservarServiceCall(Long numeroTelefonico) {
		PlanDto planDto = (PlanDto) plan.getSelectedItem();
		ModalidadCobroDto modalidadCobroDto = (ModalidadCobroDto) modalidadCobro.getSelectedItem();
		LocalidadDto localidadDto = (LocalidadDto) localidad.getSelectedItem();
		LoadingModalDialog.getInstance().showAndCenter("Reserva", "Reservando número telefónico ...");
		controller.reservarNumeroTelefonico(numeroTelefonico, planDto.getTipoTelefonia().getId(),
				modalidadCobroDto.getId(), localidadDto.getId(),
				new DefaultWaitCallback<ResultadoReservaNumeroTelefonoDto>() {
					public void success(ResultadoReservaNumeroTelefonoDto result) {
						LoadingModalDialog.getInstance().hide();
						if (result.getReservedNumber() == 0) {
							if (reservarNumeroDialog == null) {
								reservarNumeroDialog = new ReservarNumeroDialog();
							}
							reservarNumeroDialog.show(result.getAvailableNumbers(), getReservarCommnad());
						} else {
							setEnableReservaRelatedInputs(false);
							desreservar.setVisible(true);
							confirmarReserva.setVisible(false);
							setFieldsFromNumeroTelefonicoCompleto("" + result.getReservedNumber());
							lineaSolicitudServicio.setNumeroReserva(String.valueOf(result.getReservedNumber()));
							MessageDialog.getInstance().showAceptar("Reserva Exitosa",
									Sfa.constant().MSG_NUMERO_RESERVADO(), MessageDialog.getCloseCommand());
						}
					}

					public void failure(Throwable caught) {
						LoadingModalDialog.getInstance().hide();
						super.failure(caught);
					}
				});

	}

	private Command getReservarCommnad() {
		if (reservarCommnad == null) {
			reservarCommnad = new Command() {
				public void execute() {
					reservarServiceCall(reservarNumeroDialog.getSelectedNumber());
				}
			};
		}
		return reservarCommnad;
	}

	private void desreservar() {
		LoadingModalDialog.getInstance().showAndCenter("Reserva", "Desreservando número telefónico ...");
		controller.desreservarNumeroTelefonico(Long.parseLong(getNumeroTelefonicoCompletoFromFields()),
				lineaSolicitudServicio.getId(), new DefaultWaitCallback() {
					public void success(Object result) {
						LoadingModalDialog.getInstance().hide();
						setEnableReservaRelatedInputs(true);
						desreservar.setVisible(false);
						confirmarReserva.setVisible(true);
						reservarHidden.setText("");
						lineaSolicitudServicio.setNumeroReserva(null);
					}

					public void failure(Throwable caught) {
						LoadingModalDialog.getInstance().hide();
						super.failure(caught);
					}
				});
	}

	private void setEnableReservaRelatedInputs(boolean enable) {
		tipoOrden.setEnabled(enable);
		listaPrecio.setEnabled(enable);
		cantidad.setEnabled(enable);
		cantidad.setReadOnly(!enable);
		item.setEnabled(enable);
		tipoPlan.setEnabled(enable);
		plan.setEnabled(enable);
		localidad.setEnabled(enable);
		modalidadCobro.setEnabled(enable);
		//reservar.setEnabled(enable);
		reservar.setReadOnly(!enable);
	}

	/** Comprueba la validez del IMEI y carga los combos de Modelo e Item */
	private void refreshModelos() {
		if (imei.getText().length() > 0) {
			if (imei.getText().length() != 15) {
				ErrorDialog.getInstance().show(
						Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"), false);
			} else {
				Long idListaPrecio = ((ListaPreciosDto) listaPrecio.getSelectedItem()).getId();
				Long idTipoSolicitud = ((TipoSolicitudDto) tipoOrden.getSelectedItem()).getId();
				controller.getModelos(imei.getText(), idTipoSolicitud, idListaPrecio,
						new DefaultWaitCallback<List<ModeloDto>>() {
							public void success(List<ModeloDto> modelos) {
								modeloEq.clear();
								modeloEq.addAllItems(modelos);
								onChange(modeloEq);
							}
						});
			}
		}
		// LF
//		if (isActivacionOnline()) {
//			listaPrecio.setVisible(false);
//		}
	}

	private void verificarSim() {
		controller.verificarNegativeFiles(sim.getText(), new DefaultWaitCallback<String>() {
			public void success(String result) {
				if (result != null) { //#3265
					ErrorDialog.getInstance().show(result, false);
					verificarSimWrapper.setHTML(IconFactory.comprobarRojo(Sfa.constant().verificarSim())
							.toString());
				} else {
					verificarSimWrapper.setHTML(IconFactory.comprobarVerde(Sfa.constant().verificarSim())
							.toString());
				}
			}
		});
	}

	private void verificarImei() {
		if (imei.getText().length() != 15) {
			ErrorDialog.getInstance().show(
					Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"), false);
		} else {
			controller.verificarNegativeFiles(imei.getText(), new DefaultWaitCallback<String>() {
				public void success(String result) {
					if (result != null) {
						ErrorDialog.getInstance().show(result, false);
						verificarImeiWrapper.setHTML(IconFactory
								.comprobarRojo(Sfa.constant().verificarImei()).toString());
					} else {
						verificarImeiWrapper.setHTML(IconFactory.comprobarVerde(
								Sfa.constant().verificarImei()).toString());
					}
				}
			});
		}
	}
	
	public void onChange(Widget sender) {
		if (sender == listaPrecio) {
			// Cargo Items y Terminos de pago a partir de la Lista de Precios
			if (item.getItemCount() > 0) {
				item.clear();
			}
			if (terminoPago.getItemCount() > 0) {
				terminoPago.clear();
			}
			ListaPreciosDto listaSelected = (ListaPreciosDto) listaPrecio.getSelectedItem();
			item.addAllItems(listaSelected.getItemsListaPrecioVisibles());
			
			//MGR - #1077
			if (!terminoPago.hasPreseleccionados()) {
				TerminoPagoValidoDto terminoPagoValido = getTerminoPagoValidoDefault(listaSelected.getTerminosPagoValido());
				if(terminoPagoValido == null){
					//Selecciono Cuenta Corriente Vencimiento Ciclo si no tiene ninguno por default
					terminoPagoValido = getTerminoPagoValidoByIdTerminoPago(listaSelected
							.getTerminosPagoValido(), CUENTA_CORRIENTE_VENC_CICLO_ID);
				}
				terminoPago.setSelectedItem(terminoPagoValido);
			}
			terminoPago.addAllItems(listaSelected.getTerminosPagoValido());

			if (listaSelected.getItemsListaPrecioVisibles() != null && listaSelected.getItemsListaPrecioVisibles().size() == 1) {
				item.setSelectedIndex(1);
			}
			if (tipoEdicion == ACTIVACION) {
				refreshModelos();
			} else {
				onChange(item);
			}
			
			//MGR - #1039 - Si se cumplen los requisitos, debo ocultar ciertos campos
			Long idGrupoSS = controller.getEditarSSUIData().getGrupoSolicitud().getId();
			Long idTipoOrden = Long.valueOf(tipoOrden.getSelectedItemId());
			Long idLista = Long.valueOf(this.listaPrecio.getSelectedItemId());
			ItemYPlanSolicitudUI itemPlanSolicAux = ItemSolicitudDialog.obtenerItemYPlanSolicitudUI();
			
			if(itemPlanSolicAux != null){
				if(idGrupoSS != null && idTipoOrden != null && idLista != null){
					HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
					
					if(instancias != null && idGrupoSS.equals(instancias.get(GrupoSolicitudDto.DESPACHO_TEL_ANEXO))
							&& idTipoOrden.equals(instancias.get(TipoSolicitudDto.VTA_POR_TEL)) 
							&& idLista.equals(instancias.get(ListaPreciosDto.AR_EQUIP_VTA_SOLO_EQUIP))){
						
						itemPlanSolicAux.ocultarCamposBBRed();
					} else{
						itemPlanSolicAux.mostrarCamposBBRed();
					}
				}else{
					itemPlanSolicAux.mostrarCamposBBRed();
				}
			}
			
		
		} else if (sender == item) {
			// Seteo el precio del item, ajustado por el Termino de Pago y cargo el ListBox de Planes
			ItemSolicitudTasadoDto is = (ItemSolicitudTasadoDto) item.getSelectedItem();
			setDisableAndCheckedRoaming(false);
			precioListaItem.setInnerHTML(currencyFormat.format(0d));
			if (is != null) {
				TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
				double precio = is.getPrecioLista();
				if (terminoSelected != null && terminoSelected.getAjuste() != null) {
					precio = terminoSelected.getAjuste() * precio;
				}
				if (!isPermanencia()){
					precioListaItem.setInnerHTML(currencyFormat.format(precio));
				}
				if (tipoPlan.getSelectedItem() != null) {
//					MGR - #3462 - Es necesario indicar el modelo y si es activacion online
					ModeloDto modelo = (ModeloDto) modeloEq.getSelectedItem();
					boolean isActivacion = this.isActivacion() || this.isActivacionOnline();
					controller.getPlanesPorItemYTipoPlan(is, (TipoPlanDto) tipoPlan.getSelectedItem(),
							isActivacion, modelo, getActualizarPlanCallback());
					if (isPermanencia()){
						if (isActivacionOnline()){
							if (itemActivacionOnline!=null){
								controller.getSubsidiosPorItem(itemActivacionOnline, getActualizarSubsidiosCallback());
							}
						}else{
							controller.getSubsidiosPorItem(is, getActualizarSubsidiosCallback());							
						}	
					}
				}
				// if(is.getItem().) // alcanza con isEquipo, isAccesorio?
				ddn.setValue(true);
				
				if (tipoEdicion == ACTIVACION) {
					if (is.getItem().getSinModelo()) {
						this.disableTextBox(sim);
					} else {
						this.enableTextBox(sim);
					}
				// agregado para entrega por sucursal	
				} else if (controller.getEditarSSUIData().getRetiraEnSucursal().getValue()) {
					if (tipoEdicion == ITEM_PLAN || tipoEdicion == SOLO_ITEM) {
						if (is.getItem().isEquipo()) {
							this.enableTextBox(imeiRetiroEnSucursal);
							this.enableTextBox(simRetiroEnSucursal);
							this.disableTextBox(cantidad,"1");
						} else if (is.getItem().isAccesorio() && is.getItem().getEsSim()) {
							this.disableTextBox(imeiRetiroEnSucursal);
							this.enableTextBox(simRetiroEnSucursal);
							this.disableTextBox(cantidad, "1");
						} else {
							this.disableTextBox(imeiRetiroEnSucursal);
							this.disableTextBox(simRetiroEnSucursal);
							this.enableTextBox(cantidad);
						}
					}
				}
			} else {
				this.enableTextBox(sim);
			}
			if (controller.getEditarSSUIData().getRetiraEnSucursal().getValue() &&
				item.getSelectedItem()!=null) {
				//llamar al metodo de validacion de stock
				validarStock(new Long(item.getSelectedItem().getItemValue()));
			}
			refreshTotalLabel();
		} else if (sender == tipoPlan) {
			// Cargo los planes correspondientes al tipo de plan seleccionado
			if (tipoPlan.getSelectedItem() != null && item.getSelectedItem() != null) {
//				MGR - #3462 - Es necesario indicar el modelo y si es activacion online
				ModeloDto modelo = (ModeloDto) modeloEq.getSelectedItem();
				boolean isActivacion = this.isActivacion() || this.isActivacionOnline();
				controller.getPlanesPorItemYTipoPlan((ItemSolicitudTasadoDto) item.getSelectedItem(),
						(TipoPlanDto) tipoPlan.getSelectedItem(), isActivacion, modelo,
						getActualizarPlanCallback());
			}
		} else if (sender == plan) {
			// Cargo Modalidades de Cobro posibles
			if (plan.getSelectedItem() != null) {
				PlanDto planDto = (PlanDto) plan.getSelectedItem();
				
				//SB - #0004558
				
//				if (planDto.getTipoTelefonia().equals(TipoTelefoniaDto.TIPO_PREPAGO)) {
//					ddi.setValue(Boolean.TRUE);
//					//MGR - #1129
//					ddi.setEnabled(Boolean.TRUE);
//					roaming.setValue(Boolean.FALSE);
//					roaming.setEnabled(Boolean.FALSE);
//				} else {
//					ddi.setValue(Boolean.FALSE);
//					//MGR - #1129
//					ddi.setEnabled(Boolean.TRUE);
//					roaming.setEnabled(Boolean.TRUE);
//				}
				precioListaPlan.setInnerHTML(currencyFormat.format(planDto.getPrecio()));
				if (modalidadCobro.getItemCount() > 0) {
					modalidadCobro.clear();
				}
				modalidadCobro.addAllItems(planDto.getModalidadesCobro());
				for (ModalidadCobroDto modalidad : planDto.getModalidadesCobro()) {
					if (modalidad.getId().longValue() == 1) {
						modalidadCobro.setSelectedItem(modalidad);
						break;
					}
				}
				verificarSubsidio();
				fullPrice.setEnabled(true);
			} else {
				precioListaPlan.setInnerHTML(currencyFormat.format(0d));
				fullPrice.setEnabled(false);
			}
		} else if (sender == cantidad) {
			refreshTotalLabel();
			enableAliasYReserva(isCantiadIgualNadaOUno());
		} else if (sender == modeloEq) {
			// Cargo los items correspondientes al modelo seleccionado
			ModeloDto modelo = (ModeloDto) modeloEq.getSelectedItem();
			if (modelo != null) {
				item.clear();
				item.addAllItems(modelo.getItems());
				//LF - Si el combo Item trae un solo valor se autocompletara el combo.
				if(isActivacionOnline() && modelo.getItems().size() == 1) {
					item.setSelectedItem(modelo.getItems().get(0));
				}
				if (lineaSolicitudServicio.getItem() != null) {
					ItemSolicitudTasadoDto itemTasado = new ItemSolicitudTasadoDto();
					itemTasado.setItem(lineaSolicitudServicio.getItem());
					idItemAnterior = lineaSolicitudServicio.getItem().getId();
					item.setSelectedItem(itemTasado);
				}
				onChange(item);
				// Habilito PIN si es Blackberry o N Serie si es otro
				if (modelo.isEsBlackberry()) {
					setSerieVisibleAndPinInvisible(false);
					setDisableAndCheckedRoaming(true);
				} else {
					setSerieVisibleAndPinInvisible(true);
					setDisableAndCheckedRoaming(false);
				}
				// busco el item relacionado al modelo para activacion online
				if (isActivacionOnline()){
					Long idListaPrecio = ((ListaPreciosDto) listaPrecio.getSelectedItem()).getId();
					controller.getItemPorModelo(modelo.getId(), idListaPrecio,
						new DefaultWaitCallback<ItemSolicitudTasadoDto>() {
							public void success(ItemSolicitudTasadoDto result) {
								itemActivacionOnline = result; 
								onChange(item);
							}
						});
				}
			}
		} else if (sender == imei) {
			refreshModelos();
		}
		if (isActivacionOnline()) {
			listaPrecio.setVisible(false);
		}
	}

	private void verificarSubsidio() {
		ItemSolicitudTasadoDto is = (ItemSolicitudTasadoDto) item.getSelectedItem();

		if (is != null && isPermanencia()) {
			TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
			double precio = is.getPrecioLista();
			// Selecciono el item relacionado al modelo si es activacion online
			if (isActivacionOnline() && itemActivacionOnline!=null){
				precio = itemActivacionOnline.getPrecioLista();
			}
			if (terminoSelected != null && terminoSelected.getAjuste() != null) {
				precio = terminoSelected.getAjuste() * precio;
			}
			if (!fullPrice.getValue()){
				precio = obtenerPrecioSubsidiado(precio);				
			}
			precioListaItem.setInnerHTML(currencyFormat.format(precio));
		}
	}
	
	private double obtenerPrecioSubsidiado(double precio) {
		PlanDto planSelected = (PlanDto) plan.getSelectedItem();
		boolean existeSubsidio = false;

		for (SubsidiosDto subsidio : subsidios) {
			if (subsidio.getIdPlan().equals(planSelected.getId())){
				existeSubsidio = true;
				precio = precio - subsidio.getSubsidio();
				break;
			}
		}
		if (!existeSubsidio){
			MessageDialog.getInstance().showAceptar("No existe configurado el subsidio para el item y plan seleccionado"
					, MessageDialog.getCloseCommand());
			fullPrice.setValue(true);
		}
		return precio;
	}

	private void disableTextBox(TextBox tb,String defecto) {
		tb.setText(defecto);
		tb.setEnabled(false);
		tb.setReadOnly(true);
	}

	private void disableTextBox(TextBox tb) {
		disableTextBox(tb,"");
	}

	private void enableTextBox(TextBox tb) {
		tb.setEnabled(true);
		tb.setReadOnly(false);
	}
	
	private void enableAliasYReserva(boolean enabled) {
		alias.setEnabled(enabled);
		alias.setReadOnly(!enabled);
		reservar.setEnabled(enabled);
		reservar.setReadOnly(!enabled);
	}

	private void setSerieVisibleAndPinInvisible(boolean visible) {
		serie.setVisible(visible);
		serieLabel.setVisible(visible);
		pin.setVisible(!visible);
		pinLabel.setVisible(!visible);
	}

	//SB - #0004558
	private void setDisableAndCheckedRoaming(boolean checked) {
		ddn.setValue(checked);
		// ddi.setValue(checked);
		// roaming.setValue(checked);
		ddn.setEnabled(!checked);
		// ddi.setEnabled(!checked);
		// roaming.setEnabled(!checked);
	}

	public DefaultWaitCallback<List<PlanDto>> getActualizarPlanCallback() {
		return new DefaultWaitCallback<List<PlanDto>>() {
			public void success(List<PlanDto> result) {
				if (plan.getItemCount() > 0) {
					plan.clear();
				}
				plan.addAllItems(result);
				if (result.size() == 1) {
					plan.setSelectedIndex(1);
				}
				onChange(plan);
			}
		};
	}

	public DefaultWaitCallback<List<SubsidiosDto>> getActualizarSubsidiosCallback() {
		
		return new DefaultWaitCallback<List<SubsidiosDto>>() {
			public void success(List<SubsidiosDto> result) {
				if (!subsidios.isEmpty()) {
					subsidios.clear();
				}
				subsidios.addAll(result);
			}
		};
	}

	private String getNumeroTelefonicoCompletoFromFields() {
		return reservarHidden.getText() + reservar.getText();
	}

	private void setFieldsFromNumeroTelefonicoCompleto(String numero) {
		if (numero != null) {
			if (numero.length() > 4) {
				reservarHidden.setText(numero.substring(0, numero.length() - 4));
				reservar.setText(numero.substring(numero.length() - 4, numero.length()));
			} else {
				reservarHidden.setText("");
				reservar.setText(numero);
			}
		}
	}

	public ListBox getListaPrecio() {
		return listaPrecio;
	}

	public CheckBox getFullPrice() {
		return fullPrice;
	}

	public TextBox getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidadNueva) {
		 cantidad.setText(cantidadNueva);
	}

	public ListBox getTipoOrden() {
		return tipoOrden;
	}

	public ListBox getTipoPlan() {
		return tipoPlan;
	}

	public ListBox getPlan() {
		return plan;
	}

	public SimpleLabel getPrecioListaItem() {
		return precioListaItem;
	}

	public SimpleLabel getPrecioListaPlan() {
		return precioListaPlan;
	}

	public ListBox getLocalidad() {
		return localidad;
	}

	public ListBox getModalidadCobro() {
		return modalidadCobro;
	}

	public TextBox getAlias() {
		return alias;
	}

	public TextBox getReservarHidden() {
		return reservarHidden;
	}

	public TextBox getReservar() {
		return reservar;
	}

	public CheckBox getDdn() {
		return ddn;
	}

	public CheckBox getDdi() {
		// SB - 0004558: TKT351510- Servicios de DDI y ROAMING en SFA. 
		ddi.setEnabled(false);
		return ddi;
	}
	
	public CheckBox getRoaming() {
		// SB - 0004558: TKT351510- Servicios de DDI y ROAMING en SFA.
		roaming.setEnabled(false);
		return roaming;
	}
	
	public CheckBox getPortabilidad() {
		return portabilidad;
	}

	public TextBox getImei() {
		return imei;
	}

	public ListBox getModeloEq() {
		return modeloEq;
	}

	public ListBox getItem() {
		return item;
	}

	public ListBox getTerminoPago() {
		return terminoPago;
	}

	public TextBox getSim() {
		return sim;
	}

	public TextBox getSerie() {
		return serie;
	}

	public TextBox getPin() {
		return pin;
	}

	public Button getConfirmarReserva() {
		return confirmarReserva;
	}

	public Button getDesreservar() {
		return desreservar;
	}

	public HTML getVerificarImeiWrapper() {
		return verificarImeiWrapper;
	}

	public HTML getVerificarSimWrapper() {
		return verificarSimWrapper;
	}

	public void setTipoEdicion(int tipoEdicion) {
		this.tipoEdicion = tipoEdicion;
	}

	public List<String> validate() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(listaPrecio).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Lista de Precios"));
		validator.addTarget(item).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Item"));
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION || tipoEdicion == VENTA_CDW || tipoEdicion == VENTA_SIM) {
			validator.addTarget(tipoPlan).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Tipo Plan"));
			validator.addTarget(plan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Plan"));
			validator.addTarget(localidad).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Localidad"));
			if (!"".equals(cantidad.getText().trim()) && Integer.parseInt(cantidad.getText()) == 1) {
				validator.addTarget(alias).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Alias"));
			}
			if (tipoEdicion != VENTA_CDW) {
				validator.addTarget(modalidadCobro).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "CPP/MPP"));
				if (!"".equals(reservar.getText().trim())) {
					validator.addTarget(reservar).length(4, Sfa.constant().ERR_NUMERO_RESERVA());
				}
			}
		}
		if (tipoEdicion != ACTIVACION) {
			validator.addTarget(cantidad).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Cantidad")).greater(
					Sfa.constant().ERR_CANT_MA_CERO().replaceAll(v1, "Cantidad"), 0);
		} else {
			ModeloDto modelo = (ModeloDto) modeloEq.getSelectedItem();
			if (modelo != null && !isActivacionOnline()) {
				if (modelo.isEsBlackberry()) {
					validator.addTarget(pin).required(
							Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "PIN")).length(8,
							Sfa.constant().ERR_LENGHT().replaceAll(v1, "PIN").replaceAll(v2, "8"));
				} else {
					validator.addTarget(serie).required(
							Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Nº Serie")).length(10,
							Sfa.constant().ERR_LENGHT().replaceAll(v1, "Nº Serie").replaceAll(v2, "10"));
				}
			}
			validator.addTarget(imei).required(
					Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"));
			if (sim.isEnabled()) {
				validator.addTarget(sim).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "SIM"));
			}
		}
		
		if (controller.getEditarSSUIData().getRetiraEnSucursal().getValue()
				&& tipoEdicion != VENTA_SIM) {
			if (imeiRetiroEnSucursal.isEnabled()) {
				validator.addTarget(imeiRetiroEnSucursal).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "IMEI")).regEx(
						imeiMensajeRegex.getMensaje(),imeiMensajeRegex.getRegexPattern());
			}
			if (simRetiroEnSucursal.isEnabled()) {
				validator.addTarget(simRetiroEnSucursal).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "SIM")).regEx(
						simMensajeRegex.getMensaje(), simMensajeRegex.getRegexPattern());
			}
		}
		
//		MGR**********
		if(tipoEdicion == VENTA_SIM){
			validator.addTarget(terminoPago).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Termino de Pago"));
			
			TipoVendedorDto tipoVend = ClientContext.getInstance().getVendedor().getTipoVendedor();
			if(tipoVend.isIngresaSIM()) //#6702
				validator.addTarget(sim).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "SIM")).length(15,Sfa.constant().ERR_LENGHT().replaceAll(v1, "SIM").replaceAll(v2, "15"));;
		}
		
		return validator.fillResult().getErrors();
	}

	public void load(List<ListaPreciosDto> listasPrecios) {
		if (listaPrecio.getItemCount() > 0) {
			listaPrecio.clear();
		}
		listaPrecio.addAllItems(listasPrecios);
		onChange(listaPrecio);
	}

	public List getIdsTipoSolicitudBaseItemYPlan() {
		return idsTipoSolicitudBaseItemYPlan;
	}

	public List getIdsTipoSolicitudBaseItem() {
		return idsTipoSolicitudBaseItem;
	}

	public List getIdsTipoSolicitudBaseActivacion() {
		return idsTipoSolicitudBaseActivacion;
	}

	public List getIdsTipoSolicitudBaseCDW() {
		return idsTipoSolicitudBaseCDW;
	}

	public void setLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		this.lineaSolicitudServicio = linea;
		serie.setEnabled(true);
		serie.setReadOnly(false);
		pin.setEnabled(true);
		pin.setReadOnly(false);
		clearListBoxForSelect();
		clean();
		precioListaItem.setInnerHTML(currencyFormat.format(0d));
		precioListaPlan.setInnerHTML(currencyFormat.format(0d));
		if (linea.getAlias() == null || "".equals(linea.getAlias().trim())) {
			alias.setText(nombreMovil);
		} else {
			alias.setText(linea.getAlias());
		}
		cantidad.setText(linea.getCantidad() != null ? "" + linea.getCantidad() : "");
		enableAliasYReserva(isCantiadIgualNadaOUno());
		ddn.setValue(linea.getDdn());
		// SB - #0004558
		// ddi.setValue(linea.getDdi());
		if (linea.getLocalidad() != null) {
			localidad.setSelectedItem(linea.getLocalidad());
		} else {
			localidad.setSelectedItem(ClientContext.getInstance().getVendedor().getLocalidad());
		}
		setFieldsFromNumeroTelefonicoCompleto(linea.getNumeroReserva());
		boolean tieneNReserva = !sinReservaAlAbrir();
		setEnableReservaRelatedInputs(!tieneNReserva);
		desreservar.setVisible(tieneNReserva);
		confirmarReserva.setVisible(!tieneNReserva);
		serie.setText(linea.getNumeroSerie());
		sim.setText(linea.getNumeroSimcard());
		// SB - #0004558
		// roaming.setValue(linea.getRoaming());
		fullPrice.setEnabled(false);
		fullPrice.setValue(linea.getFullPrice());
		if (linea.getPlan() != null) {
			tipoPlan.setSelectedItem(linea.getPlan().getTipoPlan());
		} else {
			tipoPlan.setSelectedItem(new TipoPlanDto(Long.valueOf(8), "Plan Directo"));
		}

		// Los siguientes combos se seleccionan al cagar las opciones en los combos (ver preselecionados en
		// ListBox)
		listaPrecio.setSelectedItem(linea.getListaPrecios());
		idItemAnterior = null;
		if (linea.getItem() != null) {
			ItemSolicitudTasadoDto itemTasado = new ItemSolicitudTasadoDto();
			itemTasado.setItem(linea.getItem());
			item.setSelectedItem(itemTasado);
			idItemAnterior = linea.getItem().getId();
		}
		plan.setSelectedItem(linea.getPlan());
		idPlanAnterior = linea.getPlan() != null ? linea.getPlan().getId() : null;
		fullPriceAnterior = linea.getFullPrice() != null ? linea.getFullPrice() : null;
		
		modalidadCobro.setSelectedItem(linea.getModalidadCobro());
		if (linea.getTerminoPago() != null) {
			terminoPago.setSelectedItem(getTerminoPagoValidoByIdTerminoPago(linea.getListaPrecios()
					.getTerminosPagoValido(), linea.getTerminoPago().getId().longValue()));
		}
		if (tipoEdicion == ACTIVACION) {
			imei.setText(linea.getNumeroIMEI());
			modeloEq.setSelectedItem(linea.getModelo());
			if (linea.getModelo() != null && linea.getModelo().isEsBlackberry()) {
				pin.setText(linea.getNumeroSerie());
			} else {
				serie.setText(linea.getNumeroSerie());
			}
			sim.setText(linea.getNumeroSimcard());
		}
		if (imei.getText()!=null) {
			imei.setText(linea.getNumeroIMEI());
			modeloEq.setSelectedItem(linea.getModelo());
			if (linea.getModelo() != null && linea.getModelo().isEsBlackberry()) {
				pin.setText(linea.getNumeroSerie());
			} else {
				serie.setText(linea.getNumeroSerie());
			}
			sim.setText(linea.getNumeroSimcard());
		}
		//Desc de Despacho
		if (imeiRetiroEnSucursal.getText()!=null) {
			imeiRetiroEnSucursal.setText(linea.getNumeroIMEI());
		}
		if (simRetiroEnSucursal.getText()!=null) {
			simRetiroEnSucursal.setText(linea.getNumeroSimcard());
		}
		//Fin de Desc de Despacho
		
		// TODO: Portabilidad
		portabilidadPanel.resetearPortabilidad();
		if(linea.getPortabilidad() != null) portabilidadPanel.loadSolicitudPortabilidad(linea.getPortabilidad(),false);

	}

	/** Limpia las selecciones de los combos */
	private void clearListBoxForSelect() {
		listaPrecio.clear();
		listaPrecio.clearPreseleccionados();
		item.clear();
		item.clearPreseleccionados();
		plan.clear();
		plan.clearPreseleccionados();
		modalidadCobro.clear();
		modalidadCobro.clearPreseleccionados();
		modeloEq.clear();
		modeloEq.clearPreseleccionados();
		terminoPago.clear();
		terminoPago.clearPreseleccionados();
	}

	public LineaSolicitudServicioDto getLineaSolicitudServicio() {
		lineaSolicitudServicio.setAlias("");
		ItemSolicitudTasadoDto itemTasadoSelected = (ItemSolicitudTasadoDto) item.getSelectedItem();
		lineaSolicitudServicio.setItem(itemTasadoSelected.getItem());
		lineaSolicitudServicio.setPrecioLista(itemTasadoSelected.getPrecioLista());
		lineaSolicitudServicio.setPrecioVenta(itemTasadoSelected.getPrecioLista());
		lineaSolicitudServicio.setListaPrecios((ListaPreciosDto) listaPrecio.getSelectedItem());
		lineaSolicitudServicio.setFullPrice(true);
		TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
		if(terminoSelected != null){
			lineaSolicitudServicio.setTerminoPago(terminoSelected.getTerminoPago());
		}else{
			lineaSolicitudServicio.setTerminoPago(null);
		}
		if (tipoEdicion == ACTIVACION) {
			lineaSolicitudServicio.setModelo((ModeloDto) modeloEq.getSelectedItem());
			lineaSolicitudServicio.setNumeroIMEI(imei.getText());
			if (lineaSolicitudServicio.getModelo() != null
					&& lineaSolicitudServicio.getModelo().isEsBlackberry()) {
				lineaSolicitudServicio.setNumeroSerie(pin.getText());
			} else {
				lineaSolicitudServicio.setNumeroSerie(serie.getText());
			}
			lineaSolicitudServicio.setNumeroSimcard(sim.getText());
			lineaSolicitudServicio.setCantidad(1);
		} 
		else if(tipoEdicion == VENTA_SIM){
			TipoVendedorDto tipoVend = ClientContext.getInstance().getVendedor().getTipoVendedor();
			if(tipoVend.isIngresaSIM()){
				lineaSolicitudServicio.setNumeroSimcard(sim.getText());
				lineaSolicitudServicio.setCantidad(1);
			}else{
				lineaSolicitudServicio.setNumeroSimcard(null);
				lineaSolicitudServicio.setCantidad(Integer.parseInt(cantidad.getText()));
			}
		}else {
			lineaSolicitudServicio.setCantidad(Integer.parseInt(cantidad.getText()));
		}
		
		
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION 
				|| tipoEdicion == VENTA_CDW || tipoEdicion == VENTA_SIM) {
			lineaSolicitudServicio.setAlias(alias.getText());
			lineaSolicitudServicio.setLocalidad((LocalidadDto) localidad.getSelectedItem());
			if (tipoEdicion != VENTA_CDW) {
				lineaSolicitudServicio
						.setModalidadCobro((ModalidadCobroDto) modalidadCobro.getSelectedItem());
				lineaSolicitudServicio.setNumeroReserva(getNumeroTelefonicoCompletoFromFields());
				lineaSolicitudServicio.setNumeroReservaArea(reservarHidden.getText());
				// SB - #0004558
				// lineaSolicitudServicio.setDdi(ddi.getValue());
				lineaSolicitudServicio.setDdn(ddn.getValue());
				// SB - #0004558
				// lineaSolicitudServicio.setRoaming(roaming.getValue());
			}
			PlanDto planSelected = (PlanDto) plan.getSelectedItem();

			if (planSelected != null) {
				if (lineaSolicitudServicio.getPlan() == null
						|| !planSelected.getId().equals(lineaSolicitudServicio.getPlan().getId())) {
					lineaSolicitudServicio.setPlan(planSelected);
					lineaSolicitudServicio.setPrecioListaPlan(planSelected.getPrecio());
					lineaSolicitudServicio.setPrecioVentaPlan(planSelected.getPrecio());
				}
			} else {
				lineaSolicitudServicio.setPlan(planSelected);
				lineaSolicitudServicio.setPrecioListaPlan(0d);
				lineaSolicitudServicio.setPrecioVentaPlan(0d);
			}
			double precio = itemTasadoSelected.getPrecioLista();
			if (terminoSelected != null && terminoSelected.getAjuste() != null) {
				precio = terminoSelected.getAjuste() * precio;
			}

			lineaSolicitudServicio.setPrecioListaAjustado(precio);
			
			// Campo subsidio
			if (isPermanencia()){
				lineaSolicitudServicio.setFullPrice(fullPrice.getValue());
				if (!fullPrice.getValue() && !isActivacionOnline()){
					double precioSubsidiado = obtenerPrecioSubsidiado(precio);
					if (precio==precioSubsidiado){ // #5864 si el subsidio es 0, se comporta como si fuera full price
						lineaSolicitudServicio.setFullPrice(true);
					}
					lineaSolicitudServicio.setPrecioLista(precioSubsidiado);
					lineaSolicitudServicio.setPrecioVenta(precioSubsidiado);
				}
			}

			// Limpio los servicios adicionales para que los actualice
			if (!(lineaSolicitudServicio.getPlan().getId().equals(idPlanAnterior) && lineaSolicitudServicio
					.getItem().getId().equals(idItemAnterior) && lineaSolicitudServicio.getFullPrice().equals(fullPriceAnterior))) {
				lineaSolicitudServicio.getServiciosAdicionales().clear();
			}
		} else {
			lineaSolicitudServicio.setPrecioListaAjustado(itemTasadoSelected.getPrecioLista());
		}
		
		//MGR - #1182
		if (tipoEdicion == SOLO_ITEM){
			lineaSolicitudServicio.setLocalidad(null);
			lineaSolicitudServicio.setModalidadCobro(null);
			lineaSolicitudServicio.setPlan(null);
			lineaSolicitudServicio.setPrecioListaPlan(0d);
			lineaSolicitudServicio.setPrecioVentaPlan(0d);
			lineaSolicitudServicio.setDdi(false);
			lineaSolicitudServicio.setDdn(false);
			lineaSolicitudServicio.setRoaming(false);
			lineaSolicitudServicio.getServiciosAdicionales().clear();
		}
		
		lineaSolicitudServicio.setTipoSolicitud((TipoSolicitudDto) tipoOrden.getSelectedItem());
		
		controller.borrarDescuentoSeleccionados();
		//si tenía descuentos aplicados, se los eliminio
		if (lineaSolicitudServicio.getDescuentosLinea().size() > 0) {
			lineaSolicitudServicio.getDescuentosLinea().clear();
			MessageDialog.getInstance().setDialogTitle("Advertencia");
			MessageDialog.getInstance().showAceptar("Se han eliminado los descuentos aplicados, si lo desea, puede cargarlos nuevamente", 
					MessageDialog.getCloseCommand());
		}

		// TODO:Portabilidad
		lineaSolicitudServicio.setPortabilidad(portabilidadPanel.getSolicitudPortabilidad(lineaSolicitudServicio));
		portabilidadPanel.setSolicitudPortabilidad(null);
		 
		//Desc de Despacho, esto se creo para no tocar el compartamiento
		//de imei y sim que ya existia en la activacion 
		if(!FormUtils.fieldEmpty(imeiRetiroEnSucursal)){
			lineaSolicitudServicio.setNumeroIMEI(imeiRetiroEnSucursal.getText());
		}
		if(!FormUtils.fieldEmpty(simRetiroEnSucursal)){
			lineaSolicitudServicio.setNumeroSimcard(simRetiroEnSucursal.getText());
			
		}
//		controller.tieneLineasSolicitud();
		//Fin de desc de despacho
		return lineaSolicitudServicio;
		
	}

	public void setNombreMovil(String nombreMovil) {
		this.nombreMovil = nombreMovil;
	}

	private void refreshTotalLabel() {
		if (!"".equals(cantidad.getText().trim()) && item.getSelectedItem() != null) {
			ItemSolicitudTasadoDto itemSelected = (ItemSolicitudTasadoDto) item.getSelectedItem();
			double precio = itemSelected.getPrecioLista() * Double.parseDouble(cantidad.getText());
			totalLabel.setText(currencyFormat.format(precio));
		} else {
			totalLabel.setText(currencyFormat.format(0d));
		}
	}

	public Widget getTotalLabel() {
		return totalLabel;
	}

	public boolean hasNumeroSinReservar() {
		return !"".equals(reservar.getText().trim()) && confirmarReserva.isVisible();
	}

	private boolean isCantiadIgualNadaOUno() {
		return "".equals(cantidad.getText().trim()) || Integer.parseInt(cantidad.getText()) == 1;
	}

	public InlineHTML getPinLabel() {
		return pinLabel;
	}

	public InlineHTML getSerieLabel() {
		return serieLabel;
	}

	public void resetIMEICheck() {
		verificarImeiWrapper.setHTML(IconFactory.comprobarNegro(Sfa.constant().verificarImei()).toString());
		verificarSimWrapper.setHTML(IconFactory.comprobarNegro(Sfa.constant().verificarSim()).toString());
	}

	public void desreservarSiNoFueGrabado() {
		// Si no fue guardado nunca no tiene tipo
		if (sinReservaAlAbrir()) {
			String numeroReservado = getNumeroTelefonicoCompletoFromFields();
			boolean tieneNReserva = numeroReservado != null && numeroReservado.length() > 4;
			if (tieneNReserva) {
				controller.desreservarNumeroTelefonico(Long.parseLong(numeroReservado),
						lineaSolicitudServicio.getId(), new DefaultWaitCallback() {
							public void success(Object result) {
							}
						});
			}
		}
	}

	private boolean sinReservaAlAbrir() {
		return lineaSolicitudServicio.getNumeroReservaArea() == null
				|| "".equals(lineaSolicitudServicio.getNumeroReservaArea().trim());
	}

	private TerminoPagoValidoDto getTerminoPagoValidoByIdTerminoPago(
			List<TerminoPagoValidoDto> terminosPagoValido, long id) {
		int i = 0;
		int index = -1;
		for (TerminoPagoValidoDto terminoPagoValido : terminosPagoValido) {
			if (terminoPagoValido.getTerminoPago().getId().longValue() == id) {
				index = i;
				break;
			}
			i++;
		}
		return index >= 0 ? terminosPagoValido.get(index) : null;
	}
	
	//MGR - #1077
	private TerminoPagoValidoDto getTerminoPagoValidoDefault(
			List<TerminoPagoValidoDto> terminosPagoValido) {
		for (TerminoPagoValidoDto terminoPagoValido : terminosPagoValido) {
			if (terminoPagoValido.getTerminoPagoDefault()) {
				return terminoPagoValido;
			}
		}
		return null;
	}

	/**
	 * Portabilidad
	 * TODO: portabilidad
	 */
	public PortabilidadUIData getPortabilidadPanel() {
		return portabilidadPanel;
	}
	
	public boolean isActivacionOnline() {
		return activacionOnline;
	}

	public void setActivacionOnline(boolean activacionOnline) {
		this.activacionOnline = activacionOnline;
	}
	
	public Long getIdTipoSolicitudBaseActivacionOnline() {
		return idTipoSolicitudBaseActivacionOnline;
	}

	public void setIdTipoSolicitudBaseActivacionOnline(
			Long idTipoSolicitudBaseActivacionOnline) {
		this.idTipoSolicitudBaseActivacionOnline = idTipoSolicitudBaseActivacionOnline;
	}
	
	public Long getIdsTipoSolicitudBaseItemYPlanPermanencia() {
		return idsTipoSolicitudBaseItemYPlanPermanencia;
	}

	public void setIdsTipoSolicitudBaseItemYPlanPermanencia(
			Long idsTipoSolicitudBaseItemYPlanPermanencia) {
		this.idsTipoSolicitudBaseItemYPlanPermanencia = idsTipoSolicitudBaseItemYPlanPermanencia;
	}
	
//	MGR - #3462
	public boolean isActivacion() {
		return activacion;
	}

	public void setActivacion(boolean activacion) {
		this.activacion = activacion;
	}

	public Long getIdTipoSolicitudBaseActivacion() {
		return idTipoSolicitudBaseActivacion;
	}

	public void setIdTipoSolicitudBaseActivacion(Long idTipoSolicitudBaseActivacion) {
		this.idTipoSolicitudBaseActivacion = idTipoSolicitudBaseActivacion;
	}

	public void setPermanencia(boolean permanencia) {
		this.permanencia = permanencia;
	}

	public boolean isPermanencia() {
		return permanencia;
	}

	public TextBox getImeiRetiroEnSucursal() {
		return imeiRetiroEnSucursal;
	}

	public TextBox getSimRetiroEnSucursal() {
		return simRetiroEnSucursal;
	}
	
	public void validarStock(Long idItem){
		final VendedorDto vendedorDto = ClientContext.getInstance().getVendedor();
		StockRpcService.Util.getInstance().validarStockDesdeSS(idItem, vendedorDto.getId(),
				new DefaultWaitCallback<String>() {

					@Override
					public void success(String result) {
					    if (!result.equals("")){
						MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
								result, MessageDialog.getCloseCommand());}
						
					}
				});
	}

	public void visualizarCheckBoxPermanencia() {
		if (isPermanencia() && !ClientContext.getInstance().checkPermiso(PermisosEnum.VER_PERMANENCIA.getValue())){
			fullPrice.setVisible(false);
			fullPrice.setValue(false);
		}
	}

	public List getIDsTipoSolicitudBaseVentaSim() {
		return idsTipoSolicitudBaseVentaSim;
	}
	
}
