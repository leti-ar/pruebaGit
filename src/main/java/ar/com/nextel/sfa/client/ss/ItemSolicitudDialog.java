package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudDialog extends NextelDialog implements ChangeListener, ClickListener {

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

	public ItemSolicitudDialog(String title, EditarSSUIController controller) {
		super(title);
		addStyleName("gwt-ItemSolicitudDialog");
		this.controller = controller;
		tipoSolicitudPanel = new SimplePanel();
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		itemSolicitudUIData = new ItemSolicitudUIData(controller);
		tipoOrden = itemSolicitudUIData.getTipoOrden();

		tipoSolicitudPanel.setWidget(getItemYPlanSolicitudUI());
		tipoOrden.addChangeListener(this);

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
			if (itemSolicitudUIData.hasNumeroSinReservar()) {
				MessageDialog.getInstance().showSiNo("", getReservarYGuardarCommand(), new Command() {
					public void execute() {
						itemSolicitudUIData.getReservar().setText("");
						MessageDialog.getInstance().hide();
						executeItemCreation(sender);
					}
				});
			} else {
				executeItemCreation(sender);
			}
		} else if (sender == cerrar) {
			hide();
		}
	}

	private void executeItemCreation(Widget sender) {
		List errors = itemSolicitudUIData.validate();
		if (errors.isEmpty()) {
			aceptarCommand.execute();
			if (sender == aceptar) {
				hide();
			} else if (sender == nuevoItem) {
				show(new LineaSolicitudServicioDto());
			}
		} else {
			ErrorDialog.getInstance().show(errors);
		}
	}

	private Command getReservarYGuardarCommand() {
		return new Command() {
			public void execute() {
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

	public void onChange(Widget sender) {
		TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto) tipoOrden.getSelectedItem();
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

	private void loadUIData(final TipoSolicitudDto tiposSolicitud) {
		if (tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios() == null) {
			controller.getListaPrecios(tiposSolicitud, new DefaultWaitCallback<List<ListaPreciosDto>>() {
				public void success(List<ListaPreciosDto> listasPrecios) {
					tiposSolicitudes.get(tiposSolicitud.getId()).setListasPrecios(listasPrecios);
					itemSolicitudUIData.load(tiposSolicitud.getListasPrecios());
				};
			});
		} else {
			itemSolicitudUIData.load(tiposSolicitudes.get(tiposSolicitud.getId()).getListasPrecios());
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
		if (tipoOrden.getItemCount() > 0) {
			refreshTipoOrden();
		}
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
			tipoOrden.addAllItems(tiposSolicitudesPorGrupo.get(GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS));
			idGrupoSolicitudLoaded = GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS;
		}
		onChange(tipoOrden);
	}
}
