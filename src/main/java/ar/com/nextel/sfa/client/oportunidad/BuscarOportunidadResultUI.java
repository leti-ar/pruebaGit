package ar.com.nextel.sfa.client.oportunidad;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.dto.OportunidadDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioSearchResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.client.widget.TablePageBar;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BuscarOportunidadResultUI extends FlowPanel implements ClickListener {
	private BuscarOportunidadFilterUIData buscarOportunidadFilterUIData;
	private NextelTable resultTable;
	private SimplePanel resultTableWrapper;
	private TablePageBar tablePageBar;
	private List<OportunidadNegocioSearchResultDto> oportunidades;
	private int offset;
	private Long totalRegistrosBusqueda;
	private DateTimeFormat FormattedDate = DateTimeFormat.getMediumDateFormat();
	private Label numResultadosLabel = new Label();
	private FormButtonsBar footerBar;
	private int cantResultadosPorPagina = 10;
	private PopupPanel popupCrearSS;
	private PopupPanel popupAgregarCuenta;
	private Hyperlink crearEquipos;
	private Hyperlink crearCDW;
	// private Hyperlink crearMDS;

	public Long getTotalRegistrosBusqueda() {
		return totalRegistrosBusqueda;
	}

	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
	}

	public BuscarOportunidadResultUI() {
		super();
		buscarOportunidadFilterUIData = new BuscarOportunidadFilterUIData();
		addStyleName("gwt-OportunidadesResultPanel");
		resultTableWrapper = new SimplePanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		tablePageBar = new TablePageBar();
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				List oportunidadesActuales = new ArrayList<OportunidadNegocioSearchResultDto>();
				if (oportunidades.size() >= cantResultadosPorPagina) {
					for (int i = (tablePageBar.getCantRegistrosParcI() - 1); i < tablePageBar
							.getCantRegistrosParcF(); i++) {
						oportunidadesActuales.add(oportunidades.get(i));
					}
				} else {
					for (int i = 0; i < oportunidades.size(); i++) {
						oportunidadesActuales.add(oportunidades.get(i));
					}
				}
				tablePageBar.setCantRegistrosTot(oportunidades.size());
				tablePageBar.refrescaLabelRegistros();
				loadTable(oportunidadesActuales);
			}
		});

		resultTable = new NextelTable();
		numResultadosLabel.addStyleName("numeroResultadosLabel");
		resultTableWrapper.add(resultTable);
		add(numResultadosLabel);
		add(resultTableWrapper);
		add(tablePageBar);
		add(getFooter());
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
		tablePageBar.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBar.setCantRegistrosParcI(1);
		tablePageBar.setCantRegistrosParcF(tablePageBar.getCantResultadosPorPagina());
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
		OportunidadNegocioRpcService.Util.getInstance().searchOportunidad(oportunidadSearchDto,
				new DefaultWaitCallback<List<OportunidadNegocioSearchResultDto>>() {
					public void success(List result) {
						if (result != null) {
							if (result.size() == 0) {
								MessageDialog.getInstance().showAceptar(
										"No se encontraron datos con los criterios utilizados",
										MessageDialog.getCloseCommand());
							}
							oportunidades = result;
							tablePageBar.setCantResultados(oportunidades.size());
							double calculoCantPaginasReserva = ((double) oportunidades.size() / (double) cantResultadosPorPagina);
							int cantPaginasReserva = (int) Math.ceil(calculoCantPaginasReserva);
							tablePageBar.setCantPaginas(cantPaginasReserva);
							tablePageBar.setPagina(1);
							setOportunidades();
						}
					}
				});
	}

	public void setOportunidades() {
		List<OportunidadNegocioSearchResultDto> oportunidadesActuales = new ArrayList<OportunidadNegocioSearchResultDto>();
		if (oportunidades.size() >= cantResultadosPorPagina) {
			for (int i = (tablePageBar.getCantRegistrosParcI() - 1); i < tablePageBar.getCantRegistrosParcF(); i++) {
				oportunidadesActuales.add(oportunidades.get(i));
			}
		} else {
			for (int i = 0; i < oportunidades.size(); i++) {
				oportunidadesActuales.add(oportunidades.get(i));
			}
		}
		tablePageBar.setCantRegistrosTot(oportunidades.size());
		tablePageBar.refrescaLabelRegistros();
		loadTable(oportunidadesActuales);
	}

	private void loadTable(List<OportunidadNegocioSearchResultDto> oportunidadesActuales) {
		while (resultTable.getRowCount() > 1) {
			resultTable.removeRow(1);
		}
		initTable(resultTable);
		int row = 1;
		for (OportunidadNegocioSearchResultDto oportunidad : oportunidadesActuales) {
			// resultTable.setWidget(row, 0, IconFactory.lapiz());
			resultTable.setWidget(row, 0, IconFactory.lapizAnchor(UILoader.EDITAR_CUENTA + "?opp="
					+ oportunidad.getIdOportunidadNegocio()));
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
		// add(getFooter());
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "200px", "120px", "120px", "120px", "120px", "120px", "120px", "120px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
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

	public void onClick(Widget sender) {
		OportunidadNegocioSearchResultDto oportunidadSelected = oportunidades.get(resultTable
				.getRowSelected() - 1);
		Long idCuenta;
		if (oportunidadSelected != null && oportunidadSelected.getCuentaOrigen().getId() != null) {
			idCuenta = oportunidadSelected.getCuentaOrigen().getId();
			if (sender == buscarOportunidadFilterUIData.getCrearSS()) {
				crearEquipos.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta,
						GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS));
				crearCDW.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_CDW));
				// crearMDS.setTargetHistoryToken(getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_MDS));
				popupCrearSS.show();
				popupCrearSS.setPopupPosition(
						buscarOportunidadFilterUIData.getCrearSS().getAbsoluteLeft() - 10,
						buscarOportunidadFilterUIData.getCrearSS().getAbsoluteTop() - 50);
			} else if (sender == buscarOportunidadFilterUIData.getCrearCuenta()) {
				History.newItem(UILoader.EDITAR_CUENTA + "?cuenta_id=" + idCuenta + "&sus=1");
			} else if (sender == crearEquipos || sender == crearCDW) { // || sender == crearMDS
				popupCrearSS.hide();
			}
		} else {
			MessageDialog.getInstance().showAceptar("Error", "Debe seleccionar una Cuenta",
					MessageDialog.getCloseCommand());
		}
	}

	public FormButtonsBar getFooter() {
		popupCrearSS = new PopupPanel(true);
		popupAgregarCuenta = new PopupPanel(true);
		popupCrearSS.addStyleName("dropUpStyle");
		popupAgregarCuenta.addStyleName("dropUpStyle");

		FlowPanel linksCrearSS = new FlowPanel();
		linksCrearSS.add(crearEquipos = new Hyperlink("Equipos/Accesorios", "" + UILoader.BUSCAR_CUENTA));
		linksCrearSS.add(crearCDW = new Hyperlink("CDW", "" + UILoader.BUSCAR_CUENTA));
		// linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
		popupCrearSS.setWidget(linksCrearSS);
		crearEquipos.addClickListener(this);
		crearCDW.addClickListener(this);
		// crearMDS.addClickListener(this);

		footerBar = new FormButtonsBar();
		footerBar.addLink(buscarOportunidadFilterUIData.getCrearSS());
		footerBar.addLink(buscarOportunidadFilterUIData.getCrearCuenta());
		buscarOportunidadFilterUIData.getCrearSS().addClickListener(this);
		buscarOportunidadFilterUIData.getCrearCuenta().addClickListener(this);
		return footerBar;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
