package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.domicilio.DomicilioUI;
import ar.com.nextel.sfa.client.dto.DescuentoDto;
import ar.com.nextel.sfa.client.dto.DescuentoLineaDto;
import ar.com.nextel.sfa.client.dto.DescuentoTotalDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudPortabilidadDto;
import ar.com.nextel.sfa.client.dto.TipoDescuentoDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.PortabilidadInitializer;
import ar.com.nextel.sfa.client.util.PortabilidadUtil;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DatosSSUI extends Composite implements ClickHandler {

	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private Grid nnsLayout;
	private Grid controlLayout;
	private Grid historicoLayout;
	private Grid domicilioLayout;
	private FlexTable detalleSS;
	private ServiciosAdicionalesTable serviciosAdicionales;
	private EditarSSUIController controller;
	private Button crearDomicilio;
	private Button crearLinea;
	private ItemSolicitudDialog itemSolicitudDialog;
	private NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	private int selectedDetalleRow = 0;
	private HTML editarDomicioFacturacion;
	private HTML borrarDomicioFacturacion;
	private HTML editarDomicioEntrega;
	private HTML borrarDomicioEntrega;
	private DomiciliosCuentaDto domicilioAEditar = null;
	private TextBox precioVentaPlan;
	private List listaDomicilios = new ArrayList<DomiciliosCuentaDto>();
	private DescuentoDialog descuentoDialog;
	private Long idLinea;
	private LineaSolicitudServicioDto lineaSeleccionada;
	private Long lineaModificada = new Long(0);
	private DescuentoDto descuento;
	private List<DescuentoDto> descuentos = new ArrayList<DescuentoDto>();
	private List<TipoDescuentoDto> descuentosAplicados = new ArrayList<TipoDescuentoDto>();
	private List<TipoDescuentoDto> descuentosAAplicar = new ArrayList<TipoDescuentoDto>();
	private List<TipoDescuentoSeleccionado> descuentoSeleccionados = new ArrayList<TipoDescuentoSeleccionado>();
	private boolean sacarTipoDescuento;
	private boolean descuentoTotalAplicado = false;
	private PortabilidadInitializer portabilidadInitializer;
	private PortabilidadUtil portabilidadUtil;
	private static final String ESTADO_ENCARGA_SS= "ESTADO_ENCARGA_SS";
	private static final String SELECTED_ROW = "selectedRow";

	private ChangeHandler handlerNroSS;
	
	public DatosSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
		mainpanel.add(getNssLayout());
		//Estefania Iguacek - Comentado para salir solo con cierre - CU#6
		//mainpanel.add(getControlLayout());
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			mainpanel.add(getHistoricoVentasPanel());
//		}
		mainpanel.add(getDomicilioPanel());
		mainpanel.add(getDetallePanel());
		
		ChangeHandler handlerNroSS = new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				asignarNroSSPortabilidad();
			}
		}; 
		
		editarSSUIData.getNss().addChangeHandler(handlerNroSS);
	}

	/**
	 * Portabilidad
	 */
	private void asignarNroSSPortabilidad(){
		if(portabilidadUtil == null) portabilidadUtil = new PortabilidadUtil();
		portabilidadUtil.generarNroSS(controller.getEditarSSUIData().getSolicitudServicio());
	}

	/**
	 * portabilidad
	 * TODO
	 */
	public void setPortabilidadInitializer(PortabilidadInitializer unaPortabilidadInitializer){
		portabilidadInitializer = unaPortabilidadInitializer;
	}
	
	private Widget getNssLayout() {
		//nnsLayout = new Grid(1, 6);
		//MGR - #1027
		nnsLayout = new Grid(1, 12);
		nnsLayout.addStyleName("layout");
		refreshNssLayout();
		return nnsLayout;
	}


