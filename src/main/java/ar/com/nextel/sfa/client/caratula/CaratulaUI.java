package ar.com.nextel.sfa.client.caratula;

import java.util.List;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.cuenta.CaratulaVerazModalDialog;
import ar.com.nextel.sfa.client.cuenta.CuentaEdicionTabPanel;
import ar.com.nextel.sfa.client.dto.BancoDto;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.VerazResponseDto;
import ar.com.nextel.sfa.client.widget.NextelDialog;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Componente para poder editar caratulas
 * 
 * @author mrotger
 *
 */
public class CaratulaUI extends NextelDialog implements ChangeListener, ClickListener{

	private static CaratulaUI instance = null;
	private CaratulaDto caratulaAEditar;
	private int nroCaratula = 0; //Para saber si es la primer caratula o no (requerido validacion)
	
	private Grid gridCabecera;
	
	private Grid gridBanco;
	private Grid gridDatosBanco;
	private HorizontalPanel panelBanco;
	
	private Grid gridActividad;
	private Grid gridTarj;
	private Grid gridIngDem;
	private Grid gridCOM;
	private Grid gridVeraz;
	private Grid gridIMEI;
	private Grid gridValidFirma;
	private Grid gridInferior;
	private Grid gridBotonesVeraz;
	
	private CaratulaUIData caratulaData;
	private SimpleLink aceptar;
	private SimpleLink cancelar;
	private Command aceptarCommand;
	private Button generarVeraz;
	private Button verVeraz;
	
	public static CaratulaUI getInstance(){
		if(instance == null){
			instance = new CaratulaUI();
		}
		return instance;
	}
	
	private CaratulaUI(){
		super(Sfa.constant().crearCaratula(), false, true);
		init();
	}
	
