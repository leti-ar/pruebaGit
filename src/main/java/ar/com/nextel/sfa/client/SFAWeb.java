package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.widget.HeaderMenu;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point principal de la aplicación web SFA Revolution
 */
public class SFAWeb implements EntryPoint {

	private static HeaderMenu headerMenu;

	public void onModuleLoad() {
		SFAWeb.headerMenu = new HeaderMenu();
		RootPanel.get().add(SFAWeb.headerMenu);
		RootPanel.get().add(UILoader.getInstance());
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler(){
			public void onUncaughtException(Throwable e) {
				ErrorDialog.getInstance().show(e);
			}
		});
	}

	public static HeaderMenu getHeaderMenu() {
		return headerMenu;
	}

}