//Estefania Iguacel - Comentado para salir solo con cierre - CU#6
//	private Widget getControlLayout() {
//	
//		controlLayout = new Grid(1,4);
//		controlLayout.addStyleName("layout");
//		refreshNssLayout();
//		refreshControlLayout();
//		return controlLayout;
//	}
//	
//	
//	private void refreshControlLayout(){
//		
//		
//		controlLayout.setHTML(0,0, Sfa.constant().estado());
//		controlLayout.setWidget(0,1, editarSSUIData.getEstado());
//		
//	//	if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_ESTADO.getValue())){
//		controlLayout.setHTML(0,2, Sfa.constant().control());
//		controlLayout.setWidget(0,3, editarSSUIData.getControl());
//	//	}
//	}
//	
	
	private void refreshNssLayout() {
		lineaModificada = new Long(0);
		//MGR - #1050
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias == null){
			throw new RuntimeException("Error al acceder a las instancias conocidas.");
		}
		
		nnsLayout.setHTML(0, 0, Sfa.constant().nssReq());
		nnsLayout.setWidget(0, 1, editarSSUIData.getNss());
		if (editarSSUIData.getGrupoSolicitud() == null
				|| !instancias.get(GrupoSolicitudDto.ID_CDW).equals(editarSSUIData.getGrupoSolicitud().getId())) {
			nnsLayout.setHTML(0, 2, Sfa.constant().nflota());
			nnsLayout.setWidget(0, 3, editarSSUIData.getNflota());
			nnsLayout.setHTML(0, 4, Sfa.constant().origenReq());
			nnsLayout.setWidget(0, 5, editarSSUIData.getOrigen());
		
		} else {
			nnsLayout.setHTML(0, 2, Sfa.constant().origenReq());
			nnsLayout.setWidget(0, 3, editarSSUIData.getOrigen());
			nnsLayout.clearCell(0, 4);
			nnsLayout.clearCell(0, 5);
		}
		
		if (editarSSUIData.getGrupoSolicitud()!= null){
		if (!editarSSUIData.isCDW() && !editarSSUIData.isMDS()&& !editarSSUIData.isTRANSFERENCIA()) {
			nnsLayout.setHTML(0, 6, Sfa.constant().retiraEnSucursal());
			nnsLayout.setWidget(0, 7, editarSSUIData.getRetiraEnSucursal());
		}
		}

		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			nnsLayout.setHTML(0, 8, Sfa.constant().vendedorReq());
			nnsLayout.setWidget(0, 9, editarSSUIData.getVendedor());
		}
		
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			nnsLayout.setHTML(0, 10, Sfa.constant().sucOrigenReq());
			nnsLayout.setWidget(0, 11, editarSSUIData.getSucursalOrigen());
		}
		
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
//			nnsLayout.setHTML(0, 6, "Descuento Total:");
//			nnsLayout.setWidget(0, 7, editarSSUIData.getDescuentoTotal());
//			nnsLayout.setWidget(0, 8, editarSSUIData.getTildeVerde());
//			editarSSUIData.getTildeVerde().addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent clickEvent) {
//					if (editarSSUIData.getTildeVerde().isEnabled()) {
//						descuentoTotalAplicado = true;
//						List<LineaSolicitudServicioDto> lineas = editarSSUIData.getLineasSolicitudServicio();
//						for (Iterator<LineaSolicitudServicioDto> iterator = lineas.iterator(); iterator.hasNext();) {
//							LineaSolicitudServicioDto linea = (LineaSolicitudServicioDto) iterator.next();
//							if (!linea.getPrecioConDescuento().equals(0.0)) {
//								SolicitudRpcService.Util.getInstance().getDescuentosTotales(linea.getId(), new DefaultWaitCallback<DescuentoTotalDto>() {
//									@Override
//									public void success(DescuentoTotalDto result) {
//										agregarDescuentoTotal(result);
//									}
//								});
//							}
//						}
//					}
//					editarSSUIData.deshabilitarDescuentoTotal();
//				}
//			});
//		} else {
//			nnsLayout.clearCell(0, 6);
//			nnsLayout.clearCell(0, 7);
//			nnsLayout.clearCell(0, 8);
//		}
			
		if(editarSSUIData.getGrupoSolicitud() != null &&
				instancias.get(GrupoSolicitudDto.ID_FAC_MENSUAL).equals(editarSSUIData.getGrupoSolicitud().getId())){
			nnsLayout.setHTML(0, 10, Sfa.constant().ordenCompraReq());
			nnsLayout.setWidget(0, 11, editarSSUIData.getOrdenCompra());
		} else {
			nnsLayout.clearCell(0, 10);
			nnsLayout.clearCell(0, 11);
		}
	

	}
	
	private Widget getHistoricoVentasPanel() {
		TitledPanel historico = new TitledPanel("Histórico de Ventas");
		historicoLayout = new Grid(1, 8);
		historicoLayout.addStyleName("layout");
		historicoLayout.setHTML(0, 0, Sfa.constant().cantidadEquipos());
		historicoLayout.setWidget(0, 1, editarSSUIData.getCantidadEquipos());
		historicoLayout.setHTML(0, 2, Sfa.constant().fechaFirma());
		historicoLayout.setWidget(0, 3, editarSSUIData.getFechaFirma());
		historicoLayout.setHTML(0, 4, Sfa.constant().estadoReq());
		historicoLayout.setWidget(0, 5, editarSSUIData.getEstadoH());
		historicoLayout.setHTML(0, 6, Sfa.constant().fechaEstado());
		historicoLayout.setWidget(0, 7, editarSSUIData.getFechaEstado());
		historico.add(historicoLayout);
		return historico;
	}
	
	private Widget getDomicilioPanel() {
		TitledPanel domicilio = new TitledPanel("Domicilio");

		crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.ensureDebugId("EditarSS-Datos-CrearDomicilio");
		crearDomicilio.addClickHandler(this);
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("h20");
		domicilio.add(crearDomicilioWrapper);
		crearDomicilio.setVisible(controller.isEditable());

		editarDomicioFacturacion = IconFactory.lapiz();
		borrarDomicioFacturacion = IconFactory.cancel();
		editarDomicioEntrega = IconFactory.lapiz();
		borrarDomicioEntrega = IconFactory.cancel();
		editarDomicioFacturacion.addClickHandler(this);
		borrarDomicioFacturacion.addClickHandler(this);
		editarDomicioEntrega.addClickHandler(this);
		borrarDomicioEntrega.addClickHandler(this);
		editarDomicioFacturacion.setVisible(controller.isEditable());
		borrarDomicioFacturacion.setVisible(controller.isEditable());
		editarDomicioEntrega.setVisible(controller.isEditable());
		borrarDomicioEntrega.setVisible(controller.isEditable());

		domicilioLayout = new Grid(3, 4);
		domicilioLayout.addStyleName("layout");
		domicilioLayout.getColumnFormatter().setWidth(1, "500px");
		refreshDomicilioLayout();
		domicilio.add(domicilioLayout);
		return domicilio;
	}

	private void refreshDomicilioLayout() {
		//MGR - #1050
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		if(instancias == null){
			throw new RuntimeException("Error al acceder a las instancias conocidas.");
		}
		
		if (editarSSUIData.getGrupoSolicitud() == null
				|| !instancias.get(GrupoSolicitudDto.ID_CDW).equals(editarSSUIData.getGrupoSolicitud().getId())) {
			domicilioLayout.setHTML(0, 0, Sfa.constant().entregaReq());
			domicilioLayout.setWidget(0, 1, editarSSUIData.getEntrega());
			domicilioLayout.setWidget(0, 2, editarDomicioEntrega);
			domicilioLayout.setWidget(0, 3, borrarDomicioEntrega);
			domicilioLayout.setHTML(1, 0, Sfa.constant().facturacionReq());
			domicilioLayout.setWidget(1, 1, editarSSUIData.getFacturacion());
			domicilioLayout.setWidget(1, 2, editarDomicioFacturacion);
			domicilioLayout.setWidget(1, 3, borrarDomicioFacturacion);
			domicilioLayout.setHTML(2, 0, Sfa.constant().aclaracion());
			domicilioLayout.setWidget(2, 1, editarSSUIData.getAclaracion());
		} else {
			domicilioLayout.setHTML(0, 0, Sfa.constant().facturacionReq());
			domicilioLayout.setWidget(0, 1, editarSSUIData.getFacturacion());
			domicilioLayout.setWidget(0, 2, editarDomicioFacturacion);
			domicilioLayout.setWidget(0, 3, borrarDomicioFacturacion);
			domicilioLayout.setHTML(1, 0, Sfa.constant().emailReq());
			domicilioLayout.setWidget(1, 1, editarSSUIData.getEmail());
			domicilioLayout.clearCell(1, 2);
			domicilioLayout.clearCell(1, 3);
			domicilioLayout.clearCell(2, 0);
			domicilioLayout.clearCell(2, 1);
		}
	}

	private Widget getDetallePanel() {
		TitledPanel detalle = new TitledPanel("Detalle");

		crearLinea = new Button("Crear nuevo");
		crearLinea.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		crearLinea.addClickHandler(this);
		crearLinea.addStyleName("crearLineaButton");
		crearLinea.setVisible(controller.isEditable());
		SimplePanel crearLineaWrapper = new SimplePanel();
		crearLineaWrapper.add(crearLinea);
		crearLineaWrapper.addStyleName("h20");
		detalle.add(crearLineaWrapper);
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("resumenSSTableWrapper mlr5");
		detalleSS = new FlexTable();
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
			String[] titlesDetalle = tableConDescuento();	
			for (int i = 0; i < titlesDetalle.length; i++) {
				detalleSS.setHTML(0, i, titlesDetalle[i]);
			}
		} else {
			String[] titlesDetalle = tableSinDescuento();
			for (int i = 0; i < titlesDetalle.length; i++) {
				detalleSS.setHTML(0, i, titlesDetalle[i]);
			}
		}
		detalleSS.setCellPadding(0);
		detalleSS.setCellSpacing(0);
		detalleSS.ensureDebugId(DebugConstants.EDITAR_SOLICITUD_DATOS_DETTALE_TABLE);
		detalleSS.addStyleName("dataTable");
		detalleSS.setWidth("98%");
		detalleSS.getRowFormatter().addStyleName(0, "header");
		detalleSS.addClickHandler(this);
		wrapper.setWidget(detalleSS);
		detalle.add(wrapper);

		Label serviciosAdicionalesLabel = new Label("Servicios Adicionales");
		serviciosAdicionalesLabel.addStyleName("mt5 mb5");
		detalle.add(serviciosAdicionalesLabel);

		serviciosAdicionales = new ServiciosAdicionalesTable(controller);
		serviciosAdicionales.getTable().addClickHandler(this);
		detalle.add(serviciosAdicionales);

		return detalle;
	}
	
	public String[] tableConDescuento(){
		String[] titlesDetalleEditable = {  Sfa.constant().whiteSpace(), Sfa.constant().whiteSpace(), Sfa.constant().whiteSpace(), 
				Sfa.constant().whiteSpace(), "Item", "Pcio Vta.", "Precio con Desc.", "Alias", "Plan", 
				"Pcio Vta. Plan", "Localidad", "Nº Reserva", "Tipo SS", "Cant.", "DDN", "DDI", "Roaming",Sfa.constant().portabilidad() };
		String[] titlesDetalle = { "Item", "Pcio Vta.", "Precio con Desc.", "Alias", "Plan", 
				"Pcio Vta. Plan", "Localidad", "Nº Reserva", "Tipo SS", "Cant.", "DDN", "DDI", "Roaming",Sfa.constant().portabilidad() };		
		if(controller.isEditable()) 
			return titlesDetalleEditable;
		else
			return titlesDetalle;
	}
	
	public String[] tableSinDescuento() {
		String[] titlesDetalleEditable = {  Sfa.constant().whiteSpace(), Sfa.constant().whiteSpace(), Sfa.constant().whiteSpace(), "Item",
				"Pcio Vta.", "Alias", "Plan", "Pcio Vta. Plan", "Localidad", "Nº Reserva", "Tipo SS",
				"Cant.", "DDN", "DDI", "Roaming",Sfa.constant().portabilidad() };
		String[] titlesDetalle = { "Item", "Pcio Vta.", "Alias", "Plan", "Pcio Vta. Plan", "Localidad", "Nº Reserva",
				"Tipo SS",	"Cant.", "DDN", "DDI", "Roaming",Sfa.constant().portabilidad() };		
		if(controller.isEditable()) 
			return titlesDetalleEditable;
		else
			return titlesDetalle;		
	}

	public void onClick(ClickEvent clickEvent) {
		Widget sender = (Widget) clickEvent.getSource();
		if(controller.isEditable()) {
			if (sender == crearLinea) {
				openItemSolicitudDialog(new LineaSolicitudServicioDto());
			} else if (sender == crearDomicilio || sender == editarDomicioFacturacion
					|| sender == editarDomicioEntrega) {
				onClickEdicionDomicilios(sender);
			} else if (sender == borrarDomicioFacturacion || sender == borrarDomicioEntrega) {
				if ((sender == borrarDomicioFacturacion)) {
					borrarDomicilioFacturacion();
	
				} else if (sender == borrarDomicioEntrega) {
					borrarDomicilioEntrega();
				}
	
			} else if (sender == detalleSS || sender == serviciosAdicionales.getTable()) {
				Cell cell = ((HTMLTable) sender).getCellForEvent(clickEvent);
				if (cell != null) {
					onTableClick(sender, cell.getRowIndex(), cell.getCellIndex());
				}
			}
		}
	}

	public void borrarDomicilioFacturacion() {
		if (editarSSUIData.getFacturacion().getSelectedItemText() == null) {
		} else {
			domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem();
			DomicilioUI.getInstance().openPopupDeleteDialog(editarSSUIData.getCuenta().getPersona(),
					domicilioAEditar, new Command() {
						public void execute() {
							editarSSUIData.refreshDomiciliosListBox();
						}
					});
		}
	}

	public void borrarDomicilioEntrega() {
		if (editarSSUIData.getEntrega().getSelectedItemText() != null) {
			domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem();
			DomicilioUI.getInstance().openPopupDeleteDialog(editarSSUIData.getCuenta().getPersona(),
					domicilioAEditar, new Command() {
						public void execute() {
							editarSSUIData.refreshDomiciliosListBox();
						}
					});
		}
	}

	public void onClickEdicionDomicilios(Widget sender) {
		// Verifico si esta completo el domicilio, si no esta no haca ninguna accion
		if (((sender == editarDomicioEntrega) && (editarSSUIData.getEntrega().getSelectedItemText() == null))
				|| ((sender == editarDomicioFacturacion) && (editarSSUIData.getFacturacion()
						.getSelectedItemText() == null))) {
		} else {
			DomicilioUI.getInstance().setComandoAceptar(getCommandGuardarDomicilio());
			listaDomicilios = editarSSUIData.getCuenta().getPersona().getDomicilios();

			if (sender == crearDomicilio) {
				domicilioAEditar = new DomiciliosCuentaDto();
			} else if (sender == editarDomicioEntrega) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getEntrega().getSelectedItem();
			} else if (sender == editarDomicioFacturacion) {
				domicilioAEditar = (DomiciliosCuentaDto) editarSSUIData.getFacturacion().getSelectedItem();
			}
			domicilioAEditar = domicilioAEditar != null ? domicilioAEditar : new DomiciliosCuentaDto();
			DomicilioUI.getInstance().setParentContacto(false);
			DomicilioUI.getInstance().cargarListBoxEntregaFacturacion(listaDomicilios, domicilioAEditar);
			DomicilioUI.getInstance().cargarPopupEditarDomicilio(domicilioAEditar);
		}
	}

	private Command getCommandGuardarDomicilio() {
		return new Command() {
			public void execute() {
				DomiciliosCuentaDto domicilio = DomicilioUI.getInstance().getDomicilioAEditar();
				editarSSUIData.addDomicilio(domicilio);
				editarSSUIData.refreshDomiciliosListBox();
			}
		};
	}

	public void onTableClick(Widget sender, final int row, int col) {
//		if(controller.isEditable()) {
			if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
				if (detalleSS == sender) {
					if (row > 0) {
						if (col == 9) {
							if (!serviciosAdicionales.isEditing()) {
								editarPrecioDeVentaPlan();
							}
						} else if (col > 3) {
							// Carga servicios adicionales en la tabla
							if (!serviciosAdicionales.isEditing()) {
								selectDetalleLineaSSRow(row);
							}
						} else if (col == 0) {
							// Abre panel de edicion de la LineaSolicitudServicio
							lineaSeleccionada = editarSSUIData.getLineasSolicitudServicio().get(row - 1);
							openItemSolicitudDialog(lineaSeleccionada);
						} else if (col == 1) {
							// Elimina la LineaSolicitudServicio
							ModalMessageDialog.getInstance().showAceptarCancelar("", "Desea eliminar el Item?",
									new Command() {
								public void execute() {
									removeDetalleLineaSSRow(row);
									asignarNroSSPortabilidad();
								};
							}, ModalMessageDialog.getCloseCommand());
						} else if (col == 3) {
							if (descuentoTotalAplicado) {
								noSePuedeAplicarDescuento(false);
							} else {
								//Abre el panel de descuento de la LineaSolicitudServicio
								lineaSeleccionada = editarSSUIData.getLineasSolicitudServicio().get(row - 1); 
								verificarDescuento(lineaSeleccionada);
							}
						}
					}
				} else if (serviciosAdicionales.getTable() == sender) {
					if (col == 0 && row > 0) {
						serviciosAdicionales.agregarQuitarServicioAdicional(row);
					} else if (col == 5 && row > 0) {
						serviciosAdicionales.editarPrecioDeVentaServicioAdicional(row);
					}
				}
			} else {
				if (detalleSS == sender) {
	
					if (row > 0) {
						if (col == 7) {
							if (!serviciosAdicionales.isEditing()) {
								editarPrecioDeVentaPlan();
							}
						} else if (col > 2) {
							// Carga servicios adicionales en la tabla
							if (!serviciosAdicionales.isEditing()) {
								selectDetalleLineaSSRow(row);
							}
						} else if (col == 0) {
							// Abre panel de edicion de la LineaSolicitudServicio
							openItemSolicitudDialog(editarSSUIData.getLineasSolicitudServicio().get(row - 1));
						} else if (col == 1) {
							// Elimina la LineaSolicitudServicio
							ModalMessageDialog.getInstance().showAceptarCancelar("", "Desea eliminar el Item?",
									new Command() {
										public void execute() {
											removeDetalleLineaSSRow(row);
											asignarNroSSPortabilidad();
										};
									}, ModalMessageDialog.getCloseCommand());
						}
					}
				} else if (serviciosAdicionales.getTable() == sender) {
					if (col == 0 && row > 0) {
						serviciosAdicionales.agregarQuitarServicioAdicional(row);
					} else if (col == 4 && row > 0) {
						serviciosAdicionales.editarPrecioDeVentaServicioAdicional(row);
					}
				}
			}
			// TODO: Portabilidad
			if(detalleSS == sender){
				if(row > 0 && col == 2){
					openPortabilidadReplicarDialog(row);
				}
			}
