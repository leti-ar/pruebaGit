package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.util.ModalUtils;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingModalDialog extends NextelDialog {

	private static LoadingModalDialog loadingModalDialog;
	private PopupPanel modalPopup;
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
	
	public void show() {
		modalPopup = ModalUtils.showModal(modalPopup);
		super.show();
	}
	
	public void hide() {
		modalPopup = ModalUtils.hideModal(modalPopup);
		super.hide();
	}
}
