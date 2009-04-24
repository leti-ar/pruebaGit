package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.widget.UIData;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador 
 **/
public class DomiciliosUIData extends UIData {
	///
	TextBox calle = new TextBox();
	TextBox numero = new TextBox();
	TextBox piso = new TextBox();
	TextBox codigoPostal = new TextBox();
	TextBox departamento = new TextBox();
	TextBox entreCalle = new TextBox();
	TextBox manzana = new TextBox();
	TextBox puerta = new TextBox();
	TextBox ycalle = new TextBox();
	TextBox cpa = new TextBox();
	TextBox torre = new TextBox();
	TextBox unidadFuncional = new TextBox();
	TextArea observaciones = new TextArea();
	TextBox localidad = new TextBox();
	TextBox partido = new TextBox();
	PersonaDto persona = new PersonaDto();

//TODO: Hacer los combos hardcodeados!
	//FacturacionDto facturacion = new FacturacionDto();
	//EntregaDto entrega = new EntregaDto();
	//ProvinciaDto provincia = new ProvinciaDto();
	//EstadoDomicilioDto estado = new EstadoDomicilioDto();	
	//Boolean noNormalizar;
//
	CheckBox validado = new CheckBox();
	TextBox codigoFNCL = new TextBox();
	CheckBox enCarga = new CheckBox();
	TextBox nombreUsuarioUltimaModificacion = new TextBox();
	///
	
	public DomiciliosUIData() {
		fields.add(calle);
		fields.add(numero);
		fields.add(piso);
		fields.add(codigoPostal);
		fields.add(departamento);
		fields.add(entreCalle);
		fields.add(manzana);
		fields.add(puerta);
		fields.add(ycalle);
		fields.add(cpa);
		fields.add(torre);
		fields.add(unidadFuncional);
		fields.add(observaciones);
		fields.add(localidad);
		fields.add(partido);
		fields.add((Widget)validado);
		fields.add(codigoFNCL);
		fields.add(enCarga);
		fields.add(nombreUsuarioUltimaModificacion);
	}

	public TextBox getCalle() {
		calle.setWidth("300px");
		return calle;
	}

	public TextBox getNumero() {
		numero.setWidth("70px");
		return numero;
	}

	public TextBox getPiso() {
		piso.setWidth("30px");
		return piso;
	}

	public TextBox getCodigoPostal() {
		return codigoPostal;
	}

	public TextBox getDepartamento() {
		departamento.setWidth("30px");
		return departamento;
	}

	public TextBox getEntreCalle() {
		return entreCalle;
	}

	public TextBox getManzana() {
		manzana.setWidth("30px");
		return manzana;
	}

	public TextBox getPuerta() {
		return puerta;
	}

	public TextBox getYcalle() {
		return ycalle;
	}

	public TextBox getCpa() {
		cpa.setWidth("120px");
		return cpa;
	}

	public TextBox getTorre() {
		torre.setWidth("30px");
		return torre;
	}

	public TextBox getUnidadFuncional() {
		unidadFuncional.setWidth("30px");
		return unidadFuncional;
	}

	public TextArea getObservaciones() {
		observaciones.setWidth("615px");
		return observaciones;
	}

	public TextBox getLocalidad() {
		return localidad;
	}

	public TextBox getPartido() {
		return partido;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public CheckBox getValidado() {
		return validado;
	}

	public TextBox getCodigoFNCL() {
		return codigoFNCL;
	}

	public CheckBox getEnCarga() {
		return enCarga;
	}

	public TextBox getNombreUsuarioUltimaModificacion() {
		return nombreUsuarioUltimaModificacion;
	}
}
