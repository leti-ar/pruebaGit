package ar.com.nextel.sfa.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EquipoDto implements IsSerializable {

	private int id;
	private int tel;
	private String abono;
	private String alquiler;
	private String garantia;
	private String servicio;
	private String reint;
	private int excRadio;
	private int excTel;
	private int red;
	private int DDN;
	private int roam;
	private int pager;
	private int imp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(String alquiler) {
		this.alquiler = alquiler;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getReint() {
		return reint;
	}

	public void setReint(String reint) {
		this.reint = reint;
	}

	public int getExcRadio() {
		return excRadio;
	}

	public void setExcRadio(int excRadio) {
		this.excRadio = excRadio;
	}

	public int getExcTel() {
		return excTel;
	}

	public void setExcTel(int excTel) {
		this.excTel = excTel;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getDDN() {
		return DDN;
	}

	public void setDDN(int ddn) {
		DDN = ddn;
	}

	public int getRoam() {
		return roam;
	}

	public void setRoam(int roam) {
		this.roam = roam;
	}

	public int getPager() {
		return pager;
	}

	public void setPager(int pager) {
		this.pager = pager;
	}

	public int getImp() {
		return imp;
	}

	public void setImp(int imp) {
		this.imp = imp;
	}
}