	private void init(){
		setWidth("840px");
		caratulaData = new CaratulaUIData();
		
		gridCabecera = new Grid(2,8);
		gridCabecera.addStyleName("layout");
		gridCabecera.getColumnFormatter().setWidth(1, "85px");
		gridCabecera.getColumnFormatter().setWidth(3, "120px");
		caratulaData.getWidgetFechaInicio().setWidth("100%");
		
		gridCabecera.setHTML(0, 0, Sfa.constant().numSS());
		gridCabecera.setWidget(0, 1, caratulaData.getNroSS());
		gridCabecera.setText(0, 2, Sfa.constant().fechaInicio());
		gridCabecera.setWidget(0, 3, caratulaData.getWidgetFechaInicio());
		gridCabecera.setText(0, 4, Sfa.constant().antiguedad());
		gridCabecera.setWidget(0, 5, caratulaData.getAntiguedad());
		
		gridCabecera.setHTML(1, 0, Sfa.constant().limCreditoReq());
		gridCabecera.setWidget(1, 1, caratulaData.getLimiteCred());
		gridCabecera.setHTML(1, 2, Sfa.constant().calificacionReq());
		gridCabecera.setWidget(1, 3, caratulaData.getCalificacion()); 
		gridCabecera.setHTML(1, 4, Sfa.constant().riskCodeReq());
		gridCabecera.setWidget(1, 5, caratulaData.getRiskCode());
		gridCabecera.setWidget(1, 6, caratulaData.getOkEECCAgente());
		gridCabecera.setText(1, 7, Sfa.constant().oKEECCAgente());
		
		add(gridCabecera);
		
		gridActividad = new Grid(2,6);
		gridActividad.addStyleName("layout");
		
		gridActividad.getColumnFormatter().setWidth(5, "80px");
		caratulaData.getEquiposActivos().setWidth("95%");
		
		gridActividad.setText(0, 0, Sfa.constant().actividad());
		gridActividad.setWidget(0, 1, caratulaData.getActividad());
		gridActividad.setText(0, 2, Sfa.constant().validacionDomicilio());
		gridActividad.setWidget(0, 3, caratulaData.getValidDomicilio());

//		Al llamar a este metodo obtengo la lista de caratulas, con lo cual si es una lista vacia, se que el documento es un CrÈdito, 
//		de lo contrario es un anexo. 
		List<CaratulaDto> listaDocumentosCaratula = obtenerListaCaratulas();
		boolean isDocCredito = listaDocumentosCaratula.isEmpty()? true:false;

		if(isDocCredito){
			gridActividad.setText(0, 4, Sfa.constant().equiposActivos());
		}else{
			gridActividad.setHTML(0, 4, Sfa.constant().equiposActivosReq());
		}		
		gridActividad.setWidget(0, 5, caratulaData.getEquiposActivos());
		
		add(gridActividad);
		
		gridBotonesVeraz = new Grid(1,2);
		gridBotonesVeraz.setWidget(0, 1, crearBotonesVeraz());
		add(gridBotonesVeraz);
		
		gridTarj = new Grid(1,8);
		gridTarj.getColumnFormatter().setWidth(7, "80px");
		caratulaData.getConsumoProm().setWidth("95%");
		
		gridTarj.addStyleName("layout");
		gridTarj.getColumnFormatter().setWidth(1, "150px");
		gridTarj.getColumnFormatter().setWidth(3, "100px");
		caratulaData.getTarjCredito().setWidth("80%");
		
		gridTarj.setWidget(0, 0, caratulaData.getIngDemostrado());
		gridTarj.setText(0, 1, Sfa.constant().ingresoDemostrado());
		gridTarj.setText(0, 2, Sfa.constant().tarjCredito());
		gridTarj.setWidget(0, 3, caratulaData.getTarjCredito());
		gridTarj.setText(0, 4, Sfa.constant().limiteTarj());
		gridTarj.setWidget(0, 5, caratulaData.getLimiteTarj());
		
		gridTarj.setText(0, 6, Sfa.constant().consumoProm());
		gridTarj.setWidget(0, 7, caratulaData.getConsumoProm());
		
		add(gridTarj);		
		
		gridIngDem = new Grid(3,6);
		gridIngDem.addStyleName("layout");
		gridIngDem.getColumnFormatter().setWidth(1, "140px");
		
		gridIngDem.setText(0, 0, Sfa.constant().PYP());
		gridIngDem.setWidget(0, 1, caratulaData.getPyp());
		gridIngDem.setText(0, 2, Sfa.constant().IVA());
		gridIngDem.setWidget(0, 3, caratulaData.getIva());
		gridIngDem.setText(0, 4, Sfa.constant().ganancia());
		gridIngDem.setWidget(0, 5, caratulaData.getGanancias());
		
		gridIngDem.setText(1, 2, Sfa.constant().balance());
		gridIngDem.setWidget(1, 3, caratulaData.getBalance());
		gridIngDem.setText(1, 4, Sfa.constant().iibb());
		gridIngDem.setWidget(1, 5, caratulaData.getIngBrutos());
		
		gridIngDem.setText(2, 2, Sfa.constant().reciboHaberes());
		gridIngDem.setWidget(2, 3, caratulaData.getReciboHaberes());
		gridIngDem.setText(2, 4, Sfa.constant().factCelular());
		gridIngDem.setWidget(2, 5, caratulaData.getFactCelular());
		
		add(gridIngDem);		
		
		gridBanco = new Grid(1,2);
		gridBanco.addStyleName("layout");
		gridBanco.getColumnFormatter().setWidth(1, "250px");
		caratulaData.getBanco().setWidth("95%");
		
		gridBanco.setText(0, 0, Sfa.constant().banco());
		gridBanco.setWidget(0, 1, caratulaData.getBanco());
		
		gridDatosBanco = new Grid(2, 4);
		gridDatosBanco.addStyleName("layout");
		gridDatosBanco.setText(0, 0, Sfa.constant().refBancaria());
		gridDatosBanco.setWidget(0, 1, caratulaData.getRefBancaria());
		gridDatosBanco.setText(0, 2, Sfa.constant().tipoCuenta());
		gridDatosBanco.setWidget(0, 3, caratulaData.getTipoCuenta());
		gridDatosBanco.setText(1, 0, Sfa.constant().mayorSaldo());
		gridDatosBanco.setWidget(1, 1, caratulaData.getMayorSaldoFavor());
		gridDatosBanco.setText(1, 2, Sfa.constant().ingresoProm());
		gridDatosBanco.setWidget(1, 3, caratulaData.getIngPromedio());
		
		panelBanco = new HorizontalPanel();
		panelBanco.add(gridBanco);
		panelBanco.add(gridDatosBanco);
		
		add(panelBanco);

		gridCOM = new Grid(2,4);
		gridCOM.addStyleName("layout");
		gridCOM.getColumnFormatter().setWidth(1, "340px");
		
		gridCOM.setText(0, 0, Sfa.constant().COM());
		gridCOM.setWidget(0, 1, caratulaData.getCom());
		if(isDocCredito){
			gridCOM.setText(0, 2, Sfa.constant().firmante());
		}else{
			gridCOM.setHTML(0, 2, Sfa.constant().firmanteReq());
		}
		gridCOM.setWidget(0, 3, caratulaData.getFirmante());
		gridCOM.setText(1, 0, Sfa.constant().BCRA());
		gridCOM.setWidget(1, 1, caratulaData.getBcra());
		if(isDocCredito){
			gridCOM.setText(1, 2, Sfa.constant().compPago());
		}else {
			gridCOM.setHTML(1, 2, Sfa.constant().compPagoReq());	
		}
		gridCOM.setWidget(1, 3, caratulaData.getComprPago());
		
		add(gridCOM);
		
		gridVeraz = new Grid(2,4);
		gridVeraz.addStyleName("layout");
		
		gridVeraz.setText(0, 0, Sfa.constant().veraz());
		gridVeraz.setWidget(0, 1, caratulaData.getVeraz());
		gridVeraz.setText(0, 2, Sfa.constant().nosis());
		gridVeraz.setWidget(0, 3, caratulaData.getNosis());

//		gridVeraz.setWidget(1, 1, crearBotonesVeraz());
		gridVeraz.setHTML(1, 2, Sfa.constant().imei());
		
		gridIMEI = new Grid(1,5);
		gridIMEI.setWidget(0, 0, caratulaData.getImei());
		gridIMEI.setWidget(0, 1, caratulaData.getVerificarImeiWrapper());
		gridIMEI.setWidget(0, 3, caratulaData.getFindImei());
		gridIMEI.setText(0, 4, Sfa.constant().findIMEI());
		
		gridVeraz.setWidget(1, 3, gridIMEI);
		add(gridVeraz);
		
		gridValidFirma = new Grid(2,6);
		gridValidFirma.addStyleName("layout");
		gridValidFirma.getColumnFormatter().setWidth(1, "320px");
		gridValidFirma.getColumnFormatter().setWidth(3, "350px");
		gridValidFirma.getColumnFormatter().setWidth(5, "200px");
		caratulaData.getValidFirma().setWidth("95%");
		caratulaData.getDepGarantia().setWidth("95%");
		caratulaData.getAnticipo().setWidth("95%");
		caratulaData.getOtras().setWidth("95%");
		
		gridValidFirma.setText(0, 0, Sfa.constant().validFirma());
		gridValidFirma.setWidget(0, 1, caratulaData.getValidFirma());
		gridValidFirma.setText(0, 2, Sfa.constant().deposGarantia());
		gridValidFirma.setWidget(0, 3, caratulaData.getDepGarantia());
		gridValidFirma.setWidget(0, 4, caratulaData.getSoloHibridos());
		gridValidFirma.setText(0, 5, Sfa.constant().soloHibridos());
		
		gridValidFirma.setText(1, 0, Sfa.constant().anticipo());
		gridValidFirma.setWidget(1, 1, caratulaData.getAnticipo());
		gridValidFirma.setText(1, 2, Sfa.constant().otras());
		gridValidFirma.setWidget(1, 3, caratulaData.getOtras());
		
		add(gridValidFirma);
		
		gridInferior = new Grid(1,4);
		gridInferior.addStyleName("layout");
		gridInferior.getColumnFormatter().setWidth(1, "400px");
		gridInferior.getColumnFormatter().setWidth(3, "250px");
		caratulaData.getComentAnalista().setWidth("95%");
		caratulaData.getScoring().setWidth("95%");
		
		gridInferior.setText(0, 0, Sfa.constant().comentAnalista());
		gridInferior.setWidget(0, 1, caratulaData.getComentAnalista());
		gridInferior.setText(0, 2, Sfa.constant().scoringTitle());
		gridInferior.setWidget(0, 3, caratulaData.getScoring());
		
		add(gridInferior);
		
		caratulaData.getBanco().addChangeListener(this);
		onChange(caratulaData.getBanco());
		
		caratulaData.getIngDemostrado().addClickListener(this);
		onClick(caratulaData.getIngDemostrado());
		
		cancelar = new SimpleLink("Cerrar");
		cancelar.setStyleName("link");
		aceptar = new SimpleLink("Aceptar");
		aceptar.setStyleName("link");
		addFormButtons(aceptar);
		addFormButtons(cancelar);
		setFormButtonsVisible(true);
		setFooterVisible(false);
		
		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {

				List<String> errores = caratulaData.validarCamposObligatorios(nroCaratula);
				if(errores.isEmpty()){
					String nroSolicitud = caratulaData.getNroSS().getText();
					
					if(nroSolicitud != null && !nroSolicitud.equals("")){
						CuentaRpcService.Util.getInstance().validarExistenciaTriptico(nroSolicitud, 
								new DefaultWaitCallback<Boolean>() {

									@Override
									public void success(Boolean result) {
										if(result){
											hide();
											if(aceptarCommand != null){
												aceptarCommand.execute();
											}
										}else{
											ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
											ErrorDialog.getInstance().show(Sfa.constant().ERR_NRO_SOLICITUD_NO_EXISTE(), false);
										}
									}
								});
					
					}else{
						hide();
						if(aceptarCommand != null){
							aceptarCommand.execute();
						}
					}
				}else{
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(errores, false);
				}
				
			}
		});
		cancelar.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				hide();
			}
		});
		
	}

	private HorizontalPanel crearBotonesVeraz() {
		this.generarVeraz = crearBotonGenerarVeraz();
		this.verVeraz = crearBotonVerVeraz();
		HorizontalPanel verazBotones = new HorizontalPanel();
		verazBotones.add(this.generarVeraz);
		verazBotones.add(this.verVeraz);
		return verazBotones;
	}
	
	/*
	 * Obtiene la lista de las caratulas
	 */
	private List<CaratulaDto> obtenerListaCaratulas(){
		CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();		
		return cuentaTab.getCuenta2editDto().getCaratulas();
	}
	
	private void habilitarBotonesVeraz() {
		/*  
		 * Si la caratula est· confirmada, el botÛn "Generar Veraz", debe estar deshabilitado, 
		 * independientemente de que si el archivo no fue generado.
		 */
		boolean isVerazNoGenerado = !this.caratulaAEditar.isConfirmada() && (this.caratulaAEditar.getArchivoVeraz() == null || 
			this.caratulaAEditar.getArchivoVeraz().length() == 0);

		setEnabledButton(isVerazNoGenerado,this.generarVeraz,"btn-disabled");
		setEnabledButton(!isVerazNoGenerado,this.verVeraz,"btn-disabled");
	}
	
	private void setEnabledButton(boolean enabled, Button btn, String styleDisable) {
		btn.setEnabled(enabled);
		if (enabled) {
			btn.removeStyleName(styleDisable);
		} else {
			if (!btn.getStyleName().contains(styleDisable)) {
				btn.addStyleName(styleDisable);
			}
			
		}
	}

	private Button crearBotonGenerarVeraz() {
		Button generarVeraz = new Button(Sfa.constant().generarVeraz());
		generarVeraz.setStyleName("btn-bkg-big");
		generarVeraz.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
			    CuentaRpcService.Util.getInstance().consultarDetalleVeraz(caratulaAEditar.getIdCuenta(),caratulaAEditar.getId(), 
			    		new DefaultWaitCallback<VerazResponseDto>() {

							@Override
							public void success(VerazResponseDto result) {
								caratulaAEditar.setArchivoVeraz(result.getFileName());
								habilitarBotonesVeraz();
							}
				    			
					});
			}
		});
		return generarVeraz;
	}
	
	private Button crearBotonVerVeraz() {
		Button verVeraz = new Button(Sfa.constant().verVeraz());
		verVeraz.setStyleName("btn-bkg");
		verVeraz.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				CaratulaVerazModalDialog.getInstance().showAndCenter(caratulaAEditar.getArchivoVeraz());
			}
		});
		return verVeraz;
	}

	/**
	 * Metodo que setea la accion a tomar por el bot√≥n Aceptar del popup CaratulaUI.
	 **/
	public void setAceptarCommand(Command aceptarCommand) {
		this.aceptarCommand = aceptarCommand;
	}
	
	public void showAndCenter() {
		super.showAndCenter();
	}
	
	//El pop-up se muestra con el t√≠tulo "Crear Caratula"
	public void cargarPopupNuevaCaratula(CaratulaDto caratulaDto, int nroCaratula){
		cargarPopupCaratula(caratulaDto, Sfa.constant().crearCaratula(), nroCaratula, true);
	}
	
	//El pop-up se muestra con el t√≠tulo "Editar Caratula"
	public void cargarPopupEditarCaratula(CaratulaDto caratulaDto, int nroCaratula){
		cargarPopupCaratula(caratulaDto, Sfa.constant().editarCaratula(), nroCaratula, true);
	}
	
	//El pop-up se muestra con el t√≠tulo "Caratula Confirmada"
	public void cargarPopupCaratulaConfirmada(CaratulaDto caratulaDto){
		cargarPopupCaratula(caratulaDto, "Caratula Confirmada", -1, false);
	}
	
	private void cargarPopupCaratula(CaratulaDto caratula, String titulo, int nroCaratula, boolean habilitar){
		this.caratulaAEditar = caratula;
		this.nroCaratula = nroCaratula;
		caratulaData.setDatosCaratula(caratulaAEditar);
		caratulaData.habilitarCampos(habilitar);
		habilitarBotonesVeraz();
		aceptar.setVisible(habilitar);
//		onChange(caratulaData.getBanco());
		mostrarDatosBanco(caratulaAEditar.getBanco());
		onClick(caratulaData.getIngDemostrado());
		setDialogTitle(titulo);
		showAndCenter();
	}
	
	public CaratulaDto getCaraturaAEditar(){
		return caratulaData.getDatosCaratula();
	}
	
	/*
	 * Si el banco no es nulo, muestra los datos de la grilla del banco (Ref Bancaria, Tipo Cuenta, Mayo saldo, Ingreso prom). De lo
	 * contrario no los muestra.
	 *
	 */
	public void mostrarDatosBanco(BancoDto banco){
		if(banco != null){
			gridDatosBanco.setVisible(true);
		}else{
			gridDatosBanco.setVisible(false);
		} 
	}
	
	public void onChange(Widget sender) {
		if(sender == caratulaData.getBanco()){
			
			if(caratulaData.getBanco().getSelectedItem() != null){
				gridDatosBanco.setVisible(true);
			}else{
				gridDatosBanco.setVisible(false);
			}
			
		} 
	}

	public void onClick(Widget sender) {
		if(sender == caratulaData.getIngDemostrado()){
			
			if(caratulaData.getIngDemostrado().getValue()){
				gridIngDem.setVisible(true);
				hide();
				showAndCenter();
			}else
				gridIngDem.setVisible(false);
		}
	}

	public List<String> validarCaratulaAConfirmar(CaratulaDto caratulaAConfirmar, int nroCaratula) {

		this.caratulaAEditar = caratulaAConfirmar;
		this.nroCaratula = nroCaratula;
		caratulaData.setDatosCaratula(caratulaAConfirmar);
		
		return caratulaData.validarCamposObligatorios(nroCaratula);
	}
	
}
