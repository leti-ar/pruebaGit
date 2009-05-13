package ar.com.nextel.sfa.client.widget;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.CustomDialogBox;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class MessageDialog extends CustomDialogBox implements ClickListener {

	private static MessageDialog instance;
	private HTML message;
	private SimpleLink aceptar;
	private SimpleLink si;
	private SimpleLink no;
	private SimpleLink cancelar;
	private List<SimpleLink> links;
	private Command aceptarCommand;
	private Command siCommand;
	private Command noCommand;
	private Command cancelarCommand;
	private Command closeCommand;

	public static MessageDialog getInstance() {
		if (instance == null) {
			instance = new MessageDialog(" ", false, false);
		}
		return instance;
	}

	private MessageDialog(String title, boolean autoHide, boolean modal) {
		super(title, autoHide, modal);
		message = new HTML();
		add(message);
		message.addStyleName("message");
		links = new ArrayList();
		links.add(si = new SimpleLink(Sfa.constant().si()));
		links.add(no = new SimpleLink(Sfa.constant().no()));
		links.add(aceptar = new SimpleLink(Sfa.constant().aceptar()));
		links.add(cancelar = new SimpleLink(Sfa.constant().cancelar()));
		
		addFormButtons(si);
		addFormButtons(no);
		addFormButtons(aceptar);
		addFormButtons(cancelar);

		closeCommand = new Command() {
			public void execute() {
				hide();
			}
		};

		aceptar.addClickListener(this);
		cancelar.addClickListener(this);
		si.addClickListener(this);
		no.addClickListener(this);
	}

	public void showAceptar(String msg, Command aceptarComm) {
		message.setHTML(msg);
		aceptarCommand = aceptarComm;
		aceptar.setVisible(true);
		cancelar.setVisible(false);
		si.setVisible(false);
		no.setVisible(false);
		showAndCenter();
	}

	public void showAceptarCancelar(String msg, Command aceptarComm, Command cancelarComm) {
		message.setHTML(msg);
		aceptarCommand = aceptarComm;
		cancelarCommand = cancelarComm;
		aceptar.setVisible(true);
		cancelar.setVisible(true);
		si.setVisible(false);
		no.setVisible(false);
		showAndCenter();
	}

	public void showSiNo(String msg, Command siComm, Command noComm) {
		message.setHTML(msg);
		siCommand = siComm;
		noCommand = noComm;
		aceptar.setVisible(false);
		cancelar.setVisible(false);
		si.setVisible(true);
		no.setVisible(true);
		showAndCenter();
	}
	
	public void showSiNoCancelar(String msg, Command siComm, Command noComm, Command cancelarComm) {
		message.setHTML(msg);
		siCommand = siComm;
		noCommand = noComm;
		cancelarCommand = cancelarComm;
		si.setVisible(true);
		no.setVisible(true);
		cancelar.setVisible(true);
		aceptar.setVisible(false);
		showAndCenter();
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			if (aceptarCommand != null)
				aceptarCommand.execute();
		} else if (sender == cancelar) {
			if (cancelarCommand != null)
				cancelarCommand.execute();
		} else if (sender == si) {
			if (siCommand != null)
				siCommand.execute();
		} else if (sender == no) {
			if (noCommand != null)
				noCommand.execute();
		}
	}

	/** Este comando cierra la ventana sin realizar ninguna accion */
	public Command getCloseCommand() {
		return closeCommand;
	}

}
