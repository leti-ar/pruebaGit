package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.infocom.InfocomUI;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class SFAInfocom implements EntryPoint {
	private InfocomUI infocomUI;

	public void onModuleLoad() {
		infocomUI = new InfocomUI();
		infocomUI.load();
		RootPanel.get().add(infocomUI);
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler(){
			public void onUncaughtException(Throwable e) {
				ErrorDialog.getInstance().show(e);
			}
		});
	}		
}


