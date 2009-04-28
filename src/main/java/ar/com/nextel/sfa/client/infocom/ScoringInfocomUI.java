package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ScoringInfocomUI extends Composite {
	
	private FlowPanel mainPanel;
	private ResumenEquipoPopUp equipoPopUp;
	private EstadoEquipoPopUp estadoPopUp;
	private ScoringPopUpUI scoringPopUpUI;
	private FidelizacionInfocomUI infocomFidelizacionUI;
	private CCInfocomUI ccInfocomUI;
	
	public ScoringInfocomUI(){
		mainPanel = new FlowPanel();
		mainPanel.addStyleName("headerCont");
		equipoPopUp = new ResumenEquipoPopUp("Resumen Equipo");
		estadoPopUp = new EstadoEquipoPopUp("Informacion Equipo");
		infocomFidelizacionUI = new FidelizacionInfocomUI();
		ccInfocomUI = new CCInfocomUI();
		initWidget(mainPanel);
		initScoringInfocomUI();
	}

	private void initScoringInfocomUI() {
		initHeader();
		mainPanel.add(infocomFidelizacionUI);
		mainPanel.add(ccInfocomUI);
	}
	
	private void initHeader(){
		Grid primerHeader = new Grid(1,10);
		mainPanel.add(primerHeader);
		Label estadoLabel = new Label("Estado");
		estadoLabel.addStyleName("lblEstado");
		primerHeader.setWidget(0, 0, estadoLabel);
		
			
		/**Genero grafica del estado*/
		Label activo = new Label("1");
		activo.addStyleName("estado activo");
		activo.setTitle("Ver informacion equipos activo");
		primerHeader.setWidget(0, 1, activo);
		Label suspendido = new Label("1");
		suspendido.addStyleName("estado suspendido");
		suspendido.setTitle("Ver informacion equipos suspendido");
		primerHeader.setWidget(0, 2, suspendido);
		Label desactivado = new Label("1");
		desactivado.addStyleName("estado desactivado");
		desactivado.setTitle("Ver informacion equipos desactivado");
		primerHeader.setWidget(0, 3, desactivado);
		
		Label cicloLabel = new Label("Ciclo:");
		cicloLabel.addStyleName("cicloLabel");
		primerHeader.setWidget(0, 4, cicloLabel);
		SimpleLabel cicloInfo = new SimpleLabel();
		cicloInfo.addStyleName("cicloLabel cicloInfo");
		primerHeader.setWidget(0, 5, cicloInfo);
		Label flotaLabel = new Label("Flota:");
		flotaLabel.addStyleName("lblEstado");
		primerHeader.setWidget(0, 6, flotaLabel);
		
		/**Genero link al Scoring*/
		ScoringDto scoringDto = new ScoringDto();
		scoringDto.setScoring("El cliente posee deuda vencida");
		scoringPopUpUI = new ScoringPopUpUI("Cuentas - Scoring",scoringDto);
		FlowPanel scoring = new FlowPanel();
		scoring.addStyleName("scoring");
		Hyperlink scoringLink = new Hyperlink("Scoring","#");
		scoringLink.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				scoringPopUpUI.showAndCenter();
			}});
		scoring.add(scoringLink);
		primerHeader.setWidget(0, 7, scoring);

		Label credLabel = new Label("Lim. Credito");
		credLabel.addStyleName("credLabel");
		primerHeader.setWidget(0, 8, credLabel);
		SimpleLabel credInfo = new SimpleLabel();
		primerHeader.setWidget(0, 9, credInfo);
		
		Grid segundoHeader = new Grid(1,3);
		mainPanel.add(segundoHeader);
		Label responsable = new Label("Nº Responsable Pago:");
		segundoHeader.setWidget(0, 0, responsable);
		final ListBox listaResponsable = new ListBox();
		listaResponsable.setWidth("270px");
		listaResponsable.addItem("26.5898");
		listaResponsable.addItem("Todos");
		segundoHeader.setWidget(0, 1, listaResponsable);

		/**Genero link descripcion de equipos*/
		FlowPanel equipo = new FlowPanel();
		equipo.addStyleName("resumenEquipo");
		Hyperlink linkEquipo = new Hyperlink("Resumen por equipo","#");
		equipo.add(linkEquipo);
		segundoHeader.setWidget(0, 2, equipo);

		/**Oculto los paneles mediante la seleccion del ListBox*/
		listaResponsable.addChangeListener(new ChangeListener(){
			public void onChange(Widget arg0) {
				if(listaResponsable.getSelectedItemText().equals("Todos")){
					mainPanel.remove(infocomFidelizacionUI);
				}
				else
					mainPanel.add(infocomFidelizacionUI);
				
			}});
		
		/**Muestro el PopUp de la descripcion de equipos*/
		linkEquipo.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				equipoPopUp.showAndCenter();	
			}
		});
		
		/**Muestro el PopUp del estado*/
		ClickListener listener = new ClickListener(){
			public void onClick(Widget arg0) {
				estadoPopUp.showAndCenter();
			}};
			
			activo.addClickListener(listener);
	}	
}