//		}
	}

	/**
	 * TODO: Portabilidad
	 * @param row
	 */
	private void openPortabilidadReplicarDialog(final int row){
		SolicitudPortabilidadDto portabilidad = editarSSUIData.getLineasSolicitudServicio().get(row - 1).getPortabilidad();
		

		if(portabilidad != null){
			//LF
			if(portabilidad.getTipoPersona() != null) {
					final DatosSSUI datos = this;
					SolicitudRpcService.Util.getInstance().getPortabilidadInitializer(editarSSUIData.getCuentaId().toString(),editarSSUIData.getCuenta().getCodigoVantive(),new DefaultWaitCallback<PortabilidadInitializer>() {
						@Override
						public void success(PortabilidadInitializer result) {
							PortabilidadReplicarDialog replicarDialog = new PortabilidadReplicarDialog();
							replicarDialog.show(editarSSUIData.getSolicitudServicio(),row - 1,result,datos,controller);
						}
					});
			} else {
				ModalMessageDialog.getInstance().showAceptar(
						"Debe seleccionar un Tipo de Persona en el item seleccionado", 
						ModalMessageDialog.getCloseCommand());
			}
	    }else{
			ModalMessageDialog.getInstance().showAceptar(
					"El Item seleccionado no posee una Solicitud de Portabilidad para replicar", 
					ModalMessageDialog.getCloseCommand());
		}
	}
	
