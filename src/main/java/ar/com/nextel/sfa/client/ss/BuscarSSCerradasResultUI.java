package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioDto;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Muestra la tabla con los resultados de la busqueda de cuentas. También maneja la logica de busqueda para
 * facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author jlgperez
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel {

	private FlexTable resultTable;
	private SimplePanel resultTableWrapper;
	private TablePageBar tablePageBar;
	private List<SolicitudServicioDto> solicitudesServicioDto;
	private SolicitudServicioDto lastSSCerradaSearchDto;
	private int numeroPagina = 1;
	private Long totalRegistrosBusqueda;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setLastVisible(false);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastSSCerradaSearchDto.setOffset(tablePageBar.getOffset());
				// tablePageBar.setCantPaginas(getTotalRegistrosBusqueda().intValue() /
				// tablePageBar.getCantResultados());
				searchSSCerradas(lastSSCerradaSearchDto, false);
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
	 * @param: cuentaSearchDto
	 * */
	public void searchSSCerradas(SolicitudServicioDto solicitudServicioDto) {
		tablePageBar.setOffset(0);
		tablePageBar.setCantResultadosVisibles(solicitudServicioDto.getCantidadResultados());
		this.lastSSCerradaSearchDto = solicitudServicioDto;
		this.searchSSCerradas(solicitudServicioDto, true);
	}

	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez. (o
	 * sea, cada vez que se hace click en los botones del paginador)
	 * 
	 * @param: cuentaSearchDto
	 * @param: firstTime
	 **/
	private void searchSSCerradas(SolicitudServicioDto solicitudServicioDto, boolean firstTime) {
		CuentaRpcService.Util.getInstance().searchSSCerrada(solicitudServicioDto,
				new DefaultWaitCallback<List<SolicitudServicioDto>>() {
					public void success(List<SolicitudServicioDto> result) {
						setSolicitudServicioDto(result);
						// setTotalRegistrosBusqueda(CuentaRpcService.Util.getInstance().searchTotalCuentas(cuentaSearchDto));
					}
				});
	}

	public void setSolicitudServicioDto(List<SolicitudServicioDto> solicitudServicioDto) {
		this.solicitudesServicioDto = solicitudServicioDto;
		loadTable();
	}

	private void loadTable() {
		if (resultTable != null) {
			resultTable.unsinkEvents(Event.getEventsSunk(resultTable.getElement()));
			resultTable.removeFromParent();
		}
		resultTable = new FlexTable();
		initTable(resultTable);
		resultTableWrapper.setWidget(resultTable);
		int row = 1;
		for (SolicitudServicioDto solicitudServicioDto : solicitudesServicioDto) {
			resultTable.setHTML(row, 0, "");
			resultTable.setHTML(row, 1, solicitudServicioDto.getNumeroSS());
			resultTable.setHTML(row, 2, solicitudServicioDto.getNumeroCuenta());
			resultTable.setHTML(row, 3, solicitudServicioDto.getRazonSocial());
			resultTable.setHTML(row, 4, solicitudServicioDto.getCantidadEquipos().toString());
			resultTable.setHTML(row, 5, solicitudServicioDto.getPataconex());
			resultTable.setHTML(row, 6, solicitudServicioDto.getFirmas() ? "SI" : "NO");
			row++;
		}
		setVisible(true);
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "150px", "150px", "150px", "250px", "195px", "170px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, "N° SS");
		table.setHTML(0, 2, "N° Cuenta");
		table.setHTML(0, 3, "Razon Social");
		table.setHTML(0, 4, "Equipos");
		table.setHTML(0, 5, "Pataconex");
		table.setHTML(0, 6, "Firmas");
	}
}
