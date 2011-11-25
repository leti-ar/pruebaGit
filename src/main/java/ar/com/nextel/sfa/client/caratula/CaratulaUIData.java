package ar.com.nextel.sfa.client.caratula;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.SolicitudRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.BancoDto;
import ar.com.nextel.sfa.client.dto.CalifCrediticiaDto;
import ar.com.nextel.sfa.client.dto.CalificacionDto;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.ComDto;
import ar.com.nextel.sfa.client.dto.CompPagoDto;
import ar.com.nextel.sfa.client.dto.EstadoCreditBancoCentralDto;
import ar.com.nextel.sfa.client.dto.FirmanteDto;
import ar.com.nextel.sfa.client.dto.RiskCodeDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.ValidacionDomicilioDto;
import ar.com.nextel.sfa.client.dto.VerazNosisDto;
import ar.com.nextel.sfa.client.image.IconFactory;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.validator.GwtValidator;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrotger
 * 
 */
public class CaratulaUIData extends UIData{// implements ChangeListener, ClickListener {

	//#LF
	private static CaratulaUIData instance = null;
	
	private static final int MAX_LENGHT_100 = 100;
	private static final int MAX_LENGHT_200 = 200;
	private static final int MAX_LENGHT_240 = 240;
	private static final String v1 = "\\{1\\}";
	private static final String v2 = "\\{2\\}";
	public static final String RISK_CODE_EECC_AGENTE = "RISK_CODE_EECC_AGENTE";
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	
	private TextBox nroSS;
	private SimpleDatePicker fechaInicio;
	private TextArea actividad = new TextArea();
	private ListBox validDomicilio = new ListBox("");
	private ListBox banco = new ListBox("");
//	private TextBox refBancaria;
	private TextArea refBancaria = new TextArea();
	private ListBox tipoCuenta = new ListBox("");
	private TextBox mayorSaldoFavor;
	private TextBox ingPromedio;
	private ListBox tarjCredito = new ListBox("");
	private TextBox limiteTarj;
	private CheckBox ingDemostrado = new CheckBox();
	private TextArea iva = new TextArea();
	private TextArea ganancias = new TextArea();
	private TextArea balance = new TextArea();
	private TextArea ingBrutos = new TextArea();
	private TextArea reciboHaberes = new TextArea();
	private ListBox pyp = new ListBox("");
	private TextArea factCelular = new TextArea();
	private CheckBox okEECCAgente = new CheckBox();
	private ListBox com = new ListBox("");
	private TextBox antiguedad;
	private TextBox limiteCred;
	private TextBox auxLimCred = new TextBox();
	private ListBox calificacion = new ListBox("");
	private ListBox riskCode = new ListBox("");
	private ListBox comprPago  = new ListBox("");
	private ListBox firmante = new ListBox("");
	private TextBox consumoProm;
	private TextBox equiposActivos;
	private ListBox veraz = new ListBox("");
	private ListBox nosis = new ListBox("");
	private ListBox bcra = new ListBox("");
	private TextArea otras = new TextArea();
	private TextArea validFirma = new TextArea();
	private TextArea depGarantia = new TextArea();
	private TextArea anticipo = new TextArea();
	private CheckBox soloHibridos = new CheckBox();
	private CheckBox findImei = new CheckBox();
	private TextArea comentAnalista = new TextArea();
	private TextArea scoring = new TextArea();
	private HTML verificarImeiWrapper;
	private TextBox imei;
	//#LF
	private boolean isCombosCargados;
	private Label estadoVeraz = new Label("");
	
	private CaratulaDto caratula = null;

	//#LF
	public static CaratulaUIData getInstance(){
		if(instance == null){
			instance = new CaratulaUIData();
		}
		return instance;
	}
	
	//#LF
	//public CaratulaUIData(){
	private CaratulaUIData(){
		setPropiedadesCampos();
		cargarCombos();
	}
	
