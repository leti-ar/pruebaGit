package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.dialog.CustomDialogBox;

/**
 * @author <a href="mailto:jperez@snoop.com.ar">Jorge L Garcia</a>
 */
public class NextelDialog extends CustomDialogBox {

	public NextelDialog(String title) {
		this(title, false, false);
	}

	public NextelDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
		addStyleName("gwt-NextelDialog");
		addStyleName("whiteBackground");
		formButtons.setVisible(false);
	}

}
