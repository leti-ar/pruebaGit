package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.util.WindowUtils;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
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
import com.google.gwt.user.client.ui.HTML;
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
public class CerrarSSDialog extends NextelDialog implements ClickListener {

	private FlowPanel loadingPanel;
	private FlowPanel cierreExitoso;
	private Command aceptarCommand;
	private InlineHTML successText;
	private SimpleLink solicitudLink;
	private SimpleLink aceptar;
	private HTML loadingMessage;
	private String fileName;

	private static Command closeCommand;
	private static CerrarSSDialog instance;

	public static CerrarSSDialog getInstance() {
		if (instance == null) {
			instance = new CerrarSSDialog("Cerrar SS", false, true);
		}
		return instance;
	}

	private CerrarSSDialog(String title) {
		super(title);
		init();
	}

	private CerrarSSDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
		init();
	}

	private void init() {
		addStyleName("gwt-CerrarSSDialog");
		mainPanel.setWidth("350px");
		loadingPanel = new FlowPanel();
		loadingPanel.addStyleName("alignCenter m30");
		loadingMessage = new HTML("Cerrando solicitud");
		loadingPanel.add(loadingMessage);
		loadingPanel.add(new Image("images/loader.gif"));
		add(loadingPanel);
		loadingPanel.setVisible(false);

		successText = new InlineHTML();
		cierreExitoso = new FlowPanel();
		Grid layout = new Grid(1, 2);
		layout.setWidth("290px");
		Image check = new Image("images/ss-check.gif");
		check.addStyleName("mb10 mr10");
		layout.setWidget(0, 0, check);
		layout.setWidget(0, 1, successText);
		layout.addStyleName("m30 cierreExitosoTable");
		layout.getRowFormatter().setVerticalAlign(0, HasAlignment.ALIGN_MIDDLE);
		cierreExitoso.add(layout);
		solicitudLink = new SimpleLink("Solicitud link", "#" + History.getToken(), true);
		solicitudLink.addClickListener(this);
		Grid solicitudRtf = new Grid(1, 2);
		solicitudRtf.setWidget(0, 0, IconFactory.word());
		solicitudRtf.setWidget(0, 1, solicitudLink);
		cierreExitoso.add(solicitudRtf);
		cierreExitoso.setWidth("350px");
		add(cierreExitoso);
		cierreExitoso.setVisible(false);

		aceptar = new SimpleLink(Sfa.constant().aceptar());
		aceptar.addClickListener(this);
		addFormButtons(aceptar);
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			hide();
			aceptarCommand.execute();
		} else if (sender == solicitudLink) {
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, getUrlReporte(fileName));
			requestBuilder.setCallback(new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					WaitWindow.hide();
					if (response.getStatusCode() == 200) {
						WindowUtils.redirect(getUrlReporte(fileName));
					} else {
						showFileNotFoundError();
					}
				}

				public void onError(Request request, Throwable exception) {
					WaitWindow.hide();
					showFileNotFoundError();
				}
			});
			try {
				requestBuilder.send();
				WaitWindow.show();
			} catch (RequestException e) {
				showFileNotFoundError();
			}
		}
	}

	private void showFileNotFoundError() {
		MessageDialog.getInstance().showAceptar("Error", Sfa.constant().ERR_FILE_NOT_FOUND(),
				MessageDialog.getCloseCommand());
	}

	public void showLoading(boolean cerrando) {
		successText.setText("La solicitud se " + (cerrando ? "cerró" : "generó") + " correctamente");
		setDialogTitle(cerrando ? "Cerrar SS" : "Generar SS");
		loadingMessage.setHTML(cerrando ? "Cerrando solicitud" : "Generando solicitud");
		loadingPanel.setVisible(true);
		cierreExitoso.setVisible(false);
		formButtons.setVisible(false);
		showAndCenter();
	}

	public void showCierreExitoso(String fileName) {
		loadingPanel.setVisible(false);
		cierreExitoso.setVisible(true);
		formButtons.setVisible(true);
		this.fileName = fileName;
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
