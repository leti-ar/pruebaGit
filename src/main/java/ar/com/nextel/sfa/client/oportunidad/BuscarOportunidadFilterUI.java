package ar.com.nextel.sfa.client.oportunidad;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class BuscarOportunidadFilterUI extends Composite {

	private FlowPanel mainPanel;

	private BuscarOportunidadFilterUIData buscarOportunidadFilterEditor;
	private BuscarOportunidadResultUI buscarOportunidadResultPanel;
	private FlexTable layout;

	public BuscarOportunidadFilterUI() {
		buscarOportunidadFilterEditor = new BuscarOportunidadFilterUIData();
		init();
	}

	private void init() {
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-BuscarCuentaFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML("Búsqueda de Cuentas");
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);

		// 6 columnas
		layout = new FlexTable();
		layout.addStyleName("layout");
		layout.setWidth("98%");

		layout.getFlexCellFormatter().setColSpan(0, 3, 5);

		layout.setHTML(0, 0, Sfa.constant().numeroCliente());
		layout.setWidget(0, 1, buscarOportunidadFilterEditor.getNumeroCuentaTextBox());
		layout.setHTML(0, 2, Sfa.constant().razonSocial());
		layout.setWidget(0, 3, buscarOportunidadFilterEditor.getRazonSocialTextBox());
		layout.setHTML(1, 0, Sfa.constant().nombre());
		layout.setWidget(1, 1, buscarOportunidadFilterEditor.getNombreTextBox());
		layout.setHTML(1, 2, Sfa.constant().apellido());
		layout.setWidget(1, 3, buscarOportunidadFilterEditor.getApellidoTextBox());
		layout.setHTML(2, 0, Sfa.constant().tipoDocumento());
		layout.setWidget(2, 1, buscarOportunidadFilterEditor.getTipoDocListBox());
		layout.setHTML(2, 2, Sfa.constant().numeroDocumento());
		layout.setWidget(2, 3, buscarOportunidadFilterEditor.getNumeroDocumentoTextBox());
		layout.setHTML(2, 4, Sfa.constant().estadoOpp());
		layout.setWidget(2, 5, buscarOportunidadFilterEditor.getEstadoOPPListBox());
		layout.setHTML(3, 0, Sfa.constant().desde());
		layout.setWidget(3, 1, buscarOportunidadFilterEditor.getDesdeDate());
		layout.setHTML(3, 2, Sfa.constant().hasta());
		layout.setWidget(3, 3, buscarOportunidadFilterEditor.getHastaDate());

		mainPanel.add(layout);

		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscarOportunidadFilterEditor.getBuscarButton());
		commandPanel.add(buscarOportunidadFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscarOportunidadFilterEditor.getBuscarButton().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				buscarOportunidadResultPanel.searchOportunidades(buscarOportunidadFilterEditor
						.getOportunidadSearch());
			}
		});
	}

	public void setBuscarOportunidadResultPanel(BuscarOportunidadResultUI buscarOportunidadResultPanel) {
		this.buscarOportunidadResultPanel = buscarOportunidadResultPanel;

	}
}
