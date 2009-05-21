package ar.com.nextel.sfa.client.veraz;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrial
 *
 */

public class VerazFilterUI extends Composite {
	
	private FlowPanel mainPanel;
	private FlexTable verazTable;
	private VerazUIData verazEditor; 
	private VerazResultUI verazResultUI;
	private FormButtonsBar footerBar;
	private List<String> errorList = new ArrayList();
	
	public VerazFilterUI() {	
		verazEditor = new VerazUIData();
		init();		
	}
	
	private void init() {		
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("centralPanel");
		mainPanel.addStyleName("gwt-VerazFilterForm");
		initWidget(mainPanel);

		HTML titulo = new HTML("Validar Veraz");
		titulo.addStyleName("titulo");
		mainPanel.add(titulo);
	
		verazTable = new FlexTable(); 
		verazTable.addStyleName("layout");
		verazTable.setWidth("98%");
		verazTable.getFlexCellFormatter().setColSpan(0, 1, 3);

		verazTable.setText(0, 0, Sfa.constant().tipoDocumento());
		verazTable.setWidget(0, 1, verazEditor.getTipoDocListBox());
		verazTable.setText(0, 2, Sfa.constant().numeroDocumento());
		verazTable.setWidget(0, 3, verazEditor.getNumeroDocTextBox());
		verazTable.setText(0, 4, Sfa.constant().sexo());
		verazTable.setWidget(0, 5, verazEditor.getSexoListBox());	
				
		mainPanel.add(verazTable);
		
		verazEditor.getValidarVerazLink().addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {

				/**Validar la completitud y formato de los campos**/
				errorList.addAll(verazEditor.validarFormatoYCompletitud());

				/**Mostrar los mensajes de error**/
				if (!errorList.isEmpty()){
					for (int i = 0; i < errorList.size(); i++) {
						String error = errorList.get(i);
						ErrorDialog.getInstance().show(error);
					}
				} else
					verazResultUI.searchVeraz(verazEditor.getVerazSearch(verazEditor.getNumeroDocTextBox(), verazEditor.getTipoDocListBox(), verazEditor.getSexoListBox()));
			}
		});
	}
	
	public void setVerazResultUI(VerazResultUI verazResultUI) {
		this.verazResultUI = verazResultUI;
	}

	
	public FormButtonsBar getFooter() {
		footerBar = new FormButtonsBar();
		footerBar.addLink(verazEditor.getValidarVerazLink());
		footerBar.addLink(verazEditor.getAgregarProspectLink());
		return footerBar;
	}

	
}
