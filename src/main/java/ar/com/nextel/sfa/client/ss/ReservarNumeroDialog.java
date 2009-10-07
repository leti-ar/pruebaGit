package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.AvailableNumberDto;
import ar.com.nextel.sfa.client.util.ModalUtils;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ReservarNumeroDialog extends NextelDialog implements ClickListener {

	private SimpleLink aceptar = new SimpleLink("aceptar");
	private SimpleLink cerrar = new SimpleLink("cerrrar");
	private NextelTable numerosDiponiblesTable;
	private List<AvailableNumberDto> numerosDiponibles;
	private SimplePanel numerosDiponiblesWrapper;
	private Command commandAceptar;
	private HTML mensaje;
	private PopupPanel modalPopup;

	public ReservarNumeroDialog() {
		super("Números sugeridos", false, true);
		mensaje = new HTML(Sfa.constant().ERR_NUMERO_NO_DISPONIBLE());
		mensaje.setWidth("300px");
		mensaje.addStyleName("m10");
		add(mensaje);

		numerosDiponiblesTable = new NextelTable(0);
		numerosDiponiblesWrapper = new SimplePanel();
		numerosDiponiblesWrapper.addStyleName("reservaTelefonoWrapper");
		numerosDiponiblesWrapper.add(numerosDiponiblesTable);
		numerosDiponiblesTable.setWidth("100px");
		add(numerosDiponiblesWrapper);

		setFormButtonsVisible(true);
		aceptar.addClickListener(this);
		cerrar.addClickListener(this);
		addFormButtons(aceptar);
		addFormButtons(cerrar);
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			if (numerosDiponiblesTable.getRowSelected() >= 0) {
				commandAceptar.execute();
				hide();
			} else {
				ErrorDialog.getInstance().show(Sfa.constant().ERR_REQUIRED_SELECTION(), false);
			}
		} else if (sender == cerrar) {
			hide();
		}
	}

	public void show(List<AvailableNumberDto> numeros, Command commandAceptar) {
		numerosDiponibles = numeros;
		this.commandAceptar = commandAceptar;
		numerosDiponiblesTable.clearContent();
		int i = 0;
		for (; i < numerosDiponibles.size(); i++) {
			numerosDiponiblesTable.setHTML(i, 0, "" + numerosDiponibles.get(i).getAvailableNumber());
		}
		if (i == 0) {
			mensaje.setHTML(Sfa.constant().ERR_NINGUN_NUMERO_DISPONIBLE());
			aceptar.setVisible(false);
			numerosDiponiblesWrapper.setVisible(false);
		} else {
			mensaje.setHTML(Sfa.constant().ERR_NUMERO_NO_DISPONIBLE());
			aceptar.setVisible(true);
			numerosDiponiblesWrapper.setVisible(true);
		}
		showAndCenter();
	}

	public long getSelectedNumber() {
		return numerosDiponibles.get(numerosDiponiblesTable.getRowSelected()).getAvailableNumber();
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
