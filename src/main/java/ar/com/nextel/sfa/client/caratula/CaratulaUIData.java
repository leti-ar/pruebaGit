package ar.com.nextel.sfa.client.caratula;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CaratulaDto;
import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.initializer.CaratulaInitializer;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author mrotger
 * 
 */
public class CaratulaUIData extends UIData{// implements ChangeListener, ClickListener {

	private TextBox nroSS = new TextBox();
	private TextBox fechaInicio = new TextBox();
	private TextArea actividad = new TextArea();
	private ListBox validDomicilio = new ListBox();
	private ListBox banco = new ListBox();
	private TextBox refBancaria = new TextBox();
	private ListBox tipoCuenta = new ListBox();
	private TextBox mayorSaldoFavor = new TextBox();
	private TextBox ingPromedio = new TextBox();
	private ListBox tarjCredito = new ListBox();
	private TextBox limiteTarj = new TextBox();
	private CheckBox ingDemostrado = new CheckBox();
	private TextArea iva = new TextArea();
	private TextArea ganancias = new TextArea();
	private TextArea balance = new TextArea();
	private TextArea ingBrutos = new TextArea();
	private TextArea reciboHaberes = new TextArea();
	private ListBox pyp = new ListBox();
	private TextArea factCelular = new TextArea();
	private CheckBox okEECCAgente = new CheckBox();
	private ListBox com = new ListBox();
	private TextBox antiguedad = new TextBox();
	private TextBox limiteCred = new TextBox();
	private ListBox calificacion = new ListBox();
	private ListBox riskCode = new ListBox();
	private ListBox comprPago  = new ListBox();
	private ListBox cargo = new ListBox();
	private TextArea consumoProm = new TextArea();
	private TextArea equiposActivos = new TextArea();
	private ListBox veraz = new ListBox();
	private ListBox nosis = new ListBox();
	private ListBox bcra = new ListBox();
	private TextArea otras = new TextArea();
	private TextArea validFirma = new TextArea();
	private TextArea depGarantia = new TextArea();
	private TextArea anticipo = new TextArea();
	private CheckBox soloHibridos = new CheckBox();
	private CheckBox findImei = new CheckBox();
	private TextArea comentAnalista = new TextArea();
	private TextArea scoring = new TextArea();
	//MGR**** Adm_Vtas R2
	//TODO Poner los validadores

	public CaratulaUIData(){
		cargarCombos();
		setLargoCampos();
		//fields.add(cpa);
		//camposModificables.add(piso);
		//this.addFocusListeners(fields);
//		int i = 1;
//		for (Widget field : fields) {
//			((FocusWidget) field).setTabIndex(i++);
//		}
	}
	
	private void cargarCombos() {
		System.out.println("Hola");
		CuentaRpcService.Util.getInstance().getCaratulaInicializarte(
				new DefaultWaitCallback<CaratulaInitializer>() {
			
			@Override
			public void success(CaratulaInitializer initializer) {
				getValidDomicilio().addAllItems(initializer.getValidDomicilio());
				
			}
		});
		
	}
	
	private void setLargoCampos(){
		nroSS.setMaxLength(50);
		//MGR**** Adm_Vtas R2
		//TODO Terminar
	}
	
	
	public void setCaratula(CaratulaDto caratulaDto){
//		if (domicilio == null) {
//			this.domicilio = new DomiciliosCuentaDto();
//		} else {
//			this.domicilio = domicilio;
		nroSS.setText(caratulaDto.getNroSS());
		//MGR**** Adm_Vtas R2
		//TODO Terminar
	}
	
	public CaratulaDto getCaratula(){
		CaratulaDto caratula = new CaratulaDto();
		caratula.setNroSS(nroSS.getText());
		//MGR**** Adm_Vtas R2
		//TODO Terminar
		return caratula;
	}
	//*************************//
	public TextBox getNroSS() {
		return nroSS;
	}

	public void setNroSS(TextBox nroSS) {
		this.nroSS = nroSS;
	}

	public TextBox getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(TextBox fechaInicio) {
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

	public TextArea getConsumoProm() {
		return consumoProm;
	}

	public void setConsumoProm(TextArea consumoProm) {
		this.consumoProm = consumoProm;
	}

	public TextArea getEquiposActivos() {
		return equiposActivos;
	}

	public void setEquiposActivos(TextArea equiposActivos) {
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
}
