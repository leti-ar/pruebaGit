package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
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
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudUIData extends UIData implements ChangeListener, ClickListener {

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

	private List idsTipoSolicitudBaseItemYPlan;
	private List idsTipoSolicitudBaseItem;
	private List idsTipoSolicitudBaseActivacion;

	private LineaSolicitudServicioDto lineaSolicitudServicio;
	private EditarSSUIController controller;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private String nombreMovil;
	private ReservarNumeroDialog reservarNumeroDialog;
	private Command reservarCommnad = null;

	private Long idPlanAnterior;
	private Long idItemAnterior;

	public static final int SOLO_ITEM = 0;
	public static final int ITEM_PLAN = 1;
	public static final int ACTIVACION = 2;
	private static final String v1 = "\\{1\\}";
	private int tipoEdicion;

	public ItemSolicitudUIData(EditarSSUIController controller) {

		this.controller = controller;

		fields = new ArrayList<Widget>();
		fields.add(tipoOrden = new ListBox());
		fields.add(listaPrecio = new ListBox());
		fields.add(cantidad = new RegexTextBox(RegularExpressionConstants.numeros));
		fields.add(tipoPlan = new ListBox());
		fields.add(plan = new ListBox());
		fields.add(precioListaItem = new SimpleLabel());
		fields.add(precioListaPlan = new SimpleLabel());
		fields.add(localidad = new ListBox());
		fields.add(modalidadCobro = new ListBox());
		fields.add(alias = new TextBox());
		fields.add(reservarHidden = new TextBox());
		fields.add(reservar = new TextBox());
		fields.add(imei = new TextBox());
		fields.add(modeloEq = new ListBox());
		fields.add(item = new ListBox());
		fields.add(terminoPago = new ListBox());
		fields.add(sim = new TextBox());
		fields.add(serie = new TextBox());
		fields.add(pin = new TextBox());
		fields.add(ddn = new CheckBox());
		fields.add(ddi = new CheckBox());
		fields.add(roaming = new CheckBox());
		confirmarReserva = new Button("Ok");
		desreservar = new Button("Des");

		listaPrecio.setWidth("400px");
		item.setWidth("400px");
		cantidad.setWidth("70px");
		terminoPago.setWidth("400px");
		tipoPlan.setWidth("400px");
		modeloEq.setWidth("400px");
		plan.setWidth("400px");
		localidad.setWidth("400px");
		alias.setWidth("70px");
		reservarHidden.setWidth("70px");
		reservarHidden.setEnabled(false);
		reservarHidden.addStyleName("m0p0");
		reservar.setWidth("50px");
		reservar.addStyleName("m0p0");
		confirmarReserva.addStyleName("btn-bkg");
		confirmarReserva.addStyleName("ml10");
		desreservar.addStyleName("btn-bkg");
		desreservar.addStyleName("ml10");
		desreservar.setVisible(false);
		ddn.setText(Sfa.constant().ddn());
		ddi.setText(Sfa.constant().ddi());
		roaming.setText(Sfa.constant().roaming());

		listaPrecio.addChangeListener(this);
		item.addChangeListener(this);
		tipoPlan.addChangeListener(this);
		confirmarReserva.addClickListener(this);
		desreservar.addClickListener(this);

		initIdsTipoSolicitudBase();
	}

	private void initIdsTipoSolicitudBase() {
		idsTipoSolicitudBaseItemYPlan = new ArrayList<Long>();
		idsTipoSolicitudBaseItem = new ArrayList<Long>();
		idsTipoSolicitudBaseActivacion = new ArrayList<Long>();

		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(1)); // 1-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(7)); // 7-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(10)); // 10-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(14)); // 14-TIPO_SOLICITUD_BASE_VENTA_EQUIPOS_USADOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(11)); // 11-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_NUEVOS_G4
		idsTipoSolicitudBaseItemYPlan.add(Long.valueOf(15)); // 15-TIPO_SOLICITUD_BASE_ALQUILER_EQUIPOS_USADOS_G4

		idsTipoSolicitudBaseItem.add(Long.valueOf(8)); // 8-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS
		idsTipoSolicitudBaseItem.add(Long.valueOf(16)); // 16-TIPO_SOLICITUD_BASE_VTA_LICENCIAS_BB
		idsTipoSolicitudBaseItem.add(Long.valueOf(12)); // 12-TIPO_SOLICITUD_BASE_VENTA_ACCESORIOS_G4

		idsTipoSolicitudBaseActivacion.add(Long.valueOf(9)); // 9-TIPO_SOLICITUD_BASE_ACTIVACION
		idsTipoSolicitudBaseActivacion.add(Long.valueOf(13)); // 13-TIPO_SOLICITUD_BASE_ACTIVACION_G4
	}

	public void onClick(Widget sender) {
		if (sender == confirmarReserva) {
			reservar();
		} else if (sender == desreservar) {
			desreservar();
		}
	}

	private void reservar() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(plan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Plan"));
		validator.addTarget(modalidadCobro).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "CPP/MPP"));
		validator.addTarget(localidad).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Localidad"));
		validator.addTarget(cantidad).equals("1", Sfa.constant().ERR_CANT_UNO());
		validator.addTarget(reservar).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Reservar Nº"));

		if (validator.fillResult().getErrors().isEmpty()) {
			reservarServiceCall(Long.parseLong(reservar.getText()));
		} else {
			ErrorDialog.getInstance().show(validator.getErrors(), false);
		}
	}

	private void reservarServiceCall(Long numeroTelefonico) {
		PlanDto planDto = (PlanDto) plan.getSelectedItem();
		ModalidadCobroDto modalidadCobroDto = (ModalidadCobroDto) modalidadCobro.getSelectedItem();
		LocalidadDto localidadDto = (LocalidadDto) localidad.getSelectedItem();
		controller.reservarNumeroTelefonico(numeroTelefonico, planDto.getTipoTelefonia().getId(),
				modalidadCobroDto.getId(), localidadDto.getId(),
				new DefaultWaitCallback<ResultadoReservaNumeroTelefonoDto>() {
					public void success(ResultadoReservaNumeroTelefonoDto result) {
						if (result.getReservedNumber() == 0) {
							if (reservarNumeroDialog == null) {
								reservarNumeroDialog = new ReservarNumeroDialog();
							}
							reservarNumeroDialog.show(result.getAvailableNumbers(), getReservarCommnad());
						} else {
							setEnableReservaRelatedInputs(false);
							desreservar.setVisible(true);
							confirmarReserva.setVisible(false);
							String numeroText = "" + result.getReservedNumber();
							reservarHidden.setText(numeroText.substring(0, numeroText.length() - 4));
							reservar.setText(numeroText.substring(numeroText.length() - 4, numeroText
									.length()));
						}
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
		String numeroText = reservarHidden.getText() + reservar.getText();
		controller.desreservarNumeroTelefonico(Long.parseLong(numeroText), new DefaultWaitCallback() {
			public void success(Object result) {
				setEnableReservaRelatedInputs(true);
				desreservar.setVisible(false);
				confirmarReserva.setVisible(true);
				reservarHidden.setText("");
			}
		});
	}

	private void setEnableReservaRelatedInputs(boolean enable) {
		tipoOrden.setEnabled(enable);
		listaPrecio.setEnabled(enable);
		cantidad.setEnabled(enable);
		item.setEnabled(enable);
		tipoPlan.setEnabled(enable);
		plan.setEnabled(enable);
		localidad.setEnabled(enable);
		modalidadCobro.setEnabled(enable);
		reservar.setEnabled(enable);
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
			terminoPago.addAllItems(listaSelected.getTerminosPagoValido());
			onChange(item);
		} else if (sender == item) {
			// Seteo el precio del item, ajustado por el Termino de Pago y cargo el ListBox de Planes
			ItemSolicitudTasadoDto is = (ItemSolicitudTasadoDto) item.getSelectedItem();
			if (is != null) {
				TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
				double precio = is.getPrecioLista();
				if (terminoSelected.getAjuste() != null) {
					precio = terminoSelected.getAjuste() * precio;
				}
				precioListaItem.setInnerHTML(currencyFormat.format(precio));
				if (tipoPlan.getSelectedItem() != null) {
					controller.getPlanesPorItemYTipoPlan(is, (TipoPlanDto) tipoPlan.getSelectedItem(),
							getActualizarPlanCallback());
				}
			}
		} else if (sender == tipoPlan) {
			// Cargo los planes correspondientes
			if (tipoPlan.getSelectedItem() != null && item.getSelectedItem() != null) {
				controller.getPlanesPorItemYTipoPlan((ItemSolicitudTasadoDto) item.getSelectedItem(),
						(TipoPlanDto) tipoPlan.getSelectedItem(), getActualizarPlanCallback());
			}
		} else if (sender == plan) {
			// Cargo Modalidades de Cobro
			if (plan.getSelectedItem() != null) {
				PlanDto planDto = (PlanDto) plan.getSelectedItem();
				precioListaPlan.setInnerHTML(currencyFormat.format(planDto.getPrecio()));
				if (modalidadCobro.getItemCount() > 0) {
					modalidadCobro.clear();
				}
				modalidadCobro.addAllItems(planDto.getModalidadesCobro());
			}
		}
	}

	public DefaultWaitCallback<List<PlanDto>> getActualizarPlanCallback() {
		return new DefaultWaitCallback<List<PlanDto>>() {
			public void success(List<PlanDto> result) {
				if (plan.getItemCount() > 0) {
					plan.clear();
				}
				plan.addAllItems(result);
				onChange(plan);
			}
		};
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

	public void setTipoEdicion(int tipoEdicion) {
		this.tipoEdicion = tipoEdicion;
	}

	public List<String> validate() {
		GwtValidator validator = new GwtValidator();
		validator.addTarget(listaPrecio).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Lista de Precios"));
		validator.addTarget(item).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Item"));
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION) {
			validator.addTarget(tipoPlan).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Tipo Plan"));
			validator.addTarget(plan).required(Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Plan"));
			validator.addTarget(localidad).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Localidad"));
			validator.addTarget(modalidadCobro).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "CPP/MPP"));
			validator.addTarget(alias).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Alias"));
		}
		validator.addTarget(cantidad).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, "Cantidad")).greater(
				Sfa.constant().ERR_CANT_MA_CERO().replaceAll(v1, "Cantidad"), 0);
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

	public void setLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		this.lineaSolicitudServicio = linea;
		clearListBoxForSelect();
		clean();
		if (linea.getAlias() == null || "".equals(linea.getAlias().trim())) {
			alias.setText(nombreMovil);
		} else {
			alias.setText(linea.getAlias());
		}
		cantidad.setText(linea.getCantidad() != null ? "" + linea.getCantidad() : "");
		ddn.setChecked(linea.getDdn());
		ddi.setChecked(linea.getDdi());
		localidad.setSelectedItem(linea.getLocalidad());
		reservar.setText(linea.getNumeroReserva());
		reservarHidden.setText(linea.getNumeroReservaArea());
		serie.setText(linea.getNumeroSerie());
		sim.setText(linea.getNumeroSimcard());
		roaming.setChecked(linea.getRoaming());
		if (linea.getPlan() != null) {
			tipoPlan.setSelectedItem(linea.getPlan().getTipoPlan());
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
		modeloEq.setSelectedItem(linea.getModelo());
	}

	private void clearListBoxForSelect() {
		listaPrecio.clear();
		item.clear();
		plan.clear();
		modalidadCobro.clear();
		modeloEq.clear();
	}

	public LineaSolicitudServicioDto getLineaSolicitudServicio() {
		lineaSolicitudServicio.setAlias("");
		lineaSolicitudServicio.setCantidad(Integer.parseInt(cantidad.getText()));
		lineaSolicitudServicio.setDdi(ddi.isChecked());
		lineaSolicitudServicio.setDdn(ddn.isChecked());
		ItemSolicitudTasadoDto itemTasadoSelected = (ItemSolicitudTasadoDto) item.getSelectedItem();
		lineaSolicitudServicio.setItem(itemTasadoSelected.getItem());
		lineaSolicitudServicio.setPrecioLista(itemTasadoSelected.getPrecioLista());
		lineaSolicitudServicio.setPrecioVenta(itemTasadoSelected.getPrecioLista());
		lineaSolicitudServicio.setListaPrecios((ListaPreciosDto) listaPrecio.getSelectedItem());
		if (tipoEdicion == ITEM_PLAN || tipoEdicion == ACTIVACION) {
			lineaSolicitudServicio.setAlias(alias.getText());
			lineaSolicitudServicio.setLocalidad((LocalidadDto) localidad.getSelectedItem());
			lineaSolicitudServicio.setModalidadCobro((ModalidadCobroDto) modalidadCobro.getSelectedItem());
			lineaSolicitudServicio.setModelo((ModeloDto) modeloEq.getSelectedItem());
			lineaSolicitudServicio.setNumeroIMEI(imei.getText());
			lineaSolicitudServicio.setNumeroReserva(reservar.getText());
			lineaSolicitudServicio.setNumeroSerie(serie.getText());
			lineaSolicitudServicio.setNumeroSimcard(sim.getText());
			PlanDto planSelected = (PlanDto) plan.getSelectedItem();
			lineaSolicitudServicio.setPlan(planSelected);
			if (planSelected != null) {
				lineaSolicitudServicio.setPrecioListaPlan(planSelected.getPrecio());
				lineaSolicitudServicio.setPrecioVentaPlan(planSelected.getPrecio());
			} else {
				lineaSolicitudServicio.setPrecioListaPlan(0d);
				lineaSolicitudServicio.setPrecioVentaPlan(0d);
			}
			lineaSolicitudServicio.setRoaming(roaming.isChecked());
			TerminoPagoValidoDto terminoSelected = (TerminoPagoValidoDto) terminoPago.getSelectedItem();
			lineaSolicitudServicio.setTerminoPago(terminoSelected.getTerminoPago());
			double precio = itemTasadoSelected.getPrecioLista();
			if (terminoSelected.getAjuste() != null) {
				precio = terminoSelected.getAjuste() * precio;
			}
			lineaSolicitudServicio.setPrecioListaAjustado(precio);
			if (!(lineaSolicitudServicio.getPlan().getId().equals(idPlanAnterior) && lineaSolicitudServicio
					.getItem().getId().equals(idItemAnterior))) {
				lineaSolicitudServicio.getServiciosAdicionales().clear();
			}
		} else {
			lineaSolicitudServicio.setPrecioListaAjustado(itemTasadoSelected.getPrecioLista());
		}
		lineaSolicitudServicio.setTipoSolicitud((TipoSolicitudDto) tipoOrden.getSelectedItem());
		// Limpio los servicios adicionales para que los actualice
		return lineaSolicitudServicio;
	}

	public void setNombreMovil(String nombreMovil) {
		this.nombreMovil = nombreMovil;
	}
}
