package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.GeneracionCierreResultDto;
import ar.com.nextel.sfa.client.dto.NumeroReservaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.util.ReportUtils;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SimplePanel;
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
	private SimpleLink remitoLink;
	private SimpleLink aceptar;
	private String fileName;
	private String remitoName;
	private Long idSolicitudCerrada;
	private Grid solicitudReport;
	
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
	}

	private CerradoSSExitosoDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
		loadThings();
	}

	private void loadThings(){
		successText = new InlineHTML();
		cierreExitoso = new FlowPanel();
		solicitudLink = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(this);
//		MGR - Remito
		remitoLink = new SimpleLink("Remito link", "#" + History.getToken(), true);
	}
	
	private void init(List<String> fileNamePorta, List<NumeroReservaDto> numerosReservados) {
		cierreExitoso.clear();
		
		addStyleName("gwt-CerrarSSDialog");
		mainPanel.setWidth("350px");
		
		if (!numerosReservados.isEmpty()) {
			SimplePanel numerosReservadosTable = listarNumerosReservados(numerosReservados);
			cierreExitoso.add(numerosReservadosTable);
		}
		
		Grid layout = new Grid(1, 2);
		layout.setWidth("290px");
		Image check = new Image("images/ss-check.gif");
		check.addStyleName("mb10 mr10");
		layout.setWidget(0, 0, check);
		layout.setWidget(0, 1, successText);
		layout.addStyleName("m30 cierreExitosoTable");
		layout.getRowFormatter().setVerticalAlign(0, HasAlignment.ALIGN_MIDDLE);
		cierreExitoso.add(layout);
		solicitudLink.addClickListener(this);
		remitoLink.addClickListener(this);
		
//		MGR - Remito
		int filas = this.remitoName == null ? 0 : 1;
		filas += 1 + fileNamePorta.size();
		solicitudReport = new Grid(filas, 2);
		solicitudReport.setWidget(0, 0, IconFactory.word());
		solicitudReport.setWidget(0, 1, solicitudLink);
		
//		MGR - Remito
		if(this.remitoName != null){
			solicitudReport.setWidget(1, 0, IconFactory.word());
			solicitudReport.setWidget(1, 1, remitoLink);
		}
		
		//MGR - Los Vendedores del tipo retail generan pdf, el resto, rtf
		String extension = null;
        if(ClientContext.getInstance().getVendedor().isRetail())
        	extension = ".pdf";
        else
        	extension = ".rtf";
		
		List<String> aux = new ArrayList<String>();
		for (String fname : fileNamePorta) {
			aux.add(fname.substring(0,fname.lastIndexOf(".")));
		}
		Collections.sort(aux);
		
		int i = this.remitoName == null ? 0 : 1;
		for (;i < fileNamePorta.size(); i++) {
			solicitudReport.setWidget(i + 1, 0, IconFactory.word());
			solicitudReport.setWidget(i + 1, 1, new SimpleLink(aux.get(i) + extension,"#" + History.getToken(),true));
			((SimpleLink)solicitudReport.getWidget(i + 1, 1)).addClickListener(this);
			((SimpleLink)solicitudReport.getWidget(i + 1, 1)).setTitle(aux.get(i) + extension);
		}
		
		cierreExitoso.add(solicitudReport);
		cierreExitoso.setWidth("350px");
		add(cierreExitoso);
		cierreExitoso.setVisible(false);

		addFormButtons(aceptar);
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			hide();
			aceptarCommand.execute();
		}else if(sender == solicitudLink){ 
			ReportUtils.descargarArchivoSS(idSolicitudCerrada,fileName);
		}else if(sender == remitoLink){
			ReportUtils.descargarArchivoSS(idSolicitudCerrada,remitoName);
		}else{
			boolean find = false;
			SimpleLink link;
			
			for(int i = 1; i < solicitudReport.getRowCount() && !find; i++){
				link = (SimpleLink)solicitudReport.getWidget(i, 1);
				if(sender == link){
					find = true;
					ReportUtils.descargarArchivoSS(idSolicitudCerrada, link.getTitle());
				}
			}
		}
	}

