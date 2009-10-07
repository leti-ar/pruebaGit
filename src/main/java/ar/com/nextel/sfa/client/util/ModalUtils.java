package ar.com.nextel.sfa.client.util;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Herramientas para solucionar problemas con paneles modal. Por ejemplo para el bug de Explorer con los
 * listBox
 */
public class ModalUtils {

	public static PopupPanel showModal(PopupPanel popupPanel) {
		if (!getUserAgent().contains("msie")) {
			return null;
		}
		final PopupPanel popup;
		if (popupPanel == null) {
			popup = new PopupPanel();
			popup.addStyleName("modalPopup");
			Window.addResizeHandler(new ResizeHandler() {
				public void onResize(ResizeEvent event) {
					updateFullScreenSize(popup.getWidget());
				}
			});
			popup.setWidget(new HTML());
		} else {
			popup = popupPanel;
		}
		popup.show();
		updateFullScreenSize(popup.getWidget());
		return popup;
	}

	public static PopupPanel hideModal(PopupPanel popupPanel) {
		if (!getUserAgent().contains("msie") || popupPanel == null) {
			return null;
		}
		popupPanel.hide();
		return popupPanel;
	}

	private static void updateFullScreenSize(Widget widget) {
		int width = Window.getClientWidth();
		if (RootPanel.getBodyElement().getOffsetHeight() > width) {
			width = RootPanel.getBodyElement().getOffsetHeight();
		}
		widget.setWidth(width + "px");
		widget.setHeight(RootPanel.getBodyElement().getOffsetWidth() + "px");
	}

	private static native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
}
