package ar.com.nextel.sfa.client.veraz;

import org.eclipse.jdt.internal.compiler.ast.CaseStatement;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
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
	private VerazResponseDto verazResponseDto;
	private Label razonSocial = new Label();
	private Label apellido = new Label();
	private Label nombre = new Label();
	private VerazFilterUI verazFilterUI;
	private Label estado;

	
	public VerazResultUI() {
		super();
		addStyleName("gwt-VerazResultUI");
		verazTitledPanel = new TitledPanel(Sfa.constant().verazTitledPanel());
		verazTitledPanel.addStyleName("abmPanelVeraz");
		add(verazTitledPanel);
		setVisible(false);		
	}
	
	public void searchVeraz(PersonaDto personaDto) {
		CuentaRpcService.Util.getInstance().consultarVeraz(personaDto, new DefaultWaitCallback<VerazResponseDto>() {
			public void success(VerazResponseDto result) {
				setResultado(result);
			}
		});
	}

	public void setResultado(VerazResponseDto veraz) {
		this.verazResponseDto = veraz;
		loadTable();
	}
	
	private void loadTable() {
		if (verazResultTable != null) {
			verazResultTable.unsinkEvents(Event.getEventsSunk(verazResultTable
					.getElement()));
			verazResultTable.removeFromParent();
		}
		verazResultTable = new FlexTable();
		verazFilterUI = new VerazFilterUI();
		verazResultTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		initTable(verazResultTable);
		estado = new Label(verazResponseDto.getEstado());
		verazResultTable.setHTML(0, 1, verazResponseDto.getMensaje());

		/** Pregunta por el estado para 
		 * poner el icono que corresponda **/
		
		if ("ACEPTAR".equals(verazResponseDto.getEstado())) {
			estado.setStyleName("resultadoVerazAceptar");
		}else if ("REVISAR".equals(verazResponseDto.getEstado())) {
			estado.setStyleName("resultadoVerazRevisar");
		}
		else {
			estado.setStyleName("resultadoVerazRechazar");
		}
		
		verazResultTable.setWidget(0, 0, estado);

		verazResultTable.setHTML(1, 1, verazResponseDto.getRazonSocial());
		verazResultTable.setHTML(2, 1, verazResponseDto.getNombre());
		verazResultTable.setHTML(3, 1, verazResponseDto.getApellido());

		verazResultTable.addStyleName("abmPanelChild");
		verazTitledPanel.add(verazResultTable);
		verazFilterUI.getFooter();

		setVisible(true);
	}
	
	private void initTable(FlexTable table) {
		razonSocial = new Label (Sfa.constant().razonSocial());
		razonSocial.addStyleName("gwt-LabelVeraz");
		nombre = new Label (Sfa.constant().nombre());
		nombre.addStyleName("gwt-LabelVeraz");
		apellido = new Label (Sfa.constant().apellido());
		apellido.addStyleName("gwt-LabelVeraz");
		verazResultTable.setWidget(1, 0, razonSocial);
		verazResultTable.setWidget(2, 0, nombre);
		verazResultTable.setWidget(3, 0, apellido);
	}
}
