package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.nextel.model.solicitudes.beans.IMEI;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudDialog extends NextelDialog implements ChangeHandler, ClickListener {

	private ListBox tipoOrden;
	private SimpleLink aceptar;
	private Command aceptarCommand;
	private SimpleLink cerrar;
	private SimplePanel tipoSolicitudPanel;
	private SoloItemSolicitudUI soloItemSolicitudUI;
	//MGR - #1039
	//private ItemYPlanSolicitudUI itemYPlanSolicitudUI;
	private static ItemYPlanSolicitudUI itemYPlanSolicitudUI;
	private static TipoPlanDto tipoPlanPorDefecto = null;
	
	private ItemSolicitudUIData itemSolicitudUIData;
	private EditarSSUIController controller;
	private Map<Long, TipoSolicitudDto> tiposSolicitudes = new HashMap();
	private Map<Long, List<TipoSolicitudDto>> tiposSolicitudesPorGrupo = new HashMap();
	private Long idGrupoSolicitudLoaded;
	private boolean tiposSolicitudLoaded = false;
	private HTML nuevoItem;
	private List<TipoPlanDto> tiposPlan = null;
	private boolean empresa = false;

	public ItemSolicitudDialog(String title, EditarSSUIController controller) {
		super(title, false, true);
		addStyleName("gwt-ItemSolicitudDialog");
		this.controller = controller;
		tipoSolicitudPanel = new SimplePanel();
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		itemSolicitudUIData = new ItemSolicitudUIData(controller);
		itemSolicitudUIData.setItemSolicitudDialog(this);
		tipoOrden = itemSolicitudUIData.getTipoOrden();
		tipoSolicitudPanel.setWidget(getItemYPlanSolicitudUI());
		tipoOrden.addChangeHandler(this);

		FlowPanel topBar = new FlowPanel();
		topBar.addStyleName("topBar");
		nuevoItem = IconFactory.nuevo("Nuevo Item");
		nuevoItem.addClickListener(this);
		nuevoItem.addStyleName("floatRight");
		topBar.add(nuevoItem);
		Widget tipoOrdenLabel = new InlineLabel(Sfa.constant().tipoOrden());
		tipoOrdenLabel.addStyleName("mr30");
		topBar.add(tipoOrdenLabel);
		topBar.add(tipoOrden);
		add(topBar);
		
		add(tipoSolicitudPanel);
		
		
		addFormButtons(aceptar);
		addFormButtons(cerrar);
		setFormButtonsVisible(true);
		setFooterVisible(false);

		aceptar.addClickListener(this);
		cerrar.addClickListener(this);
		
		controller.getLineasSolicitudServicioInitializer(initTiposOrdenCallback());
	}
	
	
	/**
	 * TODO: Portabilidad
	 */
	public void resetearPanelPortabilidad(){
		itemSolicitudUIData.getPortabilidadPanel().resetearPortabilidad();
	}
	
	/**
	 * TODO: Portabilidad
	 */
	public void cargarPortabilidadInitializer(PortabilidadInitializer portabilidadInitializer){
		if(portabilidadInitializer != null) itemSolicitudUIData.getPortabilidadPanel().setInitializer(portabilidadInitializer);
	}
	
	/**
	 * TODO: Portabilidad
	 * @param unaPersona
	 */
	public void cargarPersona(PersonaDto unaPersona){
		itemSolicitudUIData.getPortabilidadPanel().setPersona(unaPersona);
	}
	
	public void onClick(final Widget sender) {
		if (sender == aceptar || sender == nuevoItem) {
			executeItemCreation(sender);
		} else if (sender == cerrar) {
			itemSolicitudUIData.desreservarSiNoFueGrabado();
			itemSolicitudUIData.getConfirmarReserva().setEnabled(true);
			hide();
		}
	}

	private void executeItemCreation(final Widget sender) {
		boolean portabilidadVisible = itemSolicitudUIData.getPortabilidadPanel().isVisible();
		List<String> errors = new ArrayList<String>();
		List<String> errorsPortabilidad = new ArrayList<String>();
		
		errors.addAll(itemSolicitudUIData.validate());

		if(portabilidadVisible) errorsPortabilidad.addAll(itemSolicitudUIData.getPortabilidadPanel().ejecutarValidacion());

		if (errors.isEmpty()) {
			if(errorsPortabilidad.isEmpty()){
				
//				MGR - RQN 2328 - Si hay portabilidad, valido que el numero ingresado pertenesca a un area de billing válida
				if(portabilidadVisible){
					String numeroAPortar = itemSolicitudUIData.getPortabilidadPanel().obtenerNumeroAportar();
					SolicitudRpcService.Util.getInstance().validarAreaBilling(numeroAPortar,
							new DefaultWaitCallback<Boolean>() {
						@Override
						public void success(Boolean result) {
							if(!result){ 
								ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
								ErrorDialog.getInstance().show(Sfa.constant().ERR_AREA_BILLING_NO_VALIDA(), false);
							}else{
								aceptarItem(sender);
							}
						}
					});
				}else{
					aceptarItem(sender);
				}
				
			}else{
				ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
				ErrorDialog.getInstance().show(errorsPortabilidad, false);
			}
		} else {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			if(!errorsPortabilidad.isEmpty()) errors.addAll(errorsPortabilidad);
			ErrorDialog.getInstance().show(errors, false);

		}
	}
	
//	MGR - RQN 2328 - Continuo con la aceptación del item ingresado
	public void aceptarItem(final Widget sender){
		// Si ingreso un numero para reservar y no lo reservo le pregunto si desea hacerlo.
		if (itemSolicitudUIData.hasNumeroSinReservar()) {
			ModalMessageDialog.getInstance().setDialogTitle("Reserva");
			ModalMessageDialog.getInstance().showSiNo("Desea reservar el número elegido?",
					getReservarCommand(), new Command() {
						public void execute() {
							itemSolicitudUIData.getReservar().setText("");
							ModalMessageDialog.getInstance().hide();
							guardarItem(sender == aceptar);
						}
					});
		} else {
			guardarItem(sender == aceptar);
		}
	}
	

	private void guardarItem(boolean soloGuardar) {
		aceptarCommand.execute();
		if (soloGuardar) {
			hide();
		} else {
			show(new LineaSolicitudServicioDto());
		}
	}

	private Command getReservarCommand() {
		return new Command() {
			public void execute() {
				ModalMessageDialog.getInstance().hide();
				itemSolicitudUIData.reservar();
			}
		};
	}

	private DefaultWaitCallback initTiposOrdenCallback() {
		return new DefaultWaitCallback<LineasSolicitudServicioInitializer>() {
			public void success(LineasSolicitudServicioInitializer initializer) {
				List<TipoSolicitudDto> tiposSS = new ArrayList<TipoSolicitudDto>();
				tiposSolicitudesPorGrupo = initializer.getTiposSolicitudPorGrupo();
				for (Map.Entry<Long, List<TipoSolicitudDto>> tiposSSDeGrupo : tiposSolicitudesPorGrupo
						.entrySet()) {
					tiposSS.addAll(tiposSSDeGrupo.getValue());
				}
				for (TipoSolicitudDto tipoSS : tiposSS) {
					tiposSolicitudes.put(tipoSS.getId(), tipoSS);
				}
				tiposPlan = initializer.getTiposPlanes();
				itemSolicitudUIData.getLocalidad().addAllItems(initializer.getLocalidades());
				refreshTipoOrden();
			}
		};
	}


	public void onChange(ChangeEvent event) {
		TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto) tipoOrden.getSelectedItem();
		itemSolicitudUIData.setActivacionOnline(false);
		
		if (tipoSolicitud != null) {
			if (itemSolicitudUIData.getIdsTipoSolicitudBaseItemYPlan().contains(
					tipoSolicitud.getTipoSolicitudBase().getId())) {
				tipoSolicitudPanel.setWidget(getItemYPlanSolicitudUI());
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.ITEM_PLAN);
			} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseItem().contains(
					tipoSolicitud.getTipoSolicitudBase().getId())) {
				tipoSolicitudPanel.setWidget(getSoloItemSolicitudUI());
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.SOLO_ITEM);
			} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseActivacion().contains(
					tipoSolicitud.getTipoSolicitudBase().getId())) {
				if(itemSolicitudUIData.getIdTipoSolicitudBaseActivacionOnline().equals(tipoSolicitud.getTipoSolicitudBase().getId())) {
					itemSolicitudUIData.setActivacionOnline(true);
				}
//				MGR - #3462
				if(itemSolicitudUIData.getIdTipoSolicitudBaseActivacion().equals(tipoSolicitud.getTipoSolicitudBase().getId())) {
					itemSolicitudUIData.setActivacion(true);
				}
				tipoSolicitudPanel.setWidget(getItemSolicitudActivacionUI(itemSolicitudUIData.isActivacionOnline()));
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.ACTIVACION);
			} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseCDW().contains(
					tipoSolicitud.getTipoSolicitudBase().getId())) {
				tipoSolicitudPanel.setWidget(getItemSolicitudCDWUI());
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.VENTA_CDW);
			} else {
				tipoSolicitudPanel.clear();
			}
			
