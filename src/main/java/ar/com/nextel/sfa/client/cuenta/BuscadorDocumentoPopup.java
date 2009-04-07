package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BuscadorDocumentoPopup extends NextelDialog implements ClickListener {
	
	private SimplePanel buscadorDocumentoPanel; 
	private FlexTable buscadorDocumentoTable; 
	private TextBox tipoDocTextBox;
	private TextBox numeroDocTextBox;
	private Label tipoDocLabel;
	private Label numeroDocLabel;
	private SimpleLink aceptar = new SimpleLink("ACEPTAR", "#", true);
	private SimpleLink cerrar = new SimpleLink("CERRAR", "#", true);
	private AgregarCuentaUI agregarCuentaPage;

	public BuscadorDocumentoPopup(String title) {
		super(title);
		SimplePanel buscadorDocumentoPanel = new SimplePanel();
		FlexTable buscadorDocumentoTable = new FlexTable();
		TextBox tipoDocTextBox = new TextBox();
		TextBox numeroDocTextBox = new TextBox();
		Label tipoDocLabel = new Label("Tipo Doc:");
		Label numeroDocLabel = new Label("Número:");
		
		buscadorDocumentoTable.setWidget(0, 0, tipoDocLabel);
		buscadorDocumentoTable.setWidget(0, 1, tipoDocTextBox);
		buscadorDocumentoTable.setWidget(1, 0, numeroDocLabel);
		buscadorDocumentoTable.setWidget(1, 1, numeroDocTextBox);
		buscadorDocumentoPanel.add(buscadorDocumentoTable);
		addFooter(aceptar);
		addFooter(cerrar);
		add(buscadorDocumentoPanel);	
	
		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				agregarCuentaPage.load();
				hide();
			}
		});		
		
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				hide();
			}
		});
	
	}
	
	
	public void onClick(Widget sender) {
		// TODO Auto-generated method stub
		
	}
	
}
