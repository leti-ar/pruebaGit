package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalIncluidoDto;
import ar.com.nextel.sfa.client.dto.ServicioContratoDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.TipoPlanDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.event.ClickPermanenciaEvent;
import ar.com.nextel.sfa.client.event.ClickPermanenciaEventHandler;
import ar.com.nextel.sfa.client.event.ClickPincheEvent;
import ar.com.nextel.sfa.client.event.ClickPincheEventHandler;
import ar.com.nextel.sfa.client.event.EventBusUtil;
import ar.com.nextel.sfa.client.widget.ContratoConChinche;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.PlanCesionarioConLapiz;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DatosTransferenciaSSUI extends Composite implements ClickHandler {
	
	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private Grid nnsLayout;
//Estefania Iguacel - Comentado para salir solo con cierre - CU#6	
//	private Grid controlLayout;
	private Grid historicoLayout;
	private Grid obsLayout;
	private Grid cedenteLayout;
	private Grid busqLayout;
	private Grid contratosLayout;
	private Grid grillaTotalServ;
	private Label textTotalServ;
	
	private FlexTable contratosTable;
	private int selectedContratoRow = 0;
	private FlexTable facturadosTable;
	private BusqClienteCedenteDialog busqClienteCedenteDialog;
	private Button buscar;
	private SimpleLink verTodos;
	private SimpleLink eliminar;
	
	private EditarSSUIController controller;
	
	private static final String SELECTED_ROW = "selectedRow";
	
	private CuentaDto ctaCedenteDto;
	private List<ContratoViewDto> todosContratosActivos = new ArrayList<ContratoViewDto>();
	private List<ContratoViewDto> contratosActivosVisibles = new ArrayList<ContratoViewDto>();
	private Set<ContratoViewDto> contratosChequeados = new HashSet<ContratoViewDto>();
	//Se guardan los servicios contratados para cada contrato
	private HashMap<Long, List<ServicioContratoDto>> serviciosContratados = new HashMap<Long, List<ServicioContratoDto>>();
	private List<ServicioContratoDto> serviciosAMostrar = null;
		
	private PlanTransferenciaDialog planTransferenciaDialog;
	private ContratoViewDto contratoSeleccionado;
	private int filaSeleccionada;
	
	private NumberFormat currFormatter = NumberFormat.getCurrencyFormat();
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getMediumDateFormat();
	
	public DatosTransferenciaSSUI(EditarSSUIController controller){
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		
		verTodos = new SimpleLink(Sfa.constant().verTodos());
		eliminar = new SimpleLink(Sfa.constant().eliminar());
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
		mainpanel.add(getNssLayout());
		////////////////////////////////////////////////////////////////////////////
		//Estefania Iguacel - Comentado para salir solo con cierre - CU#6	
		//	mainpanel.add(getControlLayout());
		//larce - Comentado para salir solo con cierre
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_HISTORICO.getValue())) {
//			mainpanel.add(getHistoricoVentasPanel());
//		}			
		mainpanel.add(getObsLayout());
		if(controller.isEditable()) {
			mainpanel.add(getCedenteLayout());
		}	
		mainpanel.add(getBusqLayout());
		mainpanel.add(getContratosLayout());//LF#3controller.isEditable()));
		
		verTodos.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				mostrarTodos();
			}
		});
		
		eliminar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				eliminarContratosSeleccionados();
			}
		});
		
		//binding de eventos
		EventBusUtil.getEventBus().addHandler(ClickPincheEvent.TYPE, new ClickPincheEventHandler() {
			public void onClickPinche(ClickPincheEvent event) {
				doClickChinche(event);
			}
		});
		
		EventBusUtil.getEventBus().addHandler(ClickPermanenciaEvent.TYPE, new ClickPermanenciaEventHandler() {
			public void onClickPermanencia(ClickPermanenciaEvent event) {
				doClickPopUpPermanencia(event);
			}
		});

	}
	
