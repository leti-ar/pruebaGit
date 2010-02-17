package ar.com.nextel.sfa.client.infocom;

import java.util.List;

import ar.com.nextel.sfa.client.InfocomRpcService;
import ar.com.nextel.sfa.client.dto.DatosEquipoPorEstadoDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

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
		verde.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				getInformacionEquipos(InfocomUI.getInstance().getIdCuenta(), InfocomUI.getInstance().getIdCliente(), "a");				
			}
		});
		
		HTML amarillo = IconFactory.ledAmarillo();
		amarillo.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				getInformacionEquipos(InfocomUI.getInstance().getIdCuenta(), InfocomUI.getInstance().getIdCliente(), "s");
			}
		});
		
		HTML rojo = IconFactory.ledRojo();
		rojo.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				getInformacionEquipos(InfocomUI.getInstance().getIdCuenta(), InfocomUI.getInstance().getIdCliente(), "d");
			}
		});
		
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
	
	private void getInformacionEquipos(String numeroCuenta, String codigoVantive, String estado) {
		InfocomRpcService.Util.getInstance().getInformacionEquipos(numeroCuenta, codigoVantive, estado, new DefaultWaitCallback<List<DatosEquipoPorEstadoDto>>() {
			public void success(List<DatosEquipoPorEstadoDto>  result) {
				if (result != null) {
					EstadoEquipoPopUp estadoEquipoPopUp = new EstadoEquipoPopUp("Cuentas - Información de Equipos", "750", result);
					estadoEquipoPopUp.setEstado(result);
					estadoEquipoPopUp.showAndCenter();
				}
			}
		});
	}	

	public void setEstado(int activas, int suspendidas, int desactivadas) {
		panel.setText(0, 1, "" + activas);
		panel.setText(0, 3, "" + suspendidas);
		panel.setText(0, 5, "" + desactivadas);
	}
}
