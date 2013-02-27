package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.SolicitudServicioCerradaResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class BuscarSSCerradasReportsDialog extends NextelDialog{

	private static final String TITULO = "Descargar Reportes de la Solicitud "; 
	
	private SimpleLink lnkSS;
	private SimpleLink lnkAceptar;
	private TitledPanel pnlDescripcionReplica;
	private Grid gridLayout;
	private ClickListener clickListener; 
	private String fileSolicitudReport;
	private List<String> portabilidadFileNames;
//	MGR - Reporte remito
	private String fileRemitoRtf;
	private SimpleLink lnkRemito;
	
	private Long idSolicitud;
	
	/**
	 * 
	 */
	public BuscarSSCerradasReportsDialog() {
		super(TITULO,false,true);
		addStyleName("gwt-ItemSolicitudDialog");

		pnlDescripcionReplica = new TitledPanel("Archivos");
		gridLayout = new Grid();
		lnkSS = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		lnkAceptar = new SimpleLink("ACEPTAR");
		lnkRemito = new SimpleLink("Remito link", "#" + History.getToken(), true);
	}

	/**
	 * 
	 * @param solicitudServicio
	 */
	public void show(SolicitudServicioCerradaResultDto solicitudServicio){
		idSolicitud = solicitudServicio.getId();
		
		String reportFileName = solicitudServicio.getIdCuenta().toString() + "-5-" + solicitudServicio.getNumeroSS();
		//MGR - Los Vendedores del tipo retail generan pdf, el resto, rtf
        if(ClientContext.getInstance().getVendedor().isRetail())
        	reportFileName += ".pdf";
        else
        	reportFileName += ".rtf";
		
		// Setea el nombre del archivo de la solicitud de servicio
		setFileSolicitudReport(reportFileName);
		
//		MGR - Reporte remito - Seteo el nombre del remito de la solicitud si corresponde
		if(solicitudServicio.getRemito() != null)
			setFileRemitoRtf("remito_" + solicitudServicio.getNumeroSS() + ".rtf");
		else
			setFileRemitoRtf(null);
		
		// Genera los parametros y los nombres de los archivos de las solicitudes de portabilidad
		SolicitudRpcService.Util.getInstance().generarParametrosPortabilidadRTF(idSolicitud, 
				new DefaultWaitCallback<List<String>>() {
					@Override
					public void success(List<String> result) {
//						MGR - Reporte remito
						portabilidadFileNames = new ArrayList<String>();
						for (String fname : result) {
							portabilidadFileNames.add(fname.substring(0,fname.lastIndexOf(".")));
						}
						Collections.sort(portabilidadFileNames);

						// inicializa el escuchador
						clickListener = new ClickListener() {
							public void onClick(Widget sender) {
								if(sender == lnkAceptar) 
									hide();
								else if(sender == lnkSS) 
									descargarArchivo(getFileSolicitudReport());
								else if(sender == lnkRemito)
									descargarArchivo(getFileRemitoRtf());
								else{
//									MGR - Reporte remito
									int fila = fileRemitoRtf == null ? 1 : 2;
									boolean encontro = false;
										String extension = null;
										//MGR - Los Vendedores del tipo retail generan pdf, el resto, rtf
								        if(ClientContext.getInstance().getVendedor().isRetail())
								        	extension = ".pdf";
								        else
								        	extension = ".rtf";
									for(int i = 0; i < portabilidadFileNames.size() && !encontro; i++){
										if(sender == gridLayout.getWidget(i + fila, 1)){
											descargarArchivo(((SimpleLink)gridLayout.getWidget(i + fila, 1)).getTitle() + extension);
											encontro = true;
										}
									}
								}
							}
						};

//						MGR - Reporte remito
						int fila = 1 + (fileRemitoRtf == null ? 0 : 1);
						gridLayout.resize(fila + portabilidadFileNames.size(),2);
						
						gridLayout.addStyleName("layout");
						gridLayout.getColumnFormatter().setWidth(1, "320px");

						// Carga la lista primeros con el de la solicitud de servicio,
						//luego con el remito si corresponde y despues con los de portabilidad
						gridLayout.setWidget(0,0, IconFactory.word());
						gridLayout.setWidget(0,1, lnkSS);
						lnkSS.addClickListener(clickListener);
						lnkSS.setTitle(getFileSolicitudReport());
						
//						MGR - Reporte remito - Hay Remito
						if(getFileRemitoRtf() != null){
							gridLayout.setWidget(1,0, IconFactory.word());
							gridLayout.setWidget(1,1, lnkRemito);
							lnkRemito.addClickListener(clickListener);
							lnkRemito.setTitle(getFileRemitoRtf());
						}
							
						for(int i = 0; i < portabilidadFileNames.size(); i++){
							gridLayout.setWidget(i + fila, 0, IconFactory.word());
							gridLayout.setWidget(i + fila, 1, new SimpleLink(portabilidadFileNames.get(i), "#" + History.getToken(), true));
							((SimpleLink)gridLayout.getWidget(i + fila, 1)).addClickListener(clickListener);
							((SimpleLink)gridLayout.getWidget(i + fila, 1)).setTitle(portabilidadFileNames.get(i));
						}
						
						pnlDescripcionReplica.add(gridLayout);
						lnkAceptar.addClickListener(clickListener);

						add(pnlDescripcionReplica);
						addFormButtons(lnkAceptar);

						setFormButtonsVisible(true);
						setFooterVisible(false);

						showAndCenter();
					}
		});
	}
	
	/**
	 * 
	 * @param fileName
	 */
	private void descargarArchivo(String fileNameArg){
		final String fileName = fileNameArg;
		final String contextRoot = WindowUtils.getContextRoot();

		SolicitudRpcService.Util.getInstance().existReport(fileName, new DefaultWaitCallback<Boolean>() {
			@Override
			public void success(Boolean result) {
				LoadingModalDialog.getInstance().hide();
				if(result) {
					WindowUtils.redirect("/" + contextRoot + "/download/" + fileName + "?module=solicitudes&service=rtf&name=" + fileName);
				}else{
					SolicitudRpcService.Util.getInstance().crearArchivo(idSolicitud, false,new DefaultWaitCallback<Boolean>() {
						@Override
						public void success(Boolean result) {
							RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(fileName));
							
							requestBuilder.setCallback(new RequestCallback() {
								public void onResponseReceived(Request request, Response response) {
									WaitWindow.hide();
									LoadingModalDialog.getInstance().hide();
									
									if (response.getStatusCode() == Response.SC_OK) WindowUtils.redirect(getUrlReporte(fileName));
									else MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
								}

								public void onError(Request request, Throwable exception) {
									WaitWindow.hide();
									LoadingModalDialog.getInstance().hide();
									MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
								}
							});

							try {
								requestBuilder.setTimeoutMillis(10 * 1000);
								requestBuilder.send();
								WaitWindow.show();
								LoadingModalDialog.getInstance().showAndCenter("Solicitud","Esperando Solicitud de Servicio ...");
							} catch (RequestException e) {
								MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
								LoadingModalDialog.getInstance().hide();
							}
						
						}// end success
					});
				}// end else
			}
			
			@Override
			public void failure(Throwable caught) {
				WaitWindow.hide();
				LoadingModalDialog.getInstance().hide();
				super.failure(caught);
			}
		});

	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public String getUrlReporte(String fileName) {
		return "/" + WindowUtils.getContextRoot() + "/download/" + fileName
				+ "?module=solicitudes&service=rtf&name=" + fileName;
	}

	public String getFileSolicitudReport() {
		return fileSolicitudReport;
	}

	public void setFileSolicitudReport(String fileSolicitudReport) {
		this.fileSolicitudReport = fileSolicitudReport;
	}

	public String getFileRemitoRtf() {
		return fileRemitoRtf;
	}

	public void setFileRemitoRtf(String fileRemitoRtf) {
		this.fileRemitoRtf = fileRemitoRtf;
	}
}
