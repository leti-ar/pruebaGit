package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
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

public class BuscarSSCerradasRemitoDialog extends NextelDialog {

private static final String TITULO = "Descargar Remito "; 
	
	private SimpleLink lnkSS;
	private SimpleLink lnkAceptar;
	private TitledPanel pnlDescripcionReplica;
	private Grid gridLayout;
	private ClickListener clickListener; 
	private Long idSolicitud;
	private String fileRemitoRtf;
	
	public BuscarSSCerradasRemitoDialog() {
		super(TITULO,false,true);
		addStyleName("gwt-ItemSolicitudDialog");

		pnlDescripcionReplica = new TitledPanel("Archivos");
		gridLayout = new Grid();
		lnkSS = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		lnkAceptar = new SimpleLink("ACEPTAR");
	}
	
	public void show(SolicitudServicioCerradaResultDto solicitudServicio){
		WaitWindow.hide();
		// inicializa el escuchador
		idSolicitud = solicitudServicio.getId();
		clickListener = new ClickListener() {
			public void onClick(Widget sender) {
				if(sender == lnkAceptar) hide();
				else if(sender == lnkSS) descargarArchivo(fileRemitoRtf);
				else{
					boolean encontro = false;
					for(int i = 0; i < 1 && !encontro; i++){
						if(sender == gridLayout.getWidget(i + 1, 1)){
							descargarArchivo(((SimpleLink)gridLayout.getWidget(i + 1, 1)).getTitle() + ".rtf");
							encontro = true;
						}
					}
				}
			}
		};
		
		
		setFileRemitoRtf("remito_" + solicitudServicio.getNumeroSS() + ".rtf");
		gridLayout.resize(2,2);
		gridLayout.addStyleName("layout");
		gridLayout.getColumnFormatter().setWidth(1, "320px");

		gridLayout.setWidget(0,0, IconFactory.word());
		gridLayout.setWidget(0,1, lnkSS);
		lnkSS.addClickListener(clickListener);
		
		lnkSS.setTitle("Titulo");

		((SimpleLink)gridLayout.getWidget(0, 1)).addClickListener(clickListener);
		((SimpleLink)gridLayout.getWidget(0, 1)).setTitle("Descargar remito");
		
		pnlDescripcionReplica.add(gridLayout);
		lnkAceptar.addClickListener(clickListener);

		add(pnlDescripcionReplica);
		addFormButtons(lnkAceptar);

		setFormButtonsVisible(true);
		setFooterVisible(false);

		showAndCenter();
	}

	public String getFileRemitoRtf() {
		return fileRemitoRtf;
	}

	public void setFileRemitoRtf(String fileRemitoRtf) {
		this.fileRemitoRtf = fileRemitoRtf;
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
					SolicitudRpcService.Util.getInstance().crearRemito(idSolicitud, new DefaultWaitCallback<Boolean>() {
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
}
