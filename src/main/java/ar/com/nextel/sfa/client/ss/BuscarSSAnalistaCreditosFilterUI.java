package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.EventWrapper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Esta clase "dibuja" el filtro para la busqueda de SS del Analista de creditos logueado y lo que
 * pertenezcan a su misma sucursal.
 * @author fernaluc
 *
 */


public class BuscarSSAnalistaCreditosFilterUI extends Composite{

	private FlowPanel mainPanel;
	private BuscarSSAnalistaCreditosFilterUIData buscadorSSFilterEditor;
	private BuscarSSCerradasResultUI buscarSSCerradasResultPanel;
	private FlexTable layout;
	
	public BuscarSSAnalistaCreditosFilterUI () {
		buscadorSSFilterEditor = new BuscarSSAnalistaCreditosFilterUIData();
		init();
	}
	
	private void init() {
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-BuscarCuentaFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML(Sfa.constant().busquedaSS());
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);

		layout = new FlexTable();
		layout.addStyleName("layout");
		layout.setWidth("96%");
		
		layout.getFlexCellFormatter().setColSpan(0, 1, 3);
		
		layout.setHTML(0, 0, Sfa.constant().ss());
		layout.setWidget(0, 1, buscadorSSFilterEditor.getNroSS());
		layout.setHTML(0, 2, Sfa.constant().numeroCuenta()+":");
		layout.setWidget(0, 3, buscadorSSFilterEditor.getNroCliente());	
		
		layout.setHTML(1, 0, Sfa.constant().creacionDesde());
		layout.setWidget(1, 1, buscadorSSFilterEditor.getCreacionDesde());
		layout.setHTML(1, 4, Sfa.constant().hasta());
		layout.setWidget(1, 5, buscadorSSFilterEditor.getCreacionHasta());
		
		layout.setHTML(2, 0, Sfa.constant().cierreDesde());
		layout.setWidget(2, 1, buscadorSSFilterEditor.getCierreDesde());
		layout.setHTML(2, 4, Sfa.constant().hasta());
		layout.setWidget(2, 5, buscadorSSFilterEditor.getCierreHasta());
		
		layout.setHTML(3, 0, Sfa.constant().estado());
		layout.setWidget(3, 1, buscadorSSFilterEditor.getEstadoCombo());		
		layout.setHTML(3, 4, Sfa.constant().tipoYNumeroDoc());
		layout.setWidget(3, 5, new FullDocumentWidget(buscadorSSFilterEditor.getTipoDocumento(),buscadorSSFilterEditor.getNroDoc()));
		
		layout.setHTML(4, 0, Sfa.constant().resultados());
		layout.setWidget(4, 1, buscadorSSFilterEditor.getResultadosCombo());			
		
		
		EventWrapper eventWrapper = new EventWrapper() {
			public void doEnter() {			
				buscarSSCerradasResultPanel.searchSolicitudesServicio(buscadorSSFilterEditor
						.getSSCerradaSearch());
			}
		};
		eventWrapper.add(layout);
		mainPanel.add(eventWrapper);

		FlowPanel commandPanel = new FlowPanel();
		commandPanel.add(buscadorSSFilterEditor.getBuscarButton());
		commandPanel.add(buscadorSSFilterEditor.getLimpiarButton());
		commandPanel.addStyleName("mb5 mt5");

		mainPanel.add(commandPanel);

		buscadorSSFilterEditor.getBuscarButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buscarSSCerradasResultPanel.searchSolicitudesServicio(buscadorSSFilterEditor
						.getSSCerradaSearch());
			}
		});
	}

	public void setBuscarCuentaResultPanel(BuscarSSCerradasResultUI buscarCuentaResultPanel) {
		this.buscarSSCerradasResultPanel = buscarCuentaResultPanel;
	}

	public BuscarSSAnalistaCreditosFilterUIData getbuscadorSSFilterEditor() {
		return this.buscadorSSFilterEditor;
	}
		
}