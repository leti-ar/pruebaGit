package ar.com.nextel.sfa.client.stock;

import ar.com.nextel.sfa.client.StockRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.ListaPreciosDto;
import ar.com.nextel.sfa.client.dto.TipoSolicitudDto;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.util.CargaDeCombos;
import ar.com.nextel.sfa.client.widget.EventWrapper;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class StockUIDlg extends NextelDialog {

	private SimplePanel buscadorDocumentoPanel;
	private FlexTable buscadorDocumentoTable;
	private FlexTable botonesTable;
	private ListBox tipoOrdenLst = new ListBox();
	private ListBox listaPrecioLst = new ListBox();

	private ListBox cantItemsLst = new ListBox();

	private Label tipoOrden;

	private Label listaPrecio;
	private Label cantItems;
	private Anchor aceptar = new Anchor(Sfa.constant().aceptar());
	private Anchor cerrar = new Anchor(Sfa.constant().cerrar());
	public static Long idOpp;
	public static boolean fromMenu = true;
	public static final String LUGAR_DE_LLAMADA_DE_VALIDACION_STOCK= "menu";
	public StockUIDlg(String title) {
		super(title, false, true);
		init();
		aceptar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				Long idItem = null;
				final VendedorDto vendedorDto = ClientContext.getInstance().getVendedor();

				if (cantItemsLst.getSelectedItem() != null) {
					
					idItem = Long.valueOf(cantItemsLst.getSelectedItemId());
				}
				
				if (cantItemsLst.getSelectedItem() != null){
				StockRpcService.Util.getInstance().validarStock(idItem, vendedorDto.getId(),LUGAR_DE_LLAMADA_DE_VALIDACION_STOCK,
						new DefaultWaitCallback<String>() {

							@Override
							public void success(String result) {
//								hide();
								MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
										result, MessageDialog.getCloseCommand());
								History.newItem(UILoader.SOLO_MENU + "");
							}
						});
				}else{
					MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO,
							Sfa.constant().ERROR_DEBE_SEL_ITEM(), MessageDialog.getCloseCommand());
				}
			}
		});

		cerrar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				hide();
				History.newItem(UILoader.SOLO_MENU + "");
			}
		});
	}

	public void init() {
		buscadorDocumentoPanel = new SimplePanel();
		buscadorDocumentoPanel.setWidth("320");
		buscadorDocumentoPanel.setHeight("90");

		buscadorDocumentoTable = new FlexTable();
		botonesTable = new FlexTable();
		botonesTable.setWidth("100%");

		buscadorDocumentoTable.setWidth("100%");
		// numeroDocTextBox = new TextBox();
		// numeroDocTextBox.setMaxLength(13);
		tipoOrden = new Label(Sfa.constant().tipoOrden().trim());
		listaPrecio = new Label(Sfa.constant().listaPrecio().trim());
		cantItems = new Label(Sfa.constant().cantidadItems().trim());
		tipoOrden.addStyleName("req");
		listaPrecio.addStyleName("req");
		cantItems.addStyleName("req");

		buscadorDocumentoTable.setWidget(0, 0, tipoOrden);
		buscadorDocumentoTable.setWidget(0, 1, tipoOrdenLst);
		buscadorDocumentoTable.setWidget(1, 0, listaPrecio);
		buscadorDocumentoTable.setWidget(1, 1, listaPrecioLst);
		buscadorDocumentoTable.setWidget(2, 0, cantItems);
		buscadorDocumentoTable.setWidget(2, 1, cantItemsLst);

		EventWrapper ew = new EventWrapper() {
			public void doEnter() {
				// reservaCreacionCuenta();
			}
		};
		ew.add(buscadorDocumentoTable);
		buscadorDocumentoPanel.add(ew);

		botonesTable.setWidget(0, 0, aceptar);
		botonesTable.setWidget(0, 1, cerrar);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		botonesTable.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_LEFT);
		addFooter(botonesTable);
		add(buscadorDocumentoPanel);

		cerrar.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_BUTTON_CERRAR_ID);
		aceptar.ensureDebugId(DebugConstants.AGREGAR_CUENTAS_POPUP_BUTTON_ACEPTAR_ID);
		
		initCombos();

		tipoOrdenLst.setWidth("220");
		listaPrecioLst.setWidth("220");
		cantItemsLst.setWidth("220");
		listaPrecioLst.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				final ListaPreciosDto lstPrecio = (ListaPreciosDto) getListaPrecioLst().getSelectedItem();
				getCantItemsLst().clear();
				getCantItemsLst().addAllItems(lstPrecio.getItemsListaPrecioVisibles());
			}
		});

		tipoOrdenLst.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				final TipoSolicitudDto tipoSolicitudDTO = (TipoSolicitudDto) getTipoOrdenLst()
						.getSelectedItem();
				getCantItemsLst().clear();
				CargaDeCombos.cargarListaDePrecios(tipoSolicitudDTO,
						listaPrecioLst, cantItemsLst);
			}
		});

	}

	public void initCombos(){
		CargaDeCombos.cargarComboTipoOrden(tipoOrdenLst, listaPrecioLst,
				cantItemsLst);
	}
	
	public void clearCombos(){
		getTipoOrdenLst().clear();
		getListaPrecioLst().clear();
		getCantItemsLst().clear();
	}

	public ListBox getTipoOrdenLst() {
		return tipoOrdenLst;
	}

	public ListBox getListaPrecioLst() {
		return listaPrecioLst;
	}
	
	public ListBox getCantItemsLst() {
		return cantItemsLst;
	}
}
