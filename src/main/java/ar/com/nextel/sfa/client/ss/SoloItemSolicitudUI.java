package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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
	private FlexTable activacionModeloImei;
	private FlexTable activacionSimSeriePin;
	private Grid precioCantidad;
	private Grid total;
	private ItemSolicitudUIData itemSolicitudData;

	public static final int LAYOUT_SIMPLE = 0;
	public static final int LAYOUT_ACTIVACION = 1;
	public static final int LAYOUT_CON_TOTAL = 2;

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

		activacionModeloImei = new FlexTable();
		activacionModeloImei.addStyleName("layout");
		activacionModeloImei.getCellFormatter().setWidth(0, 0, "100px");
		activacionModeloImei.setVisible(false);
		activacionModeloImei.setHTML(0, 0, Sfa.constant().imeiReq());
		activacionModeloImei.setWidget(0, 1, itemSolicitudData.getImei());
		activacionModeloImei.setWidget(0, 2, itemSolicitudData.getVerificarImeiWrapper());
		activacionModeloImei.setHTML(1, 0, Sfa.constant().modeloEq());
		activacionModeloImei.setWidget(1, 1, itemSolicitudData.getModeloEq());
		activacionModeloImei.getFlexCellFormatter().setColSpan(1, 1, 2);

		mainPanel.add(activacionModeloImei);
		Grid segundaTabla = new Grid(2, 2);
		segundaTabla.addStyleName("layout");
		segundaTabla.getCellFormatter().setWidth(0, 0, "100px");
		segundaTabla.setHTML(0, 0, Sfa.constant().item());
		segundaTabla.setWidget(0, 1, itemSolicitudData.getItem());
		segundaTabla.setHTML(1, 0, Sfa.constant().terminoPago());
		segundaTabla.setWidget(1, 1, itemSolicitudData.getTerminoPago());
		mainPanel.add(segundaTabla);

		precioCantidad = new Grid(1, 4);
		precioCantidad.addStyleName("layout");
		precioCantidad.getCellFormatter().setWidth(0, 0, "100px");
		precioCantidad.getCellFormatter().setWidth(0, 1, "125px");
		precioCantidad.getCellFormatter().setWidth(0, 2, "100px");
		precioCantidad.setHTML(0, 0, Sfa.constant().precioLista());
		precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		precioCantidad.setHTML(0, 2, Sfa.constant().cantidadReq());
		precioCantidad.setWidget(0, 3, itemSolicitudData.getCantidad());
		mainPanel.add(precioCantidad);

		total = new Grid(1, 2);
		total.addStyleName("layout");
		total.getCellFormatter().setWidth(0, 0, "100px");
		total.setHTML(0, 0, Sfa.constant().total());
		total.setWidget(0, 1, itemSolicitudData.getTotalLabel());
		mainPanel.add(total);

		activacionSimSeriePin = new FlexTable();
		activacionSimSeriePin.addStyleName("layout");
		activacionSimSeriePin.getCellFormatter().setWidth(0, 0, "100px");
		activacionSimSeriePin.getCellFormatter().setWidth(0, 1, "160px");
		activacionSimSeriePin.getCellFormatter().setWidth(0, 2, "50px");
		activacionSimSeriePin.setVisible(false);
		activacionSimSeriePin.setHTML(0, 0, Sfa.constant().precioLista());
		activacionSimSeriePin.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		activacionSimSeriePin.setHTML(0, 2, Sfa.constant().sim());
		activacionSimSeriePin.setWidget(0, 3, itemSolicitudData.getSim());
		activacionSimSeriePin.setWidget(0, 4, itemSolicitudData.getVerificarSimWrapper());
		activacionSimSeriePin.setWidget(1, 0, itemSolicitudData.getSerieLabel());
		activacionSimSeriePin.setWidget(1, 1, itemSolicitudData.getSerie());
		activacionSimSeriePin.setWidget(1, 2, itemSolicitudData.getPinLabel());
		activacionSimSeriePin.setWidget(1, 3, itemSolicitudData.getPin());
		activacionSimSeriePin.getFlexCellFormatter().setColSpan(1, 3, 4);
		mainPanel.add(activacionSimSeriePin);
	}

	public SoloItemSolicitudUI setLayout(int layout) {

		total.setVisible(false);

		switch (layout) {
		case LAYOUT_ACTIVACION:
			activacionModeloImei.setVisible(true);
			activacionSimSeriePin.setVisible(true);
			activacionSimSeriePin.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			itemSolicitudData.resetIMEICheck();
			precioCantidad.setVisible(false);
			break;

		case LAYOUT_CON_TOTAL:
			total.setVisible(true);

		case LAYOUT_SIMPLE:
			precioCantidad.setVisible(true);
			activacionModeloImei.setVisible(false);
			activacionSimSeriePin.setVisible(false);
			precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			break;
		default:
			break;
		}
		return this;
	}

}
