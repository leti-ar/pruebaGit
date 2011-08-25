package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.caratula.CaratulaUI;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.domicilio.DomicilioUI;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.EstadoTipoDomicilioDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.SuscriptorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class CuentaCaratulaForm extends Composite{
	
	private static CuentaCaratulaForm instance;
	private FlowPanel mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
	
	private Button crearCaratula;
	private SimplePanel crearCaratulaWrapper;
	
	private CuentaDto cuentaDto;
	
	public static CuentaCaratulaForm getInstance(){
		if(instance == null){
			instance = new CuentaCaratulaForm();
		}
		return instance;
	}

	private CuentaCaratulaForm(){
		mainPanel = new FlowPanel();
		footerBar = new FormButtonsBar();
		datosTabla = new FlexTable();
		datosTabla.setWidth("100%");
		agregaTableListeners();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-CaratulaResultTable");
		
		crearCaratula = new Button("Crear nueva");
		crearCaratula.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				CaratulaUI.getInstance().cargarPopupNuevaCaratula(new CaratulaDto());
				
			}
		});
		crearCaratula.addStyleName("crearCaratulaButton");
		
		crearCaratulaWrapper = new SimplePanel();
		crearCaratulaWrapper.add(crearCaratula);
		crearCaratulaWrapper.addStyleName("h20");
		mainPanel.add(crearCaratulaWrapper);
		
		mainPanel.add(datosTabla);
		mainPanel.add(footerBar);
	}

	private void initTableCompleta(FlexTable table) {
        limpiarPrimeraFilaTabla();
		String[] widths = { "24px", "24px", "100px", "50px", "150px", "150px", "45%" };
		for (int col = 0; col < widths.length; col++) {
			table.getColumnFormatter().setWidth(col, widths[col]);
		}
		table.getColumnFormatter().addStyleName(0, "alignCenter");
		table.getColumnFormatter().addStyleName(1, "alignCenter");
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.addStyleName("gwt-CaratulaResultTable");
		table.getRowFormatter().addStyleName(0, "header");
		table.setHTML(0, 0, Sfa.constant().whiteSpace());
		table.setHTML(0, 1, Sfa.constant().whiteSpace());
		table.setHTML(0, 2, Sfa.constant().documento());
		table.setHTML(0, 3, Sfa.constant().numSS());
		table.setHTML(0, 4, Sfa.constant().usuarioCreacion());
		table.setHTML(0, 5, Sfa.constant().fechaCreacion());
		table.setHTML(0, 6, Sfa.constant().whiteSpace());
	}
	
	private void limpiarPrimeraFilaTabla() {
		if (datosTabla.isCellPresent(0, 0)) {
			datosTabla.removeRow(0);
		}
	}
	
	/**
	 *Refresca la grilla de caratulas
	 **/
	public void refrescaTablaConNuevoCaratula() {
		cargaTablaCaratula(cuentaDto);
	}
	
	public void cargaTablaCaratula(CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
        int col;
		List<CaratulaDto> caratulas = cuentaDto.getCaratulas();
		limpiarTablaCaratulas();
		initTableCompleta(datosTabla);
		
		//MGR**** Adm_Vtas R2 Prueba
		CaratulaDto caratulaPrueba = new CaratulaDto();
		caratulaPrueba.setNroSS("45688");
		cuentaDto.addCaratula(caratulaPrueba);
		caratulas = cuentaDto.getCaratulas();
		//***********
		
		for (int i = 0; i < caratulas.size(); i++) {
			CaratulaDto caratulaDto = caratulas.get(i);
			if (caratulaDto != null) {
				// Carga los iconos:
				col = 0;
				if(!caratulaDto.isConfirmada()){
					datosTabla.setWidget(i + 1, col, IconFactory.lapiz());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					
					datosTabla.setWidget(i + 1, ++col, IconFactory.comprobarVerde());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					
					if(caratulaDto.getCuenta().getPersona() != null && caratulaDto.getCuenta().getPersona().getDocumento() != null){
						datosTabla.setHTML(i+1, ++col, caratulaDto.getCuenta().getPersona().getDocumento().getNumero());
					}
					else{
						datosTabla.setHTML(i+1, ++col, "");
					}
					datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
					
					if(caratulaDto.getNroSS() != null){
						datosTabla.setHTML(i+1, ++col, caratulaDto.getNroSS());
					}
					else{
						datosTabla.setHTML(i+1, ++col, "");
					}
					datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
					
					datosTabla.setHTML(i+1, ++col, caratulaDto.getUsuarioCreacion().getApellidoYNombre());
					datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
					
					//MGR**** Adm_Vtas R2
					//TODO Ver bien como formatear la fecha para mostrar
					datosTabla.setHTML(i+1, ++col, caratulaDto.getFechaCreacion().toLocaleString());
					datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
				}
			}
		}
	}

	private void limpiarTablaCaratulas() {
		while (datosTabla.getRowCount() > 1) {
			datosTabla.removeRow(1);
		}
	}

	private void agregaTableListeners() {
		datosTabla.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				Cell cell = ((HTMLTable) clickEvent.getSource()).getCellForEvent(clickEvent);
				if (cell == null) {
					return;
				}
				int row = cell.getRowIndex();
				int col = cell.getCellIndex();
				if (row != 0) {
//					DomiciliosCuentaDto domicilio = null;
//					if (isSuscriptor(cuentaDto)) {
//						domicilio = ((SuscriptorDto) cuentaDto).getGranCuenta().getPersona().getDomicilios()
//								.get(row - 1);
//					} else {
//						domicilio = cuentaDto.getPersona().getDomicilios().get(row - 1);
//					}
					CaratulaDto caratulaAEditar = cuentaDto.getCaratulas().get(row - 1);
					//Toco el lapiz
					if (col == 0) {
						CaratulaUI.getInstance().cargarPopupEditarCaratula(caratulaAEditar);
						
					}
					//Toco el confirmar
					if (col == 1) {
						
					}
				}
			}
		});
	}
}
