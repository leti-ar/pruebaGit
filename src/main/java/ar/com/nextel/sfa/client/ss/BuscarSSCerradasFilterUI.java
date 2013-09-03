/**
 * 
 */
package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;
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

/**
 * Layout del filtro de de busqueda de SS cerradas.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradasFilterUI extends Composite {

	private FlowPanel mainPanel;

	private BuscarSSCerradasFilterUIData buscadorSSCerradasFilterEditor;
	private BuscarSSCerradasResultUI buscarSSCerradasResultPanel;
	private FlexTable layout;
	private List<String> errorList = new ArrayList();

	public BuscarSSCerradasFilterUI() {
		buscadorSSCerradasFilterEditor = new BuscarSSCerradasFilterUIData();
		init();
	}

	private void init() {
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-BuscarCuentaFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML("Búsqueda de SS Cerradas");
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);

		layout = new FlexTable();
		layout.addStyleName("layout");
		layout.setWidth("98%");

		layout.getFlexCellFormatter().setColSpan(0, 1, 3);

		layout.setHTML(0, 0, Sfa.constant().numeroCliente());
		layout.setWidget(0, 1, buscadorSSCerradasFilterEditor.getNroCliente());
		layout.setHTML(0, 2, Sfa.constant().ss());
		layout.setWidget(0, 3, buscadorSSCerradasFilterEditor.getNroSS());
		layout.setHTML(1, 0, Sfa.constant().cierreDesde());
		layout.setWidget(1, 1, buscadorSSCerradasFilterEditor.getDesde());
		layout.setHTML(1, 4, Sfa.constant().hasta());
		layout.setWidget(1, 5, buscadorSSCerradasFilterEditor.getHasta());
		layout.setHTML(2, 0, Sfa.constant().estado());
		layout.setWidget(2, 1, buscadorSSCerradasFilterEditor.getEstadoCombo());
		layout.setHTML(2, 4, Sfa.constant().firmas());
		layout.setWidget(2, 5, buscadorSSCerradasFilterEditor.getFirmasCombo());
		layout.setHTML(3, 0, Sfa.constant().pataconex());
		layout.setWidget(3, 1, buscadorSSCerradasFilterEditor.getPataconesCombo());
		layout.setHTML(3, 4, Sfa.constant().resultados());
		layout.setWidget(3, 5, buscadorSSCerradasFilterEditor.getResultadosCombo());
		EventWrapper eventWrapper = new EventWrapper() {
			public void doEnter() {
				buscarSSCerradasResultPanel.searchSolicitudesServicio(buscadorSSCerradasFilterEditor
						.getSSCerradaSearch());
			}
		};
		eventWrapper.add(layout);
		mainPanel.add(eventWrapper);
		
		
//		mainPanel.add(layout);
		

		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscadorSSCerradasFilterEditor.getBuscarButton());
		commandPanel.add(buscadorSSCerradasFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscadorSSCerradasFilterEditor.getBuscarButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				errorList.clear();
				/** Valida los datos introducidos por el usuario **/
				errorList.addAll(buscadorSSCerradasFilterEditor.validarCriterioBusquedaSSCerradas());

				/** Muestra los mensajes de error **/
				if (!errorList.isEmpty()) {
					for (int i = 0; i < errorList.size(); i++) {
						String error = errorList.get(i);
						ErrorDialog.getInstance().show(error);
					}
				} else
					buscarSSCerradasResultPanel.searchSolicitudesServicio(buscadorSSCerradasFilterEditor
							.getSSCerradaSearch());
			}
		});
		
			
//		
//		.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				buscarSSCerradasResultPanel.searchSolicitudesServicio(buscadorSSCerradasFilterEditor
//						.getSSCerradaSearch());
//			}
//		});
	}

	

	
	
	
	public void setBuscarCuentaResultPanel(BuscarSSCerradasResultUI buscarCuentaResultPanel) {
		this.buscarSSCerradasResultPanel = buscarCuentaResultPanel;
	}

	public BuscarSSCerradasFilterUIData getBuscadorSSCerradasFilterEditor() {
		return this.buscadorSSCerradasFilterEditor;
	}

}
