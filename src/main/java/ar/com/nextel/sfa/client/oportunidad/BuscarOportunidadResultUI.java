package ar.com.nextel.sfa.client.oportunidad;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.OportunidadNegocioRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.AgregarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.BuscadorDocumentoPopup;
import ar.com.nextel.sfa.client.cuenta.CuentaClientService;
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
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.widget.table.RowListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BuscarOportunidadResultUI extends FlowPanel implements ClickHandler, ClickListener {
	private BuscarOportunidadFilterUIData buscarOportunidadFilterUIData;
	private NextelTable resultTable;
	private SimplePanel resultTableWrapper;
	private TablePageBar tablePageBar;
	private List<OportunidadNegocioSearchResultDto> oportunidades;
	private List<OportunidadNegocioSearchResultDto> oportunidadesActuales;
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
	private int rowIndexSelected;

	private Hyperlink crearMDS;

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
		tablePageBar.addStyleName("mlr8");
		tablePageBar.setCantResultadosPorPagina(cantResultadosPorPagina);
		tablePageBar.setBeforeClickCommand(new Command() {
			public void execute() {
				oportunidadesActuales = new ArrayList<OportunidadNegocioSearchResultDto>();
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
				loadTable(oportunidadesActuales);
			}
		});

		resultTable = new NextelTable();
		resultTable.addRowListener(new RowListener() {
			public void onRowClick(Widget sender, int row) {
			}

			public void onRowEnter(Widget sender, int row) {
				rowIndexSelected = row - 1;
			}

			public void onRowLeave(Widget sender, int row) {
			}
		});

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
								MessageDialog.getInstance().showAceptar("Búsqueda de Oportunidades",
										"No se encontraron datos con los criterios utilizados",
										MessageDialog.getCloseCommand());
							}
							oportunidades = result;
							tablePageBar.setCantRegistrosTot(oportunidades.size());
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
		loadTable(oportunidadesActuales);
	}

	private void loadTable(List<OportunidadNegocioSearchResultDto> oportunidadesActuales) {
		resultTable.clearContent();
		initTable(resultTable);
		int rowIndex = 1;
		for (OportunidadNegocioSearchResultDto oportunidad : oportunidadesActuales) {
			HTML iconLapiz = IconFactory.lapiz();
			iconLapiz.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					cargarDatosOportunidad();
				}
			});
			resultTable.setWidget(rowIndex, 0, iconLapiz);
			resultTable.setHTML(rowIndex, 1, oportunidad.getRazonSocial());
			resultTable.setHTML(rowIndex, 2, oportunidad.getNombre());
			resultTable.setHTML(rowIndex, 3, oportunidad.getApellido());
			resultTable.setHTML(rowIndex, 4, oportunidad.getTelefonoPrincipal());
			resultTable.setHTML(rowIndex, 5, oportunidad.getNroDocumento());
			resultTable.setHTML(rowIndex, 6, oportunidad.getNroCuenta());
			resultTable.setHTML(rowIndex, 7, FormattedDate.format(oportunidad.getFechaAsignacion()));
			resultTable.setHTML(rowIndex, 8, oportunidad.getDescripcionEstadoOportunidad());
			rowIndex++;
		}
		numResultadosLabel.setText("Numero de Resultados: " + oportunidades.size());
		setVisible(true);
		// add(getFooter());
	}

	private void cargarDatosOportunidad() {
		OportunidadNegocioSearchResultDto oportunidad = oportunidadesActuales != null ? oportunidadesActuales
				.get(rowIndexSelected) : oportunidades.get(rowIndexSelected);
		CuentaClientService.getOportunidadNegocio(oportunidad.getIdOportunidadNegocio());
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

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		onClick(sender);
	}

	public void onClick(Widget sender) {
		if (resultTable.getRowSelected() > 0) {
			OportunidadNegocioSearchResultDto oportunidadSelected = oportunidades.get(resultTable
					.getRowSelected() - 1);
			// Long idCuenta;
			// if (oportunidadSelected != null && oportunidadSelected.getCuentaOrigen().getId() != null) {
			Long idCuenta = oportunidadSelected.getIdCuentaOrigen();
			if (sender == buscarOportunidadFilterUIData.getCrearSS()) {
				crearEquipos.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta,
						GrupoSolicitudDto.ID_EQUIPOS_ACCESORIOS));
				crearCDW.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_CDW));
				crearMDS.setTargetHistoryToken(EditarSSUI.getEditarSSUrl(idCuenta, GrupoSolicitudDto.ID_MDS));
				popupCrearSS.show();
				popupCrearSS.setPopupPosition(
						buscarOportunidadFilterUIData.getCrearSS().getAbsoluteLeft() - 10,
						buscarOportunidadFilterUIData.getCrearSS().getAbsoluteTop() - 50);
			} else if (sender == buscarOportunidadFilterUIData.getCrearCuenta()) {
				AgregarCuentaUI.getInstance().load();
				BuscadorDocumentoPopup.idOpp = oportunidadSelected.getIdOportunidadNegocio();
				BuscadorDocumentoPopup.fromMenu = false;
			} else if (sender == crearEquipos || sender == crearCDW || sender == crearMDS) { 
				popupCrearSS.hide();
			}
		} else {
			MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_NO_CUENTA_SELECTED(),
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
		linksCrearSS.add(crearMDS = new Hyperlink("MDS", "" + UILoader.BUSCAR_CUENTA));
		popupCrearSS.setWidget(linksCrearSS);
		crearEquipos.addClickHandler(this);
		crearCDW.addClickHandler(this);
		crearMDS.addClickListener(this);

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
