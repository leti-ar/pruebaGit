/**
 * 
 */
package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.EventWrapper;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * Layout del filtro de de busqueda de cuentas.
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaFilterUI extends Composite {

	private FlowPanel mainPanel;

	private BuscarCuentaFilterUIData buscadorCuentasFilterEditor;
	private FlexTable layout;
	private final BuscarCuentaController controller;

	public BuscarCuentaFilterUI(BuscarCuentaController controller) {
		buscadorCuentasFilterEditor = new BuscarCuentaFilterUIData();
		this.controller = controller;
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

		layout.getFlexCellFormatter().setColSpan(0, 1, 3);

		List<Label> listaLabels = cargaLabels(buscadorCuentasFilterEditor.getListaLabels());
		layout.setWidget(0, 0, listaLabels.get(0));
		layout.setWidget(0, 1, buscadorCuentasFilterEditor.getRazonSocialTextBox());
		layout.setWidget(0, 2, listaLabels.get(1));
		layout.setWidget(0, 3, buscadorCuentasFilterEditor.getNumeroCuentaTextBox());
		layout.setWidget(1, 0, listaLabels.get(2));
		layout.setWidget(1, 1, buscadorCuentasFilterEditor.getNumeroNextelTextBox());
		layout.setWidget(1, 2, listaLabels.get(3));
		layout.setWidget(1, 3, buscadorCuentasFilterEditor.getFlotaIdTextBox());
		layout.setWidget(1, 4, listaLabels.get(4));
		layout.setWidget(1, 5, buscadorCuentasFilterEditor.getNumeroSolicitudServicioTextBox());
		layout.setWidget(2, 0, listaLabels.get(5));
		layout.setWidget(2, 1, buscadorCuentasFilterEditor.getResponsableTextBox());
		layout.setWidget(2, 2, listaLabels.get(6));
		layout.setWidget(2, 3, buscadorCuentasFilterEditor.getGrupoDocumentoCombo());
		layout.setWidget(2, 4, listaLabels.get(7));
		layout.setWidget(2, 5, buscadorCuentasFilterEditor.getNumeroDocumentoTextBox());
		layout.setWidget(3, 0, listaLabels.get(8));
		layout.setWidget(3, 1, buscadorCuentasFilterEditor.getPredefinidasCombo());
		layout.setWidget(3, 2, listaLabels.get(9));
		layout.setWidget(3, 3, buscadorCuentasFilterEditor.getCategoriaCombo());
		layout.setWidget(3, 4, listaLabels.get(10));
		layout.setWidget(3, 5, buscadorCuentasFilterEditor.getResultadosCombo());
		EventWrapper eventWrapper = new EventWrapper() {
			public void doEnter() {
				doSearch();
			}
		};
		eventWrapper.add(layout);
		mainPanel.add(eventWrapper);
		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscadorCuentasFilterEditor.getBuscarButton());
		commandPanel.add(buscadorCuentasFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscadorCuentasFilterEditor.getBuscarButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});
	}

	private void doSearch() {
		/** BuscarButton Validation: */
		List<String> listaErrores = buscadorCuentasFilterEditor.validatePreSearch();
		if (!listaErrores.isEmpty()) {
			StringBuilder error = new StringBuilder();
			for (int i = 0; i < listaErrores.size(); i++) {
				error.append(listaErrores.get(i) + "<br />");
			}
			ErrorDialog.getInstance().show(error.toString(), false);
		} else {
			controller.searchCuentas(buscadorCuentasFilterEditor.getCuentaSearch());
		}
	}

	private List<Label> cargaLabels(List<Label> listaLabels) {
		listaLabels.add(new Label(Sfa.constant().razonSocial())); // 0
		listaLabels.add(new Label(Sfa.constant().numeroCliente()));// 1
		listaLabels.add(new Label(Sfa.constant().numeroNextel()));// 2
		listaLabels.add(new Label(Sfa.constant().flotaId()));// 3
		listaLabels.add(new Label(Sfa.constant().ss()));// 4
		listaLabels.add(new Label(Sfa.constant().responsable()));// 5
		listaLabels.add(new Label(Sfa.constant().tipoDocumento()));// 6
		listaLabels.add(new Label(Sfa.constant().numeroDocumento()));// 7
		listaLabels.add(new Label(Sfa.constant().predefinidas()));// 8
		listaLabels.add(new Label(Sfa.constant().categoria()));// 9
		listaLabels.add(new Label(Sfa.constant().resultados()));// 10
		return listaLabels;
	}

	public void cleanAndEnableFields() {
		buscadorCuentasFilterEditor.cleanAndEnableFields();
	}
}
