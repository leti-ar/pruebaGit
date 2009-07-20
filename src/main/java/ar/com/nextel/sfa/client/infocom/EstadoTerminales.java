package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.image.IconFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

public class EstadoTerminales extends Composite {

	private Grid panel;

	public EstadoTerminales() {
		this(" ", " ", " ");
	}

	public EstadoTerminales(int activas, int suspendidas, int desactivadas) {
		this("" + activas, "" + suspendidas, "" + desactivadas);
	}

	public EstadoTerminales(String activas, String suspendidas, String desactivadas) {
		panel = new Grid(1, 6);
		HTML verde = IconFactory.ledVerde();
		HTML amarillo = IconFactory.ledAmarillo();
		HTML rojo = IconFactory.ledRojo();
		verde.addStyleName("ml5");
		amarillo.addStyleName("ml5");
		rojo.addStyleName("ml5");
		panel.setWidget(0, 0, verde);
		panel.setText(0, 1, activas);
		panel.setWidget(0, 2, amarillo);
		panel.setText(0, 3, suspendidas);
		panel.setWidget(0, 4, rojo);
		panel.setText(0, 5, desactivadas);
		panel.setCellPadding(0);
		panel.setCellSpacing(0);
		panel.addStyleName("m0p0");
		initWidget(panel);
	}

	public void setEstado(int activas, int suspendidas, int desactivadas) {
		panel.setText(0, 1, "" + activas);
		panel.setText(0, 3, "" + suspendidas);
		panel.setText(0, 5, "" + desactivadas);
	}
}
