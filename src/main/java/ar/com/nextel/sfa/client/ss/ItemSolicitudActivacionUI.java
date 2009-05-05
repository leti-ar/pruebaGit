package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.InlineLabel;

public class ItemSolicitudActivacionUI extends Composite {
	
	private FlowPanel mainPanel;
	private ItemSolicitudUIData itemSolicitudUIData;
	
	private static ItemSolicitudActivacionUI itemSolicitudActivacionUI;
	
	public static ItemSolicitudActivacionUI getInstance(){
		if(itemSolicitudActivacionUI == null){
			itemSolicitudActivacionUI = new ItemSolicitudActivacionUI();
		}
		return itemSolicitudActivacionUI;
	}
	
	public ItemSolicitudActivacionUI(){
		itemSolicitudUIData = new ItemSolicitudUIData();
		mainPanel = new FlowPanel();
		initWidget(mainPanel);
		initItemSolicitudActivacionUI();
	}

	private void initItemSolicitudActivacionUI() {
		Grid primeraTabla = new Grid(5,2);
		primeraTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().listaPrecio()));
		primeraTabla.setWidget(0, 1, itemSolicitudUIData.getListaPrecio());
		primeraTabla.setWidget(1, 0,  new InlineLabel(Sfa.constant().imei()));
		primeraTabla.setWidget(1, 1, itemSolicitudUIData.getImei());
		primeraTabla.setWidget(2, 0, new InlineLabel(Sfa.constant().modeloEq()));
		primeraTabla.setWidget(2, 1, itemSolicitudUIData.getModeloEq());
		primeraTabla.setWidget(3, 0, new InlineLabel(Sfa.constant().item()));
		primeraTabla.setWidget(3, 1, itemSolicitudUIData.getItem());
		primeraTabla.setWidget(4, 0, new InlineLabel(Sfa.constant().terminoPago()));
		primeraTabla.setWidget(4, 1, itemSolicitudUIData.getTerminoPago());
		mainPanel.add(primeraTabla);
		Grid segundaTabla = new Grid(2,4);
		segundaTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().precioLista()));
		segundaTabla.setWidget(0, 1, itemSolicitudUIData.getPrecioLista());
		segundaTabla.setWidget(0, 2, new InlineLabel(Sfa.constant().sim()));
		segundaTabla.setWidget(0, 3, itemSolicitudUIData.getSim());
		segundaTabla.setWidget(1, 0, new InlineLabel(Sfa.constant().serie()));
		segundaTabla.setWidget(1, 1, itemSolicitudUIData.getSerie());
		segundaTabla.setWidget(1, 2, new InlineLabel(Sfa.constant().pin()));
		segundaTabla.setWidget(1, 3, itemSolicitudUIData.getPin());
		mainPanel.add(segundaTabla);
		Grid terceraTabla = new Grid(4,2);
		terceraTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().tipoPlan()));
		terceraTabla.setWidget(0, 1, itemSolicitudUIData.getTipoPlan());
		terceraTabla.setWidget(1, 0, new InlineLabel(Sfa.constant().plan()));
		terceraTabla.setWidget(1, 1, itemSolicitudUIData.getPlan());
		terceraTabla.setWidget(2, 0, new InlineLabel(Sfa.constant().precioLista()));
		terceraTabla.setWidget(2, 1, itemSolicitudUIData.getPrecioLista());
		terceraTabla.setWidget(3, 0, new InlineLabel(Sfa.constant().localidad()));
		terceraTabla.setWidget(3, 1, itemSolicitudUIData.getLocalidad());
		mainPanel.add(terceraTabla);
		Grid cuartaTabla = new Grid(2,4);
		cuartaTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().cppMpp()));
		cuartaTabla.setWidget(0, 1, itemSolicitudUIData.getCcp());
		cuartaTabla.setWidget(0, 2, new InlineLabel(Sfa.constant().alias()));
		cuartaTabla.setWidget(0, 3, itemSolicitudUIData.getAlias());
		cuartaTabla.setWidget(1, 0, new InlineLabel(Sfa.constant().reservarN()));
		cuartaTabla.setWidget(1, 1, itemSolicitudUIData.getReservarHidden());
		cuartaTabla.setWidget(1, 2, itemSolicitudUIData.getReservar());
		cuartaTabla.setWidget(1, 3, itemSolicitudUIData.getConfirmarReserva());
		mainPanel.add(cuartaTabla);
		Grid quintaTabla = new Grid(1,6);
		quintaTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().ddn()));
		quintaTabla.setWidget(0, 1, itemSolicitudUIData.getDdn());
		quintaTabla.setWidget(0, 2, new InlineLabel(Sfa.constant().ddi()));
		quintaTabla.setWidget(0, 3, itemSolicitudUIData.getDdi());
		quintaTabla.setWidget(0, 4, new InlineLabel(Sfa.constant().roaming()));
		quintaTabla.setWidget(0, 5, itemSolicitudUIData.getRoaming());
		mainPanel.add(quintaTabla);
	}
	
}
