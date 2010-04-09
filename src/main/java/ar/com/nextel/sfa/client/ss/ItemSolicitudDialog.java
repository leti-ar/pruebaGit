package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudDialog extends NextelDialog implements ChangeHandler, ClickListener {

	private ListBox tipoOrden;
	private SimpleLink aceptar;
	private Command aceptarCommand;
	private SimpleLink cerrar;
	private SimplePanel tipoSolicitudPanel;
	private SoloItemSolicitudUI soloItemSolicitudUI;
	private ItemYPlanSolicitudUI itemYPlanSolicitudUI;
	private ItemSolicitudUIData itemSolicitudUIData;
	private EditarSSUIController controller;
	private Map<Long, TipoSolicitudDto> tiposSolicitudes = new HashMap();
	private Map<Long, List<TipoSolicitudDto>> tiposSolicitudesPorGrupo = new HashMap();
	private Long idGrupoSolicitudLoaded;
	boolean tiposSolicitudLoaded = false;
	private HTML nuevoItem;
	private EditarSSUIData editarSSUIData;
	private boolean checkBoxValue = false;

	public ItemSolicitudDialog(String title, EditarSSUIController controller) {
		super(title, false, true);
		editarSSUIData = controller.getEditarSSUIData();
		addStyleName("gwt-ItemSolicitudDialog");
		this.controller = controller;
		tipoSolicitudPanel = new SimplePanel();
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		itemSolicitudUIData = new ItemSolicitudUIData(controller);
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

	public void onClick(final Widget sender) {
		if (sender == aceptar || sender == nuevoItem) {
			executeItemCreation(sender);
		} else if (sender == cerrar) {
			itemSolicitudUIData.desreservarSiNoFueGrabado();
			hide();
		}
	}
	


	private void executeItemCreation(final Widget sender) {
		List errors = itemSolicitudUIData.validate();
		if (errors.isEmpty()) {
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
		} else {
			ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
			ErrorDialog.getInstance().show(errors, false);
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
				itemSolicitudUIData.getTipoPlan().addAllItems(initializer.getTiposPlanes());
				itemSolicitudUIData.getLocalidad().addAllItems(initializer.getLocalidades());
				refreshTipoOrden();
			}
		};
	}

	public void onChange(ChangeEvent event) {
		TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto) tipoOrden.getSelectedItem();
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
				tipoSolicitudPanel.setWidget(getItemSolicitudActivacionUI());
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.ACTIVACION);
			} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseCDW().contains(
					tipoSolicitud.getTipoSolicitudBase().getId())) {
				tipoSolicitudPanel.setWidget(getItemSolicitudCDWUI());
				itemSolicitudUIData.setTipoEdicion(ItemSolicitudUIData.VENTA_CDW);
			} else {
				tipoSolicitudPanel.clear();
			}
			loadUIData(tipoSolicitud);
		}
	}

	private void loadUIData(final TipoSolicitudDto tiposSolicitud) {
		if (tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios() == null) {
			controller.getListaPrecios(tiposSolicitud, new DefaultWaitCallback<List<ListaPreciosDto>>() {
				public void success(List<ListaPreciosDto> listasPrecios) {
					tiposSolicitudes.get(tiposSolicitud.getId()).setListasPrecios(listasPrecios);
					List<ListaPreciosDto> tipos = new ArrayList<ListaPreciosDto>(tiposSolicitud.getListasPrecios());
					itemSolicitudUIData.load(tipos, editarSSUIData.getDespacho().getValue());
				};
			});
		} else {
			List<ListaPreciosDto> tipos = new ArrayList<ListaPreciosDto>(tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios());
			itemSolicitudUIData.load(tipos, editarSSUIData.getDespacho().getValue());
		}
	}

	private SoloItemSolicitudUI getSoloItemSolicitudUI() {
		if (soloItemSolicitudUI == null) {
			soloItemSolicitudUI = new SoloItemSolicitudUI(itemSolicitudUIData);
		}
		return soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_CON_TOTAL);
	}

	private ItemYPlanSolicitudUI getItemYPlanSolicitudUI() {
		if (itemYPlanSolicitudUI == null) {
			itemYPlanSolicitudUI = new ItemYPlanSolicitudUI(getSoloItemSolicitudUI(), itemSolicitudUIData);
		}
		soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_SIMPLE);
		itemYPlanSolicitudUI.load();
		return itemYPlanSolicitudUI;
	}

	private ItemYPlanSolicitudUI getItemSolicitudActivacionUI() {
		return getItemYPlanSolicitudUI().setActivacionVisible();
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
		itemSolicitudUIData.setNombreMovil(controller.getNombreProximoMovil());
		itemSolicitudUIData.setLineaSolicitudServicio(linea);
		if (linea.getTipoSolicitud() != null) {
			tipoOrden.setSelectedItem(linea.getTipoSolicitud());
		}
		// Si ya esta cargado el ListBox actualizo el resto. Sino se actualizará al cargarse.
		if (tipoOrden.getItems() != null) {
			refreshTipoOrden();
		}
		showAndCenter();
	}

	private void refreshTipoOrden() {
		GrupoSolicitudDto grupoSolicitudSelected = controller.getEditarSSUIData().getGrupoSolicitud();
		List<TipoSolicitudDto> tipos = null;
		if (grupoSolicitudSelected != null) {
			if (!grupoSolicitudSelected.getId().equals(idGrupoSolicitudLoaded)
					|| !(checkBoxValue == editarSSUIData.getDespacho().getValue())) {
				tipos = new ArrayList<TipoSolicitudDto>(tiposSolicitudesPorGrupo.get(grupoSolicitudSelected.getId()));
				if (editarSSUIData.getDespacho().getValue()) {
					tipos = filtrar(tipos);
				}
				tipoOrden.clear();
				tipoOrden.addAllItems(tipos);
				idGrupoSolicitudLoaded = grupoSolicitudSelected.getId();
			}
		} else {
			tipos = new ArrayList<TipoSolicitudDto>(tiposSolicitudesPorGrupo.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS));
			if (editarSSUIData.getDespacho().getValue()) {
				tipos = filtrar(tipos);
			}
			tipoOrden.clear();
			tipoOrden.addAllItems(tipos);
			idGrupoSolicitudLoaded = GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS;
		}
		checkBoxValue = editarSSUIData.getDespacho().getValue();
		onChange(null);
	}
	
	private List<TipoSolicitudDto> filtrar(List<TipoSolicitudDto> tipos) {
		List<TipoSolicitudDto> tiposAux = new ArrayList<TipoSolicitudDto>();
		for (Iterator<TipoSolicitudDto> iterator = tipos.iterator(); iterator.hasNext();) {
			TipoSolicitudDto solicitudDto = (TipoSolicitudDto) iterator.next();
			//Si el checkBox está tildado, solo muestro los tipos de orden que permiten despacho
			if(!solicitudDto.getPermiteDespacho()){
				tiposAux.add(solicitudDto);
			}		
		}
		tipos.removeAll(tiposAux);
		return tipos;
	}
}
