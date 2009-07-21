package ar.com.nextel.sfa.client.operaciones;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Muestra la tabla con los resultados de la busqueda de operaciones. También maneja la logica de busqueda para
 * facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author eSalvador
 * 
 */
public class OperacionEnCursoResultUI extends FlowPanel {

	private FlexTable resultTableReservas;
	private FlexTable resultTableOpEnCurso;
	private SimplePanel resultTableWrapperReserva;
	private SimplePanel resultTableWrapperOpCurso;
	private TablePageBar tablePageBarReserva;
	private TablePageBar tablePageBarOpCurso;
	private List<OperacionEnCursoDto> opEnCurso;
	private List<VentaPotencialVistaDto> vtaPotencial;
	private FlowPanel flowPanelReservaT;
	private FlowPanel flowPanelOppEnCursoT;
	private Label reservasLabel;
	private Label oppEnCursoLabel;
	private Label reservasNoConsultadas;
	private Label numOperaciones;
	private int cantResultadosPorPagina = 5;
	private String NumeroVtasPotNoConsultadas;


	public OperacionEnCursoResultUI() {
		super();
		addStyleName("gwt-OportunidadesResultPanel");
		resultTableWrapperReserva = new SimplePanel();
		resultTableWrapperReserva.addStyleName("resultTableWrapper");
		
		resultTableWrapperOpCurso = new SimplePanel();
		resultTableWrapperOpCurso.addStyleName("resultTableWrapper");
		
		tablePageBarReserva = new TablePageBar();
		tablePageBarReserva.setBeforeClickCommand(new Command(){
			public void execute() {
				setReserva();
			}
		});
		
		tablePageBarOpCurso = new TablePageBar();
		tablePageBarOpCurso.setBeforeClickCommand(new Command(){
			public void execute() {
				setOpCurso();
			}
		});
		
		flowPanelReservaT = new FlowPanel();
		reservasLabel = new Label("OPP/Reserva");
		reservasLabel.addStyleName("oppEnCursoTitulosLabel");
		reservasNoConsultadas = new Label();		
		reservasNoConsultadas.addStyleName("oppEnCursoCantLabel");
		
		flowPanelOppEnCursoT = new FlowPanel();
		oppEnCursoLabel = new Label("Operaciones en curso");
		oppEnCursoLabel.addStyleName("oppEnCursoTitulosLabel");
		numOperaciones = new Label();
		numOperaciones.addStyleName("oppEnCursoCantLabel");
		
		resultTableReservas = new FlexTable();
		resultTableOpEnCurso = new FlexTable();
		resultTableWrapperReserva.add(resultTableReservas);
		resultTableWrapperOpCurso.add(resultTableOpEnCurso);
		
		flowPanelReservaT.add(resultTableWrapperReserva);
		flowPanelOppEnCursoT.add(resultTableWrapperOpCurso);		
		
		add(reservasLabel);
		add(reservasNoConsultadas);
		add(flowPanelReservaT);
		add(tablePageBarReserva);
		add(oppEnCursoLabel);
		add(numOperaciones);
		add(flowPanelOppEnCursoT);
		add(tablePageBarOpCurso);
		setVisible(true);
	}
	
	public void searchOperaciones() {
		this.searchOpEnCurso();
		this.searchReservas();
	}
	
	
	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca.
	 * (o sea, cuando se hace click al boton Buscar)
	 * */
	public void searchReservas() {
		tablePageBarReserva.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBarReserva.setCantRegistrosParcI(1);
		tablePageBarReserva.setCantRegistrosParcF(tablePageBarReserva.getCantResultadosPorPagina());
		this.searchReservas(true);
	}

	
	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez.
	 * (o sea, cada vez que se hace click en los botones del paginador)
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	**/
	private void searchReservas(boolean firstTime){
		OperacionesRpcService.Util.getInstance().searchReservas(new DefaultWaitCallback<VentaPotencialVistaResultDto>() {
					public void success(VentaPotencialVistaResultDto result) {
						if (result!=null) {
							vtaPotencial = result.getVentasPotencialesVistaDto();
							NumeroVtasPotNoConsultadas = result.getNumeroVtasPotNoConsultadas();
							tablePageBarReserva.setCantResultados(vtaPotencial.size());
							double calculoCantPaginasReserva = ((double) vtaPotencial.size() / (double) cantResultadosPorPagina);
							int cantPaginasReserva = (int) Math.ceil(calculoCantPaginasReserva);
							tablePageBarReserva.setCantPaginas(cantPaginasReserva);
							tablePageBarReserva.setPagina(1);
							setReserva();
						}
					}
				});
	}
	
	
	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca.
	 * (o sea, cuando se hace click al boton Buscar)
	 * */
	public void searchOpEnCurso() {
		tablePageBarOpCurso.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBarOpCurso.setCantRegistrosParcI(1);
		tablePageBarOpCurso.setCantRegistrosParcF(tablePageBarOpCurso.getCantResultadosPorPagina());
		this.searchOpEnCurso(true);
	}

	
	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez.
	 * (o sea, cada vez que se hace click en los botones del paginador)
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	**/
	private void searchOpEnCurso(boolean firstTime){
		OperacionesRpcService.Util.getInstance().searchOpEnCurso(new DefaultWaitCallback<List<OperacionEnCursoDto>>() {
			public void success(List<OperacionEnCursoDto> result) {
				if (result!=null) {
					opEnCurso = result;
					tablePageBarOpCurso.setCantResultados(opEnCurso.size());
					double calculoCantPaginasOpEnCurso = ((double) opEnCurso.size() / (double) cantResultadosPorPagina);
					int cantPaginasOpEnCurso =  (int) Math.ceil(calculoCantPaginasOpEnCurso);
					tablePageBarOpCurso.setCantPaginas(cantPaginasOpEnCurso);
					tablePageBarOpCurso.setPagina(1);
					setOpCurso();
				}
			}
		});
	}
	
