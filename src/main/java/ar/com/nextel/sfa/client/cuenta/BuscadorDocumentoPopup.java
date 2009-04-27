package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.constant.SfaStatic;
import ar.com.nextel.sfa.client.initializer.AgregarCuentaInitializer;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BuscadorDocumentoPopup extends NextelDialog implements ClickListener {
	
	private SimplePanel buscadorDocumentoPanel; 
	private FlexTable buscadorDocumentoTable; 
	private ListBox tipoDocumento;
	private TextBox numeroDocTextBox;
	private Label tipoDocLabel;
	private Label numeroDocLabel;
	private SimpleLink aceptar = new SimpleLink(Sfa.constant().aceptar(), "#", true);
	private SimpleLink cerrar  = new SimpleLink(Sfa.constant().cerrar(), "#", true);
	private AgregarCuentaUI agregarCuentaPage;

	public BuscadorDocumentoPopup(String title) {
		super(title);
        init();	
	
		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				UILoader.getInstance().setPage(UILoader.EDITAR_CUENTA);
				hide();
			}
		});		
		
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				hide();
			}
		});
	
	}
	
	public void init() {
		buscadorDocumentoPanel = new SimplePanel();
		buscadorDocumentoTable = new FlexTable();
		tipoDocumento    = new ListBox();
		numeroDocTextBox = new TextBox();
		tipoDocLabel   = new Label(Sfa.constant().tipoDocumento());
		numeroDocLabel = new Label(Sfa.constant().numero());
		
		CuentaRpcService.Util.getInstance().getAgregarCuentaInitializer(
			new DefaultWaitCallback<AgregarCuentaInitializer>() {
				public void success(AgregarCuentaInitializer result) {
					tipoDocumento.addAllItems(result.getTiposDocumento());
				}
			});
		
		buscadorDocumentoTable.setWidget(0, 0, tipoDocLabel);
		buscadorDocumentoTable.setWidget(0, 1, tipoDocumento);
		buscadorDocumentoTable.setWidget(1, 0, numeroDocLabel);
		buscadorDocumentoTable.setWidget(1, 1, numeroDocTextBox);
		buscadorDocumentoPanel.add(buscadorDocumentoTable);
		addFooter(aceptar);
		addFooter(cerrar);
		add(buscadorDocumentoPanel);
		
	}
	public void onClick(Widget sender) {
		// TODO Auto-generated method stub
	}
	
}
