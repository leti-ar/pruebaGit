package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DescuentoConfirmacionDialog extends NextelDialog implements ClickListener {

	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private FlowPanel confirmacion = new FlowPanel();
	private Command confirmarCommand;

	public DescuentoConfirmacionDialog(String title, String itemDescripcion, String precioLista, String porcentaje, String precioVenta) {
		super(title, false, true);
		addStyleName("gwt-DescuentoDialog");
		aceptar = new SimpleLink("ACEPTAR");
		cancelar = new SimpleLink("CANCELAR");
		FlexTable tablaUno = new FlexTable();
		tablaUno.addStyleName("tableDesc");
		tablaUno.setWidget(0, 0, new InlineLabel("Está a punto de aplicar un descuento al item " + itemDescripcion));
		confirmacion.add(tablaUno);
		
		FlexTable tablaDos = new FlexTable();
		tablaDos.addStyleName("tableDesc");
		tablaDos.setWidget(0, 0, new InlineLabel("Precio original:"));
		tablaDos.setWidget(0, 1, new Label(precioLista));
		tablaDos.setWidget(1, 0, new InlineLabel("Porcentaje de descuento:"));
		tablaDos.setWidget(1, 1, new Label(porcentaje));
		tablaDos.setWidget(2, 0, new InlineLabel("Precio con descuento:"));
		tablaDos.setWidget(2, 1, new Label(precioVenta));
		confirmacion.add(tablaDos);
		
		add(confirmacion);
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
		aceptar.addClickListener(this);
		cancelar.addClickListener(this);
		showAndCenter();
	}
	
	public void setAceptarCommand(Command aceptarCommand) {
		this.confirmarCommand = aceptarCommand;
	}

	public void onClick(Widget sender) {
		if (sender == aceptar) {
			confirmarCommand.execute();
			hide();
		} else if(sender == cancelar) {
			hide();
		}
	}

}
