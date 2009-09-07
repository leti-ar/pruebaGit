package ar.com.nextel.sfa.client.infocom;

import java.util.List;

import ar.com.nextel.sfa.client.dto.EquipoDto;
import ar.com.nextel.sfa.client.dto.ResumenEquipoDto;
import ar.com.nextel.sfa.client.dto.ScoringDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ScoringPopUpUI extends NextelDialog {

	private ScoringDto scoring;
	private FlexTable encabezadoTable;
	
	public ScoringPopUpUI(String title, String width, ScoringDto scoring) {
		super(title, false, true);
		this.setWidth(width);
		this.addStyleName("scoringDialog");
		this.scoring = scoring;
		initScoringPopUp();
	}

	private void initScoringPopUp() {
		encabezadoTable = new FlexTable();
		encabezadoTable.setWidget(0, 0, new Label("Scoring:"));
		encabezadoTable.setWidget(1, 0, new Label(scoring.getMensajeAdicional()));
		this.add(encabezadoTable);
		SimpleLink cerrar = new SimpleLink("Cerrar");
		cerrar.addStyleName("cerrarLink");
		cerrar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				ScoringPopUpUI.this.hide();
			}
		});

		this.addFooter(cerrar);
	}

}
