package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.enums.BuscoCuentaPorDniEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Muestra la tabla con los resultados de la busqueda de cuentas. También maneja la logica de busqueda para
 * facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaResultUI extends FlowPanel {

	static final String LUPA_TITLE = "Ver Infocom";
	static final String BLOQUEADO_TITLE = "Bloqueado para el usuario actual";
	static final String LAPIZ_TITLE = "Editar";
	static final String OTRO_BLOQUEO_TITLE = "Bloqueado por otro usuario";

	private NextelTable resultTable;
	private SimplePanel resultTableWrapper;
	private Label resultTotalCuentas;
	private Label mostrandoCantRegistros;
	private TablePageBar tablePageBar;
	private List<CuentaSearchResultDto> cuentas;
	private CuentaSearchDto lastCuentaSearchDto;
	//private int numeroPagina = 1;
	private Long totalRegistrosBusqueda;
	private BuscarCuentaController controller;

	public BuscarCuentaResultUI(BuscarCuentaController controller) {
		
		super();
		this.controller = controller;
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTotalCuentas = new Label(Sfa.constant().totalCuentasBuscadas());
		resultTotalCuentas.setVisible(false);
		resultTotalCuentas.addStyleName("titulo");
		mostrandoCantRegistros = new Label(Sfa.constant().registrosMostrados());
		mostrandoCantRegistros.addStyleName("titulo");
		mostrandoCantRegistros.setVisible(false);
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastCuentaSearchDto.setOffset(tablePageBar.getOffset());
				List <CuentaSearchResultDto>cuentasActuales = new ArrayList<CuentaSearchResultDto>();
				if (tablePageBar.getPagina() <= (tablePageBar.getCantPaginas())){
					if ((cuentas.size() >= 10) && (tablePageBar.getCantResultados() != 25 && tablePageBar.getCantResultados()!=75)){
						for (int i = (tablePageBar.getPagina()-1) *10; i < (tablePageBar.getPagina())*10; i++) {
							cuentasActuales.add(cuentas.get(i));
						}
					}else {
						for (int i = (tablePageBar.getPagina()-1) *10; i < cuentas.size(); i++) {
							cuentasActuales.add(cuentas.get(i));
						}
					}
				loadTable(cuentasActuales);
				}else{
					tablePageBar.setPagina(tablePageBar.getPagina()-1);
					tablePageBar.setCantRegistrosParcI(tablePageBar.getCantRegistrosParcI()-10);
					tablePageBar.setCantRegistrosParcF(tablePageBar.getCantRegistrosParcF()-10);
					tablePageBar.refrescaLabelRegistros();
					ErrorDialog.getInstance().setTitle("Error");
					ErrorDialog.getInstance().show("No hay más registros disponibles en esta búsqueda.");
				}
			}
		});
		add(resultTotalCuentas);
		add(resultTableWrapper);
		add(tablePageBar);
		add(mostrandoCantRegistros);
		setVisible(false);
	}

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
	 * hace click al boton Buscar)
	 * 
	 * @param: cuentaSearchDto
	 * */
	public void searchCuentas(CuentaSearchDto cuentaSearchDto) {
		tablePageBar.setOffset(0);
		tablePageBar.setCantResultados(cuentaSearchDto.getCantidadResultados());
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
				new DefaultWaitCallback<List<CuentaSearchResultDto>>() {
					public void success(List<CuentaSearchResultDto> result) {
						if (result.isEmpty()) {
							ErrorDialog.getInstance().show(
									"No se encontraron datos con el criterio utilizado.");
						}
						setCuentas(result);
						controller.setResultadoVisible(true);
					}
				});
	}
	
	public void setCuentas(List<CuentaSearchResultDto> cuentas) {
		this.cuentas = cuentas;
		resultTotalCuentas.setText(Sfa.constant().totalCuentasBuscadas() + String.valueOf(cuentas.size()));
		resultTotalCuentas.setVisible(true);
		tablePageBar.setPagina(1);
		List<CuentaSearchResultDto> cuentasActuales = new ArrayList<CuentaSearchResultDto>(); 
		if (cuentas.size() != 0){
			if (cuentas.size() >= 10){
				for (int i = 0; i < 10; i++) {
					cuentasActuales.add(cuentas.get(i));
				}
				tablePageBar.setCantRegistrosParcI(1);
				tablePageBar.setCantRegistrosParcF(cuentasActuales.size());
			}else{
				for (int i = 0; i < cuentas.size(); i++) {
					cuentasActuales.add(cuentas.get(i));
				}
				tablePageBar.setCantRegistrosParcI(1);
				tablePageBar.setCantRegistrosParcF(cuentasActuales.size());
			}
			tablePageBar.setCantRegistrosTot(cuentas.size());
			tablePageBar.refrescaLabelRegistros();
		} 
		loadTable(cuentasActuales);
	}

	private String getCondicionBusquedaPorDni(){
		String cond = "0";
		if ((lastCuentaSearchDto.getNumeroDocumento() == "")||(lastCuentaSearchDto.getNumeroDocumento() == null)) {
			cond = BuscoCuentaPorDniEnum.NO.getCondicion();
		}else{
			cond = BuscoCuentaPorDniEnum.SI.getCondicion();
		}
		return cond;
	}
	
	/**
	 * Crea una fila en la tabla por cada cuenta del CuentaSearchResultDto
	 */
	private void loadTable(List<CuentaSearchResultDto> cuentasActuales) {
		clearResultTable();
		int totalABuscar;
		if(cuentasActuales.size() < 10){
			totalABuscar = cuentasActuales.size();
		}else{
			totalABuscar = 10;			
		}
			
		for (int i = 0; i < totalABuscar; i++) {
			if (cuentasActuales.size() != 0){
			resultTable.setWidget(i+1, 0, IconFactory.lapizAnchor(UILoader.EDITAR_CUENTA + "?cuenta_id="
					+ cuentasActuales.get(i).getId() + "&cod_vantive=" + cuentas.get(i).getCodigoVantive() + "&por_dni=" + getCondicionBusquedaPorDni(), LAPIZ_TITLE));

			if (cuentasActuales.get(i).isPuedeVerInfocom()) {
				resultTable.setWidget(i+1, 1, IconFactory.lupa(LUPA_TITLE));
			}

			// LockingState == 1: Es cuando esta lockeado por el mismo usuario logueado (Verificar).
			if (cuentasActuales.get(i).getLockingState() == 1) {
				resultTable.setWidget(i+1, 2, IconFactory.locked(BLOQUEADO_TITLE));
			} else if (cuentas.get(i).getLockingState() == 2) {
				// LockingState == 2: Es cuando esta lockeado por otro usuario.
				resultTable.setWidget(i+1, 2, IconFactory.lockedOther(OTRO_BLOQUEO_TITLE));
			}

			resultTable.setHTML(i+1, 3, cuentasActuales.get(i).getNumero());
			resultTable.setHTML(i+1, 4, cuentasActuales.get(i).getRazonSocial());
			resultTable.setHTML(i+1, 5, cuentasActuales.get(i).getApellidoContacto());
			resultTable.setHTML(i+1, 6, cuentasActuales.get(i).getNumeroTelefono() != null ? cuentas.get(i).getNumeroTelefono() : "");
			}
		}
		setVisible(true);
	}

	/**
	 * Limpia la tabla de resultados
	 * 
	 * Borrando las filas de la tabla en vez de recrear la tabla entera.
	 */
	private void clearResultTable() {
		if (resultTable != null) {
			while (resultTable.getRowCount() > 1) {
				resultTable.removeRow(1);
			}
		} else {
			resultTable = new NextelTable();
			initTable(resultTable);
			resultTable.ensureDebugId(DebugConstants.BUSQUEDA_CUENTAS_TABLE_RESULT);
			resultTableWrapper.setWidget(resultTable);
		}
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

	/** Retorna el id de la Cuenta selecionada o null si no hay nada seleccionado */
	public Long getSelectedCuentaId() {
		Long idCuenta = null;
		if (resultTable != null && resultTable.getRowSelected() > 0) {
			idCuenta = cuentas.get(resultTable.getRowSelected() - 1).getId();
		}
		return idCuenta;
	}
}
