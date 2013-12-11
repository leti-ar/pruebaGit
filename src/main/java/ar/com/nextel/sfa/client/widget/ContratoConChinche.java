package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.event.ClickPincheEvent;
import ar.com.nextel.sfa.client.event.EventBusUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;

public class ContratoConChinche extends Composite {
	private FlowPanel flowPanel = new FlowPanel();
	private String contrato;
	private Button chinche = new Button();
	private boolean isClicked = false;
	
	public ContratoConChinche(final String contrato, final boolean isPinchado) {
		this.contrato = contrato;
		this.isClicked = isPinchado;
		if (isClicked) {
			this.chinche.addStyleName("icon-art-chinche-on");			
		} else {
			this.chinche.addStyleName("icon-art-chinche");
		}
		
		this.chinche.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				if (isClicked) {
					chinche.removeStyleName("icon-art-chinche-on");
					chinche.addStyleName("icon-art-chinche");
					isClicked = false;
				} else {
					chinche.removeStyleName("icon-art-chinche");
					chinche.addStyleName("icon-art-chinche-on");
					isClicked = true;
				}
				EventBusUtil.getEventBus().fireEvent(new ClickPincheEvent(contrato, isClicked));
			}
		});
		flowPanel.add(new InlineHTML(contrato));
		flowPanel.add(chinche);
		initWidget(flowPanel);
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Button getChinche() {
		return chinche;
	}
	
	public void setChinche(Button chinche) {
		this.chinche = chinche;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
}
