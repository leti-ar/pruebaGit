package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.bcel.generic.LSTORE;

import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Pantalla que muestra mientras se cierra la solicitud y una vez cerrada
 * 
 * @author jlgperez
 * 
 */
public class CerradoSSExitosoDialog extends NextelDialog implements ClickListener {

	private FlowPanel cierreExitoso;
	private Command aceptarCommand;
	private InlineHTML successText;
	private SimpleLink solicitudLink;
	private SimpleLink aceptar;
	private String fileName;
	private Long idSolicitudCerrada;
	private Grid solicitudRtf;
	
	private static Command closeCommand;
	private static CerradoSSExitosoDialog instance;

	public static CerradoSSExitosoDialog getInstance() {
		if (instance == null) {
			instance = new CerradoSSExitosoDialog("Cerrar SS", false, true);
		}
		return instance;
	}

	private CerradoSSExitosoDialog(String title) {
		super(title);
		loadThings();
//		init();
	}

	private CerradoSSExitosoDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
		loadThings();
//		init();
	}

	private void loadThings(){
		successText = new InlineHTML();
		cierreExitoso = new FlowPanel();
		solicitudLink = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		aceptar = new SimpleLink(Sfa.constant().aceptar());
	}
	
	private void init(List<String> rtfFileNamePorta) {
		addStyleName("gwt-CerrarSSDialog");
		mainPanel.setWidth("350px");
//		successText = new InlineHTML();
//		cierreExitoso = new FlowPanel();
		Grid layout = new Grid(1, 2);
		layout.setWidth("290px");
		Image check = new Image("images/ss-check.gif");
		check.addStyleName("mb10 mr10");
		layout.setWidget(0, 0, check);
		layout.setWidget(0, 1, successText);
		layout.addStyleName("m30 cierreExitosoTable");
		layout.getRowFormatter().setVerticalAlign(0, HasAlignment.ALIGN_MIDDLE);
		cierreExitoso.add(layout);
//		solicitudLink = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		solicitudLink.addClickListener(this);
		
		solicitudRtf = new Grid(1 + rtfFileNamePorta.size(), 2);
		solicitudRtf.setWidget(0, 0, IconFactory.word());
		solicitudRtf.setWidget(0, 1, solicitudLink);
		
		for (int i = 0; i < rtfFileNamePorta.size(); i++) {
			solicitudRtf.setWidget(i + 1, 0, IconFactory.word());
			solicitudRtf.setWidget(i + 1, 1, new SimpleLink(rtfFileNamePorta.get(i),"#" + History.getToken(),true));
			((SimpleLink)solicitudRtf.getWidget(i + 1, 0)).addClickListener(this);
			((SimpleLink)solicitudRtf.getWidget(i + 1, 0)).setTitle(rtfFileNamePorta.get(i));
		}
		
		cierreExitoso.add(solicitudRtf);
		cierreExitoso.setWidth("350px");
		add(cierreExitoso);
		cierreExitoso.setVisible(false);

//		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(this);
		addFormButtons(aceptar);
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			hide();
			aceptarCommand.execute();
		}else if(sender == solicitudLink) tirarDownload(fileName);
		else{
			boolean find = false;
			SimpleLink link;
			
			for(int i = 1; i < solicitudRtf.getRowCount() && !find; i++){
				link = (SimpleLink)solicitudRtf.getWidget(i, 1);
				if(sender == link){
					find = true;
					tirarDownload(link.getTitle());
				}
			}
			
		}

	}

	private void tirarDownload(final String file){
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(file));
		requestBuilder.setCallback(new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				WaitWindow.hide();
				LoadingModalDialog.getInstance().hide();
				if (response.getStatusCode() == Response.SC_OK) {
					WindowUtils.redirect(getUrlReporte(file));
				} else {
//					showFileNotFoundError();
					//MGR - #1415 - Si por alguna razon no se genero el archivo, trato de generarlo nuevamente
					SolicitudRpcService.Util.getInstance().crearArchivo(idSolicitudCerrada, false, new DefaultWaitCallback<Boolean>() {

						@Override
						public void success(Boolean result) {
							
							RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(file));
							requestBuilder.setCallback(new RequestCallback() {
								public void onResponseReceived(Request request, Response response) {
									WaitWindow.hide();
									LoadingModalDialog.getInstance().hide();
									if (response.getStatusCode() == Response.SC_OK) {
										WindowUtils.redirect(getUrlReporte(file));
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

			public void onError(Request request, Throwable exception) {
				WaitWindow.hide();
				LoadingModalDialog.getInstance().hide();
				showFileNotFoundError();
			}
		});
		try {
			requestBuilder.setTimeoutMillis(10 * 1000);
			requestBuilder.send();
			WaitWindow.show();
			LoadingModalDialog.getInstance().showAndCenter("Solicitud",
					"Esperando Solicitud de Servicio ...");
		} catch (RequestException e) {
			showFileNotFoundError();
			LoadingModalDialog.getInstance().hide();
		}
	}
	
	private void showFileNotFoundError() {
		MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
				MessageDialog.getCloseCommand());
	}

	public void showLoading(boolean cerrando) {
		successText.setText("La solicitud se " + (cerrando ? "cerró" : "generó") + " correctamente");
		setDialogTitle(cerrando ? "Cerrar SS" : "Generar SS");
		LoadingModalDialog.getInstance().showAndCenter(cerrando ? "Cerrar SS" : "Generar SS",
				cerrando ? "Cerrando solicitud" : "Generando solicitud");
		cierreExitoso.setVisible(false);
		formButtons.setVisible(false);
	}
	
	public void hideLoading(){
		LoadingModalDialog.getInstance().hide();
	}

	public void showCierreExitoso(GeneracionCierreResultDto cierreResult, Long idSolicitud) {
		List<String> rtfFileNamePorta = new ArrayList<String>();
		
		for (String str : cierreResult.getRtfFileNamePortabilidad()) {
			rtfFileNamePorta.add(str);
		}

		for (String str : cierreResult.getRtfFileNamePortabilidad_adj()) {
			rtfFileNamePorta.add(str);
		}
		
		Collections.sort(rtfFileNamePorta);
		init(rtfFileNamePorta);

		cierreExitoso.setVisible(true);
		formButtons.setVisible(true);
		this.fileName = cierreResult.getRtfFileName();
		
		this.idSolicitudCerrada = idSolicitud;
		showAndCenter();
	}

	public String getUrlReporte(String fileName) {
		return "/" + WindowUtils.getContextRoot() + "/download/" + fileName
				+ "?module=solicitudes&service=rtf&name=" + fileName;
	}

	/** Este comando cierra la ventana sin realizar ninguna accion */
	public static Command getCloseCommand() {
		if (closeCommand == null) {
			closeCommand = new Command() {
				public void execute() {
					getInstance().hide();
				}
			};
		}
		return closeCommand;
	}

	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}
}
