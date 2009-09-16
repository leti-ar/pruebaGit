package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class LoadingModalDialog extends NextelDialog {

	private static LoadingModalDialog loadingModalDialog;

	private HTML loadingMessage;

	public static LoadingModalDialog getInstance() {
		if (loadingModalDialog == null) {
			loadingModalDialog = new LoadingModalDialog();
		}
		return loadingModalDialog;
	}

	private LoadingModalDialog() {
		super("", false, true);
		setFooterVisible(false);
		setFormButtonsVisible(false);
		mainPanel.setWidth("350px");
		FlowPanel loadingPanel = new FlowPanel();
		loadingPanel.addStyleName("alignCenter m30");
		loadingMessage = new HTML("Cargando");
		loadingPanel.add(loadingMessage);
		loadingPanel.add(new Image("images/loader.gif"));
		add(loadingPanel);
		mainPanel.add(loadingPanel);
	}

	public void showAndCenter() {
		setDialogTitle("");
		loadingMessage.setHTML("Cargando...");
		super.showAndCenter();
	}

	public void showAndCenter(String title, String message) {
		setDialogTitle(title);
		loadingMessage.setHTML(message);
		super.showAndCenter();
	}
}
