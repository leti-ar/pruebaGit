package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import ar.com.nextel.sfa.client.dto.TerminoPagoValidoDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.TipoTelefoniaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	private TextBox imei;
	private ListBox modeloEq;
	private ListBox item;
	private ListBox terminoPago;
	private TextBox sim;
	private TextBox serie;
	private TextBox pin;
	private Button confirmarReserva;
	private Button desreservar;
	private InlineHTML totalLabel;

	private List idsTipoSolicitudBaseItemYPlan;
	private List idsTipoSolicitudBaseItem;
	private List idsTipoSolicitudBaseActivacion;
	private List idsTipoSolicitudBaseCDW;

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

	public static final int SOLO_ITEM = 0;
	public static final int ITEM_PLAN = 1;
	public static final int ACTIVACION = 2;
	public static final int VENTA_CDW = 3;
	private static final long CUENTA_CORRIENTE_VENC_CICLO_ID = 33;
	private static final String v1 = "\\{1\\}";
	private static final String v2 = "\\{2\\}";
	private int tipoEdicion;
	private boolean activacionOnline;
	private Long idTipoSolicitudBaseActivacionOnline;

	public ItemSolicitudUIData(EditarSSUIController controller) {

		this.controller = controller;

		fields = new ArrayList<Widget>();
		fields.add(tipoOrden = new ListBox());
		fields.add(listaPrecio = new ListBox());
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
		fields.add(modeloEq = new ListBox());
		fields.add(item = new ListBox(" "));
		fields.add(terminoPago = new ListBox());
		fields.add(sim = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(15)));
		fields.add(serie = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(10)));
		fields.add(pin = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(8)));
		fields.add(ddn = new CheckBox());
		fields.add(ddi = new CheckBox());
		fields.add(roaming = new CheckBox());
		totalLabel = new InlineHTML(currencyFormat.format(0d));
		confirmarReserva = new Button("Ok");
		desreservar = new Button();
		confirmarReserva.setTitle("Reservar");
		desreservar.setTitle("Liberar reserva");
		verificarImeiWrapper = new HTML();
		verificarSimWrapper = new HTML();

		listaPrecio.setWidth("400px");
		item.setWidth("400px");
		cantidad.setWidth("70px");
		terminoPago.setWidth("400px");
		tipoPlan.setWidth("400px");
		imei.setWidth("370px");
		imei.setMaxLength(15);
		sim.setMaxLength(15);
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
		item.addChangeListener(this);
		plan.addChangeListener(this);
		tipoPlan.addChangeListener(this);
		confirmarReserva.addClickHandler(this);
		desreservar.addClickHandler(this);
		cantidad.addChangeListener(this);
		modeloEq.addChangeListener(this);
		verificarImeiWrapper.addClickHandler(this);
		verificarSimWrapper.addClickHandler(this);
		roaming.addClickHandler(this);
		imei.addChangeListener(this);

		initIdsTipoSolicitudBase();
	}

	private void initIdsTipoSolicitudBase() {
		idsTipoSolicitudBaseItemYPlan = new ArrayList<Long>();
		idsTipoSolicitudBaseItem = new ArrayList<Long>();
		idsTipoSolicitudBaseActivacion = new ArrayList<Long>();
		idsTipoSolicitudBaseCDW = new ArrayList<Long>();

		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(1)); // 1-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(7)); // 7-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(10)); // 10-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(14)); // 14-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_USADOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(11)); // 11-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(15)); // 15-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_USADOS_G4

		idsTipoSolicitudBaseItem.add(Long.valueOf(8)); // 8-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS
		idsTipoSolicitudBaseItem.add(Long.valueOf(16)); // 16-TIPO_SOLICITUD_BASE_VTA_LICENCIAS_BB
		idsTipoSolicitudBaseItem.add(Long.valueOf(12)); // 12-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS_G4

		setIdTipoSolicitudBaseActivacionOnline(Long.valueOf(17)); // 17-TIPO_SOLICITUD_BASE_ACTIVACION_ONLINE
		
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(9)); // 9-TIPO_SOLICITUD_BASE_ACTIVACION
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(13)); // 13-TIPO_SOLICITUD_BASE_ACTIVACION_G4
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(getIdTipoSolicitudBaseActivacionOnline())); // 17-TIPO_SOLICITUD_BASE_ACTIVACION_ONLINE
		
		idsTipoSolicitudBaseCDW.add(Long.valueOf(3)); // 3-TIPO_SOLICITUD_BASE_VENTA_CDW
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
		} else if (sender == roaming) {
			if (roaming.getValue()) {
				ddi.setValue(true);
				ddi.setEnabled(false);
			} else {
				ddi.setEnabled(true);
			}
		}
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
		reservar.setEnabled(enable);
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
				if (result == null) {
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
			if (is != null) {
				TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
				double precio = is.getPrecioLista();
				if (terminoSelected != null && terminoSelected.getAjuste() != null) {
					precio = terminoSelected.getAjuste() * precio;
				}
				precioListaItem.setInnerHTML(currencyFormat.format(precio));
				if (tipoPlan.getSelectedItem() != null) {
					controller.getPlanesPorItemYTipoPlan(is, (TipoPlanDto) tipoPlan.getSelectedItem(),
							getActualizarPlanCallback());
				}
				// if(is.getItem().) // alcanza con isEquipo, isAccesorio?
				ddn.setValue(true);
				if (tipoEdicion == ACTIVACION) {
					if (is.getItem().getSinModelo()) {
						sim.setEnabled(false);
						sim.setReadOnly(true);
						sim.setText("");
					} else {
						sim.setEnabled(true);
						sim.setReadOnly(false);
					}
				}
			} else {
				sim.setEnabled(true);
				sim.setReadOnly(false);
			}

			refreshTotalLabel();
		} else if (sender == tipoPlan) {
			// Cargo los planes correspondientes al tipo de plan seleccionado
			if (tipoPlan.getSelectedItem() != null && item.getSelectedItem() != null) {
				controller.getPlanesPorItemYTipoPlan((ItemSolicitudTasadoDto) item.getSelectedItem(),
						(TipoPlanDto) tipoPlan.getSelectedItem(), getActualizarPlanCallback());
			}
		} else if (sender == plan) {
			// Cargo Modalidades de Cobro posibles
			if (plan.getSelectedItem() != null) {
				PlanDto planDto = (PlanDto) plan.getSelectedItem();
				if (planDto.getTipoTelefonia().equals(TipoTelefoniaDto.TIPO_PREPAGO)) {
					ddi.setValue(Boolean.TRUE);
					//MGR - #1129
					ddi.setEnabled(Boolean.TRUE);
					roaming.setValue(Boolean.FALSE);
					roaming.setEnabled(Boolean.FALSE);
				} else {
					ddi.setValue(Boolean.FALSE);
					//MGR - #1129
					ddi.setEnabled(Boolean.TRUE);
					roaming.setEnabled(Boolean.TRUE);
				}
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
			} else {
				precioListaPlan.setInnerHTML(currencyFormat.format(0d));
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
			}
		} else if (sender == imei) {
			refreshModelos();
		}
		if (isActivacionOnline()) {
			listaPrecio.setVisible(false);
		}
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

	private void setDisableAndCheckedRoaming(boolean checked) {
		ddn.setValue(checked);
		ddi.setValue(checked);
		roaming.setValue(checked);
		ddn.setEnabled(!checked);
		ddi.setEnabled(!checked);
		roaming.setEnabled(!checked);
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

	public TextBox getCantidad() {
		return cantidad;
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
		return ddi;
	}

	public CheckBox getRoaming() {
		return roaming;
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
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION || tipoEdicion == VENTA_CDW) {
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
		ddi.setValue(linea.getDdi());
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
		roaming.setValue(linea.getRoaming());
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
		} else {
			lineaSolicitudServicio.setCantidad(Integer.parseInt(cantidad.getText()));

		}
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION || tipoEdicion == VENTA_CDW) {
			lineaSolicitudServicio.setAlias(alias.getText());
			lineaSolicitudServicio.setLocalidad((LocalidadDto) localidad.getSelectedItem());
			if (tipoEdicion != VENTA_CDW) {
				lineaSolicitudServicio
						.setModalidadCobro((ModalidadCobroDto) modalidadCobro.getSelectedItem());
				lineaSolicitudServicio.setNumeroReserva(getNumeroTelefonicoCompletoFromFields());
				lineaSolicitudServicio.setNumeroReservaArea(reservarHidden.getText());
				lineaSolicitudServicio.setDdi(ddi.getValue());
				lineaSolicitudServicio.setDdn(ddn.getValue());
				lineaSolicitudServicio.setRoaming(roaming.getValue());
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
			// Limpio los servicios adicionales para que los actualice
			if (!(lineaSolicitudServicio.getPlan().getId().equals(idPlanAnterior) && lineaSolicitudServicio
					.getItem().getId().equals(idItemAnterior))) {
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
	
	
}
