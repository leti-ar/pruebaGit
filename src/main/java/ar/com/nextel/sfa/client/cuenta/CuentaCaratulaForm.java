package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.caratula.CaratulaUI;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.snoop.gwt.commons.client.util.DateUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;

public class CuentaCaratulaForm extends Composite{
	
	private static CuentaCaratulaForm instance;
	private FlowPanel mainPanel;
	private FormButtonsBar footerBar;
	private FlexTable datosTabla;
	
	private Button crearCaratula;
	private SimplePanel crearCaratulaWrapper;
	
	private CuentaDto cuentaDto;
	private CaratulaDto caratulaAEditar;
	
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
				CaratulaUI.getInstance().setAceptarCommand(new Command() {
					
					public void execute() {
						System.out.println("Obtengo la NUEVA caratula ya validada");
						System.out.println("La sumo a la lista de caratulas");
						CaratulaDto nuevaCaratula = CaratulaUI.getInstance().getCaraturaAEditar();
						cuentaDto.addCaratula(nuevaCaratula);
						refrescaTablaCaratula();
					}
				});
				
				CaratulaUI.getInstance().cargarPopupNuevaCaratula(new CaratulaDto(), cuentaDto.getCaratulas().size());
				
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
	//refrescaTablaConNuevoCaratula
	public void refrescaTablaCaratula() {
		limpiarTablaCaratulas();
		cargaTablaCaratula(cuentaDto);
	}
	
	private static boolean ff = true;
	public void cargaTablaCaratula(CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
        int col;
		List<CaratulaDto> caratulas = cuentaDto.getCaratulas();
//		limpiarTablaCaratulas();
		initTableCompleta(datosTabla);
		
		//MGR**** Adm_Vtas R2 Prueba
		if(ff){
			CaratulaDto caratulaPrueba = new CaratulaDto();
			caratulaPrueba.setNroSS("45688");
			caratulaPrueba.setFechaCreacion(DateUtil.today());
			cuentaDto.addCaratula(caratulaPrueba);
			caratulas = cuentaDto.getCaratulas();
			ff= false;
		}
		//***********
		
		for (int i = 0; i < caratulas.size(); i++) {
			CaratulaDto caratulaDto = caratulas.get(i);
			if (caratulaDto != null) {
				// Carga los iconos:
				col = 0;
				
				//MGR**** Adm_Vtas R2 Prueba
				//TODO: Si esta confirmada que icono mostramos para ver?
				if(!caratulaDto.isConfirmada()){
					datosTabla.setWidget(i + 1, col, IconFactory.lapiz());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
					
					datosTabla.setWidget(i + 1, ++col, IconFactory.comprobarVerde());
					datosTabla.getCellFormatter().setAlignment(i+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				}else{
					col++;
				}
				
				cargarDatosFaltantes(i, caratulaDto);
//				if(i==0){
//					caratulaDto.setDocumento(Sfa.constant().credito());
//				}else{
//					caratulaDto.setDocumento(Sfa.constant().anexo());
//				}
				
				datosTabla.setText(i+1, ++col, caratulaDto.getDocumento());
				datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
				
				if(caratulaDto.getNroSS() != null){
					datosTabla.setText(i+1, ++col, caratulaDto.getNroSS());
				}
				else{
					datosTabla.setHTML(i+1, ++col, " ");
				}
				datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
				
				if(caratulaDto.getUsuarioCreacion() != null){
					datosTabla.setText(i+1, ++col, caratulaDto.getUsuarioCreacion().getApellidoYNombre());
				}
				else{
					datosTabla.setHTML(i+1, ++col, " ");
				}
				datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
				
				if(caratulaDto.getFechaCreacion() != null){
					datosTabla.setText(i+1, ++col, DateTimeFormat.getMediumDateFormat().format(caratulaDto.getFechaCreacion()));
				}
				else{
					datosTabla.setHTML(i+1, ++col, " ");
				}
				datosTabla.getCellFormatter().addStyleName(i + 1, col, "alignCenter");
				
			}
		}
	}

	private void cargarDatosFaltantes(int nroCaratula, CaratulaDto caratula){
		if(caratula.getDocumento() == null){
			if(nroCaratula == 0){
				caratula.setDocumento(Sfa.constant().credito());
			}else{
				caratula.setDocumento(Sfa.constant().anexo());
			}
		}
		caratula.setUsuarioCreacion(ClientContext.getInstance().getVendedor());
		caratula.setFechaCreacion(DateUtil.today());
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
					caratulaAEditar = cuentaDto.getCaratulas().get(row - 1);
					//Toco el lapiz
					if (col == 0) {
						
						CaratulaUI.getInstance().setAceptarCommand(new Command() {
							public void execute() {
								//CaratulaDto CaratulaUI.getInstance().getCaraturaAEditar();
								System.out.println("Obtengo la caratula EDITADA ya validada");
								System.out.println("La sumo a la lista de caratulas");
								
								int index = cuentaDto.getCaratulas().indexOf(caratulaAEditar);
								cuentaDto.getCaratulas().remove(index);
								cuentaDto.getCaratulas().add(index, CaratulaUI.getInstance().getCaraturaAEditar());
								refrescaTablaCaratula();
							}
						});
						CaratulaUI.getInstance().cargarPopupEditarCaratula(caratulaAEditar, cuentaDto.getCaratulas().size());
						
					}
					//Toco el confirmar
					if (col == 1) {
						
					}
				}
			}
		});
	}
}