//	private void tirarDownload(final String file){
//		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(file));
//		requestBuilder.setCallback(new RequestCallback() {
//			public void onResponseReceived(Request request, Response response) {
//				WaitWindow.hide();
//				LoadingModalDialog.getInstance().hide();
//				if (response.getStatusCode() == Response.SC_OK) {
//					WaitWindow.hide();
//					LoadingModalDialog.getInstance().hide();
//					WindowUtils.redirect(getUrlReporte(file));
//				} else {
////					showFileNotFoundError();
//					//MGR - #1415 - Si por alguna razon no se genero el archivo, trato de generarlo nuevamente
//					SolicitudRpcService.Util.getInstance().crearArchivo(idSolicitudCerrada, false, new DefaultWaitCallback<Boolean>() {
//						@Override
//						public void success(Boolean result) {
//							RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(file));
//							
//							requestBuilder.setCallback(new RequestCallback() {
//								public void onResponseReceived(Request request, Response response) {
//									WaitWindow.hide();
//									LoadingModalDialog.getInstance().hide();
//									
//									if (response.getStatusCode() == Response.SC_OK) WindowUtils.redirect(getUrlReporte(file));
//									else MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
//								}
//
//								public void onError(Request request, Throwable exception) {
//									WaitWindow.hide();
//									LoadingModalDialog.getInstance().hide();
//									MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
//								}
//							});
//
//							try {
//								requestBuilder.setTimeoutMillis(10 * 1000);
//								requestBuilder.send();
//								WaitWindow.show();
//								LoadingModalDialog.getInstance().showAndCenter("Solicitud","Esperando Solicitud de Servicio ...");
//							} catch (RequestException e) {
//								MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),MessageDialog.getCloseCommand());
//								LoadingModalDialog.getInstance().hide();
//							}
//						}
//					});
//				}
//			}
//
//			public void onError(Request request, Throwable exception) {
//				WaitWindow.hide();
//				LoadingModalDialog.getInstance().hide();
//				showFileNotFoundError();
//			}
//		});
//
//		try {
//			requestBuilder.setTimeoutMillis(10 * 1000);
//			requestBuilder.send();
//			WaitWindow.show();
//			LoadingModalDialog.getInstance().showAndCenter("Solicitud","Esperando Solicitud de Servicio ...");
//		} catch (RequestException e) {
//			showFileNotFoundError();
//			WaitWindow.hide();
//			LoadingModalDialog.getInstance().hide();
//		}
//	}
	
//	private void showFileNotFoundError() {
//		MessageDialog.getInstance().showAceptar(ErrorDialog.AVISO, Sfa.constant().ERR_FILE_NOT_FOUND(),
//				MessageDialog.getCloseCommand());
//	}

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
		List<String> fileNamePorta = new ArrayList<String>();

		for (String str : cierreResult.getFileNamePortabilidad()) 
			fileNamePorta.add(str.substring(str.lastIndexOf("\\") + 1));

		for (String str : cierreResult.getFileNamePortabilidad_adj()) 
			fileNamePorta.add(str.substring(str.lastIndexOf("\\") + 1));

		Collections.sort(fileNamePorta);

//		MGR - Remito
		this.remitoName = cierreResult.getRemitoRtfFileName();
		
		init(fileNamePorta, cierreResult.getNumerosReservados());
		
		cierreExitoso.setVisible(true);
		formButtons.setVisible(true);
		this.fileName = cierreResult.getReportSSFileName();
		
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
	
	private SimplePanel listarNumerosReservados(List<NumeroReservaDto> values) {
		
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("numerosReservadosTable mtb5 mlr5");

		FlexTable numerosReservaTable = new FlexTable();
		numerosReservaTable.addStyleName("dataTable");
		numerosReservaTable.setCellPadding(0);
		numerosReservaTable.setCellSpacing(0);
		numerosReservaTable.setWidth("100%");
		numerosReservaTable.getRowFormatter().addStyleName(0, "header");
		numerosReservaTable.setText(0, 0, "IMEI/SIM");
		numerosReservaTable.setText(0, 1, "Alias");
		numerosReservaTable.setText(0, 2, "Numero de Telefono");

		int row = 1;
		for (NumeroReservaDto value : values) {
			if (value.getImei().equals("") && value.getSim().equals("")) {
				numerosReservaTable.setText(row, 0, "-");
			}else{
				numerosReservaTable.setText(row, 0, value.getImei() + " / " + value.getSim());
			}
			numerosReservaTable.setText(row, 1, value.getAlias());
			numerosReservaTable.setText(row, 2, value.getNumero());
			row++;
		}
		wrapper.add(numerosReservaTable);
		return wrapper;
	}
	
}
