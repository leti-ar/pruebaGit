package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ScoringPopUpUI extends NextelDialog {

	public ScoringPopUpUI(String title,ScoringDto scoringDto) {
		super(title);
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.setHeight("197px");
		mainPanel.setWidth("380px");
		mainPanel.add(new Label("Scoring:"));
		Label scoringInfo = new Label();
		scoringInfo.setText(scoringDto.getScoring());
		mainPanel.add(scoringInfo);
		this.add(mainPanel);
		SimpleLink cerrar = new SimpleLink();
		cerrar.addStyleName("cerrarLink");
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				ScoringPopUpUI.this.hide();
			}
		});
		
		this.addFooter(cerrar);
	}
}
