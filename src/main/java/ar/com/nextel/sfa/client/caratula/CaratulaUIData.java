package ar.com.nextel.sfa.client.caratula;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.BancoDto;
import ar.com.nextel.sfa.client.dto.CalifCrediticiaDto;
import ar.com.nextel.sfa.client.dto.CalificacionDto;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.CargoDto;
import ar.com.nextel.sfa.client.dto.ComDto;
import ar.com.nextel.sfa.client.dto.CompPagoDto;
import ar.com.nextel.sfa.client.dto.EstadoCreditBancoCentralDto;
import ar.com.nextel.sfa.client.dto.RiskCodeDto;
import ar.com.nextel.sfa.client.dto.TipoCuentaBancariaDto;
import ar.com.nextel.sfa.client.dto.TipoTarjetaDto;
import ar.com.nextel.sfa.client.dto.ValidacionDomicilioDto;
import ar.com.nextel.sfa.client.dto.VerazNosisDto;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrotger
 * 
 */
public class CaratulaUIData extends UIData{// implements ChangeListener, ClickListener {

	private static final int MAX_LENGHT_100 = 100;
	private static final int MAX_LENGHT_200 = 200;
	private static final int MAX_LENGHT_240 = 240;
	private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy");
	
	private TextBox nroSS;
	private SimpleDatePicker fechaInicio;
	private TextArea actividad = new TextArea();
	private ListBox validDomicilio = new ListBox("");
	private ListBox banco = new ListBox("");
	private TextBox refBancaria;
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
	private ListBox calificacion = new ListBox("");
	private ListBox riskCode = new ListBox("");
	private ListBox comprPago  = new ListBox("");
	private ListBox cargo = new ListBox("");
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
	private TextBox scoring;
	//MGR**** Adm_Vtas R2
	//TODO Poner los validadores

	public CaratulaUIData(){
		setPropiedadesCampos();
		cargarCombos();
		
//		banco.addChangeListener(this);
//		ingDemostrado.addClickListener(this);
		//fields.add(cpa);
		//camposModificables.add(piso);
		//this.addFocusListeners(fields);
//		int i = 1;
//		for (Widget field : fields) {
//			((FocusWidget) field).setTabIndex(i++);
//		}
	}
	
