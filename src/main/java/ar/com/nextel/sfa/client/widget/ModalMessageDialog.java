package ar.com.nextel.sfa.client.widget;

import com.google.gwt.user.client.Command;

public class ModalMessageDialog extends MessageDialog {

	private static ModalMessageDialog instance;
	private static Command closeCommand;

	public static ModalMessageDialog getInstance() {
		if (instance == null) {
			instance = new ModalMessageDialog(" ", false, true);
		}
		return instance;
	}

	private ModalMessageDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
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
}
