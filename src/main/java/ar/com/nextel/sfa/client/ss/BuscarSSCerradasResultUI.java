package ar.com.nextel.sfa.client.ss;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * Muestra la tabla correspondiente a las SS cerradas, resultado del buscador.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel implements ClickHandler {

	private FlexTable resultTable;
	private FlowPanel resultTotalTableWrapper;
	private FlowPanel resultTableWrapper;
	private FlowPanel exportarExcel;
	private HTML icon;
	private List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto;
	//private Long totalRegistrosBusqueda;
	private BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	private CambiosSSCerradasResultUI cambiosSSCerradasResultUI;
	private Long cantEquipos = new Long(0);
	private Double cantPataconex = new Double(0);
	private int cantEqFirmados = 0;
	private SolicitudServicioCerradaDto solicitudServicioCerradaDto;
	private InlineHTML numResultados = new InlineHTML("Número de Resultados: ");
	private InlineHTML cantResultados = new InlineHTML();

//	public Long getTotalRegistrosBusqueda() {
//		return totalRegistrosBusqueda;
//	}
//
//	public void setTotalRegistrosBusqueda(Long totalRegistrosBusqueda) {
//		this.totalRegistrosBusqueda = totalRegistrosBusqueda;
//	}

	public BuscarSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTotalTableWrapper = new FlowPanel();
//		exportarExcel = new FlowPanel();
//		exportarExcel.setHeight("23px");
//		icon = IconFactory.excel();
//		icon.addStyleName("exportarExcelSS m3");
//		exportarExcel.add(numResultadosLabel);
//		exportarExcel.add(icon);
//		add(exportarExcel);
		resultTableWrapper = new FlowPanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		resultTable = new FlexTable();
		resultTable.addClickHandler(this);
		initTable(resultTable);
		resultTableWrapper.add(resultTable);
		
		//resultTotalTableWrapper.add(exportarExcel);
		resultTotalTableWrapper.add(resultTableWrapper);
		setVisible(false);
	}

	/**
	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
	 * hace click al boton Buscar)
	 * 
	 * @param: cuentaSearchDto
	 * */
	public void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioSearchDto) {
		this.searchSSCerradas(solicitudServicioSearchDto, true);
	};

	private void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean firstTime) {
		this.solicitudServicioCerradaDto = solicitudServicioCerradaDto;
		cantEquipos = new Long(0);
		cantPataconex = new Double(0);
		cantEqFirmados = 0;
		numResultados.setText("Número de Resultados: ");
		//cantResultados.setText("");
		SolicitudRpcService.Util.getInstance().searchSSCerrada(solicitudServicioCerradaDto,
				new DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>>() {
					public void success(List<SolicitudServicioCerradaResultDto> result) {
						if (result != null) {
							if (result.size() == 0) {
								// cambiosSSCerradasResultUI.CleanCambiosTable();
								// cambiosSSCerradasResultUI.setSolicitudServicioCerradaDto(new
								// DetalleSolicitudServicioDto("", "", "", null));
								MessageDialog.getInstance().showAceptar("Búsqueda de SS",
										"No se encontraron datos con el criterio utilizado",
										MessageDialog.getCloseCommand());
								cambiosSSCerradasResultUI.hideCambiosTable();
							}
							loadExcel();
							setSolicitudServicioDto(result);
							buscarSSTotalesResultUI.setValues(cantEquipos.toString(), cantPataconex, String
									.valueOf(cantEqFirmados));
							buscarSSTotalesResultUI.setVisible(true);
						}
					}
				});
	}

	public void setSolicitudServicioDto(
			List<SolicitudServicioCerradaResultDto> solicitudServicioCerradaResultDto) {
		this.solicitudesServicioCerradaResultDto = solicitudServicioCerradaResultDto;
		add(loadTable());
		exportarExcel.setVisible(true);
		numResultados.setText("Número de Resultados: " + String.valueOf(solicitudServicioCerradaResultDto.size()));
		//cantResultados.setText(String.valueOf(solicitudServicioCerradaResultDto.size()));
		setVisible(true);
	}

	private void loadExcel() {
		icon.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SolicitudRpcService.Util.getInstance().buildExcel(solicitudServicioCerradaDto,
						new DefaultWaitCallback<String>() {
							public void success(String result) {
								if (result != null) {
									WindowUtils.redirect("/" + WindowUtils.getContextRoot()
											+ "/download/download?module=solicitudes&service=xls&name="
											+ result + ".xls");
								}
							}
						});
			}
		});
		//return exportarExcel;
	}

	private Widget loadTable() {
		while (resultTable.getRowCount() > 1) {
			resultTable.removeRow(1);
		}

		int row = 1;

		if (solicitudesServicioCerradaResultDto != null) {
			//exportarExcel.setVisible(true);
			for (Iterator iter = solicitudesServicioCerradaResultDto.iterator(); iter.hasNext();) {
				SolicitudServicioCerradaResultDto solicitudServicioCerradaResultDto = (SolicitudServicioCerradaResultDto) iter
						.next();
				resultTable.setWidget(row, 0, IconFactory.word());
				resultTable.setHTML(row, 1, solicitudServicioCerradaResultDto.getNumero());
				resultTable.setHTML(row, 2, solicitudServicioCerradaResultDto.getNumeroCuenta());
				if (solicitudServicioCerradaResultDto.getRazonSocialCuenta() != null) {
					resultTable.setHTML(row, 3, solicitudServicioCerradaResultDto.getRazonSocialCuenta());
				} else {
//					resultTable.setHTML(row, 3, solicitudServicioCerradaResultDto.getCuenta().getPersona()
//							.getRazonSocial());
					resultTable.setHTML(row, 3, solicitudServicioCerradaResultDto.getRazonSocialCuenta());
				}
				resultTable.setHTML(row, 4, solicitudServicioCerradaResultDto.getCantidadEquiposPorCuenta()
						.toString());
				resultTable.setHTML(row, 5, solicitudServicioCerradaResultDto.getPataconex().toString());
				if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == true) {
					resultTable.setWidget(row, 6, IconFactory.tildeVerde());
				}
				if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == Boolean.TRUE) {
					cantEqFirmados++;
				}
				cantEquipos = cantEquipos + solicitudServicioCerradaResultDto.getCantidadEquipos();
				cantPataconex = cantPataconex + solicitudServicioCerradaResultDto.getPataconex();
				row++;
			}
			setVisible(true);
		}

		return resultTotalTableWrapper;
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "150px", "200px", "320px", "100px", "100px", "100px", };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().numSS());
		table.setHTML(0, 2, Sfa.constant().numCuenta());
		table.setHTML(0, 3, Sfa.constant().razonSocial());
		table.setHTML(0, 4, Sfa.constant().equipos());
		table.setHTML(0, 5, Sfa.constant().pataconexTabla());
		table.setHTML(0, 6, Sfa.constant().firmasTabla());
	}

	public void setBuscarSSTotalesResultUI(BuscarSSTotalesResultUI buscarSSTotalesResultUI) {
		this.buscarSSTotalesResultUI = buscarSSTotalesResultUI;
	}

	public void onClick(ClickEvent event) {
		Cell cell = resultTable.getCellForEvent(event);

		String numeroSS = resultTable.getHTML(cell.getRowIndex(), 1).toString();
		SolicitudServicioCerradaResultDto solicitud = buscarSS(numeroSS);
		if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() >= 1)) {
			SolicitudRpcService.Util.getInstance().getDetalleSolicitudServicio(solicitud.getId(),
					new DefaultWaitCallback<DetalleSolicitudServicioDto>() {
						public void success(DetalleSolicitudServicioDto result) {
							cambiosSSCerradasResultUI.setSolicitudServicioCerradaDto(result);
							cambiosSSCerradasResultUI.setVisible(true);
						}
					});
		} else if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() == 0)) {

			final String contextRoot = WindowUtils.getContextRoot();
			String filename = null;
			if (solicitud.isCliente()) {
				// Si es cliente usamos el codigo Vantive, sino el Id (ya que no podemos
				// guardar archivos con los caracteres de VANCUC
				filename = solicitud.getNumeroCuenta() + "-5-" + numeroSS + ".rtf";
			} else {
				filename = solicitud.getIdCuenta().toString() + "-5-"	+ numeroSS + ".rtf";
			}
			final String filenameFinal = filename;
			SolicitudRpcService.Util.getInstance().existReport(filename, new DefaultWaitCallback<Boolean>() {
				public void success(Boolean result) {
					if (result) {
						WindowUtils.redirect("/" + contextRoot + "/download/" + filenameFinal
								+ "?module=solicitudes&service=rtf&name=" + filenameFinal);
					} else {
						MessageDialog.getInstance().showAceptar("Error", Sfa.constant().ERR_FILE_NOT_FOUND(),
								MessageDialog.getCloseCommand());
					}
				}
			});

		}

	}


	private SolicitudServicioCerradaResultDto buscarSS(String numeroSS) {
		for (Iterator iterator = solicitudesServicioCerradaResultDto.iterator(); iterator.hasNext();) {
			SolicitudServicioCerradaResultDto solicitudServicioDto = (SolicitudServicioCerradaResultDto) iterator
					.next();
			if (solicitudServicioDto.getNumero().equals(numeroSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}

	public void setCambiosSSCerradasResultUI(CambiosSSCerradasResultUI cambiosSSCerradasResultUI) {
		this.cambiosSSCerradasResultUI = cambiosSSCerradasResultUI;
	}
	
	public Widget getExportarExcel() {
		exportarExcel = new FlowPanel();
		exportarExcel.setHeight("30px");
		icon = IconFactory.excel();
		icon.addStyleName("exportarExcelSS mr10");
		numResultados.setWidth("170px");
		numResultados.addStyleName("floatLeft");
		numResultados.addStyleName("mt5");
		exportarExcel.add(numResultados);
		exportarExcel.add(cantResultados);
		exportarExcel.add(icon);
		exportarExcel.setVisible(false);
		return exportarExcel;
	}

}
