package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class VerazFilterUI extends Composite {
	
	private FlowPanel mainPanel;
	private FlexTable verazTable;
	private VerazUIData verazEditor; 
	private FormButtonsBar footerBar;
	
	
	public VerazFilterUI() {	
		verazEditor = new VerazUIData();
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		init();		
		mainPanel.add(createVerazPanel());
	}
	
	private void init() {
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("gwt-BuscarCuentaFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML("Validar Veraz");
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);
	
		verazTable = new FlexTable(); 
		verazTable.addStyleName("layout");
		verazTable.setText(0, 0, Sfa.constant().tipoDocumento());
		verazTable.setWidget(0, 1, verazEditor.getTipoDocListBox());
		verazTable.setText(0, 2, Sfa.constant().numeroDocumento());
		verazTable.setWidget(0, 3, verazEditor.getNumeroDocTextBox());
		verazTable.setText(0, 4, Sfa.constant().sexo());
		verazTable.setWidget(0, 5, verazEditor.getSexoListBox());	
		
		footerBar.setHeight("50");
		footerBar.addLink(verazEditor.getValidarVerazLink());
		footerBar.addLink(verazEditor.getAgregarProspectLink());
			
		mainPanel.add(verazTable);
		mainPanel.add(footerBar);
		
	}
	
	private Widget createVerazPanel() {
		verazTable = new FlexTable();
		verazTable.addStyleName("gwt-Label");
		return verazTable;
	}
	
}
