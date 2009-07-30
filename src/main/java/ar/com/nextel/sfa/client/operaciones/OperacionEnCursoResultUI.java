package ar.com.nextel.sfa.client.operaciones;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.OperacionesRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.OperacionEnCursoDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaDto;
import ar.com.nextel.sfa.client.dto.VentaPotencialVistaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * Muestra la tabla con los resultados de la busqueda de operaciones. También maneja la logica de busqueda
 * para facilitar el paginado de la tabla (Así queda centralizada la llamada al servicio)
 * 
 * @author eSalvador
 * 
 */
public class OperacionEnCursoResultUI extends FlowPanel implements TableListener {

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
	private static final int cantResultadosPorPagina = 5;
	private String NumeroVtasPotNoConsultadas;
	private OperacionEnCursoUIController controller;

	public OperacionEnCursoResultUI(OperacionEnCursoUIController controller) {
		super();
		this.controller = controller;
		addStyleName("gwt-OportunidadesResultPanel");
		resultTableWrapperReserva = new SimplePanel();
		resultTableWrapperReserva.addStyleName("resultTableWrapper");

		resultTableWrapperOpCurso = new SimplePanel();
		resultTableWrapperOpCurso.addStyleName("resultTableWrapper");

		tablePageBarReserva = new TablePageBar();
		tablePageBarReserva.addStyleName("mlr8");
		tablePageBarReserva.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBarReserva.setBeforeClickCommand(new Command() {
			public void execute() {
				setReserva();
			}
		});

		tablePageBarOpCurso = new TablePageBar();
		tablePageBarOpCurso.addStyleName("mlr8");
		tablePageBarOpCurso.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBarOpCurso.setBeforeClickCommand(new Command() {
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
		resultTableOpEnCurso.addTableListener(this);

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

	public void searchOperacionesYReservas() {
		this.searchOpEnCurso(true);
		this.searchReservas(true);
	}

	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez. (o
	 * sea, cada vez que se hace click en los botones del paginador)
	 * 
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	 **/
	private void searchReservas(boolean firstTime) {
		OperacionesRpcService.Util.getInstance().searchReservas(
				new DefaultWaitCallback<VentaPotencialVistaResultDto>() {
					public void success(VentaPotencialVistaResultDto result) {
						if (result != null) {
							vtaPotencial = result.getVentasPotencialesVistaDto();
							tablePageBarReserva.setPagina(1);
							setReserva();
						}
					}
				});
	}

	/**
	 * Metodo privado que contiene lo que se desea ejecutar cada vez que se busca sin ser la primera vez. (o
	 * sea, cada vez que se hace click en los botones del paginador)
	 * 
	 * @param: ? (ReservaDto)
	 * @param: firstTime
	 **/
	private void searchOpEnCurso(boolean firstTime) {
		OperacionesRpcService.Util.getInstance().searchOpEnCurso(
				new DefaultWaitCallback<List<OperacionEnCursoDto>>() {
					public void success(List<OperacionEnCursoDto> result) {
						if (result != null) {
							opEnCurso = result;
							tablePageBarOpCurso.setPagina(1);
							setOpCurso();
						}
					}
				});
	}

	public void setOpCurso() {
		tablePageBarOpCurso.setCantRegistrosTot(opEnCurso.size());
		List<OperacionEnCursoDto> opEnCursoActuales = new ArrayList<OperacionEnCursoDto>();
		for (int i = (tablePageBarOpCurso.getCantRegistrosParcI() - 1); i < tablePageBarOpCurso
				.getCantRegistrosParcF(); i++) {
			opEnCursoActuales.add(opEnCurso.get(i));
		}
		initTableOpenCurso(resultTableOpEnCurso);
		loadTableOpCurso(opEnCursoActuales);
	}

	public void setReserva() {
		tablePageBarReserva.setCantRegistrosTot(vtaPotencial.size());
		List<VentaPotencialVistaDto> vtaPotencialActuales = new ArrayList<VentaPotencialVistaDto>();
		if (vtaPotencial.size() >= cantResultadosPorPagina) {
			for (int i = (tablePageBarReserva.getCantRegistrosParcI() - 1); i < tablePageBarReserva
					.getCantRegistrosParcF(); i++) {
				vtaPotencialActuales.add(vtaPotencial.get(i));
			}
		} else {
			for (int i = 0; i < vtaPotencial.size(); i++) {
				vtaPotencialActuales.add(vtaPotencial.get(i));
			}
		}
		tablePageBarReserva.setCantRegistrosTot(vtaPotencial.size());
		initTableReservas(resultTableReservas);
		loadTableReservas(vtaPotencialActuales);
	}

	private void loadTableOpCurso(List<OperacionEnCursoDto> opEnCursoActuales) {
		while (resultTableOpEnCurso.getRowCount() > 1) {
			resultTableOpEnCurso.removeRow(1);
		}
		// initTableOpenCurso(resultTableOpEnCurso);
		resultTableWrapperOpCurso.setWidget(resultTableOpEnCurso);
		int row = 1;
		for (OperacionEnCursoDto opCursoDto : opEnCursoActuales) {
			resultTableOpEnCurso.setWidget(row, 0, IconFactory.lapiz());
			// if (opEnCurso.isPuedeVerInfocom()) {
			resultTableOpEnCurso.setWidget(row, 1, IconFactory.silvioSoldan());
			// }
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
		while (resultTableReservas.getRowCount() > 1) {
			resultTableReservas.removeRow(1);
		}
		// initTableReservas(resultTableReservas);
		resultTableWrapperReserva.setWidget(resultTableReservas);
		int row = 1;
		for (VentaPotencialVistaDto vtaPotencialDto : vtaPotencialActuales) {
			resultTableReservas.setWidget(row, 0, IconFactory.lapiz());
			// if (reserva.isPuedeVerInfocom()) {
			resultTableReservas.setWidget(row, 1, IconFactory.silvioSoldan());
			// }vb
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

	public void onCellClicked(SourcesTableEvents table, int row, int cell) {
		if (table == resultTableOpEnCurso && row > 0) {
			int listPosition = tablePageBarOpCurso.getCantRegistrosParcI() + row - 2;
			OperacionEnCursoDto op = opEnCurso.get(listPosition);
			if (cell == 0) {
				if (op.getIdGrupoSolicitud() != null) {
					History.newItem(EditarSSUI.getEditarSSUrl(op.getIdCuenta(), op.getIdGrupoSolicitud()));
				} else {
					ErrorDialog.getInstance().setDialogTitle("Error");
					ErrorDialog.getInstance().show(Sfa.constant().ERR_SIN_SS(), false);
				}
			} else if (cell == 1) {
			} else if (cell == 2) {
				cancelarOperacionEnCurso(op);
			}
		}
	}

	private void cancelarOperacionEnCurso(OperacionEnCursoDto op) {
		MessageDialog.getInstance().setDialogTitle("Eliminar Operación en Curso");
		MessageDialog.getInstance().setSize("300px", "100px");
		MessageDialog.getInstance().showSiNo("Se cancelará la operación en curso. ¿Desea continuar?",
				getComandoCancelarOperacionEnCurso(op.getId()), MessageDialog.getCloseCommand());

	}

	private Command getComandoCancelarOperacionEnCurso(final String idOperacionEnCurso) {
		return new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
				controller.cancelarOperacionEnCurso(idOperacionEnCurso, new DefaultWaitCallback() {
					public void success(Object result) {
						removeOperacionEnCursoAndRefresh(idOperacionEnCurso);
					}
				});
			}
		};
	}

	private void removeOperacionEnCursoAndRefresh(String idOperacionEnCurso) {
		for (int i = 0; i < opEnCurso.size(); i++) {
			if (opEnCurso.get(i).getId().equals(idOperacionEnCurso)) {
				opEnCurso.remove(i);
				break;
			}
		}
		setOpCurso();
	}

}