	private void cargarCombos() {
		//#LF
		setCombosCargados(false);
		CuentaRpcService.Util.getInstance().getCaratulaInicializarte(
				new DefaultWaitCallback<CaratulaInitializer>() {
		      
			public void success(CaratulaInitializer initializer) {
				validDomicilio.addAllItems(initializer.getValidDomicilio());
				banco.addAllItems(initializer.getBancos());
				tipoCuenta.addAllItems(initializer.getTipoCtaBancaria());
				tarjCredito.addAllItems(initializer.getTipoTarjCred());
				pyp.addAllItems(initializer.getCalifCrediticia());
				com.addAllItems(initializer.getCom());
				calificacion.addAllItems(initializer.getCalificacion());
				riskCode.addAllItems(initializer.getRiskCode());
				comprPago.addAllItems(initializer.getCompPago());
				firmante.addAllItems(initializer.getFirmante());
				veraz.addAllItems(initializer.getVerazNosis());
				nosis.addAllItems(initializer.getVerazNosis());
				bcra.addAllItems(initializer.getEstadoCredBC());
				//#LF
				setCombosCargados(true);
			}
		});
		
	}
	
	private void setPropiedadesCampos(){
		nroSS = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(10), true);
		fechaInicio = new SimpleDatePicker(false, true);
		fechaInicio.setWeekendSelectable(true);
		
