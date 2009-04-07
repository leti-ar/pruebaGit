/**
 * 
 */
package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Layout del filtro de de busqueda de cuentas.
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaFilterUI extends Composite {

	private FlowPanel mainPanel;

	private BuscarCuentaFilterUIData buscadorCuentasFilterEditor;
	private BuscarCuentaResultUI buscarCuentaResultPanel;
	private FlexTable layout;

	public BuscarCuentaFilterUI() {
		buscadorCuentasFilterEditor = new BuscarCuentaFilterUIData();
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

		layout.setHTML(0, 0, Sfa.constant().razonSocial());
		layout.setWidget(0, 1, buscadorCuentasFilterEditor.getRazonSocialTextBox());
		layout.setHTML(0, 2, Sfa.constant().numeroCliente());
		layout.setWidget(0, 3, buscadorCuentasFilterEditor.getNumeroCuentaTextBox());
		layout.setHTML(1, 0, Sfa.constant().numeroNextel());
		layout.setWidget(1, 1, buscadorCuentasFilterEditor.getNumeroNextelTextBox());
		layout.setHTML(1, 2, Sfa.constant().flotaId());
		layout.setWidget(1, 3, buscadorCuentasFilterEditor.getFlotaIdTextBox());
		layout.setHTML(1, 4, Sfa.constant().ss());
		layout.setWidget(1, 5, buscadorCuentasFilterEditor.getNumeroSolicitudServicioTextBox());
		layout.setHTML(2, 0, Sfa.constant().responsable());
		layout.setWidget(2, 1, buscadorCuentasFilterEditor.getResponsableTextBox());
		layout.setHTML(2, 2, Sfa.constant().tipoDocumento());
		layout.setWidget(2, 3, buscadorCuentasFilterEditor.getTipoDocumentoCombo());
		layout.setHTML(2, 4, Sfa.constant().numeroDocumento());
		layout.setWidget(2, 5, buscadorCuentasFilterEditor.getNumeroDocumentoTextBox());
		layout.setHTML(3, 0, Sfa.constant().predefinidas());
		layout.setWidget(3, 1, buscadorCuentasFilterEditor.getPredefinidasCombo());
		layout.setHTML(3, 2, Sfa.constant().categoria());
		layout.setWidget(3, 3, buscadorCuentasFilterEditor.getCategoriaCombo());
		layout.setHTML(3, 4, Sfa.constant().resultados());
		layout.setWidget(3, 5, buscadorCuentasFilterEditor.getResultadosCombo());

		mainPanel.add(layout);

		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscadorCuentasFilterEditor.getBuscarButton());
		commandPanel.add(buscadorCuentasFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscadorCuentasFilterEditor.getBuscarButton().addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				buscarCuentaResultPanel.searchCuentas(buscadorCuentasFilterEditor.getCuentaSearch());
			}
		});

	}

	public void setBuscarCuentaResultPanel(BuscarCuentaResultUI buscarCuentaResultPanel) {
		this.buscarCuentaResultPanel = buscarCuentaResultPanel;
	}

}
