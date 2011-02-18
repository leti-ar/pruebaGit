package ar.com.nextel.sfa.client.ss;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreRequest;
import ar.com.nextel.business.solicitudes.generacionCierre.request.GeneracionCierreResponse;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.nextel.sfa.server.businessservice.SolicitudBusinessService;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.widget.table.RowListener;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * Muestra la tabla correspondiente a las SS cerradas, resultado del buscador.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel implements ClickHandler {

	private NextelTable resultTable;
	private FlowPanel resultTotalTableWrapper;
	private FlowPanel resultTableWrapper;
	private List<SolicitudServicioCerradaResultDto> solicitudesServicioCerradaResultDto;
	private BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	private CambiosSSCerradasResultUI cambiosSSCerradasResultUI;
	private ExportarExcelSSResultUI exportarExcelSSResultUI;
	private Long cantEquipos = new Long(0);
	private Double cantPataconex = new Double(0);
	private int cantEqFirmados = 0;
	private SolicitudServicioCerradaDto solicitudServicioCerradaDto;
	private int indiceRowTabla;
	private String fileName;
	private SolicitudServicioCerradaResultDto solicitud; 
	private String numeroSS;
	
	public BuscarSSCerradasResultUI() {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTotalTableWrapper = new FlowPanel();
		resultTableWrapper = new FlowPanel();
		resultTableWrapper.addStyleName("resultTableWrapper");
		resultTable = new NextelTable();
		resultTable.addClickHandler(this);
		initTable(resultTable);
		resultTableWrapper.add(resultTable);
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
		exportarExcelSSResultUI.setNumResultados("Número de Resultados: ");
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
							exportarExcelSSResultUI.setVisible(true);
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
		exportarExcelSSResultUI.setNumResultados("Número de Resultados: "
				+ String.valueOf(solicitudServicioCerradaResultDto.size()));
		setVisible(true);
	}

	private void loadExcel() {
		exportarExcelSSResultUI.getIcon().addClickHandler(new ClickHandler() {
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
	}

	private Widget loadTable() {
		resultTable.clearContent();

		indiceRowTabla = 1;

		if (solicitudesServicioCerradaResultDto != null) {
			for (Iterator iter = solicitudesServicioCerradaResultDto.iterator(); iter.hasNext();) {
				SolicitudServicioCerradaResultDto solicitudServicioCerradaResultDto = (SolicitudServicioCerradaResultDto) iter
						.next();
								resultTable.setWidget(indiceRowTabla, 0, IconFactory.word());
								resultTable.setHTML(indiceRowTabla, 1, solicitudServicioCerradaResultDto.getNumeroSS());
								resultTable.setHTML(indiceRowTabla, 2, solicitudServicioCerradaResultDto.getNumeroCuenta());
								if (solicitudServicioCerradaResultDto.getRazonSocialCuenta() != null) {
									 resultTable.setHTML(indiceRowTabla, 3, solicitudServicioCerradaResultDto.getRazonSocial());
								} else {
									resultTable.setHTML(indiceRowTabla, 3, solicitudServicioCerradaResultDto
											.getRazonSocialCuenta());
								}
								resultTable.setHTML(indiceRowTabla, 4, solicitudServicioCerradaResultDto
										.getCantidadEquipos().toString());
								if (solicitudServicioCerradaResultDto.getPataconex() == null) {
									resultTable.setHTML(indiceRowTabla, 5, "");
								} else {
									resultTable.setHTML(indiceRowTabla, 5, solicitudServicioCerradaResultDto.getPataconex()
											.toString());
								}
								if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == true) {
									resultTable.setWidget(indiceRowTabla, 6, IconFactory.tildeVerde());
								} else {
									resultTable.setText(indiceRowTabla, 6, "");
								}
								if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == Boolean.TRUE) {
									cantEqFirmados++;
								}
								cantEquipos = cantEquipos + solicitudServicioCerradaResultDto.getCantidadEquipos();
								if (solicitudServicioCerradaResultDto.getPataconex() != null) {
									cantPataconex = cantPataconex + solicitudServicioCerradaResultDto.getPataconex();
								}
								indiceRowTabla++;
//							}
//						});
				

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

	public void setExportarExcelSSResultUI(ExportarExcelSSResultUI exportarExcelSSResultUI) {
		this.exportarExcelSSResultUI = exportarExcelSSResultUI;
	}

	public void onClick(ClickEvent event) {
		Cell cell = resultTable.getCellForEvent(event);

		if (cell == null)
			return;
		numeroSS = resultTable.getHTML(cell.getRowIndex(), 1).toString();
		solicitud = buscarSS(numeroSS);
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
			fileName = null;
			if (solicitud.isNumeroCuentaAlCierreSSIdVantive()) {
				// Si es cliente usamos el codigo Vantive, sino el Id (ya que no podemos
				// guardar archivos con los caracteres de VANCUC
				fileName = solicitud.getIdCuenta().toString() + "-5-" + numeroSS + ".rtf";
			} else {
				fileName = solicitud.getIdCuenta().toString() + "-5-" + numeroSS + ".rtf";
			}
			final String filenameFinal = fileName;
			SolicitudRpcService.Util.getInstance().existReport(fileName, new DefaultWaitCallback<Boolean>() {
				public void success(Boolean result) {
					LoadingModalDialog.getInstance().hide();
					if (result) {
						WindowUtils.redirect("/" + contextRoot + "/download/" + filenameFinal
								+ "?module=solicitudes&service=rtf&name=" + filenameFinal);
					} else {
						
						SolicitudRpcService.Util.getInstance().crearArchivo(solicitud, false, new DefaultWaitCallback<Boolean>() {

							@Override
							public void success(Boolean result) {
								
								RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(fileName));
								requestBuilder.setCallback(new RequestCallback() {
									public void onResponseReceived(Request request, Response response) {
										WaitWindow.hide();
										LoadingModalDialog.getInstance().hide();
										if (response.getStatusCode() == Response.SC_OK) {
											WindowUtils.redirect(getUrlReporte(fileName));
										} else {
											MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
													MessageDialog.getCloseCommand());
										}
									}

									public void onError(Request request, Throwable exception) {
										WaitWindow.hide();
										LoadingModalDialog.getInstance().hide();
										MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
												MessageDialog.getCloseCommand());
									}
								});
								try {
									requestBuilder.setTimeoutMillis(10 * 1000);
									requestBuilder.send();
									WaitWindow.show();
									LoadingModalDialog.getInstance().showAndCenter("Solicitud",
											"Esperando Solicitud de Servicio ...");
								} catch (RequestException e) {
									MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
											MessageDialog.getCloseCommand());
									LoadingModalDialog.getInstance().hide();
								}
							}
						});
					}
				}
				
				public void failure(Throwable caught) {
					LoadingModalDialog.getInstance().hide();
					super.failure(caught);
				}
			});

		}

	}
	
	public String getUrlReporte(String fileName) {
		return "/" + WindowUtils.getContextRoot() + "/download/" + fileName
		+ "?module=solicitudes&service=rtf&name=" + fileName;
	}

	private SolicitudServicioCerradaResultDto buscarSS(String numeroSS) {
		for (Iterator iterator = solicitudesServicioCerradaResultDto.iterator(); iterator.hasNext();) {
			SolicitudServicioCerradaResultDto solicitudServicioDto = (SolicitudServicioCerradaResultDto) iterator
					.next();
			if (solicitudServicioDto.getNumeroSS().equals(numeroSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}

	public void setCambiosSSCerradasResultUI(CambiosSSCerradasResultUI cambiosSSCerradasResultUI) {
		this.cambiosSSCerradasResultUI = cambiosSSCerradasResultUI;
	}

	// public Widget getExportarExcel() {
	// exportarExcel = new FlowPanel();
	// exportarExcel.setHeight("30px");
	// icon = IconFactory.excel();
	// icon.addStyleName("exportarExcelSS mr10");
	// numResultados.setWidth("170px");
	// numResultados.addStyleName("floatLeft");
	// numResultados.addStyleName("mt5");
	// exportarExcel.add(numResultados);
	// exportarExcel.add(icon);
	// return exportarExcel;
	// }

}