		actividad.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (actividad.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					actividad.setText(actividad.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
//		refBancaria = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(100));
		refBancaria.addKeyUpHandler(new KeyUpHandler() {			
			public void onKeyUp(KeyUpEvent event) {
				if(refBancaria.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					refBancaria.setText(refBancaria.getText().substring(0, MAX_LENGHT_200));					
				}
			}
		});
		
		mayorSaldoFavor = new RegexTextBox(RegularExpressionConstants.importe);
		ingPromedio = new RegexTextBox(RegularExpressionConstants.importe);
		limiteTarj = new RegexTextBox(RegularExpressionConstants.importe);
		
		iva.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (iva.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					iva.setText(iva.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
		ganancias.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (ganancias.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					ganancias.setText(ganancias.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
		balance.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (balance.getText().length() > MAX_LENGHT_100) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					balance.setText(balance.getText().substring(0, MAX_LENGHT_100));
				}
			}
		});
		
		ingBrutos.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (ingBrutos.getText().length() > MAX_LENGHT_100) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					ingBrutos.setText(ingBrutos.getText().substring(0, MAX_LENGHT_100));
				}
			}
		});
		
		reciboHaberes.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (reciboHaberes.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					reciboHaberes.setText(reciboHaberes.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
		factCelular.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (factCelular.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					factCelular.setText(factCelular.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
		antiguedad = new RegexTextBox(RegularExpressionConstants.fecha);
		antiguedad.setEnabled(false);
		limiteCred = new RegexTextBox(RegularExpressionConstants.importe);
		
		consumoProm = new RegexTextBox(RegularExpressionConstants.decimal);
		consumoProm.setEnabled(false);
		
		equiposActivos = new RegexTextBox(RegularExpressionConstants.numeros);
		equiposActivos.setMaxLength(8);
		
		imei = new RegexTextBox(RegularExpressionConstants.getNumerosLimitado(15));
		imei.setMaxLength(15);
		verificarImeiWrapper = new HTML();
		verificarImeiWrapper.setHTML(IconFactory.comprobarNegro(Sfa.constant().verificarImei()).toString());
		verificarImeiWrapper.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				if (imei.getText().length() != 15) {
					ErrorDialog.getInstance().show(
							Sfa.constant().ERR_LENGHT().replaceAll(v1, "IMEI").replaceAll(v2, "15"), false);
				} else {
					
					SolicitudRpcService.Util.getInstance().verificarNegativeFiles(imei.getText(), new DefaultWaitCallback<String>() {
						public void success(String result) {
							if (result != null) {
								ErrorDialog.getInstance().show(result, false);
								verificarImeiWrapper.setHTML(IconFactory
										.comprobarRojo(Sfa.constant().verificarImei()).toString());
								findImei.setValue(false);
							} else {
								findImei.setValue(true);
								verificarImeiWrapper.setHTML(IconFactory.comprobarVerde(
										Sfa.constant().verificarImei()).toString());
							}
						}
					});
				}
				
			}
		});
				
		otras.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (otras.getText().length() > MAX_LENGHT_100) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					otras.setText(otras.getText().substring(0, MAX_LENGHT_100));
				}
			}
		});
		
		validFirma.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (validFirma.getText().length() > MAX_LENGHT_200) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					validFirma.setText(validFirma.getText().substring(0, MAX_LENGHT_200));
				}
			}
		});
		
		depGarantia.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (depGarantia.getText().length() > MAX_LENGHT_100) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					depGarantia.setText(depGarantia.getText().substring(0, MAX_LENGHT_100));
				}
			}
		});
		
		anticipo.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (anticipo.getText().length() > MAX_LENGHT_100) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					anticipo.setText(anticipo.getText().substring(0, MAX_LENGHT_100));
				}
			}
		});
		
		comentAnalista.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent arg0) {
				if (comentAnalista.getText().length() > MAX_LENGHT_240) {
					ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
					ErrorDialog.getInstance().show(Sfa.constant().ERR_MAX_LARGO_CAMPO(), false);
					comentAnalista.setText(comentAnalista.getText().substring(0, MAX_LENGHT_240));
				}
			}
		});
		
		scoring.setEnabled(false);
	}
	
	
	public void setDatosCaratula(CaratulaDto caratulaDto){
		if(caratulaDto == null){
			this.caratula = new CaratulaDto();
		}else{
			this.caratula = caratulaDto;
			nroSS.setText(caratulaDto.getNroSS());
			//fechaInicio.setText(DateTimeFormat.getMediumDateFormat().format(caratulaDto.getFechaCreacion()));
			fechaInicio.setSelectedDate(caratulaDto.getFechaInicio());
			actividad.setText(caratulaDto.getActividad());
			
			if(caratulaDto.getValidDomicilio() != null){
				validDomicilio.setSelectedItem(caratulaDto.getValidDomicilio());
			}else{
				validDomicilio.setSelectedIndex(0);
			}
			
			if(caratulaDto.getBanco() != null){
				banco.setSelectedItem(caratulaDto.getBanco());
			}else{
				banco.setSelectedIndex(0);
			}
				
			refBancaria.setText(caratulaDto.getRefBancarias());
			
			if(caratulaDto.getTipoCtaBancaria() != null){
				tipoCuenta.setSelectedItem(caratulaDto.getTipoCtaBancaria());
			}else{
				tipoCuenta.setSelectedIndex(0);
			}
			
			if(caratulaDto.getMayorSaldoFavor() != null){
				mayorSaldoFavor.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getMayorSaldoFavor()));
			}
			else{
				mayorSaldoFavor.setText(null);
			}
			
			if(caratulaDto.getIngPromedio() != null){
				ingPromedio.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getIngPromedio()));
			}else{
				ingPromedio.setText(null);
			}
			
			if(caratulaDto.getTipoTarjCred() != null){
				tarjCredito.setSelectedItem(caratulaDto.getTipoTarjCred());
			}else{
				tarjCredito.setSelectedIndex(0);
			}
						
			if(caratulaDto.getLimiteTarjCred() != null) {
				limiteTarj.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getLimiteTarjCred()));
			}else{
				limiteTarj.setText(null);
			}
			
			ingDemostrado.setValue(caratulaDto.isIngresoDemostrado());
			iva.setText(caratulaDto.getIva());
			ganancias.setText(caratulaDto.getGanancia());
			balance.setText(caratulaDto.getBalance());
			ingBrutos.setText(caratulaDto.getIngBrutos());
			reciboHaberes.setText(caratulaDto.getReciboHaberes());
			
			if(caratulaDto.getCalifCred() != null){
				pyp.setSelectedItem(caratulaDto.getCalifCred());
			}else{
				pyp.setSelectedIndex(0);
			}
			
			factCelular.setText(caratulaDto.getFactCelular());
			okEECCAgente.setValue(caratulaDto.isOkEECCAgente());
			
			if(caratulaDto.getCom() != null){
				com.setSelectedItem(caratulaDto.getCom());
			}else{
				com.setSelectedIndex(0);
			}
			
			if(caratulaDto.getAntiguedad() != null){
				antiguedad.setText(DateTimeFormat.getMediumDateFormat().format(caratulaDto.getAntiguedad()));
			}else{
				antiguedad.setText(null);
			}
			
			if(caratulaDto.getLimiteCredito() != null){
				limiteCred.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getLimiteCredito()));
			}else{
				limiteCred.setText(null);
			}
				
			if(caratulaDto.getCalificacion() != null){
				calificacion.setSelectedItem(caratulaDto.getCalificacion());
			}else{
				calificacion.setSelectedIndex(0);
			}
			
			if(caratulaDto.getRiskCode() != null){
				riskCode.setSelectedItem(caratulaDto.getRiskCode());
			}else{
				riskCode.setSelectedIndex(0);
			}
			
			if(caratulaDto.getCompPago() != null){
				comprPago.setSelectedItem(caratulaDto.getCompPago());
			}else{
				comprPago.setSelectedIndex(0);
			}
			
			if(caratulaDto.getFirmante() != null){
				firmante.setSelectedItem(caratulaDto.getFirmante());
			}else{
				firmante.setSelectedIndex(0);
			}
			
			if(caratulaDto.getConsumoProm() != null){
				consumoProm.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getConsumoProm()));
			}else{
				consumoProm.setText(null);
			}
			
			equiposActivos.setText(String.valueOf(caratulaDto.getEquiposActivos()));

			if(caratulaDto.getVeraz() != null){
				veraz.setSelectedItem(caratulaDto.getVeraz());
			}else{
				veraz.setSelectedIndex(0);
			}
			
			if(caratulaDto.getNosis() != null){
				nosis.setSelectedItem(caratulaDto.getNosis());
			}else{
				nosis.setSelectedIndex(0);
			}
			
			if(caratulaDto.getEstadoCredBC() != null){
				bcra.setSelectedItem(caratulaDto.getEstadoCredBC());
			}else{
				bcra.setSelectedIndex(0);
			}
			
			otras.setText(caratulaDto.getOtras());
			validFirma.setText(caratulaDto.getValidFirma());
			depGarantia.setText(caratulaDto.getDepositoGarantia());
			anticipo.setText(caratulaDto.getAnticipo());
			soloHibridos.setValue(caratulaDto.isSoloHibridos());
			imei.setText(caratulaDto.getNumeroIMEI());
			verificarImeiWrapper.setHTML(IconFactory.comprobarNegro(Sfa.constant().verificarImei()).toString());
			findImei.setValue(caratulaDto.isFindIMEI());
			comentAnalista.setText(caratulaDto.getComentarioAnalista());
			scoring.setText(caratulaDto.getScoring());
			//LF
			CaratulaUI.getInstance().setEstadoVeraz(caratulaDto.getEstadoVeraz());
	//		mostrarOcultarCampos();
		}
	}
	
	public CaratulaDto getDatosCaratula(){
//		CaratulaDto caratula = new CaratulaDto();
		
		caratula.setNroSS(nroSS.getText());
		caratula.setFechaInicio(fechaInicio.getSelectedDate());
		caratula.setActividad(actividad.getText());
		caratula.setValidDomicilio((ValidacionDomicilioDto) validDomicilio.getSelectedItem());
		
		BancoDto bancoSelected = (BancoDto) banco.getSelectedItem();
		caratula.setBanco(bancoSelected);
		if(bancoSelected != null){
			caratula.setRefBancarias(refBancaria.getText());
			caratula.setTipoCtaBancaria((TipoCuentaBancariaDto) tipoCuenta.getSelectedItem());
			
			if(mayorSaldoFavor.getText() != null && !mayorSaldoFavor.getText().equals("")){
				caratula.setMayorSaldoFavor(NumberFormat.getDecimalFormat().parse(mayorSaldoFavor.getText()));
			}else{
				caratula.setMayorSaldoFavor(null);
			}
			
			if(ingPromedio.getText() != null && !ingPromedio.getText().equals("")){
				caratula.setIngPromedio(NumberFormat.getDecimalFormat().parse(ingPromedio.getText()));
			}else{
				caratula.setIngPromedio(null);
			}
			
		}else{
			caratula.setRefBancarias(null);
			caratula.setTipoCtaBancaria(null);
			caratula.setMayorSaldoFavor(null);
			caratula.setIngPromedio(null);
		}
		
		caratula.setTipoTarjCred((TipoTarjetaDto) tarjCredito.getSelectedItem());
		
		if(limiteTarj.getText() != null && !limiteTarj.getText().equals("")){
			caratula.setLimiteTarjCred(NumberFormat.getDecimalFormat().parse(limiteTarj.getText()));
		}else{
			caratula.setLimiteTarjCred(null);
		}
		
		boolean ingDemostrados = ingDemostrado.getValue();
		caratula.setIngresoDemostrado(ingDemostrados);
		if(ingDemostrados){
			caratula.setIva(iva.getText());
			caratula.setGanancia(ganancias.getText());
			caratula.setBalance(balance.getText());
			caratula.setIngBrutos(ingBrutos.getText());
			caratula.setReciboHaberes(reciboHaberes.getText());
			caratula.setCalifCred((CalifCrediticiaDto) pyp.getSelectedItem());
			caratula.setFactCelular(factCelular.getText());
		}else{
			caratula.setIva(null);
			caratula.setGanancia(null);
			caratula.setBalance(null);
			caratula.setIngBrutos(null);
			caratula.setReciboHaberes(null);
			caratula.setCalifCred(null);
			caratula.setFactCelular(null);
		}
		
		caratula.setOkEECCAgente(okEECCAgente.getValue());
		caratula.setCom((ComDto) com.getSelectedItem());
		if(limiteCred.getText() != null && !limiteCred.getText().equals("")){
			caratula.setLimiteCredito(NumberFormat.getDecimalFormat().parse(limiteCred.getText()));
		}else{
			caratula.setLimiteCredito(null);
		}
		caratula.setCalificacion((CalificacionDto) calificacion.getSelectedItem());
		caratula.setRiskCode((RiskCodeDto) riskCode.getSelectedItem());
		caratula.setCompPago((CompPagoDto) comprPago.getSelectedItem());
		caratula.setFirmante((FirmanteDto) firmante.getSelectedItem());
		if(equiposActivos.getText() != null && !equiposActivos.getText().equals("")){
			caratula.setEquiposActivos(Long.parseLong(equiposActivos.getText()));
		}else{
			caratula.setEquiposActivos(0);
		}
		caratula.setVeraz((VerazNosisDto) veraz.getSelectedItem());
		caratula.setNosis((VerazNosisDto) nosis.getSelectedItem());
		caratula.setEstadoCredBC((EstadoCreditBancoCentralDto) bcra.getSelectedItem());
		caratula.setOtras(otras.getText());
		caratula.setValidFirma(validFirma.getText());
		caratula.setDepositoGarantia(depGarantia.getText());
		caratula.setAnticipo(anticipo.getText());
		caratula.setSoloHibridos(soloHibridos.getValue());
		caratula.setNumeroIMEI(imei.getText());
		caratula.setFindIMEI(findImei.getValue());
		caratula.setComentarioAnalista(comentAnalista.getText());
		
		//LF
		caratula.setEstadoVeraz(estadoVeraz.getText());

		return caratula;
	}
	
	public List<String> validarCamposObligatorios(int row){
		
		GwtValidator validator = new GwtValidator();
		validator.addTarget(limiteCred).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().limCredito()));
		//Rango de Limite de crÃ©dito
		try{
			auxLimCred.setText(String.valueOf(NumberFormat.getDecimalFormat().parse(limiteCred.getText())));
			validator.addTarget(auxLimCred).greaterOrEqual(Sfa.constant().ERR_RANGO_LIM_CREDITO(), 75.0);
			validator.addTarget(auxLimCred).smallerOrEqual(Sfa.constant().ERR_RANGO_LIM_CREDITO(), 99999.0);
		}catch (Exception e) {
			auxLimCred.setText(null);
		}

			validator.addTarget(calificacion).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().calificacion()));
			validator.addTarget(riskCode).required(
				Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().riskCode()));
			
		if(row > 1){
			validator.addTarget(comprPago).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().compPago()));
			validator.addTarget(firmante).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().firmante()));
			validator.addTarget(equiposActivos).required(
					Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().equiposActivos()));
		}
		
		if(banco.getSelectedItem() != null){
			
			if(isComplete(refBancaria) || isComplete(tipoCuenta) || isComplete(mayorSaldoFavor) || isComplete(ingPromedio)){				
				validator.addTarget(refBancaria).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().refBancaria()));
				validator.addTarget(tipoCuenta).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().tipoCuenta()));
				validator.addTarget(mayorSaldoFavor).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().mayorSaldo()));
				validator.addTarget(ingPromedio).required(
						Sfa.constant().ERR_CAMPO_OBLIGATORIO().replaceAll(v1, Sfa.constant().ingresoProm()));
			}
			
		}
		
		HashMap<String, Long> instancias = ClientContext.getInstance().getKnownInstance();
		boolean okEECCChecked = okEECCAgente.getValue();
		RiskCodeDto riskCodeSelected = (RiskCodeDto) riskCode.getSelectedItem();
				
		if(okEECCChecked && (riskCodeSelected == null || 
				!instancias.get(RISK_CODE_EECC_AGENTE).equals(riskCodeSelected.getId()))){
			validator.addError(Sfa.constant().ERR_RISK_CODE_NO_EECC_AGENTE());
		
		}else if(riskCodeSelected != null && instancias.get(RISK_CODE_EECC_AGENTE).equals(riskCodeSelected.getId())
				&& !okEECCChecked){
			validator.addError(Sfa.constant().ERR_EECC_AGENTE_DEBE_SELECCIONARSE());
		}
		
		return validator.fillResult().getErrors();
	}
	
	/**
	 * Este metodo se utiliza para recorrer los elementos del riskCode y compararlo 
	 * con el valor pasado por parametro, si es igual devuelve el id para luego seleccionarlo.
	 * @param valor
	 * @return Long, id del item
	 */
	public Long getKeyRiskCode(String valor) {
		Iterator it = riskCode.getItems().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			RiskCodeDto listBox = (RiskCodeDto)entry.getValue();
			if(listBox.getDescripcion().equals(valor)) {
				return listBox.getId();
			}
		}
		return null;
	}
	
	/**
	 * Este metodo se utiliza para recorrer los elementos de calificacion y compararlo 
	 * con el valor pasado por parametro, si es igual devuelve el id para luego seleccionarlo.
	 * @param valor
	 * @return Long, id del item
	 */
	public Long getKeyCalificacion(String valor) {
		Iterator it = calificacion.getItems().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			CalificacionDto listBox = (CalificacionDto)entry.getValue();
			if(listBox.getDescripcion().equals(valor)) {
				return listBox.getId();
			}
		}
		return null;
	}
		
	private boolean isComplete(Object obj){
		
	    if (obj == null) {
		      return false;
		}
	    if (obj instanceof TextArea) {
			TextArea area = (TextArea) obj;
			if(!area.getText().equals("") && area.getText() != null){
				return true;
			}else{
				return false;
			}	
	    }
		if (obj instanceof TextBox) {
			TextBox text = (TextBox) obj;
				if(!text.getText().equals("") && text.getText() != null){
					return true;
				}else{
					return false;
				}			
		}
		if (obj instanceof ListBox) {
			ListBox list = (ListBox) obj;
			if(list.getSelectedItem() != null){
				return true;
			}else{
				return false;
			}	
		}
		return false;	
	}
	
	
	public void habilitarCampos(boolean habilitar) {
		banco.setEnabled(habilitar);
		limiteCred.setEnabled(habilitar);
		firmante.setEnabled(habilitar);
		nroSS.setEnabled(habilitar);
		
		fechaInicio.setEnabled(habilitar);
		actividad.setEnabled(habilitar);
		//(TextArea).setVisible(true), para que se pueda scrollear.
		actividad.setVisible(true);
		validDomicilio.setEnabled(habilitar);
		banco.setEnabled(habilitar);
		refBancaria.setEnabled(habilitar);
		refBancaria.setVisible(true);
		tipoCuenta.setEnabled(habilitar);
		mayorSaldoFavor.setEnabled(habilitar);
		ingPromedio.setEnabled(habilitar);
		tarjCredito.setEnabled(habilitar);
		limiteTarj.setEnabled(habilitar);
		ingDemostrado.setEnabled(habilitar);
		iva.setEnabled(habilitar);
		iva.setVisible(true);
		ganancias.setEnabled(habilitar);
		ganancias.setVisible(true);
		balance.setEnabled(habilitar);
		balance.setVisible(true);
		ingBrutos.setEnabled(habilitar);
		ingBrutos.setVisible(true);
		reciboHaberes.setEnabled(habilitar);
		reciboHaberes.setVisible(true);
		pyp.setEnabled(habilitar);
		factCelular.setEnabled(habilitar);
		factCelular.setVisible(true);
		okEECCAgente.setEnabled(habilitar);
		com.setEnabled(habilitar);
		limiteCred.setEnabled(habilitar);
		calificacion.setEnabled(habilitar);
		riskCode.setEnabled(habilitar);
		comprPago.setEnabled(habilitar);
		firmante.setEnabled(habilitar);
		equiposActivos.setEnabled(habilitar);
		imei.setEnabled(habilitar);
		verificarImeiWrapper.setVisible(habilitar);
		veraz.setEnabled(habilitar);
		nosis.setEnabled(habilitar);
		bcra.setEnabled(habilitar);
		otras.setEnabled(habilitar);
		otras.setVisible(true);
		validFirma.setEnabled(habilitar);
		validFirma.setVisible(true);
		depGarantia.setEnabled(habilitar);
		depGarantia.setVisible(true);
		anticipo.setEnabled(habilitar);
		anticipo.setVisible(true);
		soloHibridos.setEnabled(habilitar);
		findImei.setEnabled(habilitar);
		comentAnalista.setEnabled(habilitar);
		comentAnalista.setVisible(true);
		scoring.setVisible(true);
	}
	//*************************//
	public TextBox getNroSS() {
		return nroSS;
	}

	public void setNroSS(TextBox nroSS) {
		this.nroSS = nroSS;
	}

	public Widget getWidgetFechaInicio() {
		Grid datePickerFull = new Grid(1, 2);
		fechaInicio.setWeekendSelectable(true);
		fechaInicio.setSelectedDate(DateUtil.today());
		fechaInicio.getTextBox().setText(dateFormatter.format(DateUtil.today()));
		datePickerFull.setWidget(0, 0, fechaInicio.getTextBox());
		datePickerFull.setWidget(0, 1, fechaInicio);
		return datePickerFull;
	}
	
	
	/**
	 * Si el domicilio de facturacion y el domicilio de entrega asociado con el numero de solicitud de servicio es validado, 
	 * en el combo Validacion domicilio se selecciona "Validado por EECC”. 
	 * @param nro_ss (Numero de solicitud de servicio).
	 * @author fernaluc
	 */
	public void validarDomicilio(String nro_ss) {
		CuentaRpcService.Util.getInstance().isDomicilioValidadoPorEECC(nro_ss, new DefaultWaitCallback<Boolean>() {
			
			@Override
			public void success(Boolean result) {
				if(result.booleanValue()){
					validDomicilio.setSelectedIndex(2);
				} else {
					validDomicilio.setSelectedIndex(0);
				}
			}
		});
		
	}
	
	public SimpleDatePicker getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(SimpleDatePicker fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public TextArea getActividad() {
		return actividad;
	}

	public void setActividad(TextArea actividad) {
		this.actividad = actividad;
	}

	public ListBox getValidDomicilio() {
		return validDomicilio;
	}

	public void setValidDomicilio(ListBox validDomicilio) {
		this.validDomicilio = validDomicilio;
	}

	public ListBox getBanco() {
		return banco;
	}

	public void setBanco(ListBox banco) {
		this.banco = banco;
	}

//	public TextBox getRefBancaria() {
//		return refBancaria;
//	}
//
//	public void setRefBancaria(TextBox refBancaria) {
//		this.refBancaria = refBancaria;
//	}

	public TextArea getRefBancaria() {
		return refBancaria;
	}

	public void setRefBancaria(TextArea refBancaria) {
		this.refBancaria = refBancaria;
	}
	
	public ListBox getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(ListBox tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public TextBox getMayorSaldoFavor() {
		return mayorSaldoFavor;
	}

	public void setMayorSaldoFavor(TextBox mayorSaldoFavor) {
		this.mayorSaldoFavor = mayorSaldoFavor;
	}

	public TextBox getIngPromedio() {
		return ingPromedio;
	}

	public void setIngPromedio(TextBox ingPromedio) {
		this.ingPromedio = ingPromedio;
	}

	public ListBox getTarjCredito() {
		return tarjCredito;
	}

	public void setTarjCredito(ListBox tarjCredito) {
		this.tarjCredito = tarjCredito;
	}

	public TextBox getLimiteTarj() {
		return limiteTarj;
	}

	public void setLimiteTarj(TextBox limiteTarj) {
		this.limiteTarj = limiteTarj;
	}

	public CheckBox getIngDemostrado() {
		return ingDemostrado;
	}

	public void setIngDemostrado(CheckBox ingDemostrado) {
		this.ingDemostrado = ingDemostrado;
	}

	public TextArea getIva() {
		return iva;
	}

	public void setIva(TextArea iva) {
		this.iva = iva;
	}

	public TextArea getGanancias() {
		return ganancias;
	}

	public void setGanancias(TextArea ganancias) {
		this.ganancias = ganancias;
	}

	public TextArea getBalance() {
		return balance;
	}

	public void setBalance(TextArea balance) {
		this.balance = balance;
	}

	public TextArea getIngBrutos() {
		return ingBrutos;
	}

	public void setIngBrutos(TextArea ingBrutos) {
		this.ingBrutos = ingBrutos;
	}

	public TextArea getReciboHaberes() {
		return reciboHaberes;
	}

	public void setReciboHaberes(TextArea reciboHaberes) {
		this.reciboHaberes = reciboHaberes;
	}

	public ListBox getPyp() {
		return pyp;
	}

	public void setPyp(ListBox pyp) {
		this.pyp = pyp;
	}

	public TextArea getFactCelular() {
		return factCelular;
	}

	public void setFactCelular(TextArea factCelular) {
		this.factCelular = factCelular;
	}

	public CheckBox getOkEECCAgente() {
		return okEECCAgente;
	}

	public void setOkEECCAgente(CheckBox okEECCAgente) {
		this.okEECCAgente = okEECCAgente;
	}

	public ListBox getCom() {
		return com;
	}

	public void setCom(ListBox com) {
		this.com = com;
	}

	public TextBox getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(TextBox antiguedad) {
		this.antiguedad = antiguedad;
	}

	public TextBox getLimiteCred() {
		return limiteCred;
	}

	public void setLimiteCred(TextBox limiteCred) {
		this.limiteCred = limiteCred;
	}

	public ListBox getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(ListBox calificacion) {
		this.calificacion = calificacion;
	}

	public ListBox getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(ListBox riskCode) {
		this.riskCode = riskCode;
	}

	public ListBox getComprPago() {
		return comprPago;
	}

	public void setComprPago(ListBox comprPago) {
		this.comprPago = comprPago;
	}

	public ListBox getFirmante() {
		return firmante;
	}

	public void setFirmante(ListBox firmante) {
		this.firmante = firmante;
	}

	public TextBox getConsumoProm() {
		return consumoProm;
	}

	public void setConsumoProm(TextBox consumoProm) {
		this.consumoProm = consumoProm;
	}

	public TextBox getEquiposActivos() {
		return equiposActivos;
	}

	public void setEquiposActivos(TextBox equiposActivos) {
		this.equiposActivos = equiposActivos;
	}

	public ListBox getVeraz() {
		return veraz;
	}

	public void setVeraz(ListBox veraz) {
		this.veraz = veraz;
	}

	public ListBox getNosis() {
		return nosis;
	}

	public void setNosis(ListBox nosis) {
		this.nosis = nosis;
	}

	public ListBox getBcra() {
		return bcra;
	}

	public void setBcra(ListBox bcra) {
		this.bcra = bcra;
	}

	public TextArea getOtras() {
		return otras;
	}

	public void setOtras(TextArea otras) {
		this.otras = otras;
	}

	public TextArea getValidFirma() {
		return validFirma;
	}

	public void setValidFirma(TextArea validFirma) {
		this.validFirma = validFirma;
	}

	public TextArea getDepGarantia() {
		return depGarantia;
	}

	public void setDepGarantia(TextArea depGarantia) {
		this.depGarantia = depGarantia;
	}

	public TextArea getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(TextArea anticipo) {
		this.anticipo = anticipo;
	}

	public CheckBox getSoloHibridos() {
		return soloHibridos;
	}

	public void setSoloHibridos(CheckBox soloHibridos) {
		this.soloHibridos = soloHibridos;
	}

	public CheckBox getFindImei() {
		return findImei;
	}

	public void setFindImei(CheckBox findImei) {
		this.findImei = findImei;
	}

	public TextArea getComentAnalista() {
		return comentAnalista;
	}

	public void setComentAnalista(TextArea comentAnalista) {
		this.comentAnalista = comentAnalista;
	}

	public TextArea getScoring() {
		return scoring;
	}

	public void setScoring(TextArea scoring) {
		this.scoring = scoring;
	}
	
	public TextBox getImei() {
		return imei;
	}
	
	public void setImei(TextBox imei) {
		this.imei = imei;
	}

	public HTML getVerificarImeiWrapper() {
		return verificarImeiWrapper;
	}

	public boolean isCombosCargados() {
		return isCombosCargados;
	}

	public void setCombosCargados(boolean isCombosCargados) {
		this.isCombosCargados = isCombosCargados;
	}

	public Label getEstadoVeraz() {
		return estadoVeraz;
	}

	public void setEstadoVeraz(String estado) {
		this.estadoVeraz = new Label(estado);
	}
	
	
}
