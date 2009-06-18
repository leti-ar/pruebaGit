package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel de edicion de linea de solicitud que contiene Item (Mediante SoloItemSolicitudUI) y Plan, como por
 * ejemplo un Alquiler
 * 
 * @author jlgperez
 * 
 */
public class ItemYPlanSolicitudUI extends Composite {

	private FlowPanel mainPanel;
	private ItemSolicitudUIData itemSolicitudUIData;
	private SoloItemSolicitudUI soloItemSolicitudUI;
	private SimplePanel soloItemSolicitudWrapper;

	public ItemYPlanSolicitudUI(SoloItemSolicitudUI soloItemSolicitudUI,
			ItemSolicitudUIData itemSolicitudUIData) {
		this.soloItemSolicitudUI = soloItemSolicitudUI;
		mainPanel = new FlowPanel();
		this.itemSolicitudUIData = itemSolicitudUIData;
		initWidget(mainPanel);
		initItemSolicitudAMBAUI();
	}

	private void initItemSolicitudAMBAUI() {
		soloItemSolicitudWrapper = new SimplePanel();
		soloItemSolicitudWrapper.setWidget(soloItemSolicitudUI);
		mainPanel.add(soloItemSolicitudWrapper);
		Grid table = new Grid(4, 2);
		table.addStyleName("layout");
		table.getCellFormatter().setWidth(0, 0, "100px");
		table.setHTML(0, 0, Sfa.constant().tipoPlan());
		table.setWidget(0, 1, itemSolicitudUIData.getTipoPlan());
		table.setHTML(1, 0, Sfa.constant().plan());
		table.setWidget(1, 1, itemSolicitudUIData.getPlan());
		table.setHTML(2, 0, Sfa.constant().precioLista());
		table.setWidget(2, 1, itemSolicitudUIData.getPrecioListaPlan());
		table.setHTML(3, 0, Sfa.constant().localidad());
		table.setWidget(3, 1, itemSolicitudUIData.getLocalidad());
		mainPanel.add(table);
		FlexTable segundaTabla = new FlexTable();
		segundaTabla.addStyleName("layout");
		segundaTabla.getCellFormatter().setWidth(0, 0, "100px");
		segundaTabla.getCellFormatter().setWidth(0, 1, "125px");
		segundaTabla.getCellFormatter().setWidth(0, 2, "100px");
		((FlexTable) segundaTabla).getFlexCellFormatter().setColSpan(1, 1, 3);
		segundaTabla.setHTML(0, 0, Sfa.constant().cppMpp());
		segundaTabla.setWidget(0, 1, itemSolicitudUIData.getModalidadCobro());
		segundaTabla.setHTML(0, 2, Sfa.constant().alias());
		segundaTabla.setWidget(0, 3, itemSolicitudUIData.getAlias());
		segundaTabla.setHTML(1, 0, Sfa.constant().reservarN());
		segundaTabla.setWidget(1, 1, getReservaPanel());
		mainPanel.add(segundaTabla);
		Grid terceraTabla = new Grid(1, 6);
		terceraTabla.addStyleName("layout ml10");
		terceraTabla.getCellFormatter().setWidth(0, 0, "100px");
		terceraTabla.getCellFormatter().setWidth(0, 1, "100px");
		terceraTabla.setWidget(0, 0, itemSolicitudUIData.getDdn());
		terceraTabla.setWidget(0, 1, itemSolicitudUIData.getDdi());
		terceraTabla.setWidget(0, 2, itemSolicitudUIData.getRoaming());
		mainPanel.add(terceraTabla);
	}

	private Widget getReservaPanel() {
		FlowPanel reservaPanel = new FlowPanel();
		reservaPanel.add(itemSolicitudUIData.getReservarHidden());
		reservaPanel.add(itemSolicitudUIData.getReservar());
		reservaPanel.add(itemSolicitudUIData.getConfirmarReserva());
		reservaPanel.add(itemSolicitudUIData.getDesreservar());
		return reservaPanel;
	}

	public void load() {
		soloItemSolicitudWrapper.setWidget(soloItemSolicitudUI);
	}

	public ItemYPlanSolicitudUI setActivacionVisible() {
		soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_ACTIVACION);
		return this;
	}

}
