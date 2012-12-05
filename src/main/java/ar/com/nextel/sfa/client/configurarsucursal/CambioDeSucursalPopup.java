package ar.com.nextel.sfa.client.configurarsucursal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.ConfigurarSucursalRPCService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.SucursalDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class CambioDeSucursalPopup extends NextelDialog {
	
	private SimplePanel cambioSucursalPanel; 
	private FlexTable cambioSucursalTable;
	private FlexTable botonesTable;
	private ListBox selectsucursales = new ListBox();
	private Label sucursales;
	private Anchor aceptar = new Anchor(Sfa.constant().aceptar());
	private Anchor cerrar = new Anchor(Sfa.constant().cerrar());
	public static Long idOpp;
	public static boolean fromMenu = true;
	
	public CambioDeSucursalPopup(String title) {
		super(title, false, true);
        init();	
	
		aceptar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				cambiarSucursalVendedor();
				
			}
		});		
	
		cerrar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				hide();
				cleanForm();
				History.newItem(UILoader.SOLO_MENU + "");
			}
		});
		
	}
	
	public void init() {
		cambioSucursalPanel = new SimplePanel();
		cambioSucursalPanel.setWidth("250");
		cambioSucursalPanel.setHeight("70");
		cambioSucursalTable = new FlexTable();
		botonesTable = new FlexTable();
		botonesTable.setWidth("100%");
		cambioSucursalTable.setWidth("100%");
		selectsucursales    = new ListBox();
		sucursales   = new Label(Sfa.constant().sucursal());
		
		inicializarCombo();
	
//		cambioSucursalTable.set(0, 0, "");
//		cambioSucursalTable.setWidget(0, 1, "");
		cambioSucursalTable.setWidget(0, 0, sucursales);
		cambioSucursalTable.setWidget(0, 1, selectsucursales);
		cambioSucursalPanel.add(cambioSucursalTable);
	
		
		botonesTable.setWidget(0,0,aceptar);
		botonesTable.setWidget(0,1,cerrar);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		addFooter(botonesTable);
		add(cambioSucursalPanel);

	
	}
	
	public void cleanForm() {
	
		selectsucursales.clear();
	}
	
	private void cambiarSucursalVendedor(){
		final VendedorDto vendedorDto = ClientContext.getInstance().getVendedor();
	
		if (!selectsucursales.getSelectedItemId().equals(vendedorDto.getIdSucursal().toString())) {
		ConfigurarSucursalRPCService.Util.getInstance().cambiarSucursal(new Long(selectsucursales.getSelectedItemId()), vendedorDto.getId(), new AsyncCallback<VendedorDto>(){

			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
					
			}


			public void onSuccess(VendedorDto result) {
				hide();
				cleanForm();
				WaitWindow.hide();
				MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
				Sfa.constant().MSG_SUCURSAL_GUARDADA_OK(), MessageDialog.getCloseCommand());
				History.newItem(UILoader.SOLO_MENU + "");
			}
			
	});
		}else{
			
			MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
				Sfa.constant().ERR_NO_SUCURSAL_SELECTED(), MessageDialog.getCloseCommand());
		}
	}
	
	
	public void inicializarCombo(){
		
         ConfigurarSucursalRPCService.Util.getInstance().getSucursales(new AsyncCallback<List<SucursalDto>>() {
			
			public void onSuccess(List<SucursalDto> result) {
			
				selectsucursales.clear();
				selectsucursales.addAllItems(setearPrimerElemtodeLaLista(result));
				WaitWindow.hide();
			}
			
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());			}
		});
		
	}
	
	public List<SucursalDto> setearPrimerElemtodeLaLista(List<SucursalDto> result){
		List<SucursalDto> nuevaLista= new ArrayList<SucursalDto>();	
	    final VendedorDto vendedorDto = ClientContext.getInstance().getVendedor();
	    Iterator<SucursalDto> iter = result.iterator();
	    while (iter.hasNext())
	    {
	    	SucursalDto obj = (SucursalDto) iter.next();
	    	int comparacion= vendedorDto.getIdSucursal().compareTo(obj.getId());
	    	if(comparacion==0){
	    		nuevaLista.add(0, obj);
	    		
	    	}else{
	          nuevaLista.add(obj);
	       }
	    }
	    return nuevaLista;
	}
}

