package ar.com.nextel.sfa.client.util;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/**
 * Utilidades para la generacion y descarga de reportes 
 * @author monczer
 */
public class ReportUtils {

	/**
	 * Descarga archivo relativo a la solicitud (solicitud, remito, portabilidad)
	 * @param fileName
	 */
	public static void descargarArchivoSS(Long idSolicitud, String fileNameArg){
		final String fileName = fileNameArg;
		final String contextRoot = WindowUtils.getContextRoot();
		final Long idSS = idSolicitud;

		SolicitudRpcService.Util.getInstance().existReport(fileName, new DefaultWaitCallback<Boolean>() {
			@Override
			public void success(Boolean result) {
				LoadingModalDialog.getInstance().hide();
				if(result) {
					WindowUtils.redirect("/" + contextRoot + "/download/" + fileName + "?module=solicitudes&service=rtf&name=" + fileName);
				}else{
					SolicitudRpcService.Util.getInstance().crearArchivo(idSS, false,new DefaultWaitCallback<Boolean>() {
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
	public static String getUrlReporte(String fileName) {
		return "/" + WindowUtils.getContextRoot() + "/download/" + fileName
				+ "?module=solicitudes&service=rtf&name=" + fileName;
	}
	
}
