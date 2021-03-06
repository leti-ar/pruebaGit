package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.DetalleSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.ServicioAdicionalLineaSolicitudServicioDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaDto;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
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
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;

/**
 * Muestra la tabla correspondiente a las SS cerradas, resultado del buscador.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasResultUI extends FlowPanel implements ClickHandler{

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
//	LF#3	private boolean analistaCreditos;
//	LF#3    private OrdenTablaSolicitudServicio ordenTablaSS;
	
	public BuscarSSCerradasResultUI() { //LF#3 - BuscarSSGenericoUI generic) {
		super();
		addStyleName("gwt-BuscarCuentaResultPanel");
		resultTotalTableWrapper = new FlowPanel();
		resultTableWrapper = new FlowPanel();
//		LF#3
		// Se es analista de Creditos y cliente de Nexus se "alarga" la tabla.
//		if(generic.esAnalistaCreditos() && ClientContext.getInstance().soyClienteNexus()) {
//			resultTableWrapper.addStyleName("resultTableWrapperLarge");
//		}else {
			resultTableWrapper.addStyleName("resultTableWrapper");
//		}
		resultTable = new NextelTable();
		resultTable.addClickHandler(this);
//		LF#3
//		setAnalistaCreditos(generic.esAnalistaCreditos());
//		if(generic.esAnalistaCreditos()) {
//			initTableAnalistaCredito(resultTable);
//		} else {
			initTable(resultTable);
//		}
//		LF#3 ordenTablaSS = new OrdenTablaSolicitudServicio();
		resultTableWrapper.add(resultTable);
		resultTotalTableWrapper.add(resultTableWrapper);
		setVisible(false);
		
	}
	
	//LF
//	/**
//	 * Metodo publico que contiene lo que se desea ejecutar la primera vez que se busca. (o sea, cuando se
//	 * hace click al boton Buscar)
//	 * 
//	 * @param: cuentaSearchDto
//	 * */
//	public void searchSSCerradas(SolicitudServicioCerradaDto solicitudServicioSearchDto) {
	public void searchSolicitudesServicio(SolicitudServicioCerradaDto solicitudServicioSearchDto) {
//		this.searchSSCerradas(solicitudServicioSearchDto, true);
//	};
//
//	private void searchSSCerradas(final SolicitudServicioCerradaDto solicitudServicioCerradaDto, boolean firstTime) {
//		this.solicitudServicioCerradaDto = solicitudServicioCerradaDto;
		this.solicitudServicioCerradaDto = solicitudServicioSearchDto;
		cantEquipos = new Long(0);
		cantPataconex = new Double(0);
		cantEqFirmados = 0;
		exportarExcelSSResultUI.setNumResultados("Número de Resultados: ");
		solicitudServicioCerradaDto.setCantidadResultados(solicitudServicioSearchDto.getCantidadResultados());
		SolicitudRpcService.Util.getInstance().searchSolicitudesServicio(solicitudServicioCerradaDto, //LF#3isAnalistaCreditos(),
				new DefaultWaitCallback<List<SolicitudServicioCerradaResultDto>>() {
					public void success(List<SolicitudServicioCerradaResultDto> result) {
						if (result != null) {
							if (result.size() == 0) {
								if(ClientContext.getInstance().getVendedor().isADMCreditos() && 
										ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()) {
									MessageDialog.getInstance().showAceptar("Búsqueda de SS",
											"No tiene ninguna solicitud de servicio cerrada",
											MessageDialog.getCloseCommand());
								} else {
									MessageDialog.getInstance().showAceptar("Búsqueda de SS",
											"No se encontraron datos con el criterio utilizado",
											MessageDialog.getCloseCommand());
								}
								cambiosSSCerradasResultUI.hideCambiosTable();
							}
							exportarExcelSSResultUI.setVisible(true);
							loadExcel();
							setSolicitudServicioDto(result);
							buscarSSTotalesResultUI.setValues(cantEquipos.toString(), cantPataconex, String
									.valueOf(cantEqFirmados));//LF#3, isAnalistaCreditos());
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
				SolicitudRpcService.Util.getInstance().buildExcel(solicitudServicioCerradaDto, //LF#3isAnalistaCreditos(),
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

	private List<Long> generarListIdSS(){
		List<Long> listIdSS = new ArrayList<Long>();
		
		for(int i = 0; i < solicitudesServicioCerradaResultDto.size(); i++){
			listIdSS.add(solicitudesServicioCerradaResultDto.get(i).getId());
		}
		
		return listIdSS;
	}
	
	private Widget loadTable() {
		resultTable.clearContent();

		indiceRowTabla = 1;
		
		if (solicitudesServicioCerradaResultDto != null) {
			SolicitudRpcService.Util.getInstance().getCantidadLineasPortabilidad(generarListIdSS(),new DefaultWaitCallback<List<Long>>() {
				@Override
				public void success(List<Long> result) {
					for (Iterator iter = solicitudesServicioCerradaResultDto.iterator(); iter.hasNext();) {
						SolicitudServicioCerradaResultDto solicitudServicioCerradaResultDto = (SolicitudServicioCerradaResultDto) iter
								.next();
						int pos = 0;
						//LF#3
//						if(isAnalistaCreditos()) {
//							resultTable.setWidget(indiceRowTabla, pos, IconFactory.lapiz());
//							//LF Si la ss esta cerrada(en carga false) muestro el rtf en la tabla, si no no muestro nada.
//							if(solicitudServicioCerradaResultDto.getEnCarga()) {
//								resultTable.setText(indiceRowTabla, ++pos, "");
//							} else {
//								resultTable.setWidget(indiceRowTabla, ++pos, IconFactory.word());
//							}
//						} else {
							resultTable.setWidget(indiceRowTabla, pos, IconFactory.word());
//						}
						resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getNumeroSS());
						resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getNumeroCuenta());
						if (solicitudServicioCerradaResultDto.getRazonSocialCuenta() != null) {
							 resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getRazonSocial());
						} else {
							resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto
									.getRazonSocialCuenta());
						}
						//LF#3
//						if(isAnalistaCreditos()) {
//							solicitudServicioCerradaResultDto.setCantidadEquipos(calcularCantEquipos(solicitudServicioCerradaResultDto.getLineas()));
//							resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getCantidadEquipos().toString());
//							cantEquipos = cantEquipos + solicitudServicioCerradaResultDto.getCantidadEquipos();
//						}else {
							resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getCantidadEquipos().toString());
							cantEquipos = cantEquipos + solicitudServicioCerradaResultDto.getCantidadEquipos();
//						}
						
						if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == Boolean.TRUE) {
							cantEqFirmados++;
						}
						//LF#3
//						if(isAnalistaCreditos()) {
//					    	if (solicitudServicioCerradaResultDto.getApellidoUsuarioCreacion() != null) {
//								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getApellidoUsuarioCreacion() + " " + solicitudServicioCerradaResultDto.getNombreUsuarioCreacion());
//							} else {
//								resultTable.setHTML(indiceRowTabla, ++pos, "");
//							}
//					    	if (solicitudServicioCerradaResultDto.getApellidoNombreVendedor() != null) {
//								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getApellidoNombreVendedor());
//							} else {
//								resultTable.setHTML(indiceRowTabla, ++pos, "");
//							}		
//					    	if (solicitudServicioCerradaResultDto.getScoring().booleanValue()) {  //PIN/SCORING
//								resultTable.setWidget(indiceRowTabla, ++pos, IconFactory.tildeVerde());
//							} else {
//								resultTable.setText(indiceRowTabla, ++pos, "");
//							}
//					    	if (solicitudServicioCerradaResultDto.getScoring().booleanValue()) {
//								resultTable.setWidget(indiceRowTabla, ++pos, IconFactory.tildeVerde());
//							} else {
//								resultTable.setText(indiceRowTabla, ++pos, null);
//							}
//					    	if (solicitudServicioCerradaResultDto.getVeraz() != null) {
//								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getVeraz());
//							} else {
//								resultTable.setText(indiceRowTabla, ++pos, "");
//							}
//								
//					    	resultTable.setText(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getUltimoEstado());
//					    	
//					    	if (solicitudServicioCerradaResultDto.getFechaCreacion() != null) {
//								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getFechaCreacion().toLocaleString());
//							} else {
//								resultTable.setText(indiceRowTabla, ++pos, "");
//							}		
////									    	if (solicitudServicioCerradaResultDto.getFechaCierre() != null) {
////												resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getFechaCierre().toLocaleString());
////											} else {
//							resultTable.setText(indiceRowTabla, ++pos, "");
////											}	
//						    }
							if (solicitudServicioCerradaResultDto.getPataconex() == null) {
								resultTable.setHTML(indiceRowTabla, ++pos, "");
							} else {
								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getPataconex()
									.toString());
								cantPataconex = cantPataconex + solicitudServicioCerradaResultDto.getPataconex();
							}
							if (solicitudServicioCerradaResultDto.getFirmar().booleanValue() == true) {
								resultTable.setWidget(indiceRowTabla, ++pos, IconFactory.tildeVerde());
							} else {
								resultTable.setText(indiceRowTabla, ++pos, "");
							}
							
							//LF - #3416
							boolean esPortabilidad = false;
							for (Iterator iterator = solicitudServicioCerradaResultDto.getLineas().iterator(); iterator
									.hasNext();) {
								LineaSolicitudServicioDto linea = (LineaSolicitudServicioDto) iterator.next();
								if(linea.getPortabilidad() != null) {
									esPortabilidad = true;
								} 
							} 
							if (esPortabilidad) {
								resultTable.setWidget(indiceRowTabla, ++pos, IconFactory.tildeVerde());
							} else {
								resultTable.setHTML(indiceRowTabla, ++pos, "");
							}
							
							//LF#3
//							if(isAnalistaCreditos()) {
//						    	if (solicitudServicioCerradaResultDto.getAnticipo() != null) {
//									resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getAnticipo());
//								} else {
//									resultTable.setText(indiceRowTabla, ++pos, "");
//								}						    	
//								resultTable.setText(indiceRowTabla, ++pos, calcularDesvio(solicitudServicioCerradaResultDto));
//								//LF: Agrego una columna con el id_solicitud_servicio y oculto la misma para poder luego obtener el id para buscar la solicitud.
//								resultTable.setHTML(indiceRowTabla, ++pos, solicitudServicioCerradaResultDto.getIdSolicitudServicio().toString());
//								resultTable.getCellFormatter().setVisible(indiceRowTabla, pos, false);
//						    }
												
								indiceRowTabla++;
					}
					setVisible(true);
				}
			});
		}

		return resultTotalTableWrapper;
	}

	private void initTable(FlexTable table) {
		String[] widths = { "24px", "150px", "200px", "320px", "100px", "100px", "100px","120px" };
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
		table.setHTML(0, 7, Sfa.constant().portabilidad());
	}
	
	private void initTableAnalistaCredito(FlexTable table) {
		List<String> widths = new ArrayList<String>();
		int pos = 0;
		widths.add(pos,"24px"); 
		widths.add(++pos,"24px");		
		widths.add(++pos,"150px");
		widths.add(++pos,"200px");
		widths.add(++pos,"320px");
		widths.add(++pos,"70px");
		widths.add(++pos,"150px");
		widths.add(++pos,"150px");
		widths.add(++pos,"80px");
		widths.add(++pos,"80px");
		widths.add(++pos,"80px");
		widths.add(++pos,"120px");
		widths.add(++pos,"120px");
		widths.add(++pos,"70px");
		widths.add(++pos,"70px");
		widths.add(++pos,"70px");
		widths.add(++pos,"70px");
		widths.add(++pos,"70px");
		widths.add(++pos,"1px");
		
		for (int col = 0; col < widths.size(); col++) {
			table.getColumnFormatter().setWidth(col, widths.get(col));
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		int posTitle = 0;
		table.setHTML(0, posTitle, Sfa.constant().whiteSpace());
		table.setHTML(0, ++posTitle, Sfa.constant().whiteSpace());
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().numSS()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().numCuenta()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().razonSocial()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().equipos()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().usuarioCreacion()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().vendedor()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().pinScoring()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().scoringTitle()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().veraz()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().ultimoEstado()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().fechaCreacion()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().fechaCierre()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().pataconexTabla()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().firmasTabla()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().anticipo()));
		table.setWidget(0, ++posTitle, new SimpleLink(Sfa.constant().desv()));	
		
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
		//LF#3
