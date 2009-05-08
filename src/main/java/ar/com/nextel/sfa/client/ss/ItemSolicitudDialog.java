package ar.com.nextel.sfa.client.ss;

import java.util.HashMap;
import java.util.Map;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.initializer.LineasSolicitudServicioInitializer;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudDialog extends NextelDialog {

	private ListBox tipoOrden;
	private SimpleLink aceptar;
	private SimpleLink cerrar;
	private Map<Long, TipoSolicitudDto> tiposSolicitudes = new HashMap<Long, TipoSolicitudDto>();

	public ItemSolicitudDialog(String title, EditarSSUIController controller) {
		super(title);
		tipoOrden = new ListBox();
		aceptar = new SimpleLink("ACEPTAR");
		cerrar = new SimpleLink("CERRAR");
		add(new InlineLabel(Sfa.constant().tipoOrden()));
		add(tipoOrden);
		add(getTipoOrdenPanel());
		addFormButtons(aceptar);
		addFormButtons(cerrar);
		setFormButtonsVisible(true);
		setFooterVisible(false);

		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();

			}
		});
		controller
				.getLineasSolicitudServicioInitializer(new DefaultWaitCallback<LineasSolicitudServicioInitializer>() {
					public void success(LineasSolicitudServicioInitializer initializer) {
						for (TipoSolicitudDto tipoSS : initializer.getTiposSolicitudes()) {
							tiposSolicitudes.put(tipoSS.getId(), tipoSS);
						}
					//	tipoOrden.addAllItems(initializer.getTiposSolicitudes());
					}
				});
	}

	private Widget getTipoOrdenPanel() {
		final FlowPanel tipoOrdenPanel = new FlowPanel();
		tipoOrdenPanel.add(ItemSolicitudAMBAUI.getInstance());
		tipoOrden.addItem(Sfa.constant().alquilerAMBA(), "0");
		tipoOrden.addItem(Sfa.constant().ventaAMBA(), "1");
		tipoOrden.addItem(Sfa.constant().ventaAccesorio(), "2");
		tipoOrden.addItem(Sfa.constant().activacion(), "3");
		tipoOrden.addItem(Sfa.constant().ventaLicencia(), "4");
		tipoOrden.addChangeListener(new ChangeListener() {
			public void onChange(Widget arg0) {
				if (tipoOrden.getSelectedIndex() == 0) {
					tipoOrdenPanel.clear();
					tipoOrdenPanel.add(ItemSolicitudAMBAUI.getInstance());
				} else if (tipoOrden.getSelectedIndex() == 1) {
					tipoOrdenPanel.clear();
					tipoOrdenPanel.add(ItemSolicitudAMBAUI.getInstance());
				} else if (tipoOrden.getSelectedIndex() == 2) {
					tipoOrdenPanel.clear();
					tipoOrdenPanel.add(ItemSolicitudGenericPanelUI.getInstance());
				} else if (tipoOrden.getSelectedIndex() == 3) {
					tipoOrdenPanel.clear();
					tipoOrdenPanel.add(ItemSolicitudActivacionUI.getInstance());
				} else {
					tipoOrdenPanel.clear();
					tipoOrdenPanel.add(ItemSolicitudGenericPanelUI.getInstance());
				}
			}
		});
		return tipoOrdenPanel;
	}

}
