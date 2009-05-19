package ar.com.nextel.sfa.client.ss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
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
	private Map<Long, TipoSolicitudDto> tiposSolicitudes = new HashMap<Long, TipoSolicitudDto>();
	boolean tiposSolicitudLoaded = false;

	public ItemSolicitudDialog(String title, EditarSSUIController controller) {
		super(title);
		this.controller = controller;
		tipoSolicitudPanel = new SimplePanel();
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		itemSolicitudUIData = new ItemSolicitudUIData(controller);
		tipoOrden = itemSolicitudUIData.getTipoOrden();

		tipoSolicitudPanel.setWidget(getItemYPlanSolicitudUI());
		tipoOrden.addChangeListener(this);

		add(new InlineLabel(Sfa.constant().tipoOrden()));
		add(tipoOrden);
		add(tipoSolicitudPanel);
		addFormButtons(aceptar);
		addFormButtons(cerrar);
		setFormButtonsVisible(true);
		setFooterVisible(false);

		aceptar.addClickListener(this);
		cerrar.addClickListener(this);

		controller.getLineasSolicitudServicioInitializer(initTiposOrdenCallback());
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			List errors = itemSolicitudUIData.validate();
			if (errors.isEmpty()) {
				aceptarCommand.execute();
				hide();
			} else {
				ErrorDialog.getInstance().show(errors);
			}
		} else if (sender == cerrar) {
			hide();
		}
	}

	private DefaultWaitCallback initTiposOrdenCallback() {
		return new DefaultWaitCallback<LineasSolicitudServicioInitializer>() {
			public void success(LineasSolicitudServicioInitializer initializer) {
				for (TipoSolicitudDto tipoSS : initializer.getTiposSolicitudes()) {
					tiposSolicitudes.put(tipoSS.getId(), tipoSS);
				}
				itemSolicitudUIData.getTipoPlan().addAllItems(initializer.getTiposPlanes());
				itemSolicitudUIData.getLocalidad().addAllItems(initializer.getLocalidades());
				tipoOrden.addAllItems(initializer.getTiposSolicitudes());
				onChange(tipoOrden);
			}
		};
	}

	public void onChange(Widget sender) {
		TipoSolicitudDto tipoSolicitud = (TipoSolicitudDto) tipoOrden.getSelectedItem();
		if (itemSolicitudUIData.getIdsTipoSolicitudBaseItemYPlan().contains(
				tipoSolicitud.getIdTipoSolicitudBase())) {
			tipoSolicitudPanel.setWidget(getItemYPlanSolicitudUI());
		} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseItem().contains(
				tipoSolicitud.getIdTipoSolicitudBase())) {
			tipoSolicitudPanel.setWidget(getSoloItemSolicitudUI());
		} else if (itemSolicitudUIData.getIdsTipoSolicitudBaseActivacion().contains(
				tipoSolicitud.getIdTipoSolicitudBase())) {
			tipoSolicitudPanel.setWidget(getItemSolicitudActivacionUI());
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
		return soloItemSolicitudUI.setActivacionVisible(false);
	}

	private ItemYPlanSolicitudUI getItemYPlanSolicitudUI() {
		if (itemYPlanSolicitudUI == null) {
			itemYPlanSolicitudUI = new ItemYPlanSolicitudUI(getSoloItemSolicitudUI(), itemSolicitudUIData);
		}
		soloItemSolicitudUI.setActivacionVisible(false);
		itemYPlanSolicitudUI.load();
		return itemYPlanSolicitudUI;
	}

	private ItemYPlanSolicitudUI getItemSolicitudActivacionUI() {
		return getItemYPlanSolicitudUI().setActivacionVisible(true);
	}

	public ItemSolicitudUIData getItemSolicitudUIData() {
		return itemSolicitudUIData;
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}

	public void show(LineaSolicitudServicioDto linea) {
		itemSolicitudUIData.setLineaSolicitudServicio(linea);
		if (linea.getTipoSolicitud() != null) {
			tipoOrden.setSelectedItem(linea.getTipoSolicitud());

		}
		// Si ya esta cargado el ListBox actualizo el resto. Sino se actualizará al cargarse.
		if (tipoOrden.getItemCount() > 0) {
			onChange(tipoOrden);
		}
		showAndCenter();

	}
}