//		if(isAnalistaCreditos()) {
//			numeroSS = resultTable.getHTML(cell.getRowIndex(), 2).toString();
//		} else {
			numeroSS = resultTable.getHTML(cell.getRowIndex(), 1).toString();
//		}
		
		// 18 es el numero de columna en el cual esta oculto el id_solicitud_servicio para realizar la busqueda de solicitudes.
		if(cell.getRowIndex() != 0) {
			
			//LF#3
//			if(isAnalistaCreditos()) {
//				Long idSS = Long.parseLong(resultTable.getHTML(cell.getRowIndex(), 18));
//				solicitud = buscarSS(idSS);
//			} else {
				solicitud = buscarSS(numeroSS);
//			}
			
				//LF#3
//			if(isAnalistaCreditos()) {
//				if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() > 1)) {
//					if(solicitud.getIdVantive() != null) {
//						mostrarTablaDetalleSolicitudServicio();
//					} else {
//						// oculto la tabla de detalle.
//						cambiosSSCerradasResultUI.setVisible(false);
//					}
//				} else if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() == 1)) {
//					abrirArchivoRTF();
//				} else if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() == 0)) {
//					String url =  this.obtenerUrlSS(solicitud.getIdGrupoSolicitud(), solicitud.getNumeroCuenta(), solicitud.getIdCuenta());
//					url = "idss="+Long.parseLong(resultTable.getHTML(cell.getRowIndex(), 18))+"&" + url;
//					if(solicitud.getEnCarga()) {
//						new OpenPageCommand(UILoader.AGREGAR_SOLICITUD,url).execute();
//					} else {
//						new OpenPageCommand(UILoader.VER_SOLICITUD,url).execute();
//					}
//				}
//			} else {
				if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() >= 1) && solicitud.getRemito() == null) {
					mostrarTablaDetalleSolicitudServicio();
				} else if ((cell.getRowIndex() >= 1) && (cell.getCellIndex() == 0)) {
//					MGR - #3423 - Para que se muestre el pop-up que lista los archivos disponibles
					BuscarSSCerradasReportsDialog reportsDialog = new BuscarSSCerradasReportsDialog();
					reportsDialog.show(solicitud);
				}
