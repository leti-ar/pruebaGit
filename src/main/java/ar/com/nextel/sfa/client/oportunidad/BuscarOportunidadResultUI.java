package ar.com.nextel.sfa.client.oportunidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.nextel.business.oportunidades.search.result.OportunidadNegocioSearchResult;
import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author eSalvador
 */
public class BuscarOportunidadResultUI extends FlowPanel {
	private FlexTable resultTable;
	private FlowPanel resultTableWrapper;
	private SimplePanel numeroResultados;
	private TablePageBar tablePageBar;
	private List<OportunidadNegocioSearchResultDto> oportunidades;
	private OportunidadDto lastOportunidadSearchDto;
	private int numeroPagina = 1;
	private int offset;
	private Long totalRegistrosBusqueda;
	private DateTimeFormat FormattedDate = DateTimeFormat.getMediumDateFormat();
	private Label numResultadosLabel = new Label();

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarOportunidadResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new FlowPanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setLastVisible(false);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastOportunidadSearchDto.setOffset(tablePageBar.getOffset());
				List oportunidadesActuales = new ArrayList<OportunidadNegocioSearchResultDto>();
				if (tablePageBar.getPagina() <= (tablePageBar.getCantPaginas())){
					if ((oportunidades.size() >= 10) && (tablePageBar.getCantResultados() != 25 && tablePageBar.getCantResultados()!=75)){
						for (int i = (tablePageBar.getPagina()-1) *10; i < (tablePageBar.getPagina())*10; i++) {
							oportunidadesActuales.add(oportunidades.get(i));
						}
					}else {
						for (int i = (tablePageBar.getPagina()-1) *10; i < oportunidades.size(); i++) {
							oportunidadesActuales.add(oportunidades.get(i));
						}
					}
				loadTable(oportunidadesActuales);
				}else{
					tablePageBar.setPagina(tablePageBar.getPagina()-1);
					tablePageBar.setCantRegistrosParcI(tablePageBar.getCantRegistrosParcI()-10);
					tablePageBar.setCantRegistrosParcF(tablePageBar.getCantRegistrosParcF()-10);
					tablePageBar.refrescaLabelRegistros();
					ErrorDialog.getInstance().setTitle("Error");
					ErrorDialog.getInstance().show("No hay más registros disponibles en esta búsqueda.");
				}
				// tablePageBar.setCantPaginas(getTotalRegistrosBusqueda().intValue() /
				// tablePageBar.getCantResultados());
				//searchOportunidades(lastOportunidadSearchDto, false);
			}
		});
		add(resultTableWrapper);
		add(tablePageBar);
		
		numeroResultados = new SimplePanel();
		resultTable = new FlexTable();
		numeroResultados.setWidget(numResultadosLabel);
		numResultadosLabel.addStyleName("numeroResultadosLabel");
		resultTableWrapper.add(numeroResultados);
		resultTableWrapper.add(resultTable);		
		
		setVisible(false);
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
	 * hace click al boton Buscar)
	 *
	 * @author mrial
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
			public void success(List result) {
				if (result != null) {
					if (result.size() == 0) {
						MessageDialog.getInstance().showAceptar("No se encontraron datos con los criterios utilizados", MessageDialog.getCloseCommand());
					}
					setOportunidades(result);
				}
			}
		});
	}

	public void setOportunidades(List<OportunidadNegocioSearchResultDto> oportunidades) {
		this.oportunidades = oportunidades;
		tablePageBar.setPagina(1);
		List<OportunidadNegocioSearchResultDto> oportunidadesActuales = new ArrayList<OportunidadNegocioSearchResultDto>(); 
		if (oportunidades.size() >= 10){
			for (int i = 0; i < 10; i++) {
				oportunidadesActuales.add(oportunidades.get(i));
			}
			tablePageBar.setCantRegistrosParcI(1);
			tablePageBar.setCantRegistrosParcF(oportunidadesActuales.size());
		}else{
			for (int i = 0; i < oportunidades.size(); i++) {
				oportunidadesActuales.add(oportunidades.get(i));
			}
			tablePageBar.setCantRegistrosParcI(1);
			tablePageBar.setCantRegistrosParcF(oportunidadesActuales.size());
		}
		tablePageBar.setCantRegistrosTot(oportunidades.size());
		tablePageBar.refrescaLabelRegistros();
		loadTable(oportunidadesActuales);
	}

	@SuppressWarnings("deprecation")
	private void loadTable(List<OportunidadNegocioSearchResultDto> oportunidadesActuales) {
		while(resultTable.getRowCount() > 1){
			resultTable.removeRow(1);
		}
		initTable(resultTable);		
		int row = 1;
		for (OportunidadNegocioSearchResultDto oportunidad : oportunidadesActuales) {
		 // for (OportunidadNegocioSearchResultDto oportunidad : oportunidades) {
			resultTable.setWidget(row, 0, IconFactory.lapiz());
			resultTable.setHTML(row, 1, oportunidad.getRazonSocial());
			resultTable.setHTML(row, 2, oportunidad.getNombre());
			resultTable.setHTML(row, 3, oportunidad.getApellido());
			resultTable.setHTML(row, 4, oportunidad.getTelefonoPrincipal());
			resultTable.setHTML(row, 5, oportunidad.getNroDocumento());
			resultTable.setHTML(row, 6, oportunidad.getNroCuenta());			
			resultTable.setHTML(row, 7, FormattedDate.format(oportunidad.getFechaAsignacion()));
			resultTable.setHTML(row, 8, oportunidad.getEstadoOportunidad().getDescripcion());			
			row++;
		}
		numResultadosLabel.setText("Numero de Resultados: " + oportunidades.size());
		
		setVisible(true);
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "200px", "120px", "120px", "120px", "120px", "120px", "120px", "120px",};
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
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
