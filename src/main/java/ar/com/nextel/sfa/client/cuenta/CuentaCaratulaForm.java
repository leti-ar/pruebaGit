package ar.com.nextel.sfa.client.cuenta;

import java.util.Iterator;
import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.caratula.CaratulaUI;
import ar.com.nextel.sfa.client.caratula.DocDigitalizadosUI;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
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
	private Button docDigitalizados;
	private SimplePanel consultaDocDigitalizados;
	
	private CuentaDto cuentaDto;
	private boolean huboCambios = false;
	private boolean confirmandoCaratula= false;
	private int nroFila;
	private CaratulaDto caratulaSeleccionada = null;
	
	private Command cancelarCommand;
	public boolean caratulaNueva;
	
//	private CaratulaUI caratulaUI;
	
	public static CuentaCaratulaForm getInstance(){
		if(instance == null){
			instance = new CuentaCaratulaForm();
		}
		return instance;
	}

	private CuentaCaratulaForm(){
		mainPanel = new FlowPanel();
		mainPanel.setWidth("100%");
		mainPanel.addStyleName("gwt-CaratulaResultTable");
		
		crearCaratula = new Button("Crear nueva");
		crearCaratula.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setCaratulaNueva(true);
				if(isNuevaInstanciaCrearNueva())
					CaratulaUI.deleteInstance();
				CaratulaUI.getInstance().setAceptarCommand(new Command() {
					
					public void execute() {
						CaratulaDto nuevaCaratula = CaratulaUI.getInstance().getCaraturaAEditar();
						cuentaDto.getCaratulas().add(nuevaCaratula);
						refrescaTablaCaratula();
						huboCambios = true;
					}
				});

				CuentaRpcService.Util.getInstance().crearCaratula(cuentaDto, 
						new DefaultWaitCallback<CaratulaDto>() {

							public void success(CaratulaDto result) {
								
								int nroCaratula = 0;
								if(cuentaDto.getCaratulas()!= null){
									nroCaratula = cuentaDto.getCaratulas().size();
								}
								CaratulaUI.getInstance().cargarPopupNuevaCaratula(result, ++nroCaratula);
								setCaratulaSeleccionada(result);
							}
				});
			}
		});
		crearCaratula.addStyleName("crearCaratulaButton");
		
		crearCaratulaWrapper = new SimplePanel();
		crearCaratulaWrapper.add(crearCaratula);
		crearCaratulaWrapper.addStyleName("h20");
		mainPanel.add(crearCaratulaWrapper);
		
		datosTabla = new FlexTable();
		datosTabla.setWidth("100%");
		agregaTableListeners();
		mainPanel.add(datosTabla);
		
		docDigitalizados = new Button(Sfa.constant().docDigitalizados());