//			}
				//LF#3
//		} else {
//			if(isAnalistaCreditos()) {
//				setSolicitudServicioDto(ordenTablaSS.ordenar(cell.getCellIndex(), solicitudesServicioCerradaResultDto));
//			}			
		}

	}
	
	public void mostrarTablaDetalleSolicitudServicio(){
		SolicitudRpcService.Util.getInstance().getDetalleSolicitudServicio(solicitud.getId(),
				new DefaultWaitCallback<DetalleSolicitudServicioDto>() {
					public void success(DetalleSolicitudServicioDto result) {
						cambiosSSCerradasResultUI.setSolicitudServicioCerradaDto(result);
						cambiosSSCerradasResultUI.setVisible(true);
					}
				});
	}
	
	public void abrirArchivo(){
		
		final String contextRoot = WindowUtils.getContextRoot();
		fileName = null;
		if (solicitud.isNumeroCuentaAlCierreSSIdVantive()) {
			// Si es cliente usamos el codigo Vantive, sino el Id (ya que no podemos
			// guardar archivos con los caracteres de VANCUC
			fileName = solicitud.getIdCuenta().toString() + "-5-" + numeroSS;
		} else {
			fileName = solicitud.getIdCuenta().toString() + "-5-" + numeroSS;
		}
		
		//MGR - Los Vendedores del tipo retail generan pdf, el resto, rtf
        if(ClientContext.getInstance().getVendedor().isRetail())
        	fileName += ".pdf";
        else
        	fileName += ".rtf";
		
		final String filenameFinal = fileName;
		SolicitudRpcService.Util.getInstance().existReport(fileName, new DefaultWaitCallback<Boolean>() {
			public void success(Boolean result) {
				LoadingModalDialog.getInstance().hide();
				if (result) {
					WindowUtils.redirect("/" + contextRoot + "/download/" + filenameFinal
							+ "?module=solicitudes&service=rtf&name=" + filenameFinal);
				} else {
					
					//MGR - #1415 - Solo se envia el id de la solcitud
					SolicitudRpcService.Util.getInstance().crearArchivo(solicitud.getId(), false, new DefaultWaitCallback<Boolean>() {

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
	
	/**
	 * Metodo que obtiene la URL para poder dirigirla a la pantalla de SS.
	 * @param idGrupo
	 * @param codigoVantive
	 * @param idCuenta
	 * @return
	 */
	private String obtenerUrlSS(Long idGrupo, String codigoVantive,Long idCuenta) {
		StringBuilder builder = new StringBuilder();
		if (codigoVantive != null)
			builder.append(EditarSSUI.CODIGO_VANTIVE + "=" + codigoVantive + "&");
		if (idCuenta != null && idCuenta > 0)
			builder.append(EditarSSUI.ID_CUENTA + "=" + idCuenta + "&");
		if (idGrupo != null)
			builder.append(EditarSSUI.ID_GRUPO_SS + "=" + idGrupo);
		return builder.toString();
	}
	
	public String getUrlReporte(String fileName) {
		return "/" + WindowUtils.getContextRoot() + "/download/" + fileName
		+ "?module=solicitudes&service=rtf&name=" + fileName;
	}
	
	private SolicitudServicioCerradaResultDto buscarSS(String numeroSS) {
		for (Iterator iterator = solicitudesServicioCerradaResultDto.iterator(); iterator.hasNext();) {
			SolicitudServicioCerradaResultDto solicitudServicioDto = (SolicitudServicioCerradaResultDto) iterator
					.next();
			if (solicitudServicioDto.getNumeroSS()!= null && solicitudServicioDto.getNumeroSS().equals(numeroSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}

	
	/**
	 * Realiza la busqueda de la SS por medio del id_SS (id_solicitud_servicio) que se pasa por parametro.
	 * @param idSS
	 * @return
	 */
	private SolicitudServicioCerradaResultDto buscarSS(Long idSS) {
		for (Iterator iterator = solicitudesServicioCerradaResultDto.iterator(); iterator.hasNext();) {
			SolicitudServicioCerradaResultDto solicitudServicioDto = (SolicitudServicioCerradaResultDto) iterator
					.next();
			if (solicitudServicioDto.getSolicitudServicio().getId().equals(idSS)) {
				return solicitudServicioDto;
			}
		}
		return null;
	}
	
	public void setCambiosSSCerradasResultUI(CambiosSSCerradasResultUI cambiosSSCerradasResultUI) {
		this.cambiosSSCerradasResultUI = cambiosSSCerradasResultUI;
	}
	
//	LF#3
//	public boolean isAnalistaCreditos() {
//		return analistaCreditos;
//	}
//
//	public void setAnalistaCreditos(boolean analistaCreditos) {
//		this.analistaCreditos = analistaCreditos;
//	}

	/**
	 * Metodo que se utiliza para calcular si la ss tiene algun desvio. El mismo se calcula obteniendo
	 * la diferencia entre el precio del plan � el precio de venta del plan mas la sumatoria de la diferencia 
	 * del precio de lista y el precio de venta de todos los servicios seleccionados de la SS.
	 * Si no tiene desvios retorna 0 (cero).
	 * @param ssCerradaResultDto
	 * @return 
	 */
	public String calcularDesvio(SolicitudServicioCerradaResultDto ssCerradaResultDto) {
		double precioListaPlan = 0;
		double precioVentaPlan = 0;
		double precioListaServicio = 0;
		double precioVentaServicio = 0;
		double precioItemTotal = 0;
		for (LineaSolicitudServicioDto linea : ssCerradaResultDto.getLineas()) {
			precioListaPlan = precioListaPlan + linea.getPrecioListaPlan();
			precioVentaPlan = precioVentaPlan + linea.getPrecioVentaPlan();
			for (ServicioAdicionalLineaSolicitudServicioDto servicioAd : linea.getServiciosAdicionales()) {
				if (servicioAd.isChecked()) {
					precioListaServicio = precioListaServicio + servicioAd.getPrecioLista();
					precioVentaServicio = precioVentaServicio + servicioAd.getPrecioVenta();
				}
			}	
		}
		precioItemTotal = precioListaPlan - precioVentaPlan + (precioListaServicio - precioVentaServicio);
		ssCerradaResultDto.setDesvios(String.valueOf(precioItemTotal));
		if(precioItemTotal == 0) 
			return "$ 0.00";
		return String.valueOf("$ " + precioItemTotal);
	}
	
	/**
	 * Metodo que calcula a travez de las lineas de SS, la cantidad de equipos que posee.
	 * Esto se calcula si es EQUIPO,SERVICIO y si es Sim.
	 * @param lineaSS
	 * @return int 
	 */
	public Long calcularCantEquipos(List<LineaSolicitudServicioDto> lineaSS){
		Long cantEquipos = new Long(0);
		for (Iterator<LineaSolicitudServicioDto> iterator = lineaSS.iterator(); iterator.hasNext();) {
			LineaSolicitudServicioDto lineaSolicitudServicioDto = iterator.next();
			if(lineaSolicitudServicioDto.getItem().getEsSim() || lineaSolicitudServicioDto.getItem().isEquipo() || lineaSolicitudServicioDto.getItem().isServicio()) {
				cantEquipos++;
			}
		}
		return cantEquipos;
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