//	@Override
//	protected void onLoad() {
//		super.onLoad();
//
//	}
//	
//	@Override
//	protected void onUnload() {
//		super.onUnload();
//
//	}
	
	protected void doClickChinche(ClickPincheEvent event) {
		for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
			ContratoViewDto contratoViewDto = (ContratoViewDto) iterator.next();
			if (contratoViewDto.getContrato().equals(Long.valueOf(event.getContrato()))) {
				contratoViewDto.setPinchado(event.isClicked());
		
			}
		}
	}
	
	protected void doClickPopUpPermanencia(ClickPermanenciaEvent event) {
		boolean verBotonCerrar = false;
		controller.loadTransferencia(verBotonCerrar);
	}

	private Widget getNssLayout() {
		nnsLayout = new Grid(1, 8);
		nnsLayout.addStyleName("layout");
		refreshNssLayout();
		return nnsLayout;
	}
////////////////////////////////////////////////////////////////////////////
//Estefania Iguacel - Comentado para salir solo con cierre - CU#6	
//	private Widget getControlLayout() {
//		
//		controlLayout = new Grid(1,4);
//		controlLayout.addStyleName("layout");
//		refreshNssLayout();
//		refreshControlLayout();
//		return controlLayout;
//	}
	
//	private void refreshControlLayout(){
//			
//			
//			controlLayout.setHTML(0,0, Sfa.constant().estado());
//			controlLayout.setWidget(0,1, editarSSUIData.getEstado());
//			
//		//	if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_ESTADO.getValue())){
//			controlLayout.setHTML(0,2, Sfa.constant().control());
//			controlLayout.setWidget(0,3, editarSSUIData.getControl());
//		//	}
//		}
/////////////////////////////////////////////////////////////////////////////////	
	private Widget getHistoricoVentasPanel() {
		TitledPanel historico = new TitledPanel("Histórico de Ventas");
		historicoLayout = new Grid(1, 8);
		historicoLayout.addStyleName("layout");
		historicoLayout.setHTML(0, 0, Sfa.constant().cantidadEquipos());
		historicoLayout.setWidget(0, 1, editarSSUIData.getCantidadEquiposTr());
		historicoLayout.setHTML(0, 2, Sfa.constant().fechaFirma());
		historicoLayout.setWidget(0, 3, editarSSUIData.getFechaFirmaTr());
		historicoLayout.setHTML(0, 4, Sfa.constant().estadoReq());
		historicoLayout.setWidget(0, 5, editarSSUIData.getEstadoTr());
		historicoLayout.setHTML(0, 6, Sfa.constant().fechaEstado());
		historicoLayout.setWidget(0, 7, editarSSUIData.getFechaEstadoTr());
		historico.add(historicoLayout);
		return historico;
	}
	
	private Widget getObsLayout(){
		obsLayout = new Grid(1, 2);
		obsLayout.addStyleName("layout");
		obsLayout.getColumnFormatter().setWidth(1, "500px");
		refresObsLayout();		
		return obsLayout;
	}
	
	private Widget getCedenteLayout(){
		cedenteLayout = new Grid(1, 3);
		cedenteLayout.addStyleName("layout");
		refresCedenteLayout();		
		return cedenteLayout;
	}
	
	private Widget getBusqLayout(){
		busqLayout = new Grid(1, 9);
		busqLayout.addStyleName("layout");
		busqLayout.setHeight("20px");
		buscar = new Button(Sfa.constant().buscar());
		buscar.addStyleName("btn-bkg");
		buscar.addClickHandler(this);
		if(controller.isEditable()) {
			refresBusqLayout();
		}
		return busqLayout;
	}
	
	private Widget getContratosLayout(){//LF#3boolean isEditable){
		contratosLayout = new Grid(5, 1);
		contratosLayout.setWidth("100%");
		
		grillaTotalServ = new Grid(1, 2);
		textTotalServ = new Label("Total servicios por equipo:");
		textTotalServ.addStyleName("mt5 mb5");
		
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("resultTableWrapper mlr5");
		contratosTable = new NextelTable();
		String[] titlesContratos = { Sfa.constant().whiteSpace(), "Contratos", "Fecha Estado", 
				"Teléfono", "Flota*ID", "Modelo", "Contratación", "Plan cedente", "Plan cesionario",
				"Precio plan cesionario", "O.S.", "CPP / MPP", "Suscriptor"};			
		for (int i = 0; i < titlesContratos.length; i++) {
			contratosTable.setHTML(0, i, titlesContratos[i]);
		}
		CheckBox check = new CheckBox();
		check.setValue(true);
		//LF#3 check.setEnabled(isEditable);
		contratosTable.setWidget(0, 0, check);
		contratosTable.setCellPadding(0);
		contratosTable.setCellSpacing(0);
		contratosTable.addStyleName("dataTable");
		contratosTable.setWidth("100%");
		contratosTable.getRowFormatter().addStyleName(0, "header");
		contratosTable.addClickHandler(this);
		wrapper.add(contratosTable);
		contratosLayout.setWidget(0, 0, wrapper);
		
		SimplePanel wrapperFacturados = new SimplePanel();
		wrapperFacturados.addStyleName("resumenSSTableWrapper mlr5");
		facturadosTable = new FlexTable();
		String[] titlesFacturados = { "Servicio", "MSISDN", "Fecha Activ.", "Tarifa", "Ajuste",
				"Períodos Pend.", "Última fact."};			
		for (int i = 0; i < titlesFacturados.length; i++) {
			facturadosTable.setHTML(0, i, titlesFacturados[i]);
		}
		facturadosTable.setCellPadding(0);
		facturadosTable.setCellSpacing(0);
		facturadosTable.addStyleName("dataTable");
		facturadosTable.setWidth("100%");
		facturadosTable.getRowFormatter().addStyleName(0, "header");
		wrapperFacturados.add(facturadosTable);
		contratosLayout.setWidget(3, 0, wrapperFacturados);

		refresContratosLayout();		
		return contratosLayout;
	}
	
	private void refreshNssLayout(){
		nnsLayout.setHTML(0, 0, Sfa.constant().nssReq());
		nnsLayout.setWidget(0, 1, editarSSUIData.getNss());
		nnsLayout.setHTML(0, 2, Sfa.constant().origenReq());
		nnsLayout.setWidget(0, 3, editarSSUIData.getOrigenTR());
		
		if (editarSSUIData.getOrigenTR().getItemCount() == 2) {
			editarSSUIData.getOrigenTR().setSelectedIndex(1);
		}

		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			nnsLayout.setHTML(0, 4, Sfa.constant().vendedorReq());
			nnsLayout.setWidget(0, 5, editarSSUIData.getVendedor());
		}
		
		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			nnsLayout.setHTML(0, 6, Sfa.constant().sucOrigenReq());
			nnsLayout.setWidget(0, 7, editarSSUIData.getSucursalOrigen());
			editarSSUIData.getSucursalOrigen().setEnabled(controller.isEditable());
		}
		
		editarSSUIData.getNss().setEnabled(controller.isEditable());
		editarSSUIData.getOrigenTR().setEnabled(controller.isEditable());
		editarSSUIData.getVendedor().setEnabled(controller.isEditable());
		editarSSUIData.getSucursalOrigen().setEnabled(controller.isEditable());
	}
	
	private void refresObsLayout(){
		obsLayout.setHTML(0, 0, Sfa.constant().observ_transf());
		obsLayout.setWidget(0, 1, editarSSUIData.getObservaciones());	
		editarSSUIData.getObservaciones().setEnabled(controller.isEditable());
		editarSSUIData.getObservaciones().setHeight("50px");
	}
	
	private void refresCedenteLayout(){
		cedenteLayout.setHTML(0, 0,  Sfa.constant().clienteCedente());
		cedenteLayout.setWidget(0, 1, editarSSUIData.getClienteCedente());
		cedenteLayout.setWidget(0, 2, editarSSUIData.getRefreshCedente());
		editarSSUIData.getRefreshCedente().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showBusqClienteCedente();
			}
		});
	}
	
	private void refresBusqLayout(){
		busqLayout.setHTML(0, 0, Sfa.constant().busqueda());
		busqLayout.setWidget(0, 1, editarSSUIData.getCriterioBusqContrato());
		busqLayout.setWidget(0, 2, editarSSUIData.getParametroBusqContrato());
		busqLayout.setWidget(0, 3, buscar);
		busqLayout.setWidget(0, 6, verTodos);
		busqLayout.setWidget(0, 8, eliminar);
	}
	
	private void refresContratosLayout(){
		contratosLayout.setWidget(1, 0, new HTML("<br>"));
		contratosLayout.setWidget(2, 0, new InlineLabel("Servicios"));
		grillaTotalServ.setWidget(0, 0, textTotalServ);
		contratosLayout.setWidget(4, 0, grillaTotalServ);
	}

	public void refresh() {
		refreshNssLayout();
		refresObsLayout();
		//LF
		if(controller.isEditable()) {
			refresCedenteLayout();
			refresBusqLayout();
		}		
		refresContratosLayout();
		refreshTablaContratos();
	}
	
	public void showBusqClienteCedente(){
		if(busqClienteCedenteDialog == null){
			busqClienteCedenteDialog = new BusqClienteCedenteDialog("Buscar cliente cedente", this.controller);

			Command buscarCommmand = new Command(){
				public void execute() {
					final CuentaSearchDto ctaSearch = busqClienteCedenteDialog.getBusqClienteCedenteUIData().getCuentaSearch();
					//MGR - #1466 - Indico que esta busqueda no lockea la cuenta
					CuentaRpcService.Util.getInstance().searchCuentaDto(
							ctaSearch, false, new DefaultWaitCallback<List<CuentaDto>>() {

								public void success(List<CuentaDto> result) {
									ctaCedenteDto = null;
									if(result.isEmpty()){
										ErrorDialog.getInstance().show("No se encontraron cuentas con el criterio especificado.");
									}else{
										if(result.size() > 1){
											ErrorDialog.getInstance().show("Existe mas de un resultado por favor comuníquese con Adm Vtas.");
										
										}else{
											CuentaDto cuenta = result.get(0);
											if(cuenta.getCodigoVantive().contains("*")){
												if(ClientContext.getInstance().getVendedor().isDealer() &&
														ctaSearch.getGrupoDocumentoId() == null){
													ErrorDialog.getInstance().show(
															"Debe buscar por número de documento o CUIT CUIL los clientes que no son de su cartera.");
													busqClienteCedenteDialog.hide();
													//Se vacia la grilla y se elimina el cliente cedente
													todosContratosActivos.clear();
													contratosActivosVisibles.clear();
													refreshTablaContratos();
													controller.getEditarSSUIData().getClienteCedente().setText("");
												}else{
													ErrorDialog.getInstance().show("No se encontraron cuentas con el criterio especificado.");
												}
											
											}else{
												ctaCedenteDto = cuenta;
												if(ctaCedenteDto.getCodigoVantive().equals(editarSSUIData.getCuenta().getCodigoVantive())){
													ErrorDialog.getInstance().show("La cuenta cedente es la misma que el cesionario. Por favor, elija otra cuenta.");
												}else{
													controller.getEditarSSUIData().getClienteCedente().setText(ctaCedenteDto.getCodigoVantive());
													CuentaRpcService.Util.getInstance().searchContratosActivos(
															cuenta, new DefaultWaitCallback<List<ContratoViewDto>>() {
																
																public void success(List<ContratoViewDto> result) {
																	if (result.isEmpty()) {
																		ErrorDialog.getInstance().show(
																				"El cliente no posee contratos en estado activo que coincidan con la búsqueda.", false);
																	}
																	todosContratosActivos.clear();
																	contratosActivosVisibles.clear();
																	todosContratosActivos.addAll(result);
																	contratosActivosVisibles.addAll(result);
																	contratosChequeados.clear();
																	
//																	contratosChequeados.addAll(contratosActivosVisibles);
																	refreshTablaContratos();
																}
															});
													busqClienteCedenteDialog.hide();
												}
											}
										}
									}
								}
							});
				}
			};
			this.busqClienteCedenteDialog.setBuscarCommand(buscarCommmand);
		}
		this.busqClienteCedenteDialog.mostrarDialogo();
	}
	
	/**
	 * Muestro los contratos filtrados
	 */
	private void refreshTablaContratos(){
		//Limpio la tabla
		this.limpiarTablaContratos();
		//Dibujo los datos
		for (int i = 0; i < contratosActivosVisibles.size(); i++) {
			drawContrato(i + 1 , contratosActivosVisibles.get(i), true);
		}
		//Muestros los servicios contratados para la primera fila
		if(!contratosActivosVisibles.isEmpty()){
			onTableContratosClick(contratosTable, 1, 2);
		}else{
			limpiarTablaServFacturados();
		}
		//Deshabilito el primer checkBox cuando no hay contratos
		CheckBox check = (CheckBox)contratosTable.getWidget(0, 0);
		if (contratosActivosVisibles.size() == 0) {
			check.setEnabled(false);
		} else {
			check.setEnabled(true);
		}
	}
		
	private void limpiarTablaContratos(){
		for (int i = contratosTable.getRowCount()-1; i>0; i--) {
			contratosTable.removeRow(i);
		}
		//Limpio el check para seleccionar todos los contratos
		CheckBox check = (CheckBox)contratosTable.getWidget(0, 0);
		check.setValue(false);
		
	}
	
	/**
	 * Muestros todos los contratos activos
	 */
	private void mostrarTodos() {
		for (int i = 0; i < todosContratosActivos.size(); i++) {
			ContratoViewDto cto = todosContratosActivos.get(i); 
//			drawContrato(i + 1, cto, false);
			
			if(!contratosActivosVisibles.contains(cto)){
				contratosActivosVisibles.add(cto);
			}
		}
		
		refreshTablaContratos();
	}
	
	private void eliminarContratosSeleccionados() {
		for (int i = contratosTable.getRowCount()-1; i>0; i--) {
			CheckBox checked = (CheckBox) contratosTable.getWidget(i, 0);
			if (checked.getValue()) {
				ContratoConChinche contratoConChinche = (ContratoConChinche) contratosTable.getWidget(i, 1);
				
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					if (contratoConChinche.getContrato().equals(contratoActivo.getContrato().toString())) {
						contratoActivo.setPinchado(false);
						iterator.remove();
					}
				}
//				contratosTable.removeRow(i);
			}
		}
		contratosChequeados.clear();
		selectedContratoRow = 0;
		refreshTablaContratos();
	}
	
	private void drawContrato(int newRow, ContratoViewDto cto, boolean isFiltrado){
		ContratoConChinche contratoConChinche; 
		if (isFiltrado) {
			contratoConChinche = new ContratoConChinche(cto.getContrato().toString(), cto.isPinchado());
		} else {
			cto.setPinchado(false);
			contratoConChinche = new ContratoConChinche(cto.getContrato().toString(), false);
		}
		CheckBox check = new CheckBox();
		check.setValue(contratosChequeados.contains(cto));
		check.setEnabled(controller.isEditable());
		contratosTable.setWidget(newRow, 0, check);
		contratosTable.setWidget(newRow, 1, contratoConChinche);
		contratosTable.setHTML(newRow, 2, cto.getFechaEstado() != null ?
				dateTimeFormat.format(cto.getFechaEstado()) : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 3, cto.getTelefono() != null ? cto.getTelefono() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 4, cto.getFlotaId() != null ? cto.getFlotaId() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 5, cto.getModelo() != null ? cto.getModelo() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 6, cto.getContratacion() != null ? cto.getContratacion() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 7, cto.getPlanCedente() != null ? cto.getPlanCedente() : Sfa.constant().whiteSpace());
		String descPlanCesionario = cto.getPlanCesionario() != null ? 
				cto.getPlanCesionario().getDescripcion() : Sfa.constant().whiteSpace();
		contratosTable.setWidget(newRow, 8, new PlanCesionarioConLapiz(descPlanCesionario));
		if(cto.getPlanCesionario() == null){
			contratosTable.setHTML(newRow, 9, Sfa.constant().whiteSpace());
		}else{
			contratosTable.setHTML(newRow, 9, cto.getPlanCesionario().getPrecio() != null ?
					currFormatter.format(cto.getPlanCesionario().getPrecio()) : Sfa.constant().whiteSpace());
		}
		
		contratosTable.setHTML(newRow, 10, cto.getOs() != null ? cto.getOs() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 11, cto.getModalidad() != null ? cto.getModalidad() : Sfa.constant().whiteSpace());
		contratosTable.setHTML(newRow, 12, cto.getSuscriptor() != null ? cto.getSuscriptor() : Sfa.constant().whiteSpace());
	}
	
	private void drawServiciosFacturados(int selectRow){
		final ContratoViewDto ctoSelected = contratosActivosVisibles.get(selectRow-1);
		serviciosAMostrar = serviciosContratados.get(ctoSelected.getContrato());

		if(serviciosAMostrar == null){
			CuentaRpcService.Util.getInstance().searchServiciosContratados(ctoSelected.getContrato(),
					new DefaultWaitCallback<List<ServicioContratoDto>>() {

						@Override
						public void success(List<ServicioContratoDto> result) {
							serviciosAMostrar = result;
							Collections.sort(serviciosAMostrar);
							serviciosContratados.put(ctoSelected.getContrato(), result);
						}
					});
		}
		
		//Espero que se cargen los servicios y luego los muestro
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if(serviciosAMostrar != null){
					limpiarTablaServFacturados();
					
					Double total = 0.0;
					for (int i = 0; i < serviciosAMostrar.size(); i++) {
						ServicioContratoDto serv = serviciosAMostrar.get(i);
						facturadosTable.setText(i +1, 0, serv.getServicio());
						facturadosTable.setHTML(i +1, 1, serv.getMsisdn() != null ? serv.getMsisdn() : Sfa.constant().whiteSpace());
						facturadosTable.setHTML(i +1, 2, serv.getFechaEstado() != null ? 
								dateTimeFormat.format(serv.getFechaEstado()) : Sfa.constant().whiteSpace());
						facturadosTable.setHTML(i +1, 3, serv.getTarifa() != null ? 
								currFormatter.format(serv.getTarifa()) : Sfa.constant().whiteSpace());
						facturadosTable.setHTML(i +1, 4, serv.getAjuste() != null ? serv.getAjuste(): Sfa.constant().whiteSpace());
						facturadosTable.setHTML(i +1, 5, String.valueOf(serv.getPeriodosPendientes()));
						facturadosTable.setHTML(i +1, 6, serv.getUltimaFactura() != null ?
								dateTimeFormat.format(serv.getUltimaFactura()) : Sfa.constant().whiteSpace());
						facturadosTable.getCellFormatter().addStyleName(i +1, 0, "alignLeft");
						
						if(serv.getTarifa() != null){
							total = total + serv.getTarifa();
						}
					}
					grillaTotalServ.setText(0, 1, currFormatter.format(total));
					return false;
				}
				return true;
			}
		});
	}
	
	public void onClick(ClickEvent clickEvent) {
		Widget sender = (Widget) clickEvent.getSource();
		if(sender == contratosTable){
			Cell cell = ((HTMLTable) sender).getCellForEvent(clickEvent);
			if(cell != null){
				onTableContratosClick(sender, cell.getRowIndex(), cell.getCellIndex());
			}
		} else if(sender == buscar) {
			String parametro = editarSSUIData.getParametroBusqContrato().getText();
			if ("".equals(parametro)) {
				ErrorDialog.getInstance().show("Debe ingresar un parametro de búsqueda", false);
			}else{
				for (Iterator<ContratoViewDto> iterator = todosContratosActivos.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();

					int criterio = Integer.parseInt(editarSSUIData.getCriterioBusqContrato().getSelectedItemId());
					String valorCto = "";
					switch (criterio) {
					case 1:
						valorCto = contratoActivo.getContrato().toString();
						break;
					case 2:
						valorCto = contratoActivo.getTelefono();
						break;
					case 3:
						valorCto = contratoActivo.getFlotaId();
						break;
					default:
						valorCto = contratoActivo.getSuscriptor();
						break;
					}
					
					if(parametro.equals(valorCto)){
						if(!contratosActivosVisibles.contains(contratoActivo)){
							contratosActivosVisibles.add(contratoActivo);
						}
					}else if(!contratoActivo.isPinchado()){
						contratosActivosVisibles.remove(contratoActivo);
					}
				}
				refreshTablaContratos();
			}
		}
	}

	public void onTableContratosClick(Widget sender, final int row, int col){
		if (row == 0 && col == 0) {
			selectAllContratosRow();
		} else if (row > 0) {
//			if (col > 1) {
				if(col == 8) {
					modificarPlanCesionario(contratosActivosVisibles.get(row-1), row);
				} else {
					if (col==0) {// mmm bueno, si clickeo sobre el checkbox trato de agregar el iesimo elemento al conjunto de checkeados
						if (((CheckBox)contratosTable.getWidget(row, 0)).getValue())
							contratosChequeados.add(contratosActivosVisibles.get(row-1));
						else
							contratosChequeados.remove(contratosActivosVisibles.get(row-1));
						chequarPrimero();
					}
					selectContratoRow(row);
					drawServiciosFacturados(row);
				}
//			}
		}
		verificarContratosConPermanencia();
	}
	
	private void chequarPrimero() {
		CheckBox check = (CheckBox)contratosTable.getWidget(0, 0);
		if (contratosChequeados.size() == contratosActivosVisibles.size()) {
			check.setValue(true);
		} else {
			check.setValue(false);
		}
	}

	private void selectContratoRow(int row) {
		if (row > 0) {
			contratosTable.getRowFormatter().removeStyleName(selectedContratoRow, SELECTED_ROW);
			contratosTable.getRowFormatter().addStyleName(row, SELECTED_ROW);
		}
		selectedContratoRow = row;
	}
	
	private void modificarPlanCesionario(ContratoViewDto contrato, int row) {
		contratoSeleccionado = contrato;
		filaSeleccionada = row;
		
		if (planTransferenciaDialog == null) {
			planTransferenciaDialog = new PlanTransferenciaDialog("Modificar Plan", controller);
			Command aceptarCommand = new Command() {
				public void execute() {
					
					//CHINO: tengo que eliminar todos los servicios adicionales que no correspondan al plan seleccionado
					// y más aún si no eligió ningún plan
					final ContratoViewDto contratoActivo = planTransferenciaDialog.getPlanTransferenciaUIData().getContrato();
					PlanDto planSelected = planTransferenciaDialog.getSelectedPlan();
					limpiarServiciosAdicionalesHuerfanos(contratoActivo, planSelected);
					
					
					
					//MGR - #1360
					Command dibujarNuevoPlan = new Command() {
						public void execute() {
							MessageDialog.getInstance().hide();
							drawNuevoPlan(contratoActivo);
						}
					};
					
					if(ClientContext.getInstance().getVendedor().isADMCreditos() && planSelected != null){
						if (planSelected.getTipoPlan().getCodigoBSCS().equals(TipoPlanDto.TIPO_PLAN_DIRECTO_O_EMPRESA_CODE)
								&& editarSSUIData.getCuenta().isEmpresa() != planSelected.getTipoPlan().isEmpresa()) {

							String mensaje = "Advertencia. El Tipo de Plan elegido no coincide con el segmento de la cuenta.";
							MessageDialog.getInstance().showAceptar("Aviso",mensaje, dibujarNuevoPlan);
						}else{
							dibujarNuevoPlan.execute();
						}
					}else{
						dibujarNuevoPlan.execute();
					}
				}

				private void limpiarServiciosAdicionalesHuerfanos(
						ContratoViewDto contratoActivo, PlanDto planSelected) {
					List<ServicioAdicionalIncluidoDto> servicioAdicionalDtos = contratoActivo.getServiciosAdicionalesInc();
					for (Iterator<ServicioAdicionalIncluidoDto> iterator = servicioAdicionalDtos.iterator(); iterator
							.hasNext();) {
						ServicioAdicionalIncluidoDto servicioAdicionalIncluidoDto = iterator.next();
						if(!servicioAdicionalIncluidoDto.getPlan().equals(planSelected)){
							iterator.remove();
						}
					}
					
				}
			};
			planTransferenciaDialog.setAceptarCommand(aceptarCommand);
		}
		planTransferenciaDialog.setCuentaEmpresa(editarSSUIData.getCuenta().isEmpresa());
		planTransferenciaDialog.show(contratoSeleccionado, filaSeleccionada);
	}	
	
	private void drawNuevoPlan(ContratoViewDto contrato) {
		PlanDto planCesionario = contrato.getPlanCesionario();
		contratosTable.setWidget(filaSeleccionada, 8, new PlanCesionarioConLapiz(planCesionario!=null?planCesionario.getDescripcion():Sfa.constant().whiteSpace()));
		contratosTable.setHTML(filaSeleccionada, 9, planCesionario ==null ? Sfa.constant().whiteSpace(): currFormatter.format(planCesionario.getPrecio()));
	}
	
	private void selectAllContratosRow(){
		CheckBox chekAll = (CheckBox)contratosTable.getWidget(0, 0);;
		boolean selectAll = chekAll.getValue();
		for (int i = 0; i < contratosTable.getRowCount(); i++) {
			CheckBox chek = (CheckBox)contratosTable.getWidget(i, 0);
			chek.setValue(selectAll);
		}
		contratosChequeados.clear();
		if (selectAll)
			contratosChequeados.addAll(contratosActivosVisibles);
		
	}
	
	public List<ContratoViewDto>  getContratosSSChequeados() {
		List<ContratoViewDto> contratos = new ArrayList<ContratoViewDto>();
		for (ContratoViewDto cto : contratosChequeados) {
			cto.setCodVantiveCesionario(editarSSUIData.getCuenta().getCodigoVantive());
			if(cto.getPlanCesionario() != null){
				//Limpio el plan cedente para que no quiera mapear a un plan que puede no existir
				cto.setIdPlanCedente(null);
			}
			contratos.add(cto);
		}
		return contratos;
	}
	
	public CuentaDto getCtaCedenteDto() {
		return ctaCedenteDto;
	}

	public void setDatosSolicitud(final SolicitudServicioDto solicitud){
		this.ctaCedenteDto = solicitud.getCuentaCedente();
//		this.contratosActivosVisibles = solicitud.getContratosCedidos();
//		this.contratosChequeados.addAll(solicitud.getContratosCedidos());
		limpiarCampos();
		
		if(this.ctaCedenteDto != null){
			CuentaRpcService.Util.getInstance().searchContratosActivos(
					this.ctaCedenteDto, new DefaultWaitCallback<List<ContratoViewDto>>() {
						
						public void success(List<ContratoViewDto> result) {

							for (ContratoViewDto contratoCedido : solicitud.getContratosCedidos()) {
								for (ContratoViewDto contratoResult : result) {
									if (contratoCedido.getContrato().equals(contratoResult.getContrato())){
										contratoCedido.setCargosPermanencia(contratoResult.getCargosPermanencia());
										contratoCedido.setMesesPermanencia(contratoResult.getMesesPermanencia());
										contratoCedido.setGamaPlanCedente(contratoResult.getGamaPlanCedente());
									}
								}
							}
							
							// primero seteo los contratos de la solicitud
							if (!solicitud.getContratosCedidos().isEmpty()) {
								todosContratosActivos.addAll(solicitud.getContratosCedidos());
								contratosActivosVisibles.addAll(solicitud.getContratosCedidos());
								contratosChequeados.addAll(solicitud.getContratosCedidos());
							}
							// agrego el resto de los contratos del cliente cedente
							for (ContratoViewDto contratoViewDto : result) {
								int index = solicitud.getContratosCedidos().indexOf(contratoViewDto);
								if(index == -1){
									todosContratosActivos.add(contratoViewDto);
									contratosActivosVisibles.add(contratoViewDto);
								}
							}
							refresh();
						}
					});
		}else{
			//MGR - #1350
			refresh();
		}
	}

	private void verificarContratosConPermanencia() {
		boolean verContratosConPermanencia = false;
		
		if (existeContratosConPermanencia()){
			verContratosConPermanencia = true;
		}
		controller.loadTransferencia(verContratosConPermanencia);
	}

	private boolean existeContratosConPermanencia(){
		for (ContratoViewDto contrato : contratosChequeados) {
			if (!contrato.getCargosPermanencia().equals(0d)){
				return true;
			}
		}
		return false;
	}
	
	private void limpiarTablaServFacturados(){
		//Limpio la tabla de servicios
		for (int i = facturadosTable.getRowCount()-1; i>0; i--) {
			facturadosTable.removeRow(i);
		}
		
		grillaTotalServ.setText(0, 1, currFormatter.format(0));
	}

	private void limpiarCampos(){
		this.contratosActivosVisibles.clear();
		this.contratosChequeados.clear();
		this.todosContratosActivos.clear();
		this.limpiarTablaContratos();
		this.limpiarTablaServFacturados();
	}

}
