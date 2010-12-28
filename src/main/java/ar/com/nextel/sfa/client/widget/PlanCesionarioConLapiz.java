package ar.com.nextel.sfa.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class PlanCesionarioConLapiz extends Composite {
	private FlowPanel flowPanel = new FlowPanel();
	private String planCesionario;
	private Button lapiz = new Button();
	
	public PlanCesionarioConLapiz(String planCesionario) {
		lapiz.addStyleName("icon-lapiz");
		this.planCesionario = planCesionario;
		
		lapiz.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				//TODO ver cómo llamar al metodo modificarPlanCesionario de DTSS
			}
		});
		flowPanel.add(lapiz);
		Label planLabel = new Label(planCesionario);
//		planLabel.addStyleName("normalText");
		flowPanel.add(planLabel);
		initWidget(flowPanel);
	}

	public String getPlanCesionario() {
		return planCesionario;
	}

	public void setPlanCesionario(String planCesionario) {
		this.planCesionario = planCesionario;
	}

	public Button getLapiz() {
		return lapiz;
	}

	public void setLapiz(Button lapiz) {
		this.lapiz = lapiz;
	}
	
}
