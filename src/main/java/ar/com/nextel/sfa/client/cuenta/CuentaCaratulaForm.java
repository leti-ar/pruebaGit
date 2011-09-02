package ar.com.nextel.sfa.client.cuenta;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.caratula.CaratulaUI;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
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
	private boolean huboCambios = false;
	
	private Command cancelarCommand;
	
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
						cuentaDto.getCaratulas().add(nuevaCaratula);
						refrescaTablaCaratula();
						huboCambios = true;
					}
				});
				int nroCaratula = 0;
				if(cuentaDto.getCaratulas()!= null){
					nroCaratula = cuentaDto.getCaratulas().size();
				}
				CaratulaDto caratula = new CaratulaDto();
				caratula.setAntiguedad(cuentaDto.getFechaCreacion());
				CaratulaUI.getInstance().cargarPopupNuevaCaratula(caratula, nroCaratula);
				
			}
		});
		crearCaratula.addStyleName("crearCaratulaButton");
		
		crearCaratulaWrapper = new SimplePanel();
		crearCaratulaWrapper.add(crearCaratula);
		crearCaratulaWrapper.addStyleName("h20");
		mainPanel.add(crearCaratulaWrapper);
		
		mainPanel.add(datosTabla);
		mainPanel.add(footerBar);
		
		cancelarCommand = new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
			}
		};
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
	
	public void cargaTablaCaratula(CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
        int col;
		List<CaratulaDto> caratulas = cuentaDto.getCaratulas();
		//MGR*****
//		limpiarTablaCaratulas();
		initTableCompleta(datosTabla);
		
		for (int i = 0; caratulas != null && i < caratulas.size(); i++) {
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
		if(caratula.getAntiguedad() == null){
			caratula.setAntiguedad(cuentaDto.getFechaCreacion());
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
								huboCambios = true;
							}
						});
						int nroCaratula = 0;
						if(cuentaDto.getCaratulas()!= null){
							nroCaratula = cuentaDto.getCaratulas().size();
						}
						CaratulaUI.getInstance().cargarPopupEditarCaratula(caratulaAEditar, nroCaratula);
						
					}
					//Toco el confirmar
					if (col == 1) {
						
						if(huboCambios){
							MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), 
									Sfa.constant().CONFIRMAR_CAMBIOS(), cancelarCommand);
						}else{
							CuentaRpcService.Util.getInstance().confirmarCaratula(caratulaAEditar,
									new DefaultWaitCallback<CaratulaDto>() {
										
										@Override
										public void success(CaratulaDto result) {
											int index = cuentaDto.getCaratulas().indexOf(caratulaAEditar);
											cuentaDto.getCaratulas().remove(index);
											cuentaDto.getCaratulas().add(index, CaratulaUI.getInstance().getCaraturaAEditar());
											refrescaTablaCaratula();
										}
									});
							System.out.println("Confirmar caratula");
							System.out.println("Guardar");
						}
					}
				}
			}
		});
	}
	
	public boolean formularioCaratulaDirty() {
		return true;
	}
	
	public void setHuboCambios(boolean huboCambios) {
		this.huboCambios = huboCambios;
	}
	
	public List<CaratulaDto> getCaratulas(){
		return cuentaDto.getCaratulas();
	}
	
}
