package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DatosSSUI extends Composite {

	private FlowPanel mainpanel;
	private EditarSSUIData crearSSUIData;
	private Grid detalleSS;

	public DatosSSUI(EditarSSUIData crearSSUIData) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		this.crearSSUIData = crearSSUIData;
		mainpanel.add(firstRow());
		mainpanel.add(getDomicilioPanel());
		mainpanel.add(getDetallePanel());
	}

	private Widget firstRow() {
		Grid layout = new Grid(1, 6);
		layout.addStyleName("layout");
		layout.setHTML(0, 0, Sfa.constant().nss());
		layout.setWidget(0, 1, crearSSUIData.getNss());
		layout.setHTML(0, 2, Sfa.constant().nflota());
		layout.setWidget(0, 3, crearSSUIData.getNflota());
		layout.setHTML(0, 4, Sfa.constant().origen());
		layout.setWidget(0, 5, crearSSUIData.getOrigen());
		return layout;
	}

	private Widget getDomicilioPanel() {
		TitledPanel domicilio = new TitledPanel("Domicilio");
		Button crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("crearDomicilioBWrapper");
		domicilio.add(crearDomicilioWrapper);
		Grid layoutDomicilio = new Grid(3, 4);
		layoutDomicilio.addStyleName("layout");
		layoutDomicilio.setHTML(0, 0, Sfa.constant().entrega());
		layoutDomicilio.setWidget(0, 1, crearSSUIData.getEntrega());
		layoutDomicilio.setWidget(0, 2, IconFactory.lapiz());
		layoutDomicilio.setWidget(0, 3, IconFactory.cancel());
		layoutDomicilio.setHTML(1, 0, Sfa.constant().facturacion());
		layoutDomicilio.setWidget(1, 1, crearSSUIData.getFacturacion());
		layoutDomicilio.setWidget(1, 2, IconFactory.lapiz());
		layoutDomicilio.setWidget(1, 3, IconFactory.cancel());
		layoutDomicilio.setHTML(2, 0, Sfa.constant().aclaracion());
		layoutDomicilio.setWidget(2, 1, crearSSUIData.getAclaracion());
		layoutDomicilio.getColumnFormatter().setWidth(1, "500px");
		domicilio.add(layoutDomicilio);
		return domicilio;
	}

	private Widget getDetallePanel() {
		TitledPanel detalle = new TitledPanel("Detalle");
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("resumenSSTableWrapper mlr5");
		detalleSS = new Grid(1, 11);
		String[] titles = { "Item", "Pcio Vta.", "Alias", "Plan", "Pcio Vta. Plan", "Localidad",
				"Nº Reserva", "Tipo SS", "Cant." };
		for (int i = 0; i < titles.length; i++) {
			detalleSS.setHTML(0, i + 2, titles[i]);
		}
		detalleSS.setCellPadding(0);
		detalleSS.setCellSpacing(0);
		detalleSS.addStyleName("dataTable");
		detalleSS.setWidth("98%");
		detalleSS.getRowFormatter().addStyleName(0, "header");
		wrapper.add(detalleSS);
		detalle.add(wrapper);
		return detalle;
	}
}
