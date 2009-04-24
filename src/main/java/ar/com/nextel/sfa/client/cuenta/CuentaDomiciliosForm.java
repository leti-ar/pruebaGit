package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioAsociadoDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class CuentaDomiciliosForm extends Composite {

	private static CuentaDomiciliosForm instance = new CuentaDomiciliosForm();
	private FlexTable mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
	//private DomiciliosCuentaUIData domicilioEditor;

	public static CuentaDomiciliosForm getInstance() {
		return instance;
	}
	
	private CuentaDomiciliosForm() {
		mainPanel    = new FlexTable();
		footerBar    = new FormButtonsBar();
		datosTabla = new FlexTable();
		initWidget(mainPanel);
		mainPanel.clear();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		mainPanel.setCellPadding(20);
		mainPanel.setWidget(0,0,new HTML("Domicilios"));
		initTable(datosTabla);
		mainPanel.getColumnFormatter().addStyleName(0, "alignRight");
		mainPanel.getRowFormatter().addStyleName(0, "alignRight");
		mainPanel.setWidget(1, 0, datosTabla);
		mainPanel.addStyleName("aButtons");
		mainPanel.setWidget(2,0,footerBar);
	}
	
	private void initTable(FlexTable table) {
		
		String[] widths = { "24px", "24px", "24px","100px", "100px", "75%", "50px"};
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.getColumnFormatter().addStyleName(2, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-BuscarCuentaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().whiteSpace());
		table.setHTML(0, 3, Sfa.constant().entrega());
		table.setHTML(0, 4, Sfa.constant().facturacion());
		table.setHTML(0, 5, Sfa.constant().domicilios());
		table.setHTML(0, 6, Sfa.constant().whiteSpace());
	}
	
	/**
	 * @author eSalvador 
	 **/
	public void cargaTablaDomicilios(List<DomiciliosCuentaDto> domicilios){
		boolean principal = false;
		long tipoDomicilio = 666;
		for (int i = 0; i < domicilios.size(); i++) {
			//Carga los iconos:
			Hyperlink linkLapiz = new Hyperlink(IconFactory.lapiz().toString(),true,"");
			linkLapiz.addClickListener(new ClickListener() {
				public void onClick(Widget arg0) {
					DomicilioUI.getInstance().showAndCenter();
				}
			});
			datosTabla.setWidget(i+1, 0,linkLapiz);

			//if (cuenta.isPuedeVerInfocom()) {
			//datosTabla.setWidget(i+1, 1, IconFactory.lupa());
			//}
			if (true) {
				datosTabla.setWidget(i+1, 2, IconFactory.locked());
			}
			
			//Agarra el tipo de domicilio del Dto:
			TipoDomicilioAsociadoDto domicilioDto;
			if (domicilios.get(i) != null){
				if (domicilios.get(i).getTiposDomicilioAsociado() != null){
					domicilioDto = domicilios.get(i).getTiposDomicilioAsociado().get(i);
					tipoDomicilio = domicilioDto.getIdTipoDomicilio();
					principal = domicilioDto.getPrincipal();
				}
				/**Logica para tipoDomicilio:*/
				//Si es (tipoDomicilio = 0) es Domicilio de Entrega:
				if (tipoDomicilio == 0){
					datosTabla.getCellFormatter().addStyleName(i+1,3,"alignCenter");
					datosTabla.getCellFormatter().addStyleName(i+1,4,"alignCenter");
					//Y es Principal:
					if (principal){
						datosTabla.setHTML(i+1, 3, "Principal");
						datosTabla.setHTML(i+1, 4, "No");
					}else{
						datosTabla.setHTML(i+1, 3, "Si");
						datosTabla.setHTML(i+1, 4, "No");
					}
				//Si es (tipoDomicilio = 1) es Domicilio de Facturacion:
				}else if (tipoDomicilio == 1){
					datosTabla.getCellFormatter().addStyleName(i+1,3,"alignCenter");
					datosTabla.getCellFormatter().addStyleName(i+1,4,"alignCenter");
					//Si ademas es Principal:
					if (principal){
						datosTabla.setHTML(i+1, 4, "Principal");
						datosTabla.setHTML(i+1, 3, "No");
					}else{
						datosTabla.setHTML(i+1, 3, "No");
						datosTabla.setHTML(i+1, 4, "Si");
					}
				}
		     datosTabla.setHTML(i+1, 5, domicilios.get(i).getDomicilios());
		     }
		}
	}
}
