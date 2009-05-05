package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;

public class ItemSolicitudAMBAUI extends Composite {
	
	private FlowPanel mainPanel;
	private ItemSolicitudUIData itemSolicitudUIData;
	
	private static ItemSolicitudAMBAUI itemSolicitudAMBAUI = null;
	
	public static ItemSolicitudAMBAUI getInstance(){
		if(itemSolicitudAMBAUI == null){
			itemSolicitudAMBAUI = new ItemSolicitudAMBAUI();
		}
		return itemSolicitudAMBAUI;
	}
	
	public ItemSolicitudAMBAUI(){
		mainPanel = new FlowPanel();
		itemSolicitudUIData = new ItemSolicitudUIData();
		initWidget(mainPanel);
		initItemSolicitudAMBAUI();
	}
	
	private void initItemSolicitudAMBAUI() {
		mainPanel.add(ItemSolicitudGenericPanelUI.getInstance());
		Grid table = new Grid(4,2);
		table.setWidget(0, 0, new InlineLabel(Sfa.constant().tipoPlan()));
		table.setWidget(0, 1, itemSolicitudUIData.getTipoPlan());
		table.setWidget(1, 0,  new InlineLabel(Sfa.constant().plan()));
		table.setWidget(1, 1, itemSolicitudUIData.getPlan());
		table.setWidget(2, 0,  new InlineLabel(Sfa.constant().precioLista()));
		table.setWidget(2, 1, itemSolicitudUIData.getPrecioLista());
		table.setWidget(3, 0,  new InlineLabel(Sfa.constant().localidad()));
		table.setWidget(3, 1, itemSolicitudUIData.getLocalidad());
		mainPanel.add(table);
		Grid segundaTabla = new Grid(2,4);
		segundaTabla.setWidget(0, 0,  new InlineLabel(Sfa.constant().cppMpp()));
		segundaTabla.setWidget(0, 1, itemSolicitudUIData.getCcp());
		segundaTabla.setWidget(0, 2,  new InlineLabel(Sfa.constant().alias()));
		segundaTabla.setWidget(0, 3, itemSolicitudUIData.getAlias());
		segundaTabla.setWidget(1, 0,  new InlineLabel(Sfa.constant().reservarN()));
		segundaTabla.setWidget(1, 1, itemSolicitudUIData.getReservarHidden());
		segundaTabla.setWidget(1, 2, itemSolicitudUIData.getReservar());
		segundaTabla.setWidget(1, 2, itemSolicitudUIData.getConfirmarReserva());
		mainPanel.add(segundaTabla);
		Grid terceraTabla = new Grid(1,6);
		terceraTabla.setWidget(0, 0,  new InlineLabel(Sfa.constant().ddn()));
		terceraTabla.setWidget(0, 1, itemSolicitudUIData.getDdn());
		terceraTabla.setWidget(0, 2,  new InlineLabel(Sfa.constant().ddi()));
		terceraTabla.setWidget(0, 3, itemSolicitudUIData.getDdi());
		terceraTabla.setWidget(0, 4,  new InlineLabel(Sfa.constant().roaming()));
		terceraTabla.setWidget(0, 5, itemSolicitudUIData.getRoaming());
		mainPanel.add(terceraTabla);
	}
	
}
