package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
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
	private List<CuentaPotencialDto> ctaPotencial;
	//private Reserva lastReserva;
	//private OperacionEnCurso lastOpEnCurso;
	private int numeroPagina = 1;
	private Long totalRegistrosBusqueda;
	
	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public OperacionEnCursoResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapperReserva = new SimplePanel();
		resultTableWrapperReserva.addStyleName("resultTableWrapper");
		
		resultTableWrapperOpCurso = new SimplePanel();
		resultTableWrapperOpCurso.addStyleName("resultTableWrapper");
		
		tablePageBarReserva = new TablePageBar();
		tablePageBarReserva.setLastVisible(false);
		tablePageBarReserva.setBeforeClickCommand(new Command(){
			public void execute() {
				//lastCuentaSearchDto.setOffset(tablePageBar.getOffset());
				searchReservas(false);
			}
		});
		
		tablePageBarOpCurso = new TablePageBar();
		tablePageBarOpCurso.setLastVisible(false);
		tablePageBarOpCurso.setBeforeClickCommand(new Command(){
			public void execute() {
				//lastCuentaSearchDto.setOffset(tablePageBar.getOffset());
				searchOpEnCurso(false);
			}
		});
		
		add(resultTableWrapperReserva);
		add(tablePageBarReserva);
		add(resultTableWrapperOpCurso);
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
	 * @param: ?? (averiguar cual va en lugar del cuentaSearchDto)
	 * */
	public void searchReservas() {
		tablePageBarReserva.setOffset(0);
		//tablePageBarReserva.setCantResultadosVisibles(cuentaSearchDto.getCantidadResultados());
		//this.lastReserva = reserva;
		this.searchReservas(true);
	}

	
	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez.
	 * (o sea, cada vez que se hace click en los botones del paginador)
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	**/
	private void searchReservas(boolean firstTime){
		OperacionesRpcService.Util.getInstance().searchReservas(
				new DefaultWaitCallback<List<CuentaPotencialDto>>() {
					public void success(List<CuentaPotencialDto> result) {
						setReserva(result);
					}
				});
	}
	
	
	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca.
	 * (o sea, cuando se hace click al boton Buscar)
	 * @param: ?? (averiguar cual va en lugar del cuentaSearchDto)
	 * */
	public void searchOpEnCurso() {
		tablePageBarOpCurso.setOffset(0);
		//tablePageBarOpCurso.setCantResultadosVisibles(cuentaSearchDto.getCantidadResultados());
		//this.lastOpEnCurso = opEnCurso;
		this.searchOpEnCurso(true);
	}

	
	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez.
	 * (o sea, cada vez que se hace click en los botones del paginador)
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	**/
	private void searchOpEnCurso(boolean firstTime){
		OperacionesRpcService.Util.getInstance().searchOpEnCurso(
				new DefaultWaitCallback<List<OperacionEnCursoDto>>() {
					public void success(List<OperacionEnCursoDto> result) {
						setOpCurso(result);
					}
				});
	}
	
	public void setOpCurso(List<OperacionEnCursoDto> opEnCurso) {
		this.opEnCurso = opEnCurso;
		loadTableOpCurso();
	}

	public void setReserva(List<CuentaPotencialDto> ctaPotencial) {
		this.ctaPotencial = ctaPotencial;
		loadTableReservas();
	}
	
	private void loadTableOpCurso() {
		if (resultTableOpEnCurso != null) {
			resultTableOpEnCurso.unsinkEvents(Event.getEventsSunk(resultTableOpEnCurso.getElement()));
			resultTableOpEnCurso.removeFromParent();
		}
		resultTableOpEnCurso = new FlexTable();
		initTableOpenCurso(resultTableOpEnCurso);
		resultTableWrapperOpCurso.setWidget(resultTableOpEnCurso);
		int row = 1;
		for (OperacionEnCursoDto opCursoDto : opEnCurso) {
			resultTableOpEnCurso.setWidget(row, 0, IconFactory.lapiz());
			//if (opCurso.isPuedeVerInfocom()) {
			//	resultTableOpEnCurso.setWidget(row, 1, IconFactory.lupa());
			//}
			if (true) {
				resultTableOpEnCurso.setWidget(row, 2, IconFactory.locked());
			}
			resultTableOpEnCurso.setHTML(row, 3, opCursoDto.getNumeroCliente());
			resultTableOpEnCurso.setHTML(row, 4, opCursoDto.getRazonSocial());
			resultTableOpEnCurso.setHTML(row, 5, opCursoDto.getDescripcionGrupo());
			row++;
		}
		setVisible(true);
	}

	private void loadTableReservas() {
		if (resultTableReservas != null) {
			resultTableReservas.unsinkEvents(Event.getEventsSunk(resultTableReservas.getElement()));
			resultTableReservas.removeFromParent();
		}
		resultTableReservas = new FlexTable();
		initTableReservas(resultTableReservas);
		resultTableWrapperReserva.setWidget(resultTableReservas);
		int row = 1;
		for (CuentaPotencialDto ctaPotencialDto : ctaPotencial) {
			resultTableReservas.setWidget(row, 0, IconFactory.lapiz());
			//if (reserva.isPuedeVerInfocom()) {
			//	resultTableReservas.setWidget(row, 1, IconFactory.lupa());
			//}
			if (true) {
				resultTableReservas.setWidget(row, 2, IconFactory.locked());
			}
			resultTableReservas.setHTML(row, 3, ctaPotencialDto.getCodigoVantive());
			resultTableReservas.setHTML(row, 4, ctaPotencialDto.getRazonSocial());
			//resultTableReservas.setHTML(row, 5, reservaDto.getTelefono().toString());
			resultTableReservas.setHTML(row, 5, ctaPotencialDto.getNumero());
			row++;
		}
		setVisible(true);
	}
	
	private void initTableReservas(FlexTable table) {
		String[] widths = { "24px", "24px", "24px", "150px", "250px", "195px", "170px", };
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
		table.setHTML(0, 3, "Número Cliente");
		table.setHTML(0, 4, "Razon Social");
		//table.setHTML(0, 5, "Telefono");
		table.setHTML(0, 5, "Numero");
	}

	private void initTableOpenCurso(FlexTable table) {
		String[] widths = { "24px", "24px", "24px", "150px", "250px", "195px", "170px", };
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
		table.setHTML(0, 3, "Número Cliente");
		table.setHTML(0, 4, "Razon Social");
		table.setHTML(0, 5, "Grupo SS");
	}
}