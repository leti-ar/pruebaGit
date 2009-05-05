package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;

public class ItemSolicitudGenericPanelUI extends Composite {
	
	private FlowPanel mainPanel;
	private ItemSolicitudUIData itemSolicitudData;
	
	private static ItemSolicitudGenericPanelUI itemSolicitudGenericPanelUI = null;
	
	public static ItemSolicitudGenericPanelUI getInstance (){
		if(itemSolicitudGenericPanelUI == null){
			itemSolicitudGenericPanelUI = new ItemSolicitudGenericPanelUI();
		}
		return itemSolicitudGenericPanelUI;
	}
	
	public ItemSolicitudGenericPanelUI(){
		mainPanel = new FlowPanel();
		itemSolicitudData = new ItemSolicitudUIData();
		initWidget(mainPanel);
		initGenericPanel();
	}

	private void initGenericPanel() {
		Grid primeraTabla = new Grid(3,2);
		primeraTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().listaPrecio()));
		primeraTabla.setWidget(0, 1, itemSolicitudData.getListaPrecio());
		primeraTabla.setWidget(1, 0, new InlineLabel(Sfa.constant().item()));
		primeraTabla.setWidget(1, 1, itemSolicitudData.getItem());
		primeraTabla.setWidget(2, 0, new InlineLabel(Sfa.constant().terminoPago()));
		primeraTabla.setWidget(2, 1, itemSolicitudData.getTerminoPago());
		mainPanel.add(primeraTabla);
		Grid segundaTabla = new Grid(1,4);
		segundaTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().precioLista()));
		segundaTabla.setWidget(0, 1, itemSolicitudData.getPrecioLista());
		segundaTabla.setWidget(0, 2, new InlineLabel(Sfa.constant().cantidad()));
		segundaTabla.setWidget(0, 3, itemSolicitudData.getCantidad());
		mainPanel.add(segundaTabla);
	}
	
}