	private void cargarCombos() {
		CuentaRpcService.Util.getInstance().getCaratulaInicializarte(
				new DefaultWaitCallback<CaratulaInitializer>() {
			
			@Override
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
				cargo.addAllItems(initializer.getCargo());
				veraz.addAllItems(initializer.getVerazNosis());
				nosis.addAllItems(initializer.getVerazNosis());
				bcra.addAllItems(initializer.getEstadoCredBC());
				
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
		
		refBancaria = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(100));
		mayorSaldoFavor = new RegexTextBox(RegularExpressionConstants.decimal);
		mayorSaldoFavor.setMaxLength(15);
		ingPromedio = new RegexTextBox(RegularExpressionConstants.decimal);
		ingPromedio.setMaxLength(15);
		limiteTarj = new RegexTextBox(RegularExpressionConstants.decimal);
		limiteTarj.setMaxLength(15);
		
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
		limiteCred = new RegexTextBox(RegularExpressionConstants.decimal);
		limiteCred.setMaxLength(15);
		
		consumoProm = new RegexTextBox(RegularExpressionConstants.decimal);
		consumoProm.setEnabled(false);
		
		equiposActivos = new RegexTextBox(RegularExpressionConstants.numeros);
		equiposActivos.setMaxLength(8);
		
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
		
		scoring = new RegexTextBox(RegularExpressionConstants.getNumerosYLetrasLimitado(1000));
		scoring.setEnabled(false);
	}
	
	
	public void setDatosCaratula(CaratulaDto caratulaDto){
		nroSS.setText(caratulaDto.getNroSS());
		//fechaInicio.setText(DateTimeFormat.getMediumDateFormat().format(caratulaDto.getFechaCreacion()));
		fechaInicio.setSelectedDate(caratulaDto.getFechaCreacion());
		actividad.setText(caratulaDto.getActividad());
		validDomicilio.setSelectedItem(caratulaDto.getValidDomicilio());
		banco.setSelectedItem(caratulaDto.getBanco());
		refBancaria.setText(caratulaDto.getRefBancarias());
		tipoCuenta.setSelectedItem(caratulaDto.getTipoCtaBancaria());
		if(caratulaDto.getMayorSaldoFavor() != null){
			mayorSaldoFavor.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getMayorSaldoFavor()));
		}
		if(caratulaDto.getIngPromedio() != null){
			ingPromedio.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getIngPromedio()));
		}
		tarjCredito.setSelectedItem(caratulaDto.getTipoTarjCred());
		if(caratulaDto.getLimiteCredito() != null){
			limiteCred.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getLimiteCredito()));
		}
		ingDemostrado.setValue(caratulaDto.isIngresoDemostrado());
		iva.setText(caratulaDto.getIva());
		ganancias.setText(caratulaDto.getGanancia());
		balance.setText(caratulaDto.getBalance());
		ingBrutos.setText(caratulaDto.getIngBrutos());
		reciboHaberes.setText(caratulaDto.getReciboHaberes());
		pyp.setSelectedItem(caratulaDto.getCalifCred());
		factCelular.setText(caratulaDto.getFactCelular());
		okEECCAgente.setValue(caratulaDto.isOkEECCAgente());
		com.setSelectedItem(caratulaDto.getCom());
		if(caratulaDto.getAntiguedad() != null){
			antiguedad.setText(DateTimeFormat.getMediumDateFormat().format(caratulaDto.getAntiguedad()));
		}
		if(caratulaDto.getLimiteCredito() != null){
			limiteCred.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getLimiteCredito()));
		}
		calificacion.setSelectedItem(caratulaDto.getCalificacion());
		riskCode.setSelectedItem(caratulaDto.getRiskCode());
		comprPago.setSelectedItem(caratulaDto.getCompPago());
		cargo.setSelectedItem(caratulaDto.getCargo());
		if(caratulaDto.getConsumoProm() != null){
			consumoProm.setText(NumberFormat.getDecimalFormat().format(caratulaDto.getConsumoProm()));
		}
		equiposActivos.setText(String.valueOf(caratulaDto.getEquiposActivos()));
		veraz.setSelectedItem(caratulaDto.getVeraz());
		nosis.setSelectedItem(caratulaDto.getNosis());
		bcra.setSelectedItem(caratulaDto.getEstadoCredBC());
		otras.setText(caratulaDto.getOtras());
		validFirma.setText(caratulaDto.getValidFirma());
		depGarantia.setText(caratulaDto.getDepositoGarantia());
		anticipo.setText(caratulaDto.getAnticipo());
		soloHibridos.setValue(caratulaDto.isSoloHibridos());
		findImei.setValue(caratulaDto.isFindIMEI());
		comentAnalista.setText(caratulaDto.getComentarioAnalista());
		scoring.setText(caratulaDto.getScoring());
		
