package ar.com.nextel.sfa.client.oportunidad;

import java.util.List;

import ar.com.nextel.business.oportunidades.search.result.OportunidadNegocioSearchResult;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author eSalvador
 */
public class BuscarOportunidadResultUI extends FlowPanel {
	private FlexTable resultTable;
	private SimplePanel resultTableWrapper;
	private TablePageBar tablePageBar;
	private List<OportunidadNegocioSearchResultDto> oportunidades;
	//private List oportunidades;
	private OportunidadDto lastOportunidadSearchDto;
	private int numeroPagina = 1;
	private int offset;
	private Long totalRegistrosBusqueda;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarOportunidadResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setLastVisible(false);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastOportunidadSearchDto.setOffset(tablePageBar.getOffset());
				// tablePageBar.setCantPaginas(getTotalRegistrosBusqueda().intValue() /
				// tablePageBar.getCantResultados());
				searchOportunidades(lastOportunidadSearchDto, false);
			}
		});
		add(resultTableWrapper);
		add(tablePageBar);
		setVisible(false);
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
	 * hace click al boton Buscar)
	 *
	 * @author eSalvador
	 * @param: OportunidadSearchDto
	 * */
		
	public void searchOportunidades(OportunidadDto oportunidadSearchDto) {	
		tablePageBar.setOffset(0);
		tablePageBar.setCantResultados(oportunidadSearchDto.getCantidadResultados());
		this.lastOportunidadSearchDto = oportunidadSearchDto;
		this.searchOportunidades(oportunidadSearchDto, true);
	}

	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez. (o
	 * sea, cada vez que se hace click en los botones del paginador)
	 * 
	 * @author mrial
	 * @param: oportunidadSearchDto
	 * @param: firstTime
	 **/
	private void searchOportunidades(OportunidadDto oportunidadSearchDto, boolean firstTime) {
		OportunidadNegocioRpcService.Util.getInstance().searchOportunidad(oportunidadSearchDto, new DefaultWaitCallback<List<OportunidadNegocioSearchResultDto>>() {
		//OportunidadNegocioRpcService.Util.getInstance().searchOportunidad(oportunidadSearchDto, new DefaultWaitCallback<List>() {
			public void success(List result) {
				if (result != null) {
					if (result.size() == 0) {
						MessageDialog.getInstance().showAceptar("No se encontraron datos con el criterio utilizado", MessageDialog.getCloseCommand());
					}
					setOportunidades(result);
				}
			}
		});
	}

	public void setOportunidades(List<OportunidadNegocioSearchResultDto> oportunidades) {
		this.oportunidades = oportunidades;
		loadTable();
	}

	@SuppressWarnings("deprecation")
	private void loadTable() {
		if (resultTable != null) {
			resultTable.unsinkEvents(Event.getEventsSunk(resultTable.getElement()));
			resultTable.removeFromParent();
		}
		resultTable = new FlexTable();
		initTable(resultTable);
		resultTableWrapper.setWidget(resultTable);
		int row = 1;
		for (OportunidadNegocioSearchResultDto oportunidad : oportunidades) {
			resultTable.setHTML(row, 1, oportunidad.getRazonSocial());
			resultTable.setHTML(row, 2, oportunidad.getNombre());
			resultTable.setHTML(row, 3, oportunidad.getApellido());
			resultTable.setHTML(row, 4, oportunidad.getTelefonoPrincipal());
			resultTable.setHTML(row, 5, oportunidad.getNroDocumento());
			resultTable.setHTML(row, 6, oportunidad.getNroCuenta());
			resultTable.setHTML(row, 7, oportunidad.getFechaAsignacion().toString());
			resultTable.setHTML(row, 8, oportunidad.getEstadoOportunidad().getDescripcion());			
			row++;
		}
		setVisible(true);
	}

	private void initTable(FlexTable table) {
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
		table.setHTML(0, 1, Sfa.constant().razonSocial());
		table.setHTML(0, 2, Sfa.constant().nombre());
		table.setHTML(0, 3, Sfa.constant().apellido());
		table.setHTML(0, 4, Sfa.constant().telefono());
		table.setHTML(0, 5, Sfa.constant().nroDocumento());
		table.setHTML(0, 6, Sfa.constant().nroCuenta());
		table.setHTML(0, 7, Sfa.constant().fecha());
		table.setHTML(0, 8, Sfa.constant().estadoOportunidad());
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
