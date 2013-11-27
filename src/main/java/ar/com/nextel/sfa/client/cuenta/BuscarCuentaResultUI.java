package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.CuentaSearchDto;
import ar.com.nextel.sfa.client.dto.CuentaSearchResultDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.infocom.InfocomUI;
import ar.com.nextel.sfa.client.widget.ModalMessageDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.widget.table.RowListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Muestra la tabla con los resultados de la busqueda de cuentas. También maneja la logica de busqueda para
 * facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaResultUI extends FlowPanel implements ClickHandler {

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
	// private int numeroPagina = 1;
	private Long totalRegistrosBusqueda;
	private BuscarCuentaController controller;
	private static final int cantResultadosPorPagina = 10;
	private int indiceRowTabla;
	private static Command aceptarCommand;
	private List<CuentaSearchResultDto> cuentasActuales;
	private InfocomUI infocomUI;
	private String cuentaID;
	private static BuscarCuentaResultUI instance;
	private static Long dialogCuentaId;
	private static String dialogCodVantive;
	private static boolean dialogBusquedaPorDoc;

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
		tablePageBar.addStyleName("mlr8");
		tablePageBar.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				lastCuentaSearchDto.setOffset(tablePageBar.getCantRegistrosParcI());
				List<CuentaSearchResultDto> cuentasActuales = new ArrayList<CuentaSearchResultDto>();
				if (cuentas.size() >= cantResultadosPorPagina) {
					for (int i = (tablePageBar.getCantRegistrosParcI() - 1); i < tablePageBar
							.getCantRegistrosParcF(); i++) {
						cuentasActuales.add(cuentas.get(i));
					}
				} else {
					for (int i = 0; i < cuentas.size(); i++) {
						cuentasActuales.add(cuentas.get(i));
					}
				}
				tablePageBar.setCantRegistrosTot(cuentas.size());
				loadTable(cuentasActuales);
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
									"No se encontraron datos con el criterio utilizado.", false);
						}
						cuentas = result;
						tablePageBar.setCantRegistrosTot(cuentas.size());
						tablePageBar.setPagina(1);
						setCuentas();
						controller.setResultadoVisible(true);
					}
				});
	}

	public void setCuentas() {
		// this.cuentas = cuentas;
		resultTotalCuentas.setText(Sfa.constant().totalCuentasBuscadas() + String.valueOf(cuentas.size()));
		resultTotalCuentas.setVisible(true);
		// tablePageBar.setPagina(1);
		List<CuentaSearchResultDto> cuentasActuales = new ArrayList<CuentaSearchResultDto>();
		if (cuentas.size() >= cantResultadosPorPagina) {
			for (int i = (tablePageBar.getCantRegistrosParcI() - 1); i < tablePageBar.getCantRegistrosParcF(); i++) {
				cuentasActuales.add(cuentas.get(i));
			}
		} else {
			for (int i = 0; i < cuentas.size(); i++) {
				cuentasActuales.add(cuentas.get(i));
			}
		}
		tablePageBar.setCantRegistrosTot(cuentas.size());
		loadTable(cuentasActuales);
	}

	private boolean getCondicionBusquedaPorDni() {
		return (lastCuentaSearchDto.getNumeroDocumento() != null)
				&& (!"".equals(lastCuentaSearchDto.getNumeroDocumento()));
	}

	/**
	 * Crea una fila en la tabla por cada cuenta del CuentaSearchResultDto
	 */
	private void loadTable(final List<CuentaSearchResultDto> cuentasActuales) {
		this.cuentasActuales = cuentasActuales;
		clearResultTable();
		int totalABuscar;
		if (cuentasActuales.size() < 10) {
			totalABuscar = cuentasActuales.size();
		} else {
			totalABuscar = 10;
		}
		indiceRowTabla = 0;
		for (int i = 0; i < totalABuscar; i++) {
			indiceRowTabla = i;
			if (cuentasActuales.size() != 0) {
				if (ClientContext.getInstance().checkPermiso(PermisosEnum.VISUALIZAR_CUENTA.getValue())) {
					HTML iconLapiz = IconFactory.lapiz(LAPIZ_TITLE);
					iconLapiz.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							cargarDatosCuenta();
						}
					});
					resultTable.setWidget(i + 1, 0, iconLapiz);
				}

				HTML iconLupa = IconFactory.lupa(LUPA_TITLE);
				iconLupa.addClickHandler(this);
				resultTable.setWidget(i + 1, 1, iconLupa);

				// LockingState == 1: Es cuando esta lockeado por el mismo usuario logueado (Verificar).
				if (cuentasActuales.get(i).getLockingState() == 1) {
					resultTable.setWidget(i + 1, 2, IconFactory.locked(BLOQUEADO_TITLE));
				} else if (cuentas.get(i).getLockingState() == 2) {
					// LockingState == 2: Es cuando esta lockeado por otro usuario.
					resultTable.setWidget(i + 1, 2, IconFactory.lockedOther(OTRO_BLOQUEO_TITLE));
				}
				resultTable.setHTML(i + 1, 3, cuentasActuales.get(i).getNumero());
				resultTable.setHTML(i + 1, 4, cuentasActuales.get(i).getRazonSocial());
				resultTable.setHTML(i + 1, 5, cuentasActuales.get(i).getApellidoContacto());
				// No muestro telefono en cuentas del gobierno de otro usuario
				boolean ocultarTelefono = !cuentasActuales.get(i).isEjecutivoOwner(
						ClientContext.getInstance().getUsuario().getUserName())
						&& cuentasActuales.get(i).isCuentaGobierno();
				if (ocultarTelefono) {
					resultTable.setHTML(i + 1, 6, Sfa.constant().whiteSpace());
				} else {
					resultTable.setHTML(i + 1, 6,
							cuentasActuales.get(i).getNumeroTelefono() != null ? cuentas.get(i)
									.getNumeroTelefono() : "");
				}
			}
		}
		setVisible(true);
	}

	/**
	 * 
	 */
	private void cargarDatosCuenta() {
		int offset = (tablePageBar.getPagina() - 1) * tablePageBar.getCantResultadosPorPagina();
		CuentaSearchResultDto cuentaSearch = cuentas.get(offset + indiceRowTabla);
		if (cuentaSearch.getRazonSocial() != null && cuentaSearch.getRazonSocial().equals("***")) {
			ModalMessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(),
					Sfa.constant().ERR_NO_ACCESO_CUENTA(), ModalMessageDialog.getCloseCommand());
		} else if (cuentaSearch.getLockingState() == 2) {
			this.dialogCuentaId = cuentaSearch.getId();
			this.dialogCodVantive = cuentaSearch.getCodigoVantive();
			this.dialogBusquedaPorDoc = getCondicionBusquedaPorDni();
			
			if (cuentaSearch.getEjecutivo() != null &&
					cuentaSearch.isEjecutivoOwner(ClientContext.getInstance().getUsuario().getUserName())) {
				String msg = Sfa.constant().ERR_CUENTA_LOCKEADA_POR_OTRO().replaceAll("\\{1\\}",
						cuentaSearch.getNumero()).replaceAll("\\{2\\}", cuentaSearch.getSupervisor());
				ModalMessageDialog.getInstance().showAceptarCancelar(Sfa.constant().MSG_DIALOG_TITLE(), msg,
						getAceptarConsultarCtaLockeada(), ModalMessageDialog.getCloseCommand());
			} else {
				CuentaClientService.cargarDatosCuenta(cuentaSearch.getId(), cuentaSearch.getNumero(),
						getCondicionBusquedaPorDni(), true, true);
			}
		} else {
			// CuentaClientService.cargarDatosCuenta(cuentaSearch.getId(),
			// cuentaSearch.getCodigoVantive(),getCondicionBusquedaPorDni());
			CuentaClientService.cargarDatosCuenta(cuentaSearch.getId(), cuentaSearch.getNumero(),
					getCondicionBusquedaPorDni());
		}
	}

	/**
	 * Limpia la tabla de resultados
	 * 
	 * Borrando las filas de la tabla en vez de recrear la tabla entera.
	 */
	private void clearResultTable() {
		if (resultTable != null) {
			resultTable.clearContent();
		} else {
			resultTable = new NextelTable();
			resultTable.addRowListener(new RowListener() {
				public void onRowClick(Widget sender, int row) {
				}

				public void onRowEnter(Widget sender, int row) {
					indiceRowTabla = row - 1;
				}

				public void onRowLeave(Widget sender, int row) {
				}
			});
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
		return getSelectedCuenta() != null ? getSelectedCuenta().getId() : null;
	}

	public CuentaSearchResultDto getSelectedCuenta() {
		int offset = (tablePageBar.getPagina() - 1) * tablePageBar.getCantResultadosPorPagina();
		CuentaSearchResultDto cuenta = null;
		if (resultTable != null && resultTable.getRowSelected() > 0) {
			cuenta = cuentas.get(offset + (resultTable.getRowSelected() - 1));
		}
		return cuenta;
	}

	public static Command getAceptarConsultarCtaLockeada() {
		if (aceptarCommand == null) {
			aceptarCommand = new Command() {
				public void execute() {
					ModalMessageDialog.getInstance().hide();
					CuentaClientService.cargarDatosCuenta(dialogCuentaId, dialogCodVantive,
							dialogBusquedaPorDoc, true, true);
				}
			};
		}
		return aceptarCommand;
	}

	public void onClick(ClickEvent arg0) {
		CuentaSearchResultDto cuentaSearch = cuentas.get(indiceRowTabla);
		if (cuentaSearch.isPuedeVerInfocom()){
			History.newItem(UILoader.VER_INFOCOM + "?cuenta_id=" + cuentaSearch.getId());
		} else {
			//#1721
			if (cuentaSearch.getId() == 0 && !"***".equals(cuentaSearch.getNumero())
					&& (cuentaSearch.getEjecutivo() == null
						|| ClientContext.getInstance().getVendedor().getId().toString().equals(cuentaSearch.getEjecutivo()))) {
				CuentaClientService.cargarDatosCuentaInfocom(cuentaSearch.getId(), cuentaSearch.getNumero(),
						getCondicionBusquedaPorDni(), UILoader.VER_INFOCOM + "?cuenta_id=");
			} else {
				ModalMessageDialog.getInstance().setDialogTitle("Ver Infocom");
				ModalMessageDialog.getInstance().setSize("300px", "100px");
				ModalMessageDialog.getInstance().showAceptar("No tiene permisos para ver infocom",
						ModalMessageDialog.getCloseCommand());
			}
		}
	}
}
