package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.LineaTransfSolicitudServicioDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class DatosTransferenciaSSUI extends Composite implements ClickHandler{
	
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
	
	private CuentaDto cuentaDto;
	private List<ContratoViewDto> todosContratosActivos = new ArrayList<ContratoViewDto>();
	private List<ContratoViewDto> contratosActivosVisibles = new ArrayList<ContratoViewDto>();
		
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
	}
	
	private Widget getNssLayout() {
		nnsLayout = new Grid(1, 6);
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
		refresBusqLayout();		
		return busqLayout;
	}
	
	private Widget getContratosLayout(){
		contratosLayout = new Grid(5, 1);
		contratosLayout.addStyleName("layout");
		contratosLayout.setWidth("100%");
		
		contratosTable = new FlexTable();
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
		//TODO: -MGR- habilitar esto cuendo se termine todo
		//if(ClientContext.getInstance().checkPermiso(PermisosEnum.VER_COMBO_VENDEDOR.getValue())){
			nnsLayout.setHTML(0, 4, Sfa.constant().vendedor());
			nnsLayout.setWidget(0, 5, editarSSUIData.getVendedor());
		//}
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
		contratosLayout.setWidget(0, 0, contratosTable);
		//TODO: Ver como conviene dejar mas espacio
		contratosLayout.setHTML(1, 0, " ");
		contratosLayout.setHTML(2, 0, "Facturados");
		contratosLayout.setWidget(3, 0, facturadosTable);
		contratosLayout.setHTML(4, 0, "Total servicios por equipo:");
	}

	public void refresh() {
		refreshNssLayout();
		refresObsLayout();
		refresCedenteLayout();
		refresBusqLayout();
		refresContratosLayout();
	}
	
	public void showBusqClienteCedente(){
		if(busqClienteCedenteDialog == null){
			busqClienteCedenteDialog = new BusqClienteCedenteDialog("Buscar cliente cedente", this.controller);
			Command buscarCommmand = new Command() {
				public void execute() {
					//TODO: -MGR- Verificar que validaciones hace al buscar
					CuentaRpcService.Util.getInstance().searchCuentaDto(
							busqClienteCedenteDialog.getBusqClienteCedenteUIData().getCuentaSearch(), 
							new DefaultWaitCallback<CuentaDto>(){

								public void success(CuentaDto result) {
									
									if (result == null) {
										ErrorDialog.getInstance().show(
												"No se encontraron contratos con el criterio utilizado.", false);
									}
									else{
										cuentaDto = result;
										//TODO: -MGR- Verificar si la cuenta que trae es valida, sino
										//no hay que ir a buscar los contratos
										controller.getEditarSSUIData().getClienteCedente().setText(cuentaDto.getCodigoVantive());
										CuentaRpcService.Util.getInstance().searchContratosActivos(
												result, new DefaultWaitCallback<List<ContratoViewDto>>() {
													public void success(List<ContratoViewDto> result) {
														if (result.isEmpty()) {
															ErrorDialog.getInstance().show(
																	"No se encontraron contratos con el criterio utilizado.", false);
														}
														todosContratosActivos = result;
														contratosActivosVisibles = result;
														refreshTablaContratos();
													}
												});
										busqClienteCedenteDialog.hide();
									}
									
								}
							});
					}
				};
			this.busqClienteCedenteDialog.setBuscarCommand(buscarCommmand);
		}
		this.busqClienteCedenteDialog.mostrarDialogo();
	}
	
	private void refreshTablaContratos(){
		List<String> aa;
		for (int i = 0; i < contratosActivosVisibles.size(); i++) {
			drawContrato(i + 1 , contratosActivosVisibles.get(i));
		}
	}
	
	private void drawContrato(int newRow, ContratoViewDto cto){
		
		contratosTable.setWidget(newRow, 0, new CheckBox());
		contratosTable.setText(newRow, 1, cto.getId().toString());
		contratosTable.setHTML(newRow, 2, "Fecha Estado");
		contratosTable.setText(newRow, 3, cto.getTelefono());
		contratosTable.setHTML(newRow, 4, "Flota");
		contratosTable.setText(newRow, 5, cto.getModelo());
		contratosTable.setHTML(newRow, 6, "Contratacion");
		contratosTable.setHTML(newRow, 7, "Plan Cedente");
		contratosTable.setWidget(newRow, 8, IconFactory.lapiz());
		contratosTable.setHTML(newRow, 9, "$0.0");
		contratosTable.setHTML(newRow, 10, "Os");
		contratosTable.setHTML(newRow, 11, "Modalidad");
		//contratosTable.setText(newRow, 12, ""cto.getSuscriptor());
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
		}
		
	}
	
	public void onTableContratosClick(Widget sender, final int row, int col){
		if(row == 0 && col == 0){
			selectAllContratosRow();
		}
		else if(row > 0){
			if(col > 1){
				selectContratoRow(row);
				drawFacturados(row);
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
	
	private void selectAllContratosRow(){
		CheckBox chekAll = (CheckBox)contratosTable.getWidget(0, 0);;
		boolean selectAll = chekAll.getValue();
		for (int i = 0; i < contratosTable.getRowCount(); i++) {
			CheckBox chek = (CheckBox)contratosTable.getWidget(i, 0);
			chek.setValue(selectAll);
		}
		
	}

	public List<LineaTransfSolicitudServicioDto>  getLineasTransferenciaSS() {
		List<LineaTransfSolicitudServicioDto> lineas = new ArrayList<LineaTransfSolicitudServicioDto>();
		for (ContratoViewDto contrato : contratosActivosVisibles) {
			LineaTransfSolicitudServicioDto lineaTranf = new LineaTransfSolicitudServicioDto();
			
			lineaTranf.setContrato(contrato.getId());
			lineaTranf.setFechaEstadoContrato(contrato.getFechaEstado());
			lineaTranf.setTelefono(contrato.getTelefono());
			lineaTranf.setFlotaId(contrato.getFlotaId());
			lineaTranf.setModelo(contrato.getModelo());
			lineaTranf.setContratacion(contrato.getContratacion());
			//TODO: CUSTOMER_NUMBER ok 
			lineaTranf.setCustomernumber(editarSSUIData.getCuenta().getCodigoVantive());
			
			//TODO: ID_PLAN_NUEVO tiene que ser el plan cesionario
			//lineaTranf.setPlanNuevo(contrato.getPlanCedente())
			
			//TODO: PRECIO_VENTA_PLAN_NUEVO es el precio del plan cesionario
			//lineaTranf.setPrecioVtaPlanNuevo(contrato.getPlanCedente().getprecio())
			
			//TODO; PRECIO_PLAN_CEDENTE tengo que sacarlo del plan cedente
			//lineaTranf.setPrecioPlanCedente(contrato.getPlanCedente())
			
			//TODO: DESCRIPCION_PLAN_CEDENTE
			//lineaTranf.setDescripcionPlanCedente(descripcionPlanCedente)
			
			//TODO: ID_TIPO_TELEFONIA_CEDENTE del plan
			//lineaTranf.setIdTipoTelefoniaCedente(idTipoTelefoniaCedente)
			
			//TODO: ID_MODALIDAD_COBRO_PLAN_NUEVO - MODALIDAD_COBRO_PLAN_CEDENTE
			//lineaTranf.setModalidadCobro(new ModalidadCobroDto());
			//lineaTranf.setModalidadCobroPlanCedente(modalidadCobroPlanCedente)
			
			//TODO: CODIGO_BSCS_PLAN_CEDENTE
			//lineaTranf.setCodigoBSCSPlanCedente(codigoBSCSPlanCedente)
			
			//TODO: NUMERO_IMEI cto
			lineaTranf.setNumeroIMEI(contrato.getNumeroIMEI());
			
			//NUMERO_SIMCARD cto
			lineaTranf.setSimCard(contrato.getNumeroSimCard());
			
			//TODO: NUMERO_SERIE cto
			lineaTranf.setNumeroSerie(contrato.getNumeroSerie());
			
			//TODO: NUMERADOR_LINEA
			//lineaTranf.setNumeradorLinea(0l);
			
			//TODO: ID_VANTIVE_LINEA
			//lineaTranf.setIdVantiveLinea(0l);

			//TODO: ID_VANTIVE_DETALLE
			//lineaTranf.setIdVantiveDetalle(0l);
			
			lineas.add(lineaTranf);
			                        
		}
		
		return lineas;
	}
}
