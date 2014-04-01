package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;

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
	private Grid roamingTable;
	private Grid aliasTable;
	private FlexTable cppAliasReservaTable;
	//MGR - #1039 - Necesito poder identificar la tabla para poder ocultarla
	private Grid table;
	private EditarSSUIController controller;
	private FlexTable imeiSimRetiroEnSucursal;
	
	public ItemYPlanSolicitudUI(SoloItemSolicitudUI soloItemSolicitudUI,
			ItemSolicitudUIData itemSolicitudUIData,EditarSSUIController controller) {
		this.soloItemSolicitudUI = soloItemSolicitudUI;
		mainPanel = new FlowPanel();
		this.itemSolicitudUIData = itemSolicitudUIData;
		initWidget(mainPanel);
		this.controller = controller;
		initItemSolicitudAMBAUI();
	}

	private void initItemSolicitudAMBAUI() {
		soloItemSolicitudWrapper = new SimplePanel();
		soloItemSolicitudWrapper.setWidget(soloItemSolicitudUI);
		mainPanel.add(soloItemSolicitudWrapper);
		
		//MGR - #1039
		//Grid table = new Grid(4, 2);
		table = new Grid(4, 2);
		
		table.addStyleName("layout");
		table.getCellFormatter().setWidth(0, 0, "100px");
		table.setHTML(0, 0, Sfa.constant().tipoPlan());
		table.setWidget(0, 1, itemSolicitudUIData.getTipoPlan());
		table.setHTML(1, 0, Sfa.constant().planReq());
		table.setWidget(1, 1, itemSolicitudUIData.getPlan());
		table.setHTML(2, 0, Sfa.constant().precioLista());
		table.setWidget(2, 1, itemSolicitudUIData.getPrecioListaPlan());
		table.setHTML(3, 0, Sfa.constant().localidadReq());
		table.setWidget(3, 1, itemSolicitudUIData.getLocalidad());
		mainPanel.add(table);
		
		aliasTable =  new Grid(1, 2);
		aliasTable.addStyleName("layout");
		aliasTable.getCellFormatter().setWidth(0, 0, "100px");
		aliasTable.setHTML(0, 0, Sfa.constant().alias());
		aliasTable.setVisible(false);
		mainPanel.add(aliasTable);
		
		cppAliasReservaTable = new FlexTable();
		cppAliasReservaTable.addStyleName("layout");
		cppAliasReservaTable.getCellFormatter().setWidth(0, 0, "100px");
		cppAliasReservaTable.getCellFormatter().setWidth(0, 1, "125px");
		cppAliasReservaTable.getCellFormatter().setWidth(0, 2, "100px");
		((FlexTable) cppAliasReservaTable).getFlexCellFormatter().setColSpan(1, 1, 3);
		cppAliasReservaTable.setHTML(0, 0, Sfa.constant().cppMpp());
		cppAliasReservaTable.setWidget(0, 1, itemSolicitudUIData.getModalidadCobro());
		cppAliasReservaTable.setHTML(0, 2, Sfa.constant().alias());
		cppAliasReservaTable.setWidget(0, 3, itemSolicitudUIData.getAlias());
		cppAliasReservaTable.setHTML(1, 0, Sfa.constant().reservarN());
		cppAliasReservaTable.setWidget(1, 1, getReservaPanel());
		mainPanel.add(cppAliasReservaTable);
			
		roamingTable = new Grid(1, 7);
		roamingTable.addStyleName("layout ml10");
		roamingTable.getCellFormatter().setWidth(0, 0, "100px");
		roamingTable.getCellFormatter().setWidth(0, 1, "100px");
		roamingTable.setWidget(0, 0, itemSolicitudUIData.getDdn());
		roamingTable.setWidget(0, 1, itemSolicitudUIData.getDdi());
		roamingTable.setWidget(0, 2, itemSolicitudUIData.getRoaming());
		roamingTable.setWidget(0, 3, itemSolicitudUIData.getPortabilidad());
		mainPanel.add(roamingTable);
		mainPanel.add(itemSolicitudUIData.getPortabilidadPanel());
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
		cppAliasReservaTable.setWidget(0, 3, itemSolicitudUIData.getAlias());
		
		cppAliasReservaTable.getCellFormatter().setVisible(0, 0, true);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 1, true);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 2, true);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 3, true);
		cppAliasReservaTable.getCellFormatter().setVisible(1, 0, true);
		cppAliasReservaTable.getCellFormatter().setVisible(1, 1, true);
		cppAliasReservaTable.setVisible(true);
		
		aliasTable.getCellFormatter().setVisible(0, 0, false);
		aliasTable.getCellFormatter().setVisible(0, 1, false);
		aliasTable.setVisible(false);
		
		roamingTable.getCellFormatter().setVisible(0, 0, true);
		roamingTable.getCellFormatter().setVisible(0, 1, true);
		roamingTable.getCellFormatter().setVisible(0, 2, true);
		roamingTable.setVisible(true);
	}

	public ItemYPlanSolicitudUI setActivacionVisible(boolean online) {
		if(online) {
			cppAliasReservaTable.getCellFormatter().setVisible(0, 0, false);
			cppAliasReservaTable.getCellFormatter().setVisible(0, 1, false);
			cppAliasReservaTable.getCellFormatter().setVisible(0, 2, false);
			cppAliasReservaTable.getCellFormatter().setVisible(0, 3, false);
			soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_ACTIVACION_ONLINE);
		} else {
			soloItemSolicitudUI.setLayout(SoloItemSolicitudUI.LAYOUT_ACTIVACION);
		}
		return this;
	}
	
	public ItemYPlanSolicitudUI setCDWVisible() {
		aliasTable.setWidget(0, 1, itemSolicitudUIData.getAlias());
		
		cppAliasReservaTable.getCellFormatter().setVisible(0, 0, false);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 1, false);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 2, false);
		cppAliasReservaTable.getCellFormatter().setVisible(0, 3, false);
		cppAliasReservaTable.getCellFormatter().setVisible(1, 0, false);
		cppAliasReservaTable.getCellFormatter().setVisible(1, 1, false);
		cppAliasReservaTable.setVisible(false);
		
		aliasTable.getCellFormatter().setVisible(0, 0, true);
		aliasTable.getCellFormatter().setVisible(0, 1, true);
		aliasTable.setVisible(true);
		
		roamingTable.getCellFormatter().setVisible(0, 0, false);
		roamingTable.getCellFormatter().setVisible(0, 1, false);
		roamingTable.getCellFormatter().setVisible(0, 2, false);
		roamingTable.setVisible(false);
		return this;
	}
	
	//MGR - #1039
	/**
	 * Este metodo se encarga de ocultar los campos que no deben ser visibles al seleccionar
	 * Grupo SS: Despacho tel anexo, Tipo Orden: Venta x tel, Lista Precio: Ar equipos vta solo equipo
	 */
	public ItemYPlanSolicitudUI ocultarCamposBBRed(){
		table.setVisible(false);
		aliasTable.setVisible(false);
		cppAliasReservaTable.setVisible(false);
		roamingTable.setVisible(false);
		itemSolicitudUIData.getLocalidad().setSelectedItem(ClientContext.getInstance().getVendedor().getLocalidad());
		itemSolicitudUIData.getTipoPlan().setSelectedItem(ItemSolicitudDialog.obtenerTipoPlanPorDefecto());
		itemSolicitudUIData.onChange(itemSolicitudUIData.getTipoPlan());
		return this;
	}
	
	/**
	 * Este metodo se encarga de mostar los campos que se ocultaron al seleccionar
	 * Grupo SS: Despacho tel anexo, Tipo Orden: Venta x tel, Lista Precio: Ar equipos vta solo equipo
	 */
	public ItemYPlanSolicitudUI mostrarCamposBBRed(){
		table.setVisible(true);
		aliasTable.setVisible(true);
		cppAliasReservaTable.setVisible(true);
		roamingTable.setVisible(true);
		return this;
	}

	public FlexTable getImeiSimRetiroEnSucursal() {
		return imeiSimRetiroEnSucursal;
	}
	
	
}