	public void setOpCurso() {
		List<OperacionEnCursoDto> opEnCursoActuales = new ArrayList<OperacionEnCursoDto>(); 
		if (opEnCurso.size() >= cantResultadosPorPagina){
			for (int i = (tablePageBarOpCurso.getCantRegistrosParcI()-1); i < tablePageBarOpCurso.getCantRegistrosParcF(); i++) {
				opEnCursoActuales.add(opEnCurso.get(i));
			}
		}else{
			for (int i = 0; i < opEnCurso.size(); i++) {
				opEnCursoActuales.add(opEnCurso.get(i));
			}
		}
		tablePageBarOpCurso.setCantRegistrosTot(opEnCurso.size());
		tablePageBarOpCurso.refrescaLabelRegistros();
		initTableOpenCurso(resultTableOpEnCurso);
		loadTableOpCurso(opEnCursoActuales);
	}

	public void setReserva() {
		List<VentaPotencialVistaDto> vtaPotencialActuales = new ArrayList<VentaPotencialVistaDto>(); 
		if (vtaPotencial.size() >= cantResultadosPorPagina){
			for (int i = (tablePageBarReserva.getCantRegistrosParcI()-1); i < tablePageBarReserva.getCantRegistrosParcF(); i++) {
				vtaPotencialActuales.add(vtaPotencial.get(i));
			}
		}else{
			for (int i = 0; i < vtaPotencial.size(); i++) {
				vtaPotencialActuales.add(vtaPotencial.get(i));
			}
		}
		tablePageBarReserva.setCantRegistrosTot(vtaPotencial.size());
		tablePageBarReserva.refrescaLabelRegistros();
		initTableReservas(resultTableReservas);
		loadTableReservas(vtaPotencialActuales);
	}
	
	
	private void loadTableOpCurso(List<OperacionEnCursoDto> opEnCursoActuales) {		
		while(resultTableOpEnCurso.getRowCount() > 1){
			resultTableOpEnCurso.removeRow(1);
		}
		//initTableOpenCurso(resultTableOpEnCurso);
		resultTableWrapperOpCurso.setWidget(resultTableOpEnCurso);
		int row = 1;
		for (OperacionEnCursoDto opCursoDto : opEnCursoActuales) {
			resultTableOpEnCurso.setWidget(row, 0, IconFactory.lapiz());
			//if (opEnCurso.isPuedeVerInfocom()) {
				resultTableOpEnCurso.setWidget(row, 1, IconFactory.silvioSoldan());
			//}
			if (true) {
				resultTableOpEnCurso.setWidget(row, 2, IconFactory.cancel());
			}
			resultTableOpEnCurso.setHTML(row, 3, opCursoDto.getNumeroCliente());
			resultTableOpEnCurso.setHTML(row, 4, opCursoDto.getRazonSocial());
			resultTableOpEnCurso.setHTML(row, 5, opCursoDto.getDescripcionGrupo());
			row++;
		}
		numOperaciones.setText("N° Operaciones: " + opEnCurso.size());
		
		setVisible(true);
	}

	private void loadTableReservas(List<VentaPotencialVistaDto> vtaPotencialActuales) {
		while(resultTableReservas.getRowCount() > 1){
			resultTableReservas.removeRow(1);
		}
		//initTableReservas(resultTableReservas);
		resultTableWrapperReserva.setWidget(resultTableReservas);
		int row = 1;
		for (VentaPotencialVistaDto vtaPotencialDto : vtaPotencialActuales) {
			resultTableReservas.setWidget(row, 0, IconFactory.lapiz());
			//if (reserva.isPuedeVerInfocom()) {
				resultTableReservas.setWidget(row, 1, IconFactory.silvioSoldan());
			//}vb
			if (true) {
				resultTableReservas.setWidget(row, 2, IconFactory.prospect());
			}
			resultTableReservas.setWidget(row, 3, IconFactory.oportunidad());
			resultTableReservas.setHTML(row, 4, vtaPotencialDto.getNumeroCliente());
			resultTableReservas.setHTML(row, 5, vtaPotencialDto.getRazonSocial());
			resultTableReservas.setHTML(row, 6, vtaPotencialDto.getTelefono().toString());
			resultTableReservas.setHTML(row, 7, vtaPotencialDto.getNumero());
			row++;
		}
		reservasNoConsultadas.setText("Opps/Reservas no consultadas: " + NumeroVtasPotNoConsultadas);
		
		setVisible(true);
	}
	
	private void initTableReservas(FlexTable table) {
		String[] widths = { "24px", "24px", "24px", "24px", "180px", "400px", "160px", "150px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().whiteSpace());
		table.setHTML(0, 4, Sfa.constant().numeroClienteCompleto());
		table.setHTML(0, 5, Sfa.constant().razonSocial());
		table.setHTML(0, 6, Sfa.constant().telefono());
		table.setHTML(0, 7, Sfa.constant().numero());
	}

	private void initTableOpenCurso(FlexTable table) {
		String[] widths = { "24px", "24px", "24px", "180px", "400px", "160px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().numeroClienteCompleto());
		table.setHTML(0, 4, Sfa.constant().razonSocial());
		table.setHTML(0, 5, Sfa.constant().grupoSS());
	}
}