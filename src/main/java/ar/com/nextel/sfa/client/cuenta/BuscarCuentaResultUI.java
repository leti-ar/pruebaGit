package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Muestra la tabla con los resultados de la busqueda de cuentas. También maneja la logica de busqueda para
 * facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaResultUI extends FlowPanel {

	private FlexTable resultTable;
	private SimplePanel resultTableWrapper;
	private TablePageBar tablePageBar;
	private List<CuentaDto> cuentas;
	private CuentaSearchDto lastCuentaSearchDto;
	private int numeroPagina = 1;
	private Long totalRegistrosBusqueda;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarCuentaResultUI(BuscarCuentaController controller) {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setLastVisible(false);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastCuentaSearchDto.setOffset(tablePageBar.getOffset());
				// tablePageBar.setCantPaginas(getTotalRegistrosBusqueda().intValue() /
				// tablePageBar.getCantResultados());
				searchCuentas(lastCuentaSearchDto, false);
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
	public void searchCuentas(CuentaSearchDto cuentaSearchDto) {
		tablePageBar.setOffset(0);
		tablePageBar.setCantResultadosVisibles(cuentaSearchDto.getCantidadResultados());
		this.lastCuentaSearchDto = cuentaSearchDto;
		this.searchCuentas(cuentaSearchDto, true);
	}

	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez. (o
	 * sea, cada vez que se hace click en los botones del paginador)
	 * 
	 * @param: cuentaSearchDto
	 * @param: firstTime
	 **/
	private void searchCuentas(CuentaSearchDto cuentaSearchDto, boolean firstTime) {
		CuentaRpcService.Util.getInstance().searchCuenta(cuentaSearchDto,
				new DefaultWaitCallback<List<CuentaDto>>() {
					public void success(List<CuentaDto> result) {
						if(result.isEmpty()){
							ErrorDialog.getInstance().show("No se encontraron datos con el criterio utilizado.");
						}
						setCuentas(result);
						// setTotalRegistrosBusqueda(CuentaRpcService.Util.getInstance().searchTotalCuentas(cuentaSearchDto));
					}
				});
	}

	public void setCuentas(List<CuentaDto> cuentas) {
		this.cuentas = cuentas;
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
		for (CuentaDto cuenta : cuentas) {
			resultTable.setWidget(row, 0, new Hyperlink(IconFactory.lapiz().toString(),true,UILoader.EDITAR_CUENTA + "?cuenta_id=" + cuenta.getId()));
			if (cuenta.isPuedeVerInfocom()) {
				resultTable.setWidget(row, 1, IconFactory.lupa());
			}
			if (true) {
				resultTable.setWidget(row, 2, IconFactory.locked());
			}
			resultTable.setHTML(row, 3, cuenta.getNumero());
			resultTable.setHTML(row, 4, cuenta.getRazonSocial());
			resultTable.setHTML(row, 5, cuenta.getApellidoContacto());
			resultTable.setHTML(row, 6, cuenta.getNumeroTelefono() != null ? cuenta.getNumeroTelefono() : "");
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
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, "Número");
		table.setHTML(0, 4, "Razón Social");
		table.setHTML(0, 5, "Responsable");
		table.setHTML(0, 6, "Número de teléfono");

	}
}