//		docDigitalizados.addStyleName("btn-bkg-big");
		docDigitalizados.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				DocDigitalizadosUI.getInstance().cargarDocDigitalizados(cuentaDto.getCodigoVantive());
			}
		});
		
		consultaDocDigitalizados = new SimplePanel();
		consultaDocDigitalizados.add(docDigitalizados);
		mainPanel.add(consultaDocDigitalizados);
		
		footerBar = new FormButtonsBar();
		mainPanel.add(footerBar);
		initWidget(mainPanel);
		
		cancelarCommand = new Command() {
			public void execute() {
				MessageDialog.getInstance().hide();
			}
		};
	}

	private void initTableCompleta(FlexTable table) {
        limpiarPrimeraFilaTabla();
		String[] widths = { "24px", "24px", "100px", "50px", "150px", "150px", "25px", "45%" };
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
//		table.setHTML(0, 6, Sfa.constant().whiteSpace());
		table.setHTML(0, 6, Sfa.constant().confirmada());
		table.setHTML(0, 7, Sfa.constant().whiteSpace());
	}
	
	private void limpiarPrimeraFilaTabla() {
		if (datosTabla.isCellPresent(0, 0)) {
			datosTabla.removeRow(0);
		}
	}
	
	/**
	 *	Refresca la grilla de caratulas
	 **/
	public void refrescaTablaCaratula() {
		cargaTablaCaratula(cuentaDto);
	}
	
	public void cargaTablaCaratula(CuentaDto cuentaDto) {
		this.cuentaDto = cuentaDto;
        int col;
		List<CaratulaDto> caratulas = cuentaDto.getCaratulas();
		limpiarTablaCaratulas();
		initTableCompleta(datosTabla);
		
		for (int row = 0; caratulas != null && row < caratulas.size(); row++) {
			CaratulaDto caratulaDto = caratulas.get(row);
			if (caratulaDto != null) {
				// Carga los iconos:
				col = 0;
				
				datosTabla.setWidget(row + 1, col, IconFactory.lapiz());
				datosTabla.getCellFormatter().setAlignment(row+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				
				if(!caratulaDto.isConfirmada()){
					datosTabla.setWidget(row + 1, ++col, IconFactory.confirmarCaratula());
					datosTabla.getCellFormatter().setAlignment(row+1, col, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
				}else{
					datosTabla.setHTML(row+1, ++col, Sfa.constant().whiteSpace());
				
				}
				
				datosTabla.setText(row+1, ++col, caratulaDto.getDocumento());
				datosTabla.getCellFormatter().addStyleName(row + 1, col, "alignCenter");
				
				if(caratulaDto.getNroSS() != null && !caratulaDto.getNroSS().equals("")){
					datosTabla.setText(row+1, ++col, caratulaDto.getNroSS());
				}
				else{
					datosTabla.setHTML(row+1, ++col, Sfa.constant().whiteSpace());
				}
				datosTabla.getCellFormatter().addStyleName(row + 1, col, "alignCenter");
				
				if(caratulaDto.getUsuarioCreacion() != null && !caratulaDto.getUsuarioCreacion().equals("")){
					datosTabla.setText(row+1, ++col, caratulaDto.getUsuarioCreacion().getApellidoYNombre());
				}
				else{
					datosTabla.setHTML(row+1, ++col, Sfa.constant().whiteSpace());
				}
				datosTabla.getCellFormatter().addStyleName(row + 1, col, "alignCenter");
				
				if(caratulaDto.getFechaCreacion() != null){
					datosTabla.setText(row+1, ++col, DateTimeFormat.getMediumDateFormat().format(caratulaDto.getFechaCreacion()));
				}
				else{
					datosTabla.setHTML(row+1, ++col, Sfa.constant().whiteSpace());
				}
				datosTabla.getCellFormatter().addStyleName(row + 1, col, "alignCenter");
				
				//Confirmada
				if(caratulaDto.isConfirmada()) {
					datosTabla.setText(row+1, ++col, "SI");
					
				}
				else{
					datosTabla.setText(row+1, ++col, "NO");
				}
				datosTabla.getCellFormatter().addStyleName(row + 1, col, "alignCenter");
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
				setCaratulaNueva(false);
				Cell cell = ((HTMLTable) clickEvent.getSource()).getCellForEvent(clickEvent);
				if (cell == null) {
					return;
				}
				int row = cell.getRowIndex();
				int col = cell.getCellIndex();
				nroFila = row;
				
//				caratulaUI = getInstanciaCaratulaUI(caratulaSeleccionada, row);
				if(!isMismaInstancia(caratulaSeleccionada, row)) {
					CaratulaUI.deleteInstance();
				}

				if (row != 0) {				
					//#LF
					// Seteo la nueva caratula seleccionada (La anterior ya no existe referencia).
					setCaratulaSeleccionada(cuentaDto.getCaratulas().get(row - 1));
					
					//Toco el lapiz
					if (col == 0) {
						
						if(!caratulaSeleccionada.isConfirmada()){
							
							CaratulaUI.getInstance().setAceptarCommand(new Command() {
								public void execute() {
									int index = cuentaDto.getCaratulas().indexOf(caratulaSeleccionada);
									cuentaDto.getCaratulas().remove(index);
									cuentaDto.getCaratulas().add(index, CaratulaUI.getInstance().getCaraturaAEditar());
									refrescaTablaCaratula();
									huboCambios = true;
								}
							});
							CaratulaUI.getInstance().cargarPopupEditarCaratula(caratulaSeleccionada, row);
						
						}else{
							CaratulaUI.getInstance().cargarPopupCaratulaConfirmada(caratulaSeleccionada);
						}
					}
					//Toco el confirmar
					if (col == 1) {
						
						if (datosTabla.getHTML(row, col).equalsIgnoreCase(Sfa.constant().whiteSpace()))
							return;
						
						
						if(huboCambios){
							MessageDialog.getInstance().showAceptar(Sfa.constant().MSG_DIALOG_TITLE(), 
									Sfa.constant().CONFIRMAR_CAMBIOS(), cancelarCommand);
						}else{
							//Si se esta confirmando una caratula, espero a que se termine esa
							if(!confirmandoCaratula){
								caratulaSeleccionada = cuentaDto.getCaratulas().get(row - 1);
								//#LF
								confirmandoCaratula = true;
								
								//#LF
								//Se realiza el DeferredCommand para "esperar" a que los combos se puedan inicializar y cargar sus items correspondientes.
								//Luego de que esto suceda sigue ejecutandose codigo en el else..
								DeferredCommand.addCommand(new IncrementalCommand() {
									public boolean execute() {
										if (!CaratulaUI.getInstance().getCaratulaUIData().isCombosCargados()){
											return true;
										} else {
											//#LF
											//Valida que esten cargados todos los datos antes de confirmar la caratula
											List<String> errores = CaratulaUI.getInstance().validarCaratulaAConfirmar(caratulaSeleccionada, nroFila);
											if(errores == null || errores.isEmpty()){
												//#LF
												String nroSolicitud = caratulaSeleccionada.getNroSS();
												if(nroSolicitud != null && !nroSolicitud.equals("")){
													CuentaRpcService.Util.getInstance().validarExistenciaTriptico(nroSolicitud, 
															new DefaultWaitCallback<Boolean>() {

																@Override
																public void success(Boolean result) {
																	
																	if(result){
																		//#LF
																			CuentaRpcService.Util.getInstance().confirmarCaratula(caratulaSeleccionada,
																				new DefaultWaitCallback<CaratulaDto>() {
																					
																					@Override
																					public void success(CaratulaDto result) {
																						//#LF
																						int index = cuentaDto.getCaratulas().indexOf(caratulaSeleccionada);
																						cuentaDto.getCaratulas().remove(index);
																						cuentaDto.getCaratulas().add(index, result);
																						refrescaTablaCaratula();
																						confirmandoCaratula = false;
																					}
																				});
																	}else{
																		ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
																		ErrorDialog.getInstance().show(Sfa.constant().ERR_NRO_SOLICITUD_NO_EXISTE(), false);
																		confirmandoCaratula = false;
																	}
																}
															});
												}else{
													ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
													ErrorDialog.getInstance().show(Sfa.constant().ERR_NRO_SOLICITUD_NO_REQUERIDO(), false);
													confirmandoCaratula = false;
												}
											}else{
												ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
												ErrorDialog.getInstance().show(errores, false);
												confirmandoCaratula = false;
											}
											return false;
										}
									}
								});			
							}
						}
					}
				}
			}
		});
	}
	
	/**
	 * Este m�todo retorna una buleano que indica si se debe hacer una nueva instancia de CaratulaUI. 
	 * Compara la caratula seleccionada anteriormente con la reciente.
	 * Si los documentos o el estado de la consulta al veraz son distintos retorna true, porque hay campos 
	 * que son requeridos dependiendo el tipo de documento, y en el caso del veraz hace lo mismo porque pueden ser distintos
	 * estados. De lo contrario retorna false.
	 * @author fernaluc
	 * @param caratulaAnterior
	 * @param row
	 * @return boolean
	 */
	public boolean isMismaInstancia(CaratulaDto caratulaAnterior, int row){
		CaratulaDto caratulaActual = cuentaDto.getCaratulas().get(row - 1);
		if(caratulaAnterior != null){
			if(caratulaAnterior.isDocumentoCredito() != caratulaActual.isDocumentoCredito() ||
				caratulaAnterior.getEstadoVeraz() != caratulaActual.getEstadoVeraz()) {
					return false;
			} else {
				return true;
			}			
		} 
		
		return false;
	}
	
	/**
	 * Este metodo se utiliza cuando se quiere crear una nueva caratula.
	 * Recorre las caratula existentes, si existe s�lo una quiere decir que la caratula es credito y retorna true
	 * indicando que CaratulaUI debe ser una nueva instancia, si ya existe una caratula con el documento Anexo retorna
	 * false.
	 * @return boolean
	 * @author fernaluc
	 */
	public boolean isNuevaInstanciaCrearNueva(){
		if(caratulaSeleccionada != null) {
			return !caratulaSeleccionada.getDocumento().equals("Anexo");
		}
		boolean nuevaInstancia = false;
		if(cuentaDto.getCaratulas().size() != 0) {
			nuevaInstancia = true;
			for (Iterator iterator = cuentaDto.getCaratulas().iterator(); iterator.hasNext();) {
				CaratulaDto car = (CaratulaDto) iterator.next();
				if(car.getDocumento().equals("Anexo")){
					nuevaInstancia = false;
				}
			}
		}		
		return nuevaInstancia;
	}
	
	
	public boolean formularioCaratulaDirty() {
		return this.huboCambios;
	}
	
	public void setHuboCambios(boolean huboCambios) {
		this.huboCambios = huboCambios;
	}
	
	public List<CaratulaDto> getCaratulas(){
		return cuentaDto.getCaratulas();
	}

	public int getNroFila() {
		return nroFila;
	}

	public void setNroFila(int nroFila) {
		this.nroFila = nroFila;
	}
	
	/**
	 *  Este m�todo obtiene la caratula que ha sido asignada para editar o confirmar.
	 */
	public CaratulaDto getCaratulaSeleccionada(){
		return caratulaSeleccionada;
	}

	public void setCaratulaSeleccionada(CaratulaDto caratulaSeleccionada) {
		this.caratulaSeleccionada = caratulaSeleccionada;
	}

	/**
	 *  Retorna la caratula actual con la que se esta editando o modificando.
	 *  Si la caratula es nueva, retorna null
	 *  @return CaratulaDto
	 */
	public CaratulaDto getCaratulaActual(){
		if(nroFila != 0){
			return cuentaDto.getCaratulas().get(nroFila - 1);
		} else {
			return null;
		}
	}

	public boolean isCaratulaNueva() {
		return caratulaNueva;
	}

	public void setCaratulaNueva(boolean caratulaNueva) {
		this.caratulaNueva = caratulaNueva;
	}
	
	
	
	
}
