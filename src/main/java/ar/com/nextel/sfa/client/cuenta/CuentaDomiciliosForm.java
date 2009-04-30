package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioAsociadoDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

public class CuentaDomiciliosForm extends Composite {

	private static CuentaDomiciliosForm instance = new CuentaDomiciliosForm();
	private FlowPanel mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
	private CuentaUIData cuentaUIData;

	//TODO; Analizar!!
	//private DomiciliosUIData domiciliosData;
	private CuentaDto cuentaDto;
	//
	
	public static CuentaDomiciliosForm getInstance() {
		return instance;
	}
	
	private CuentaDomiciliosForm() {
		mainPanel    = new FlowPanel();
		footerBar    = new FormButtonsBar();
		datosTabla = new FlexTable();
		initWidget(mainPanel);
		mainPanel.clear();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		//
		Button crearDomicilio = new Button("Crear nuevo");
		crearDomicilio.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				//Abajo se setea en el DomicilioUI la accion a tomar al apretar Aceptar.
				DomicilioUI.getInstance().setComandoAceptar(getComandoAceptarDomicilioForm());
				DomicilioUI.getInstance().cargarPopupNuevoDomicilio();
			}
		});
		crearDomicilio.addStyleName("crearDomicilioButton");
		SimplePanel crearDomicilioWrapper = new SimplePanel();
		crearDomicilioWrapper.add(crearDomicilio);
		crearDomicilioWrapper.addStyleName("crearDomicilioBWrapper");
		mainPanel.add(crearDomicilioWrapper);
		//
		initTable(datosTabla);
		mainPanel.add(datosTabla);
		mainPanel.add(footerBar);
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
	private Command getComandoAceptarDomicilioForm(){
		Command comandoAceptar = new Command() {
			public void execute() {
			//TODO: Aca deberia agregarle un nuevo Domicilio a la Persona.
				//HardCode - TODO: Aca abajo el cuentaUIData ya deberia venir cargado. Falta terminar! (Preguntarle a Raul).
				DomiciliosCuentaDto domicilioNuevo = DomicilioUI.getInstance().getDomiciliosData().getNuevoDomicilio();
				PersonaDto persona = cuentaDto.getPersona();
				persona.getDomicilios().add(domicilioNuevo);
				DomicilioUI.getInstance().hide();
				//Refresca la grilla de domicilios
				CuentaEdicionTabPanel.getInstance().getCuentaDomicilioForm().cargaTablaDomicilios(cuentaDto);
			}
		};
		return comandoAceptar;
	}
	
	/**
	 * @author eSalvador 
	 **/
	public void cargaTablaDomicilios(final CuentaDto cuentaDto){
		boolean principal = false;
		long tipoDomicilio = 666;
		TipoDomicilioAsociadoDto domicilioDto;
		this.cuentaDto = cuentaDto;
		
		//HardCode - TODO: Aca abajo el cuentaUIData ya deberia venir cargado. Falta terminar! (Preguntarle a Raul).
		//cuentaUIData = CuentaDatosForm.getInstance().getCuentaEditor();
		//domicilios = cuentaUIData.getPersona().getDomicilios();
	 	//
		
		List<DomiciliosCuentaDto> domicilios;
		domicilios = cuentaDto.getPersona().getDomicilios();
		datosTabla.addTableListener(new TableListener(){
			public void onCellClicked(SourcesTableEvents arg0, int row, int col) {
				//Aca preguntar si es Columna = 0:
				if((col == 0) && (row != 0)){
					//Aca agarrar el row que me llega, y hacerte a la lista global 
					//de domicilios, un get(row) y llamar abajo:
					DomicilioUI.getInstance().cargarPopupEditarDomicilio(cuentaDto.getPersona().getDomicilios().get(row-1));
				}
			}
		});
		
		for (int i = 0; i < domicilios.size(); i++) {
			if (domicilios.get(i) != null){
			//Carga los iconos:
			//Hyperlink linkLapiz = new Hyperlink(IconFactory.lapiz().toString(),true,"");
			datosTabla.setWidget(i+1, 0,IconFactory.lapiz());
			
			//if (cuenta.isPuedeVerInfocom()) {
			//datosTabla.setWidget(i+1, 1, IconFactory.lupa());
			//}
			if (true) {
				datosTabla.setWidget(i+1, 2, IconFactory.locked());
			}
			//Agarra el tipo de domicilio del Dto:
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