//			visualizarIMEI_SIM(tipoSolicitud);
			loadUIData(tipoSolicitud);
		}
	}
	

	
	private void loadUIData(final TipoSolicitudDto tiposSolicitud) {
		//#1757 - La ListaPrecios se carga según el segmento del cliente
//		if (tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios() == null) {
			controller.getListaPrecios(tiposSolicitud, empresa, new DefaultWaitCallback<List<ListaPreciosDto>>() {
				public void success(List<ListaPreciosDto> listasPrecios) {
					tiposSolicitudes.get(tiposSolicitud.getId()).setListasPrecios(listasPrecios);
					itemSolicitudUIData.load(tiposSolicitud.getListasPrecios());
				};
			});
//		} else {
//			itemSolicitudUIData.load(tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios());
//		}
	}

	private SoloItemSolicitudUI getSoloItemSolicitudUI() {
		if (soloItemSolicitudUI == null) {
			soloItemSolicitudUI = new SoloItemSolicitudUI(itemSolicitudUIData);
		}
		return soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_CON_TOTAL,controller);
	}

	private ItemYPlanSolicitudUI getItemYPlanSolicitudUI() {
		if (itemYPlanSolicitudUI == null) {
			itemYPlanSolicitudUI = new ItemYPlanSolicitudUI(getSoloItemSolicitudUI(), itemSolicitudUIData,controller);
		}
		soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_SIMPLE, controller);
		itemYPlanSolicitudUI.load();
		return itemYPlanSolicitudUI;
	}

	private ItemYPlanSolicitudUI getItemSolicitudActivacionUI(boolean online) {
		return getItemYPlanSolicitudUI().setActivacionVisible(online);
	}

	private ItemYPlanSolicitudUI getItemSolicitudCDWUI() {
		return getItemYPlanSolicitudUI().setCDWVisible();
	}

	public ItemSolicitudUIData getItemSolicitudUIData() {
		return itemSolicitudUIData;
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public void show(LineaSolicitudServicioDto linea) {
		itemSolicitudUIData.getTipoPlan().clear();
		itemSolicitudUIData.getTipoPlan().clearPreseleccionados();
		itemSolicitudUIData.setNombreMovil(controller.getNombreProximoMovil());
		itemSolicitudUIData.setLineaSolicitudServicio(linea);

		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(!instancias.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS).equals(controller.getEditarSSUIData().getGrupoSolicitud().getId())){
			itemSolicitudUIData.getPortabilidad().setEnabled(false);
			itemSolicitudUIData.getPortabilidad().setVisible(false);
		}else{
			itemSolicitudUIData.getPortabilidad().setEnabled(true);
			itemSolicitudUIData.getPortabilidad().setVisible(true);
		}

		final TipoPlanDto tipoPlan = linea != null && linea.getPlan() != null ? linea.getPlan().getTipoPlan()
				: null;
		if (linea.getTipoSolicitud() != null) {
			tipoOrden.setSelectedItem(linea.getTipoSolicitud());
		}
		// Si ya esta cargado el ListBox actualizo el resto. Sino se actualizará al cargarse.
		if (tipoOrden.getItemCount() > 0) {
			refreshTipoOrden();
		}
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (tiposPlan == null) {
					return true;
				}
				//MGR - #1039
				//TipoPlanDto tipoPlanPorDefecto = null;
				tipoPlanPorDefecto = null;
				for (TipoPlanDto tipoPlan : tiposPlan) {
					if (tipoPlan.getCodigoBSCS().equals(TipoPlanDto.TIPO_PLAN_DIRECTO_O_EMPRESA_CODE)) {
						if (empresa == tipoPlan.isEmpresa()) {
							itemSolicitudUIData.getTipoPlan().addItem(tipoPlan);
							tipoPlanPorDefecto = tipoPlan;
						}
						//MGR - #1759
						else if(ClientContext.getInstance().getVendedor().isADMCreditos()){
							itemSolicitudUIData.getTipoPlan().addItem(tipoPlan);
						}
					} else {
						itemSolicitudUIData.getTipoPlan().addItem(tipoPlan);
					}
				}
				itemSolicitudUIData.getTipoPlan().setSelectedItem(tipoPlan != null ? tipoPlan : tipoPlanPorDefecto);

				itemSolicitudUIData.onChange(itemSolicitudUIData.getTipoPlan());
				return false;
			}
		});
		showAndCenter();
	}

	private void refreshTipoOrden() {
		GrupoSolicitudDto grupoSolicitudSelected = controller.getEditarSSUIData().getGrupoSolicitud();
		if (grupoSolicitudSelected != null) {
			if (!grupoSolicitudSelected.getId().equals(idGrupoSolicitudLoaded)) {
				tipoOrden.clear();
				tipoOrden.addAllItems(tiposSolicitudesPorGrupo.get(grupoSolicitudSelected.getId()));
				idGrupoSolicitudLoaded = grupoSolicitudSelected.getId();
			}
		} else {
			tipoOrden.clear();
			//MGR - #1050
			HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
			if(instancias != null){
				tipoOrden.addAllItems(tiposSolicitudesPorGrupo.get(instancias.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS)));
				idGrupoSolicitudLoaded = instancias.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS);	
			}
			
		}
		onChange(null);
	}

	public void setCuentaEmpresa(boolean empresa) {
		this.empresa = empresa;
	}
	
	
	//MGR - #1039
	public static ItemYPlanSolicitudUI obtenerItemYPlanSolicitudUI(){
		return itemYPlanSolicitudUI != null ? itemYPlanSolicitudUI : null;
	}
	
	//MGR - #1039
	public static TipoPlanDto obtenerTipoPlanPorDefecto(){
		return tipoPlanPorDefecto;
	}
}
