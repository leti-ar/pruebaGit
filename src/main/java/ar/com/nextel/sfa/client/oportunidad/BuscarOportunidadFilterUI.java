package ar.com.nextel.sfa.client.oportunidad;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class BuscarOportunidadFilterUI extends Composite {

	private FlowPanel mainPanel;

	private BuscarOportunidadFilterUIData buscarOportunidadFilterEditor;
	private BuscarOportunidadResultUI buscarOportunidadResultPanel;
	private FlexTable layout;
	private List<String> errorList = new ArrayList();

	public BuscarOportunidadFilterUI() {
		buscarOportunidadFilterEditor = new BuscarOportunidadFilterUIData();
		init();
	}

	private void init() {
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-BuscarCuentaFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML("Buscar Oportunidad");
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);

		// 6 columnas
		layout = new FlexTable();
		layout.addStyleName("layout");
		layout.setWidth("98%");

		layout.getFlexCellFormatter().setColSpan(0, 3, 5);

		List<Label> listaLabels = loadLabels(buscarOportunidadFilterEditor.getListaLabels());
		layout.setWidget(0, 0, listaLabels.get(0));
		layout.setWidget(0, 1, buscarOportunidadFilterEditor.getNumeroCuentaTextBox());
		layout.setWidget(0, 2, listaLabels.get(1));
		layout.setWidget(0, 3, buscarOportunidadFilterEditor.getRazonSocialTextBox());
		layout.setWidget(1, 0, listaLabels.get(2));
		layout.setWidget(1, 1, buscarOportunidadFilterEditor.getNombreTextBox());
		layout.setWidget(1, 2, listaLabels.get(3));
		layout.setWidget(1, 3, buscarOportunidadFilterEditor.getApellidoTextBox());
		layout.setWidget(2, 0, listaLabels.get(4));
		layout.setWidget(2, 1, buscarOportunidadFilterEditor.getTipoDocListBox());
		layout.setWidget(2, 2, listaLabels.get(5));
		layout.setWidget(2, 3, buscarOportunidadFilterEditor.getNumeroDocumentoTextBox());
		layout.setWidget(2, 4, listaLabels.get(6));
		layout.setWidget(2, 5, buscarOportunidadFilterEditor.getEstadoOPPListBox());
		layout.setWidget(3, 0, listaLabels.get(7));
		layout.setWidget(3, 1, buscarOportunidadFilterEditor.getDesdeDate());
		layout.setWidget(3, 2, listaLabels.get(8));
		layout.setWidget(3, 3, buscarOportunidadFilterEditor.getHastaDate());

		mainPanel.add(layout);

		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscarOportunidadFilterEditor.getBuscarButton());
		commandPanel.add(buscarOportunidadFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscarOportunidadFilterEditor.getBuscarButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				errorList.clear();
				/** Valida los datos introducidos por el usuario **/
				errorList.addAll(buscarOportunidadFilterEditor.validarCriterioBusqueda());

				/** Muestra los mensajes de error **/
				if (!errorList.isEmpty()) {
					for (int i = 0; i < errorList.size(); i++) {
						String error = errorList.get(i);
						ErrorDialog.getInstance().show(error);
					}
				} else
					buscarOportunidadResultPanel.searchOportunidades(buscarOportunidadFilterEditor
							.getOportunidadSearch());
			}
		});
	}

	private List<Label> loadLabels(List<Label> listaLabels) {
		listaLabels.add(new Label(Sfa.constant().numeroCliente())); // 0
		listaLabels.add(new Label(Sfa.constant().razonSocialDosPuntos()));// 1
		listaLabels.add(new Label(Sfa.constant().nombreDosPuntos()));// 2
		listaLabels.add(new Label(Sfa.constant().apellidoDosPuntos()));// 3
		listaLabels.add(new Label(Sfa.constant().tipoDocDosPuntos()));// 4
		listaLabels.add(new Label(Sfa.constant().numeroDosPuntos()));// 5
		listaLabels.add(new Label(Sfa.constant().estadoOpp()));// 6
		listaLabels.add(new Label(Sfa.constant().desde()));// 7
		listaLabels.add(new Label(Sfa.constant().hasta()));// 8
		return listaLabels;
	}

	public void setBuscarOportunidadResultPanel(BuscarOportunidadResultUI buscarOportunidadResultPanel) {
		this.buscarOportunidadResultPanel = buscarOportunidadResultPanel;

	}

}
