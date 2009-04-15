package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.NextelDialog;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudDialog extends NextelDialog {

	private ItemSolicitudUIData itemSolicitudUIData;
	
	public ItemSolicitudDialog(String title) {
		super(title);
		add(getTipoOrdenPanel());
	}

	private Widget getTipoOrdenPanel() {
		FlowPanel tipoOrdenPanel = new FlowPanel();
		tipoOrdenPanel.add(new InlineLabel(Sfa.constant().tipoOrden()));
//		tipoOrdenPanel.add(w)
		return null;
	}

}
