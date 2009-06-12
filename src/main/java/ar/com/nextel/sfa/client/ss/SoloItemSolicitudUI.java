package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * Panel de edicion de linea de solicitud que solo contiene Item, como por ejemplo una venta de accesorio
 * 
 * @author jlgperez
 * 
 */
public class SoloItemSolicitudUI extends Composite {

	private FlowPanel mainPanel;
	private Grid activacionModeloImei;
	private Grid activacionSimSeriePin;
	private Grid precioCantidad;
	private ItemSolicitudUIData itemSolicitudData;

	public SoloItemSolicitudUI(ItemSolicitudUIData itemSolicitudUIData) {
		mainPanel = new FlowPanel();
		itemSolicitudData = itemSolicitudUIData;
		initWidget(mainPanel);
		initGenericPanel();
	}

	private void initGenericPanel() {
		Grid primerTabla = new Grid(1, 2);
		primerTabla.addStyleName("layout");
		primerTabla.getCellFormatter().setWidth(0, 0, "100px");
		primerTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().listaPrecio()));
		primerTabla.setWidget(0, 1, itemSolicitudData.getListaPrecio());
		mainPanel.add(primerTabla);

		activacionModeloImei = new Grid(2, 2);
		activacionModeloImei.addStyleName("layout");
		activacionModeloImei.getCellFormatter().setWidth(0, 0, "100px");
		activacionModeloImei.setVisible(false);
		activacionModeloImei.setHTML(0, 0, Sfa.constant().imei());
		activacionModeloImei.setWidget(0, 1, itemSolicitudData.getImei());
		activacionModeloImei.setHTML(1, 0, Sfa.constant().modeloEq());
		activacionModeloImei.setWidget(1, 1, itemSolicitudData.getModeloEq());

		mainPanel.add(activacionModeloImei);
		Grid segundaTabla = new Grid(2, 2);
		segundaTabla.addStyleName("layout");
		segundaTabla.getCellFormatter().setWidth(0, 0, "100px");
		segundaTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().item()));
		segundaTabla.setWidget(0, 1, itemSolicitudData.getItem());
		segundaTabla.setWidget(1, 0, new InlineLabel(Sfa.constant().terminoPago()));
		segundaTabla.setWidget(1, 1, itemSolicitudData.getTerminoPago());
		mainPanel.add(segundaTabla);
		
		precioCantidad = new Grid(1, 4);
		precioCantidad.addStyleName("layout");
		precioCantidad.getCellFormatter().setWidth(0, 0, "100px");
		precioCantidad.getCellFormatter().setWidth(0, 1, "125px");
		precioCantidad.getCellFormatter().setWidth(0, 2, "100px");
		precioCantidad.setWidget(0, 0, new InlineLabel(Sfa.constant().precioLista()));
		precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		precioCantidad.setWidget(0, 2, new InlineLabel(Sfa.constant().cantidad()));
		precioCantidad.setWidget(0, 3, itemSolicitudData.getCantidad());
		mainPanel.add(precioCantidad);

		activacionSimSeriePin = new Grid(2, 4);
		activacionSimSeriePin.addStyleName("layout");
		activacionSimSeriePin.getCellFormatter().setWidth(0, 0, "100px");
		activacionSimSeriePin.setVisible(false);
		activacionSimSeriePin.setHTML(0, 0, Sfa.constant().precioLista());
		activacionSimSeriePin.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		activacionSimSeriePin.setHTML(0, 2, Sfa.constant().sim());
		activacionSimSeriePin.setWidget(0, 3, itemSolicitudData.getSim());
		activacionSimSeriePin.setHTML(1, 0, Sfa.constant().serie());
		activacionSimSeriePin.setWidget(1, 1, itemSolicitudData.getSerie());
		activacionSimSeriePin.setHTML(1, 2, Sfa.constant().pin());
		activacionSimSeriePin.setWidget(1, 3, itemSolicitudData.getPin());

		mainPanel.add(activacionSimSeriePin);
	}

	public SoloItemSolicitudUI setActivacionVisible(boolean visible) {
		activacionModeloImei.setVisible(visible);
		activacionSimSeriePin.setVisible(visible);
		precioCantidad.setVisible(!visible);
		if (visible) {
			activacionSimSeriePin.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		} else {
			precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		}
		return this;
	}

}