//		mostrarOcultarCampos();
	}
	
	public CaratulaDto getDatosCaratula(){
		CaratulaDto caratula = new CaratulaDto();
		
		caratula.setNroSS(nroSS.getText());
		caratula.setFechaInicio(fechaInicio.getSelectedDate());
		caratula.setActividad(actividad.getText());
		caratula.setValidDomicilio((ValidacionDomicilioDto) validDomicilio.getSelectedItem());
		caratula.setBanco((BancoDto) banco.getSelectedItem());
		caratula.setRefBancarias(refBancaria.getText());
		caratula.setTipoCtaBancaria((TipoCuentaBancariaDto) tipoCuenta.getSelectedItem());
		caratula.setMayorSaldoFavor(NumberFormat.getDecimalFormat().parse(mayorSaldoFavor.getText()));
		caratula.setIngPromedio(NumberFormat.getDecimalFormat().parse(ingPromedio.getText()));
		caratula.setTipoTarjCred((TipoTarjetaDto) tarjCredito.getSelectedItem());
		caratula.setLimiteTarjCred(NumberFormat.getDecimalFormat().parse(limiteTarj.getText()));
		caratula.setIngresoDemostrado(ingDemostrado.getValue());
		caratula.setIva(iva.getText());
		caratula.setGanancia(ganancias.getText());
		caratula.setBalance(balance.getText());
		caratula.setIngBrutos(ingBrutos.getText());
		caratula.setReciboHaberes(reciboHaberes.getText());
		caratula.setCalifCred((CalifCrediticiaDto) pyp.getSelectedItem());
		caratula.setFactCelular(factCelular.getText());
		caratula.setOkEECCAgente(okEECCAgente.getValue());
		caratula.setCom((ComDto) com.getSelectedItem());
		//MGR**** Adm_Vtas R2 Falta antiguedad
		caratula.setLimiteCredito(NumberFormat.getDecimalFormat().parse(limiteCred.getText()));
		caratula.setCalificacion((CalificacionDto) calificacion.getSelectedItem());
		caratula.setRiskCode((RiskCodeDto) riskCode.getSelectedItem());
		caratula.setCompPago((CompPagoDto) comprPago.getSelectedItem());
		caratula.setCargo((CargoDto) cargo.getSelectedItem());
		//MGR**** Adm_Vtas R2 Falta Consumo Promedio
		caratula.setEquiposActivos(Long.parseLong(equiposActivos.getSelectedText()));
		caratula.setVeraz((VerazNosisDto) veraz.getSelectedItem());
		caratula.setNosis((VerazNosisDto) nosis.getSelectedItem());
		caratula.setEstadoCredBC((EstadoCreditBancoCentralDto) bcra.getSelectedItem());
		caratula.setOtras(otras.getText());
		caratula.setValidFirma(validFirma.getText());
		caratula.setDepositoGarantia(depGarantia.getText());
		caratula.setAnticipo(anticipo.getText());
		caratula.setSoloHibridos(soloHibridos.getValue());
		caratula.setFindIMEI(findImei.getValue());
		caratula.setComentarioAnalista(comentAnalista.getText());
		////MGR**** Adm_Vtas R2 Falta Scoring

		return caratula;
	}
	
	//Logica para hacer visibles o no los campos que correspondan
//	private void mostrarOcultarCampos() {
//		setVisibleCamposBanco();
//		setVisibleCamposIngDemostrados();
//	}
//	
//	private void setVisibleCamposBanco(){
//		if(banco.getSelectedItem() != null){
//			refBancaria.setVisible(true);
//			tipoCuenta.setVisible(true);
//			mayorSaldoFavor.setVisible(true);
//			ingPromedio.setVisible(true);
//		}else{
//			refBancaria.setVisible(false);
//			tipoCuenta.setVisible(false);
//			mayorSaldoFavor.setVisible(false);
//			ingPromedio.setVisible(false);
//		}
//	}
	
//	private void setVisibleCamposIngDemostrados(){
//		if(ingDemostrado.getValue()){
//			iva.setVisible(true);
//			ganancias.setVisible(true);
//			balance.setVisible(true);
//			ingBrutos.setVisible(true);
//			reciboHaberes.setVisible(true);
//			pyp.setVisible(true);
//			factCelular.setVisible(true);
//		}else{
//			iva.setVisible(false);
//			ganancias.setVisible(false);
//			balance.setVisible(false);
//			ingBrutos.setVisible(false);
//			reciboHaberes.setVisible(false);
//			pyp.setVisible(false);
//			factCelular.setVisible(false);
//		}
//	}
	
//	public void onChange(Widget sender) {
//		if(sender == banco){
//			setVisibleCamposBanco();
//		} 
//	}
	
//	public void onClick(Widget sender) {
//		if(sender == ingDemostrado){
//			setVisibleCamposIngDemostrados();
//		}
//	}
	
	private static boolean val = false;
	public boolean validarCamposObligatorios(){
		System.out.println("Validando los campos obligatorios");
		if(!val){
			System.out.println("Validacion fallo!");
			val = true;
			return false;
		}else{
			System.out.println("Validacion OK!");
			val = false;
			return true;
		}
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

	public TextBox getRefBancaria() {
		return refBancaria;
	}

	public void setRefBancaria(TextBox refBancaria) {
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

	public ListBox getCargo() {
		return cargo;
	}

	public void setCargo(ListBox cargo) {
		this.cargo = cargo;
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

	public TextBox getScoring() {
		return scoring;
	}

	public void setScoring(TextBox scoring) {
		this.scoring = scoring;
	}
}