//    private boolean empty(String s) {
//        return s == null || s.length() == 0;
//    }
//
//    private boolean notEmpty(String s) {
//        return !empty(s);
//    }

    private void selectDetalleLineaSSRow(int row) {
		if (row > 0) {
			detalleSS.getRowFormatter().removeStyleName(selectedDetalleRow, SELECTED_ROW);
			detalleSS.getRowFormatter().addStyleName(row, SELECTED_ROW);
		}
		selectedDetalleRow = row;
		serviciosAdicionales.setServiciosAdicionalesFor(row);
	}

	private void removeDetalleLineaSSRow(int row) {
//		if (selectedDetalleRow == row) {
//			selectDetalleLineaSSRow(detalleSS.getRowCount() <= 1 ? 0 : 1);
//		} else if (selectedDetalleRow > row) {
//			selectDetalleLineaSSRow(--selectedDetalleRow);
//		}

//		MGR Inicio (23/07/2012) - Este es el codigo que habia antes de resolver el incidente #3424
		//Si pasado un tiempo el nuevo codigo no trae problemas, eliminar estas lineas comentadas
//		if(detalleSS.getRowCount() > 1) selectDetalleLineaSSRow(1);
//		else selectDetalleLineaSSRow(0);
//
//		editarSSUIData.removeLineaSolicitudServicio(row - 1);
//		detalleSS.removeRow(row);
//		
//		if(detalleSS.getRowCount() == 1) serviciosAdicionales.setServiciosAdicionalesFor(0);
//		
//		ModalMessageDialog.getInstance().hide();
//		MGR Inicio (23/07/2012)
		
//		MGR - #3424
		editarSSUIData.removeLineaSolicitudServicio(row - 1);
		detalleSS.removeRow(row);
		
		if (selectedDetalleRow == row) {
			selectDetalleLineaSSRow(editarSSUIData.getLineasSolicitudServicio().isEmpty() ? 0 : 1);
		} else if (selectedDetalleRow > row) {
			selectDetalleLineaSSRow(--selectedDetalleRow);
		}
		
		ModalMessageDialog.getInstance().hide();
	}

	/** Crea y abre el Dialog para editar la LineaSolicitudServicioDto */
	private void openItemSolicitudDialog(LineaSolicitudServicioDto linea) {
		if (itemSolicitudDialog == null) {
			itemSolicitudDialog = new ItemSolicitudDialog("Agregar Item", controller);
			Command aceptarCommand = new Command() {
				public void execute() {
					LineaSolicitudServicioDto lineaSolicitudServicio = itemSolicitudDialog.getItemSolicitudUIData().getLineaSolicitudServicio();
					if(!itemSolicitudDialog.getItemSolicitudUIData().getPortabilidadPanel().getChkPortabilidad().getValue())lineaSolicitudServicio.setPortabilidad(null);
					addLineaSolicitudServicio(lineaSolicitudServicio);
					
					// Genera los numeros de solicitudes de portabilidad
					asignarNroSSPortabilidad();
					lineaModificada = lineaSolicitudServicio.getId();
				}
			};
			itemSolicitudDialog.setAceptarCommand(aceptarCommand);

			// TODO: Carga los datos de inizializacion del componente de portabilidad
			itemSolicitudDialog.cargarPortabilidadInitializer(portabilidadInitializer);
		}
		itemSolicitudDialog.cargarPersona(portabilidadInitializer.getPersona());
		itemSolicitudDialog.resetearPanelPortabilidad();
		itemSolicitudDialog.setCuentaEmpresa(editarSSUIData.getCuenta().isEmpresa());

		itemSolicitudDialog.show(linea);
	}

	/**Verifica si se puede aplicar un descuento al item seleccionado*/
	private void verificarDescuento(LineaSolicitudServicioDto linea) {
		idLinea = linea.getId();
		if (idLinea != null) {
			SolicitudRpcService.Util.getInstance().getDescuentos(idLinea, new DefaultWaitCallback<List<DescuentoDto>>() {
				@Override
				public void success(List<DescuentoDto> result) {
					if (result.size() > 0
							&& !lineaSeleccionada.getId().equals(lineaModificada)) {
						openAplicarDescuentoDialog(lineaSeleccionada, result);
					} else {
						noSePuedeAplicarDescuento(false);
					}
				}
			});
		} else {
			noSePuedeAplicarDescuento(true);
		}
	}

	/**Crea y abre el Dialog para aplicar un descuento a la LineaSolicitudServicioDto */
	private void openAplicarDescuentoDialog(LineaSolicitudServicioDto linea, List<DescuentoDto> result) {
		descuentosAAplicar.clear();
		descuentosAplicados.clear();
		descuentos = result;
		descuento = result.get(0);
		if (descuentoDialog == null) {
			descuentoDialog = new DescuentoDialog("Aplicar Descuento", controller, linea.getItem().getSegment1());
			Command aceptarCommand = new Command() {
				public void execute() {
					editarPrecionConDescuento(lineaSeleccionada, descuentos);
					descuentoSeleccionados = descuentoDialog.getDescuentosSeleccionados();
//					editarSSUIData.modificarDescuentoTotal(descuentoSeleccionados);
				}
			};
			descuentoDialog.setAceptarCommand(aceptarCommand);
		}
		//Obtengo los tipos de descuento que ya aplicó
		SolicitudRpcService.Util.getInstance().getTiposDescuentoAplicados(idLinea, new DefaultWaitCallback<List<TipoDescuentoDto>>() {
			@Override
			public void success(List<TipoDescuentoDto> result) {
				descuentosAplicados = result;
				for (Iterator<TipoDescuentoSeleccionado> iterator = descuentoSeleccionados.iterator(); iterator.hasNext();) {
					TipoDescuentoSeleccionado seleccionado = (TipoDescuentoSeleccionado) iterator.next();
					if (lineaSeleccionada.getId().equals(seleccionado.getIdLineaSeleccionada())) {
						sacarTipoDescuento = true;
						TipoDescuentoDto tipoDescuentoDto = new TipoDescuentoDto();
						tipoDescuentoDto.setDescripcion(seleccionado.getDescripcion());
						descuentosAplicados.add(tipoDescuentoDto);
					}
				}
				//Obtengo los tipos de descuento que puede aplicar
				SolicitudRpcService.Util.getInstance().getTiposDescuento(idLinea, new DefaultWaitCallback<List<TipoDescuentoDto>>() {
					@Override
					public void success(List<TipoDescuentoDto> result) {
						if (result.size() > 0 && !lineaSeleccionada.getId().equals(lineaModificada)) {
							descuentosAAplicar = result;
								if (sacarTipoDescuento) {
									Iterator<TipoDescuentoDto> iterator = descuentosAAplicar.iterator();
									while (iterator.hasNext()) {
										TipoDescuentoDto tipoDescuento = (TipoDescuentoDto) iterator.next();
										for (Iterator<TipoDescuentoSeleccionado> iterator2 = descuentoSeleccionados.iterator(); iterator2.hasNext();) {
											TipoDescuentoSeleccionado seleccionado = (TipoDescuentoSeleccionado) iterator2.next();
											if (lineaSeleccionada.getId().equals(seleccionado.getIdLineaSeleccionada()) &&
													tipoDescuento.getDescripcion().equals(seleccionado.getDescripcion())) {
												iterator.remove();
											}
										}
									}
								}
								if (descuentosAAplicar.size() > 0
										&& !lineaSeleccionada.getId().equals(lineaModificada)) {
									descuentoDialog.show(lineaSeleccionada, descuento, descuentosAplicados, descuentosAAplicar);
								} else {
									noSePuedeAplicarDescuento(false);
								}
						} else {
							noSePuedeAplicarDescuento(false);
						}
					}
				});
			}
		});
	}

	/**
	 * Agrega una LineaSolicitudServicioDto tanto a la tabla como a la Solicitud de Servicio. Maneja la lógica
	 * de la clonación de items con plan cuando la cantidad es mayor a 1.
	 */
	private void addLineaSolicitudServicio(LineaSolicitudServicioDto linea) {
		ArrayList<LineaSolicitudServicioDto> nuevasLineas = new ArrayList();
		nuevasLineas.add(linea);
		// Si tiene plan (no es un accesorio por ej)
		if (linea.getPlan() != null) {
			int catLineas = linea.getCantidad();
			if (catLineas > 1) {
				linea.setCantidad(1);
				for (int i = 0; i < catLineas - 1; i++) {
					linea.setPortabilidad(null);
					LineaSolicitudServicioDto lineaCloned = linea.clone();
					lineaCloned.setNumeradorLinea(null);
					nuevasLineas.add(lineaCloned);
				}
			}

		}
		int firstNewRow = 0;
		for (int i = 0; i < nuevasLineas.size(); i++) {
			LineaSolicitudServicioDto nueva = nuevasLineas.get(i);
			int newRow = editarSSUIData.addLineaSolicitudServicio(nueva) + 1;
			if (firstNewRow == 0) {
				firstNewRow = newRow;
			} else {
				nueva.setAlias(editarSSUIData.getNombreMovil());
			}
			drawDetalleSSRow(nueva, newRow);
		}
		onTableClick(detalleSS, firstNewRow, 4);
	}

	/** Limpia y recarga la tabla de Detalle de Solicitud de Servicio completamente */
	private void refreshDetalleSSTable() {
		while (detalleSS.getRowCount() > 1) {
			detalleSS.removeRow(1);
		}
		for (LineaSolicitudServicioDto linea : editarSSUIData.getLineasSolicitudServicio()) {
			drawDetalleSSRow(linea, editarSSUIData.getLineasSolicitudServicio().indexOf(linea) + 1);
		}
		if (!editarSSUIData.getLineasSolicitudServicio().isEmpty()) {
			onTableClick(detalleSS, 1, 3);
		} else {
			serviciosAdicionales.clear();
		}
	}

	/** Agrega una fila a la tabla de Detalle de Solicitud de Servicio en la posicion indicada */
	private void drawDetalleSSRow(LineaSolicitudServicioDto linea, int newRow) {
		if(controller.isEditable()) {
			detalleSS.setWidget(newRow, 0, IconFactory.lapiz());
			detalleSS.setWidget(newRow, 1, IconFactory.cancel());
			detalleSS.setWidget(newRow, 2, IconFactory.copiar());
		}
		
		int i = 0;
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
			if(controller.isEditable()) {
				detalleSS.setWidget(newRow, 3-i, IconFactory.bolsaPesos());
			} else {
				detalleSS.setWidget(newRow, i, IconFactory.bolsaPesos());
			}
		} else {
			if(controller.isEditable()) {
				i = 1;
			}
			
		}
		detalleSS.setHTML(newRow, controller.isEditable()?4-i:i, linea.getItem().getDescripcion());
		detalleSS.setHTML(newRow, controller.isEditable()?5-i:++i, currencyFormat.format(linea.getPrecioLista()));
		detalleSS.getCellFormatter().addStyleName(newRow, controller.isEditable()?5-i:i, "alignRight");
		if (linea.getPrecioConDescuento() == null) {
			linea.setPrecioConDescuento(linea.getPrecioLista());
		}
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
			linea.setPrecioVenta(linea.getPrecioConDescuento());
			detalleSS.setHTML(newRow, controller.isEditable()?6-i:++i, currencyFormat.format(linea.getPrecioConDescuento()));
			detalleSS.getCellFormatter().addStyleName(newRow, controller.isEditable()?6-i:i, "alignRight");
		} else {
			if(controller.isEditable()) {
				i = 2;
			} else {
				i = 1;
			}
		}
		detalleSS.setHTML(newRow, controller.isEditable()?7-i:++i, linea.getAlias() != null ? linea.getAlias() : "");
		detalleSS.setHTML(newRow, controller.isEditable()?8-i:++i, linea.getPlan() != null ? linea.getPlan().getDescripcion() : "");
		detalleSS.setHTML(newRow, controller.isEditable()?9-i:++i, linea.getPlan() != null ? currencyFormat.format(linea
				.getPrecioVentaPlan()) : "");
		detalleSS.getCellFormatter().addStyleName(newRow, controller.isEditable()?9-i:i, "alignRight");
		detalleSS.setHTML(newRow, controller.isEditable()?10-i:++i, linea.getLocalidad() != null ? linea.getLocalidad().getDescripcion()
				: "");
		detalleSS.setHTML(newRow, controller.isEditable()?11-i:++i, linea.getNumeroReserva());
		detalleSS.setHTML(newRow, controller.isEditable()?12-i:++i, linea.getTipoSolicitud().getDescripcion());
		detalleSS.setHTML(newRow, controller.isEditable()?13-i:++i, "" + linea.getCantidad());
		detalleSS.setHTML(newRow, controller.isEditable()?14-i:++i, linea.getDdn() ? IconFactory.tildeVerde().toString() : Sfa.constant()
				.whiteSpace());
		detalleSS.setHTML(newRow, controller.isEditable()?15-i:++i, linea.getDdi() ? IconFactory.tildeVerde().toString() : Sfa.constant()
				.whiteSpace());
		detalleSS.setHTML(newRow, controller.isEditable()?16-i:++i, linea.getRoaming() ? IconFactory.tildeVerde().toString() : Sfa.constant().whiteSpace());
		
		if(linea.getPortabilidad() != null) detalleSS.setWidget(newRow,controller.isEditable()?17-i:++i,IconFactory.tildeVerde());
		else detalleSS.setHTML(newRow,controller.isEditable()?17-i:++i,Sfa.constant().whiteSpace());
	}

	public void editarPrecioDeVentaPlan() {
		LineaSolicitudServicioDto lineaSS = editarSSUIData.getLineasSolicitudServicio().get(
				selectedDetalleRow - 1);
		getPlanPrecioVentaTextBox().setText(
				NumberFormat.getDecimalFormat().format(lineaSS.getPrecioVentaPlan()));
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
			detalleSS.setWidget(selectedDetalleRow, 9, getPlanPrecioVentaTextBox());
		} else {
			detalleSS.setWidget(selectedDetalleRow, 7, getPlanPrecioVentaTextBox());
		}
		getPlanPrecioVentaTextBox().setFocus(true);
	}

	public TextBox getPlanPrecioVentaTextBox() {
		if (precioVentaPlan == null) {
			precioVentaPlan = new RegexTextBox(RegularExpressionConstants.importe);
			precioVentaPlan.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent blurEvent) {
					updatePrecioVentaDePlan(precioVentaPlan.getText());
				}
			});
			precioVentaPlan.setWidth("110px");
			precioVentaPlan.addKeyPressHandler(new KeyPressHandler() {
				public void onKeyPress(KeyPressEvent keyPressEvent) {
					if (KeyCodes.KEY_ENTER == keyPressEvent.getCharCode()) {
						updatePrecioVentaDePlan(precioVentaPlan.getText());
					}
				}
			});
		}
		return precioVentaPlan;
	}

	private void updatePrecioVentaDePlan(String precioVenta) {
		LineaSolicitudServicioDto lineaSS = editarSSUIData.getLineasSolicitudServicio().get(
				selectedDetalleRow - 1);
		double valor = lineaSS.getPrecioVentaPlan();
		MessageDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
		try {
			valor = NumberFormat.getDecimalFormat().parse(precioVenta);
		} catch (NumberFormatException e) {
			MessageDialog.getInstance().showAceptar("Ingrese un monto válido",
					MessageDialog.getCloseCommand());
		}
		if (valor > lineaSS.getPrecioVentaPlan()) {
			MessageDialog.getInstance().showAceptar(
					"El desvío debe ser menor o igual al precio de lista del Plan",
					MessageDialog.getCloseCommand());
			valor = lineaSS.getPrecioVentaPlan();
		}
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.AGREGAR_DESCUENTOS.getValue())) {
			detalleSS.setHTML(selectedDetalleRow, 9, NumberFormat.getCurrencyFormat().format(valor));
		} else {
			detalleSS.setHTML(selectedDetalleRow, 7, NumberFormat.getCurrencyFormat().format(valor));
		}
		editarSSUIData.modificarValorPlan(selectedDetalleRow - 1, valor);
	}

	public void editarPrecionConDescuento(LineaSolicitudServicioDto linea, List<DescuentoDto> descuentos) {
		descuentoDialog.modificarPrecioConDescuento(linea, descuentos);
		int newRow = editarSSUIData.addLineaSolicitudServicio(linea) + 1;
		drawDetalleSSRow(linea, newRow);
	}
	
	public void agregarDescuentoTotal(DescuentoTotalDto result) {		
		for (Iterator<LineaSolicitudServicioDto> iterator = editarSSUIData.getLineasSolicitudServicio().iterator(); iterator.hasNext();) {
			LineaSolicitudServicioDto linea = (LineaSolicitudServicioDto) iterator.next();
			if (result.getIdLinea().equals(linea.getId())) {
				//guardo en la linea los valores que modificó el usuario
				linea.setPorcentaje(new Double(100));
				linea.setMonto(linea.getPrecioLista());
				linea.setPrecioConDescuento(new Double(0.0));
					
				//creo una linea de descuento y la agrego a la linea de solicitud de servicio
				DescuentoLineaDto descuentoLinea = new DescuentoLineaDto();
					
				Long idTipoDescuento = null;
				for (Iterator<TipoDescuentoDto> iterator2 = result.getTiposDescuento().iterator(); iterator2.hasNext();) {
					TipoDescuentoDto tipoDescuentoDto = (TipoDescuentoDto) iterator2.next();
					if (editarSSUIData.getDescuentoTotal().getSelectedItemText().equals(tipoDescuentoDto.getDescripcion())) {
						idTipoDescuento = tipoDescuentoDto.getId();
					}
				}
				for (Iterator<DescuentoDto> iterator2 = result.getDescuentos().iterator(); iterator2.hasNext();) {
					DescuentoDto descuentoDto = (DescuentoDto) iterator2.next();
					if (descuentoDto.getIdTipoDescuento().equals(idTipoDescuento)) {
						descuentoLinea.setIdDescuento(descuentoDto.getId());
					}
				}
				descuentoLinea.setIdLinea(linea.getId());
				descuentoLinea.setMonto(linea.getMonto());
				descuentoLinea.setPorcentaje(linea.getPorcentaje());
				linea.addDescuentoLinea(descuentoLinea);
				
				int newRow = editarSSUIData.addLineaSolicitudServicio(linea) + 1;
				drawDetalleSSRow(linea, newRow);
				break;
			}
		} 
	}	

	private void noSePuedeAplicarDescuento(boolean recienCreada) {
		String mensaje;
		if (recienCreada) {
			mensaje = "Para aplicar descuentos debe guardar la solicitud";
		} else {
			if (lineaSeleccionada.getId().equals(lineaModificada)) {
				mensaje = "Para aplicar descuentos debe guardar la solicitud";
			} else {
				mensaje = "No se puede aplicar un descuento a este item";
			}
		}
		MessageDialog.getInstance().setDialogTitle("Advertencia");
		MessageDialog.getInstance().showAceptar(mensaje,
				MessageDialog.getCloseCommand());
	}
	
	public void refresh() {
		refreshNssLayout();

		//Estefania Iguacek - Comentado para salir solo con cierre - CU#6
		//refreshControlLayout();
		refreshDomicilioLayout();
		refreshDetalleSSTable();
		
		editarSSUIData.getNss().setEnabled(controller.isEditable());
		editarSSUIData.getNflota().setEnabled(controller.isEditable());
		editarSSUIData.getOrigen().setEnabled(controller.isEditable());
		editarSSUIData.getVendedor().setEnabled(controller.isEditable());
		editarSSUIData.getSucursalOrigen().setEnabled(controller.isEditable());
		editarSSUIData.getOrdenCompra().setEnabled(controller.isEditable());
		editarSSUIData.getEntrega().setEnabled(controller.isEditable());
		editarSSUIData.getFacturacion().setEnabled(controller.isEditable());
		editarSSUIData.getAclaracion().setEnabled(controller.isEditable());
		editarSSUIData.getEmail().setEnabled(controller.isEditable());
		editarSSUIData.getRetiraEnSucursal().setEnabled(controller.isEditable());
	}
	
	/**
	 * Después de modificar una lineaSS elimino los descuentos
	 */
	public void borrarDescuentoSeleccionados() {
		descuentoSeleccionados.clear();
		if(lineaSeleccionada!=null){
			lineaSeleccionada.setPrecioConDescuento(null);
		}
	}	
}
