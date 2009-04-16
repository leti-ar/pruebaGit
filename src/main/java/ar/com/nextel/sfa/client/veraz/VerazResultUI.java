package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.VerazSearchDto;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author mrial
 *
 */

public class VerazResultUI extends FlowPanel {

	private TitledPanel verazTitledPanel;
	private FlexTable verazResultTable;
	private MockVeraz mockVeraz;
	private Label razonSocial = new Label();
	private Label apellido = new Label();
	private Label nombre = new Label();
	
	public VerazResultUI() {
		super();
		addStyleName("gwt-VerazResultUI");
		verazTitledPanel = new TitledPanel(Sfa.constant().verazTitledPanel());
		verazTitledPanel.addStyleName("abmPanel");
		//verazTitledPanel.addStyleName("tituloPanel");
		add(verazTitledPanel);
		setVisible(false);
		
	}
	
	public void searchVeraz(VerazSearchDto verazSearchDto) {
		CuentaRpcService.Util.getInstance().searchVeraz(verazSearchDto, new DefaultWaitCallback<MockVeraz>() {
			public void success(MockVeraz result) {
				setResultado(result);
			}
		});
	}
	
	public void setResultado(MockVeraz veraz) {
		this.mockVeraz = veraz;
		loadTable();
	}
	
	private void loadTable() {
		verazResultTable = new FlexTable();
		verazResultTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		initTable(verazResultTable);

//		verazResultTable.setText(0, 1, "Melina Rial");
//		verazResultTable.setText(1, 1, "Melina");
//		verazResultTable.setText(2, 1, "Rial");
		verazResultTable.setHTML(0, 1, mockVeraz.getRazonSocial());
		verazResultTable.setHTML(1, 1, mockVeraz.getNombre());
		verazResultTable.setHTML(2, 1, mockVeraz.getApellido());
	
		verazResultTable.addStyleName("abmPanelChild");
		verazTitledPanel.add(verazResultTable);
	
		setVisible(true);
	}
	
	private void initTable(FlexTable table) {
		razonSocial = new Label (Sfa.constant().razonSocial());
		razonSocial.addStyleName("gwt-LabelVeraz");
		nombre = new Label (Sfa.constant().nombre());
		nombre.addStyleName("gwt-LabelVeraz");
		apellido = new Label (Sfa.constant().apellido());
		apellido.addStyleName("gwt-LabelVeraz");
		verazResultTable.setWidget(0, 0, razonSocial);
		verazResultTable.setWidget(1, 0, nombre);
		verazResultTable.setWidget(2, 0, apellido);
	}
}
