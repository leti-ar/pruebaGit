package ar.com.nextel.sfa.client.ss;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.AvailableNumberDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.NextelTable;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ReservarNumeroDialog extends NextelDialog implements ClickListener {

	private SimpleLink aceptar = new SimpleLink("aceptar");
	private SimpleLink cerrar = new SimpleLink("cerrrar");
	private NextelTable numerosDiponiblesTable;
	private List<AvailableNumberDto> numerosDiponibles;
	private Command commandAceptar;

	public ReservarNumeroDialog() {
		super("Números sugeridos", false, true);
		HTML mensaje = new HTML(Sfa.constant().ERR_NUMERO_NO_DISPONIBLE());
		mensaje.setWidth("300px");
		mensaje.addStyleName("m10");
		add(mensaje);

		numerosDiponiblesTable = new NextelTable(0);
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("reservaTelefonoWrapper");
		wrapper.add(numerosDiponiblesTable);
		numerosDiponiblesTable.setWidth("100px");
		add(wrapper);

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

	public void show(List<AvailableNumberDto> numeros, Command aceptar) {
		numerosDiponibles = numeros;
		commandAceptar = aceptar;
		while (numerosDiponiblesTable.getRowCount() > numeros.size()) {
			numerosDiponiblesTable.removeRow(0);
		}
		for (int i = 0; i < numerosDiponibles.size(); i++) {
			numerosDiponiblesTable.setHTML(i, 0, "" + numerosDiponibles.get(i).getAvailableNumber());
		}
		showAndCenter();
	}

	public long getSelectedNumber() {
		return numerosDiponibles.get(numerosDiponiblesTable.getRowSelected()).getAvailableNumber();
	}
}
