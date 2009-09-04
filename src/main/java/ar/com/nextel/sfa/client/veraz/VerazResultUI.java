package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

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
    private String explicacionTrunc;
	
	private static int SCORE_DNI_INEXISTENTE = 3;

	
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
			verazResultTable.unsinkEvents(Event.getEventsSunk(verazResultTable.getElement()));
			verazResultTable.removeFromParent();
		}
		verazResultTable = new FlexTable();
		verazResultTable.setWidth("100%");
		verazFilterUI = new VerazFilterUI();
		verazResultTable.getFlexCellFormatter().setColSpan(1, 1, 4);
		initTable(verazResultTable);
		estado = new Label(verazResponseDto.getEstado());		

		/** Pregunta por el estado para 
		 * poner el icono y mensaje que corresponda **/		
		
		if(verazResponseDto.getEstado() == null) {
			//analyzeExplicacion();
		} else {
			if("RECHAZAR".equals(verazResponseDto.getEstado()))
				rechazar();
			else
				if("REVISAR".equals(verazResponseDto.getEstado())) {
					revisar();
				} else
					if("ACEPTAR".equals(verazResponseDto.getEstado()))
						aceptar();
					else
						if("REVISAR POR CONYUGE".equals(verazResponseDto.getEstado()))
							verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido REVISAR POR CÓNYUGE. Puede continuar con la operaci\363n.");
						else
							verazResultTable.setHTML(0, 1, "La Respuesta de Veraz ha sido REVISAR. Puede continuar con la operaci�n.");
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
	
	private void rechazar() {
		estado.setStyleName("resultadoVerazRechazar");
		verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido: RECHAZAR.");
//		verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido: RECHAZAR. " + explicacionTrunc);
	}
	
	private void aceptar() {
		estado.setStyleName("resultadoVerazAceptar");
		verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido: ACEPTAR. Puede continuar con la operaci\363n.");
	}
	
	private void revisar() {
		estado.setStyleName("resultadoVerazRevisar");
		if(verazResponseDto.getScoreDni() != SCORE_DNI_INEXISTENTE) 
			verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido: REVISAR.");
//			verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido: REVISAR. " + explicacionTrunc);
		else 
			verazResultTable.setHTML(0, 1, "La respuesta de Veraz ha sido A REVISAR. Documento inexistente");
	}	
	
	private void analyzeExplicacion() {
		cleanExplicacion("Puntaje <= a 300");
		cleanExplicacion("Puntaje entre 301 y 599");
		cleanExplicacion("Puntaje > 599");
		cleanExplicacion(" | ");
		if(explicacionTrunc != null && explicacionTrunc.endsWith("Antecedentes en Prestadora de Telecomunicaciones"))
			explicacionTrunc = "Antecedentes en prestadora de telecomunicaciones.";
		if(explicacionTrunc != null && explicacionTrunc.trim().length() > 0)
			explicacionTrunc = (new StringBuilder(" ")).append(explicacionTrunc).toString();
	}
	
	private void cleanExplicacion(String prefix) {
		if(explicacionTrunc != null && explicacionTrunc.startsWith(prefix))
			explicacionTrunc = explicacionTrunc.substring(prefix.length(), explicacionTrunc.length());
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
