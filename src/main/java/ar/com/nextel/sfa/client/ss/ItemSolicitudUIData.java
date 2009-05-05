package ar.com.nextel.sfa.client.ss;

import java.util.ArrayList;

import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;
import ar.com.snoop.gwt.commons.client.widget.SimpleLabel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ItemSolicitudUIData extends UIData {

	private ListBox listaPrecio;
	private TextBox cantidad;
	private ListBox tipoOrden;
	private ItemSolicitudGenericPanelUI panelGeneric;
	private FlowPanel alquilerVentaAMBA;
	private FlowPanel ventaAccesorio;
	private FlowPanel activacion;
	private FlowPanel ventaLicencia;
	private ListBox tipoPlan;
	private ListBox plan;
	private SimpleLabel precioLista;
	private ListBox localidad;
	private ListBox ccp;
	private TextBox alias;
	private TextBox reservarHidden;
	private TextBox reservar;
	private CheckBox ddn;
	private CheckBox ddi;
	private CheckBox roaming;
	private	TextBox imei;
	private ListBox modeloEq;
	private ListBox item;
	private ListBox terminoPago;
	private TextBox sim;
	private TextBox serie;
	private TextBox pin;
	private Button confirmarReserva;
	
	public ItemSolicitudUIData(){
		
		fields = new ArrayList<Widget>();
		fields.add(listaPrecio = new ListBox());
		listaPrecio.setWidth("400px");
		fields.add(cantidad = new TextBox());
		fields.add(tipoPlan = new ListBox());
		tipoPlan.setWidth("400px");
		fields.add(plan = new ListBox());
		plan.setWidth("400px");
		fields.add(precioLista = new SimpleLabel());
		fields.add(localidad = new ListBox());
		localidad.setWidth("400px");
		fields.add(ccp = new ListBox());
		fields.add(alias = new TextBox());
		fields.add(reservarHidden = new TextBox());
		fields.add(reservar = new TextBox());
		fields.add(imei = new TextBox());
		fields.add(modeloEq = new ListBox());
		modeloEq.setWidth("400px");
		fields.add(item = new ListBox());
		item.setWidth("400px");
		fields.add(terminoPago = new ListBox());
		terminoPago.setWidth("400px");
		fields.add(sim = new TextBox());
		fields.add(serie = new TextBox());
		fields.add(pin = new TextBox());
		fields.add(confirmarReserva = new Button("Ok"));
		confirmarReserva.addStyleName("btn-bkg");
		fields.add(ddn = new CheckBox());
		fields.add(ddi = new CheckBox());
		fields.add(roaming = new CheckBox());
	}
	
	public ListBox getListaPrecio() {
		return listaPrecio;
	}

	public TextBox getCantidad() {
		return cantidad;
	}


	public ListBox getTipoOrden() {
		return tipoOrden;
	}

	public ItemSolicitudGenericPanelUI getPanelGeneric() {
		return panelGeneric;
	}

	public FlowPanel getAlquilerVentaAMBA() {
		return alquilerVentaAMBA;
	}

	public FlowPanel getVentaAccesorio() {
		return ventaAccesorio;
	}

	public FlowPanel getActivacion() {
		return activacion;
	}

	public FlowPanel getVentaLicencia() {
		return ventaLicencia;
	}

	public ListBox getTipoPlan() {
		return tipoPlan;
	}

	public ListBox getPlan() {
		return plan;
	}

	public SimpleLabel getPrecioLista() {
		return precioLista;
	}

	public ListBox getLocalidad() {
		return localidad;
	}

	public ListBox getCcp() {
		return ccp;
	}

	public TextBox getAlias() {
		return alias;
	}

	public TextBox getReservarHidden() {
		return reservarHidden;
	}

	public TextBox getReservar() {
		return reservar;
	}

	public CheckBox getDdn() {
		return ddn;
	}

	public CheckBox getDdi() {
		return ddi;
	}

	public CheckBox getRoaming() {
		return roaming;
	}

	public TextBox getImei() {
		return imei;
	}

	public ListBox getModeloEq() {
		return modeloEq;
	}

	public ListBox getItem() {
		return item;
	}

	public ListBox getTerminoPago() {
		return terminoPago;
	}

	public TextBox getSim() {
		return sim;
	}

	public TextBox getSerie() {
		return serie;
	}

	public TextBox getPin() {
		return pin;
	}

	public Button getConfirmarReserva() {
		return confirmarReserva;
	}
}
