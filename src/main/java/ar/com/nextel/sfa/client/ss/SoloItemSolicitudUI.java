package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.TipoVendedorDto;

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
	private FlexTable activacionImei;
	private FlexTable activacionModelo;
	private FlexTable activacionSimSeriePin;
	private Grid precioCantidad;
	private Grid total;
	private ItemSolicitudUIData itemSolicitudData;
	private FlexTable listaPrecio;
	private FlexTable permanencia;
	private FlexTable permanenciaPrecioLista;
	private FlexTable terminoPago;
	private EditarSSUIController controller;
	public static final int LAYOUT_SIMPLE = 0;
	public static final int LAYOUT_ACTIVACION = 1;
	public static final int LAYOUT_CON_TOTAL = 2;
	public static final int LAYOUT_ACTIVACION_ONLINE = 3;
	public static final int LAYOUT_SIMPLE_PERMANENCIA = 4;
//	MGR*********
	public static final int LAYOUT_VENTA_SOLO_SIM = 5;
	private FlexTable imeiSimRetiroEnSucursal;
	private boolean visibleImeiSimRetiroSucursal;
	
//	MGR*****
	private FlexTable ventaSimIngSim;
	
	
	public SoloItemSolicitudUI(ItemSolicitudUIData itemSolicitudUIData) {
		mainPanel = new FlowPanel();
		itemSolicitudData = itemSolicitudUIData;
		initWidget(mainPanel);
		initGenericPanel();
	}

	private void initGenericPanel() {
		
		listaPrecio = new FlexTable();
		listaPrecio.addStyleName("layout");
		listaPrecio.getCellFormatter().setWidth(0, 0, "100px");
		listaPrecio.setWidget(0, 0, new InlineLabel(Sfa.constant().listaPrecio()));
		listaPrecio.setWidget(0, 1, itemSolicitudData.getListaPrecio());
		mainPanel.add(listaPrecio);
		
		//LF
//		Grid primerTabla = new Grid(1, 2);
//		primerTabla.addStyleName("layout");
//		primerTabla.getCellFormatter().setWidth(0, 0, "100px");
//		primerTabla.setWidget(0, 0, new InlineLabel(Sfa.constant().listaPrecio()));
//		primerTabla.setWidget(0, 1, itemSolicitudData.getListaPrecio());
//		mainPanel.add(primerTabla);

		activacionImei = new FlexTable();
		activacionImei.addStyleName("layout");
		activacionImei.getCellFormatter().setWidth(0, 0, "100px");
		activacionImei.getCellFormatter().setWidth(0, 1, "125px");
		activacionImei.setVisible(false);
		activacionImei.setHTML(0, 0, Sfa.constant().imeiReq());
		activacionImei.setWidget(0, 1, itemSolicitudData.getImei());
		activacionImei.setWidget(0, 2, itemSolicitudData.getVerificarImeiWrapper());
		activacionImei.getFlexCellFormatter().setColSpan(0, 1, 2);
		mainPanel.add(activacionImei);
		
		permanencia = new FlexTable();
		permanencia.setVisible(false);
		permanencia.getCellFormatter().setWidth(0, 0, "100px");
		permanencia.setWidget(0, 1, itemSolicitudData.getFullPrice());
		mainPanel.add(permanencia);

		permanenciaPrecioLista = new FlexTable();
		permanenciaPrecioLista.addStyleName("layout");
		permanenciaPrecioLista.setVisible(false);
		permanenciaPrecioLista.getCellFormatter().setWidth(0, 0, "100px");
		permanenciaPrecioLista.getCellFormatter().setWidth(0, 1, "125px");
		permanenciaPrecioLista.setHTML(0, 0, Sfa.constant().precioLista());
		permanenciaPrecioLista.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
		mainPanel.add(permanenciaPrecioLista);

		activacionModelo = new FlexTable();
		activacionModelo.addStyleName("layout");
		activacionModelo.getCellFormatter().setWidth(0, 0, "100px");
		activacionModelo.setVisible(false);
		activacionModelo.setHTML(0, 0, Sfa.constant().modeloEq());
		activacionModelo.setWidget(0, 1, itemSolicitudData.getModeloEq());
//		activacionModelo.getFlexCellFormatter().setColSpan(1, 1, 2);
		mainPanel.add(activacionModelo);
		
//		Grid segundaTabla = new Grid(2, 2);
//		segundaTabla.addStyleName("layout");
//		segundaTabla.getCellFormatter().setWidth(0, 0, "100px");
//		segundaTabla.setHTML(0, 0, Sfa.constant().item());
//		segundaTabla.setWidget(0, 1, itemSolicitudData.getItem());
//		segundaTabla.setHTML(1, 0, Sfa.constant().terminoPago());
//		segundaTabla.setWidget(1, 1, itemSolicitudData.getTerminoPago());
//		mainPanel.add(segundaTabla);
		
		Grid item = new Grid(1, 2);
		item.addStyleName("layout");
		item.getCellFormatter().setWidth(0, 0, "100px");
		item.setHTML(0, 0, Sfa.constant().item());
		item.setWidget(0, 1, itemSolicitudData.getItem());
		mainPanel.add(item);
		
		terminoPago = new FlexTable();
		terminoPago.addStyleName("layout");
		terminoPago.getCellFormatter().setWidth(0, 0, "100px");
		terminoPago.setHTML(0, 0, Sfa.constant().terminoPago());
		terminoPago.setWidget(0, 1, itemSolicitudData.getTerminoPago());
		mainPanel.add(terminoPago);

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
		activacionSimSeriePin.setHTML(0, 2, Sfa.constant().simReq());
		activacionSimSeriePin.setWidget(0, 3, itemSolicitudData.getSim());
		activacionSimSeriePin.setWidget(0, 4, itemSolicitudData.getVerificarSimWrapper());
		activacionSimSeriePin.setWidget(1, 0, itemSolicitudData.getSerieLabel());
		activacionSimSeriePin.setWidget(1, 1, itemSolicitudData.getSerie());
		activacionSimSeriePin.setWidget(1, 2, itemSolicitudData.getPinLabel());
		activacionSimSeriePin.setWidget(1, 3, itemSolicitudData.getPin());
		activacionSimSeriePin.getFlexCellFormatter().setColSpan(1, 3, 4);
		mainPanel.add(activacionSimSeriePin);
		
		imeiSimRetiroEnSucursal =  new FlexTable();
		
		imeiSimRetiroEnSucursal.addStyleName("layout");
		imeiSimRetiroEnSucursal.getCellFormatter().setWidth(0, 0, "100px");
		imeiSimRetiroEnSucursal.setHTML(0, 0, Sfa.constant().imeiReq());
		imeiSimRetiroEnSucursal.setWidget(0, 1,  itemSolicitudData.getImeiRetiroEnSucursal());
		imeiSimRetiroEnSucursal.setHTML(0, 2, Sfa.constant().simReq());
		imeiSimRetiroEnSucursal.setWidget(0, 3, itemSolicitudData.getSimRetiroEnSucursal());
		imeiSimRetiroEnSucursal.setVisible(false);
		mainPanel.add(imeiSimRetiroEnSucursal);
		
//		MGR*********
		ventaSimIngSim = new FlexTable();
		ventaSimIngSim.addStyleName("layout");
		ventaSimIngSim.getCellFormatter().setWidth(0, 0, "100px");
		ventaSimIngSim.getCellFormatter().setWidth(0, 1, "100px");
		ventaSimIngSim.setVisible(false);
		ventaSimIngSim.setHTML(0, 0, Sfa.constant().simReq());
		ventaSimIngSim.setWidget(0, 1, itemSolicitudData.getSim());
		ventaSimIngSim.setWidget(0, 2, itemSolicitudData.getVerificarSimWrapper());
		ventaSimIngSim.getFlexCellFormatter().setColSpan(0, 1, 2);
		mainPanel.add(ventaSimIngSim);
		
		visibleImeiSimRetiroSucursal = false;
	}

	public SoloItemSolicitudUI setLayout(int layout, EditarSSUIController controller) {
		boolean retirar = false;
		boolean esEquipoAccesorio= false;
		visibleImeiSimRetiroSucursal = false;
		if (controller.getEditarSSUIData().isEquiposAccesorios()){
			 retirar= controller.getEditarSSUIData().getSolicitudServicio().getRetiraEnSucursal();
			 esEquipoAccesorio= true;
		}
		//solo va a estar visible este panel en el caso de que este chequeado el retiro en sucursal
		this.visibleImeiSimRetiroSucursal = (retirar && esEquipoAccesorio);
		
		//controller.getEditarSSUIData().getRetiraEnSucursal().setEnabled(false);
		return this.setLayout(layout);	
	}
	
	
	public SoloItemSolicitudUI setLayout(int layout) {
	
		total.setVisible(false);
		imeiSimRetiroEnSucursal.setVisible(false);
	
        
		switch (layout) {
		case LAYOUT_ACTIVACION:
			activacionImei.setVisible(true);
			activacionModelo.setVisible(true);
			activacionSimSeriePin.setVisible(true);
			activacionSimSeriePin.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			mostrarActivacionPrecioListaYPin(true);
			itemSolicitudData.resetIMEICheck();
			precioCantidad.setVisible(false);
			permanencia.setVisible(false);
			permanenciaPrecioLista.setVisible(false);
			break;

		case LAYOUT_CON_TOTAL:
			total.setVisible(true);
			permanencia.setVisible(false);


		case LAYOUT_SIMPLE:
			precioCantidad.setVisible(true);
			activacionImei.setVisible(false);
			activacionModelo.setVisible(false);
			activacionSimSeriePin.setVisible(false);
			precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			permanencia.setVisible(false);
			permanenciaPrecioLista.setVisible(false);
			imeiSimRetiroEnSucursal.setVisible(visibleImeiSimRetiroSucursal);
			break;
			
		case LAYOUT_SIMPLE_PERMANENCIA:
			precioCantidad.setVisible(true);
			activacionImei.setVisible(false);
			activacionModelo.setVisible(false);
			activacionSimSeriePin.setVisible(false);
			precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			permanencia.setVisible(true);
			permanenciaPrecioLista.setVisible(false);
			imeiSimRetiroEnSucursal.setVisible(visibleImeiSimRetiroSucursal);
			break;

		case LAYOUT_ACTIVACION_ONLINE:
			listaPrecio.setVisible(false);
			activacionImei.setVisible(true);
			activacionModelo.setVisible(true);
			permanenciaPrecioLista.setVisible(true);
			permanenciaPrecioLista.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			activacionSimSeriePin.setVisible(true);
			mostrarActivacionPrecioListaYPin(false);
			itemSolicitudData.resetIMEICheck();
			precioCantidad.setVisible(false);
			terminoPago.setVisible(false);
			permanencia.setVisible(true);
			break;
		
		case LAYOUT_VENTA_SOLO_SIM:
			TipoVendedorDto tipoVen = ClientContext.getInstance().getVendedor().getTipoVendedor();
			if(tipoVen.isIngresaSIM()) { //#6678
				ventaSimIngSim.setVisible(true);
				itemSolicitudData.getCantidad().setText("1");
				itemSolicitudData.getCantidad().setEnabled(false);
				itemSolicitudData.getCantidad().setReadOnly(true);
			}
			else
				ventaSimIngSim.setVisible(false);
			activacionImei.setVisible(false);
			activacionModelo.setVisible(false);
			activacionSimSeriePin.setVisible(false);
			precioCantidad.setVisible(true);
			precioCantidad.setWidget(0, 1, itemSolicitudData.getPrecioListaItem());
			break;
			
		default:
			break;
		}
		return this;
	}

	public void mostrarActivacionPrecioListaYPin(boolean visible) {
		activacionSimSeriePin.getCellFormatter().setVisible(0, 0, visible);
		activacionSimSeriePin.getCellFormatter().setVisible(0, 1, visible);
		activacionSimSeriePin.getCellFormatter().setVisible(1, 0, visible);
		activacionSimSeriePin.getCellFormatter().setVisible(1, 1, visible);
		activacionSimSeriePin.getCellFormatter().setVisible(1, 2, visible);
		activacionSimSeriePin.getCellFormatter().setVisible(1, 3, visible);
		if(!visible) {
			activacionSimSeriePin.getCellFormatter().setWidth(0, 2, "100");
		} else {
			activacionSimSeriePin.getCellFormatter().setWidth(0, 2, "50");
		}
	}
}
