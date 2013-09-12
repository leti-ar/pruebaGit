package ar.com.nextel.sfa.client.ss;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.ContratoViewDto;
import ar.com.nextel.sfa.client.event.ClickPermanenciaEvent;
import ar.com.nextel.sfa.client.event.EventBusUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PopUpPermanenciaUI extends DialogBox {

	private static PopUpPermanenciaUIUiBinder uiBinder = GWT
	.create(PopUpPermanenciaUIUiBinder.class);
	
	@UiField
	Anchor aceptarLink;
	
	@UiField
	Anchor cancelarLink;

	@UiField
	FlexTable contentTable;
	
	@UiField
	Label title;
	
	interface PopUpPermanenciaUIUiBinder extends UiBinder<Widget, PopUpPermanenciaUI> {
	}

	public PopUpPermanenciaUI() {
		setWidget(uiBinder.createAndBindUi(this));
		super.hide();
		super.setGlassEnabled(true);
	}

	public void chargeContentTable(List<ContratoViewDto> values){
		contentTable.removeAllRows();
		int row = 0;
		Collections.sort(values,new Comparator<ContratoViewDto>() {
			public int compare(ContratoViewDto c1, ContratoViewDto c2) {
				return c1.getContrato().compareTo(c2.getContrato());
			}
		});
		for (ContratoViewDto value : values) {
			if (!value.getCargosPermanencia().equals(0d)){
				String msg = Sfa.constant().infoContrato()
					.replaceAll("\\{1\\}", String.valueOf(value.getContrato()))
					.replaceAll("\\{2\\}", String.valueOf(value.getCargosPermanencia()))
					.replaceAll("\\{3\\}", String.valueOf(value.getMesesPermanencia()));
				Label contratoInfo = new Label(msg);
				contentTable.setWidget(row++, 0, contratoInfo);
			}
		}
	}
	
	@UiHandler("aceptarLink")
	public void onClickAceptar(ClickEvent event){
		EventBusUtil.getEventBus().fireEvent(new ClickPermanenciaEvent());
		super.hide();
	}
	
	@UiHandler("cancelarLink")
	public void onClickCancelar(ClickEvent event){
		super.hide();
	}

	public void setHeaderTitle(String title){
		this.title.setText(title);
	}

}
