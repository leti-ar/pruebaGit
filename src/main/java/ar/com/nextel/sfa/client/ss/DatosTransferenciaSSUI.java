package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.PlanDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.event.ClickPincheEvent;
import ar.com.nextel.sfa.client.event.ClickPincheEventHandler;
import ar.com.nextel.sfa.client.event.EventBusUtil;
import ar.com.nextel.sfa.client.widget.ContratoConChinche;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.PlanCesionarioConLapiz;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class DatosTransferenciaSSUI extends Composite implements ClickHandler {
	
	private FlowPanel mainpanel;
	private EditarSSUIData editarSSUIData;
	private Grid nnsLayout;
	private Grid obsLayout;
	private Grid cedenteLayout;
	private Grid busqLayout;
	private Grid contratosLayout;
	private FlexTable contratosTable;
	private int selectedContratoRow = 0;
	private FlexTable facturadosTable;
	//private InlineHTML totalServEquipos;
	private BusqClienteCedenteDialog busqClienteCedenteDialog;
	private Button buscar;
	private SimpleLink verTodos;
	private SimpleLink eliminar;
	
	private EditarSSUIController controller;
	
	private static final String SELECTED_ROW = "selectedRow";
	
	private CuentaDto ctaCedenteDto;
	private List<ContratoViewDto> todosContratosActivos = new ArrayList<ContratoViewDto>();
	private List<ContratoViewDto> contratosActivosVisibles = new ArrayList<ContratoViewDto>();
		
	private PlanTransferenciaDialog planTransferenciaDialog;
	private ContratoViewDto contratoSeleccionado;
	private int filaSeleccionada;
	
	public DatosTransferenciaSSUI(EditarSSUIController controller){
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		
		verTodos = new SimpleLink(Sfa.constant().verTodos());
		eliminar = new SimpleLink(Sfa.constant().eliminar());
		this.controller = controller;
		editarSSUIData = controller.getEditarSSUIData();
		mainpanel.add(getNssLayout());
		mainpanel.add(getObsLayout());
		mainpanel.add(getCedenteLayout());
		mainpanel.add(getBusqLayout());
		mainpanel.add(getContratosLayout());
		
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
	}
	
	protected void doClickChinche(ClickPincheEvent event) {
		for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
			ContratoViewDto contratoViewDto = (ContratoViewDto) iterator.next();
			if (contratoViewDto.getContrato().equals(Long.valueOf(event.getContrato()))) {
				contratoViewDto.setPinchado(event.isClicked());
			}
		}
	}

	private Widget getNssLayout() {
		nnsLayout = new Grid(1, 8);
		nnsLayout.addStyleName("layout");
		refreshNssLayout();
		return nnsLayout;
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
		refresBusqLayout();		
		return busqLayout;
	}
	
	private Widget getContratosLayout(){
		contratosLayout = new Grid(5, 1);
		contratosLayout.setWidth("100%");
		
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("resultTableWrapper mlr5");
		contratosTable = new NextelTable();
		String[] titlesContratos = { Sfa.constant().whiteSpace(), "Contratos", "Fecha Estado", 
				"Teléfono", "Flota*ID", "Modelo", "Contratación", "Plan cedente", "Plan cesionario",
				"Precio plan cesionario", "O.S.", "Modalidad", "Suscriptor"};			
		for (int i = 0; i < titlesContratos.length; i++) {
			contratosTable.setHTML(0, i, titlesContratos[i]);
		}
		contratosTable.setWidget(0, 0, new CheckBox());
		contratosTable.setCellPadding(0);
		contratosTable.setCellSpacing(0);
		contratosTable.addStyleName("dataTable");
		contratosTable.setWidth("98%");
		contratosTable.getRowFormatter().addStyleName(0, "header");
		contratosTable.addClickHandler(this);
		wrapper.add(contratosTable);
		contratosLayout.setWidget(0, 0, wrapper);
		
		facturadosTable = new FlexTable();
		String[] titlesFacturados = { "Servicio", "MSISDN", "Fecha Activ.", "Tarifa", "Ajuste",
				"Períodos Pend.", "Última fact."};			
		for (int i = 0; i < titlesFacturados.length; i++) {
			facturadosTable.setHTML(0, i, titlesFacturados[i]);
		}
		facturadosTable.setCellPadding(0);
		facturadosTable.setCellSpacing(0);
		facturadosTable.addStyleName("dataTable");
		facturadosTable.setWidth("98%");
		facturadosTable.getRowFormatter().addStyleName(0, "header");
		
		//totalServEquipos = new InlineHTML("Total servicios por equipo:");
		refresContratosLayout();		
		return contratosLayout;
	}
	
	private void refreshNssLayout(){
		nnsLayout.setHTML(0, 0, Sfa.constant().nssReq());
		nnsLayout.setWidget(0, 1, editarSSUIData.getNss());
		nnsLayout.setHTML(0, 2, Sfa.constant().origenReq());
		nnsLayout.setWidget(0, 3, editarSSUIData.getOrigen());

		//TODO: -MGR- Habilitar esto cuando este terminado.
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			nnsLayout.setHTML(0, 4, Sfa.constant().vendedor());
			nnsLayout.setWidget(0, 5, editarSSUIData.getVendedor());
//		}
//		
//		if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_SUCURSAL_ORIGEN.getValue())){
			nnsLayout.setHTML(0, 6, Sfa.constant().sucOrigen());
			nnsLayout.setWidget(0, 7, editarSSUIData.getSucursalOrigen());
//		}
	}
	
	private void refresObsLayout(){
		//TODO: -MGR- Esta bien usar la misma etiqueta?, por que no son observaciones de domicilio
		obsLayout.setHTML(0, 0, Sfa.constant().obs_domicilio());
		obsLayout.setWidget(0, 1, editarSSUIData.getObservaciones());	
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
		contratosLayout.setWidget(2, 0, new InlineLabel("Facturados"));
		contratosLayout.setWidget(3, 0, facturadosTable);
		contratosLayout.setWidget(4, 0, new InlineLabel("Total servicios por equipo:"));
	}

	public void refresh() {
		refreshNssLayout();
		refresObsLayout();
		refresCedenteLayout();
		refresBusqLayout();
		refresContratosLayout();
		refreshTablaContratos();
	}
	
	public void showBusqClienteCedente(){
		if(busqClienteCedenteDialog == null){
			busqClienteCedenteDialog = new BusqClienteCedenteDialog("Buscar cliente cedente", this.controller);

			Command buscarCommmand = new Command(){
				public void execute() {
					final CuentaSearchDto ctaSearch = busqClienteCedenteDialog.getBusqClienteCedenteUIData().getCuentaSearch();
					CuentaRpcService.Util.getInstance().searchCuentaDto(
							ctaSearch, new DefaultWaitCallback<List<CuentaDto>>() {

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
													//TODO: -MGR- en este caso se tiene que vaciar la grilla. Verificar
												}else{
													//TODO: -MGR- Verificar que si no dealer este es el mensaje a mostrar
													ErrorDialog.getInstance().show("No se encontraron cuentas con el criterio especificado.");
												}
											
											}else{
												ctaCedenteDto = cuenta;
												//-MGR- Val-6
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
																	limpiarContratosTable();
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
		for (int i = 0; i < contratosActivosVisibles.size(); i++) {
			drawContrato(i + 1 , contratosActivosVisibles.get(i), true);
		}
	}
	
	/**
	 * Muestros todos los contratos activos
	 */
	private void mostrarTodos() {
		for (int i = 0; i < todosContratosActivos.size(); i++) {
			drawContrato(i + 1, todosContratosActivos.get(i), false);
		}
	}
	
	private void eliminarContratosSeleccionados() {
		for (int i = contratosTable.getRowCount()-1; i>0; i--) {
			CheckBox checked = (CheckBox) contratosTable.getWidget(i, 0);
			if (checked.getValue()) {			
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					ContratoConChinche contratoConChinche = (ContratoConChinche) contratosTable.getWidget(i, 1);
					if (contratoConChinche.getContrato().equals(contratoActivo.getContrato())) {
						iterator.remove();
					}
				}
				contratosTable.removeRow(i);
			}
		}
	}
	
	private void drawContrato(int newRow, ContratoViewDto cto, boolean isFiltrado){
		ContratoConChinche contratoConChinche; 
		if (isFiltrado) {
			contratoConChinche = new ContratoConChinche(cto.getContrato().toString(), cto.isPinchado());
		} else {
			cto.setPinchado(false);
			contratoConChinche = new ContratoConChinche(cto.getContrato().toString(), false);
		}
		contratosTable.setWidget(newRow, 0, new CheckBox());
		contratosTable.setWidget(newRow, 1, contratoConChinche);
		contratosTable.setHTML(newRow, 2, DateTimeFormat.getFormat("dd-M-yyyy").format(cto.getFechaEstado()));
		contratosTable.setText(newRow, 3, cto.getTelefono());
		contratosTable.setHTML(newRow, 4, cto.getFlotaId());
		contratosTable.setText(newRow, 5, cto.getModelo());
		contratosTable.setHTML(newRow, 6, cto.getContratacion());
		contratosTable.setHTML(newRow, 7, cto.getPlanCedente());
		contratosTable.setWidget(newRow, 8, new PlanCesionarioConLapiz(""));
		contratosTable.setHTML(newRow, 9, cto.getPrecioPlanCesionario());
		contratosTable.setHTML(newRow, 10, cto.getOs());
		contratosTable.setHTML(newRow, 11, cto.getModalidad());
		contratosTable.setText(newRow, 12, cto.getSuscriptor());
	}
	
	private void drawFacturados(int selectRow){
		facturadosTable.setHTML(1, 1, "Se selecciono la fila " + selectRow);
	}
	
	public void onClick(ClickEvent clickEvent) {
		Widget sender = (Widget) clickEvent.getSource();
		if(sender == contratosTable){
			Cell cell = ((HTMLTable) sender).getCellForEvent(clickEvent);
			if(cell != null){
				onTableContratosClick(sender, cell.getRowIndex(), cell.getCellIndex());
			}
		} else if(sender == buscar) {
			int filaBorrada=0;
			String criterioBusqueda = editarSSUIData.getCriterioBusqContrato().getSelectedItemId();
			String parametro = editarSSUIData.getParametroBusqContrato().getText();
			if ("".equals(parametro)) {
				ErrorDialog.getInstance().show("Debe ingresar un parametro de búsqueda", false);
			} else if ("1".equals(criterioBusqueda)) {
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					if (!contratoActivo.isPinchado() && !parametro.equals(String.valueOf(contratoActivo.getContrato()))) {
						iterator.remove();
					}
					filaBorrada++;
				}
				limpiarContratosTable();
				refreshTablaContratos();
			} else if ("2".equals(criterioBusqueda)) {
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					if (!contratoActivo.isPinchado() && !parametro.equals(String.valueOf(contratoActivo.getTelefono()))) {
						iterator.remove();
					}
					filaBorrada++;
				}
				limpiarContratosTable();
				refreshTablaContratos();
			} else if ("3".equals(criterioBusqueda)) {
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					if (!contratoActivo.isPinchado() && !parametro.equals(String.valueOf(contratoActivo.getFlotaId()))) {
						iterator.remove();
					}
					filaBorrada++;
				}
				limpiarContratosTable();
				refreshTablaContratos();
			} else if ("4".equals(criterioBusqueda)) {
				for (Iterator<ContratoViewDto> iterator = contratosActivosVisibles.iterator(); iterator.hasNext();) {
					ContratoViewDto contratoActivo = (ContratoViewDto) iterator.next();
					if (!contratoActivo.isPinchado() && !parametro.equals(String.valueOf(contratoActivo.getSuscriptor()))) {
						iterator.remove();
					}
					filaBorrada++;
				}
				limpiarContratosTable();				
				refreshTablaContratos();
			}
		}
	}

	private void limpiarContratosTable() {
		for (int i = contratosTable.getRowCount()-1; i>0; i--) {
			contratosTable.removeRow(i);
		}
	}
	
	public void onTableContratosClick(Widget sender, final int row, int col){
		if (row == 0 && col == 0) {
			selectAllContratosRow();
		} else if (row > 0) {
			if (col > 1) {
				if(col == 8) {
					modificarPlanCesionario(contratosActivosVisibles.get(row-1), row);
				} else {
					selectContratoRow(row);
					drawFacturados(row);
				}
			}
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
					drawNuevoPlan(planTransferenciaDialog.getPlanTransferenciaUIData().getContrato());
				}
			};
			planTransferenciaDialog.setAceptarCommand(aceptarCommand);
		}
		planTransferenciaDialog.setCuentaEmpresa(editarSSUIData.getCuenta().isEmpresa());
		planTransferenciaDialog.show(contratoSeleccionado, filaSeleccionada);
	}	
	
	private void drawNuevoPlan(ContratoViewDto contrato) {
		contratosTable.setWidget(filaSeleccionada, 8, new PlanCesionarioConLapiz(contrato.getPlan().getItemText()));
		contratosTable.setHTML(filaSeleccionada, 9, contrato.getPrecioPlanCesionario());
	}
	
	private void selectAllContratosRow(){
		CheckBox chekAll = (CheckBox)contratosTable.getWidget(0, 0);;
		boolean selectAll = chekAll.getValue();
		for (int i = 0; i < contratosTable.getRowCount(); i++) {
			CheckBox chek = (CheckBox)contratosTable.getWidget(i, 0);
			chek.setValue(selectAll);
		}
	}

	public List<ContratoViewDto>  getContratosSS() {
		List<ContratoViewDto> contratos = new ArrayList<ContratoViewDto>();
		for (ContratoViewDto cto : contratosActivosVisibles) {
			//TODO: -MGR- Verificar que se esten guardando todos los datos en la linea
			cto.setCodVantiveCesionario(editarSSUIData.getCuenta().getCodigoVantive());
			contratos.add(cto);
		}
		
		return contratos;
	}
	
	public CuentaDto getCtaCedenteDto() {
		return ctaCedenteDto;
	}

	public void setDatosSolicitud(SolicitudServicioDto solicitud){
		this.ctaCedenteDto = solicitud.getCuentaCedente();
		this.contratosActivosVisibles = solicitud.getContratosCedidos();
		//TODO: -MGR- Tiene que buscar todos los contratos y dejarlos cargados en this.todosContratosActivos
	}
		
}
