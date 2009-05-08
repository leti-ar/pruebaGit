package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.TitledPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class VariosSSUI extends Composite {

	private FlowPanel mainpanel;
	//private EditarSSUIController controller;
	private EditarSSUIData editarSSUIData;

	public VariosSSUI(EditarSSUIController controller) {
		mainpanel = new FlowPanel();
		initWidget(mainpanel);
		//this.controller = controller;
		this.editarSSUIData = controller.getEditarSSUIData();

		mainpanel.add(getCreditoFidelizacion());
		mainpanel.add(getPataconex());
		mainpanel.add(getFirmas());
		mainpanel.add(getAnticipo());
		mainpanel.add(getResumen());
		mainpanel.add(getScoring());

	}

	private Widget getPataconex() {
		TitledPanel pataconexPanel = new TitledPanel(Sfa.constant().pataconexTitle());
		InlineLabel pataconexLabel = new InlineLabel(Sfa.constant().pataconex());
		pataconexLabel.addStyleName("mlr5");
		pataconexPanel.add(pataconexLabel);
		pataconexPanel.add(editarSSUIData.getPataconex());
		return pataconexPanel;
	}

	private Widget getCreditoFidelizacion() {
		TitledPanel creditoPanel = new TitledPanel(Sfa.constant().creditoFidelizacionTitle());
		Grid layout = new Grid(1, 6);
		layout.addStyleName("layout");
		layout.setHTML(0, 0, Sfa.constant().disponible());
		layout.setWidget(0, 1, editarSSUIData.getCredFidelDisponibleText());
		layout.setHTML(0, 2, Sfa.constant().vencimiento());
		layout.setWidget(0, 3, editarSSUIData.getCredFidelVencimientoText());
		layout.setHTML(0, 4, Sfa.constant().autilizar());
		layout.setWidget(0, 5, editarSSUIData.getCredFidelizacion());
		layout.getColumnFormatter().setWidth(1, "120px");
		layout.getColumnFormatter().setWidth(3, "120px");
		creditoPanel.add(layout);
		return creditoPanel;
	}

	private Widget getFirmas() {
		TitledPanel firmasPanel = new TitledPanel(Sfa.constant().firmaTitle());
		InlineLabel pataconexLabel = new InlineLabel(Sfa.constant().firmarSS());
		pataconexLabel.addStyleName("mlr5");
		firmasPanel.add(pataconexLabel);
		firmasPanel.add(editarSSUIData.getFirmarss());
		return firmasPanel;
	}

	private Widget getAnticipo() {
		FlowPanel anticipoPanel = new FlowPanel();
		Grid anticipoTable = new Grid(2, 2);
		anticipoTable.addStyleName("layout mlr5");
		anticipoTable.setHTML(0, 0, Sfa.constant().anticipo());
		anticipoTable.setWidget(0, 1, editarSSUIData.getAnticipo());
		anticipoTable.setHTML(1, 0, Sfa.constant().observaciones());
		anticipoTable.setWidget(1, 1, editarSSUIData.getObservaciones());
		anticipoPanel.add(anticipoTable);
		return anticipoPanel;
	}

	private Widget getResumen() {
		TitledPanel resumenPanel = new TitledPanel(Sfa.constant().resumentSSTitle());
		resumenPanel.add(getResumenTable());
		Label totalesLabel = new Label(Sfa.constant().totales());
		resumenPanel.add(totalesLabel);
		Grid totales = new Grid(2, 6);
		totales.addStyleName("layout");
		totales.setHTML(0, 0, Sfa.constant().precioLista());
		totales.setWidget(0, 1, editarSSUIData.getPrecioListaText());
		totales.setHTML(0, 2, Sfa.constant().desvios());
		totales.setWidget(0, 3, editarSSUIData.getDesvioText());
		totales.setHTML(0, 4, Sfa.constant().creditoFidel());
		totales.setWidget(0, 5, editarSSUIData.getCredFidelText());
		totales.setHTML(1, 0, Sfa.constant().pataconex());
		totales.setWidget(1, 1, editarSSUIData.getPataconexText());
		totales.setHTML(1, 2, Sfa.constant().precioVenta());
		totales.setWidget(1, 3, editarSSUIData.getPrecioVentaText());

		totales.getColumnFormatter().setWidth(1, "120px");
		totales.getColumnFormatter().setWidth(3, "120px");

		resumenPanel.add(totales);
		return resumenPanel;
	}

	private Widget getResumenTable() {
		SimplePanel wrapper = new SimplePanel();
		wrapper.addStyleName("detalleSSTableWrapper mlr5");
		Grid resumenSS = new Grid(1, 8);
		String[] titles = { "Item", "Cant.", "Forma Contrat.", "Precio Item", "Plan", "Precio Plan",
				"Precio Gtía.", "Serv. Adic." };
		for (int i = 0; i < titles.length; i++) {
			resumenSS.setHTML(0, i, titles[i]);
		}
		resumenSS.setCellPadding(0);
		resumenSS.setCellSpacing(0);
		resumenSS.addStyleName("dataTable");
		resumenSS.setWidth("98%");
		resumenSS.getRowFormatter().addStyleName(0, "header");
		wrapper.add(resumenSS);
		return wrapper;
	}

	private Widget getScoring() {
		TitledPanel scoringPanel = new TitledPanel(Sfa.constant().firmaTitle());
		HTML consultarVeraz = new HTML("Consultar Veraz");
		scoringPanel.add(consultarVeraz);
		return scoringPanel;
	}
}
