package ar.com.nextel.sfa.client.cuenta;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.dto.DomiciliosCuentaDto;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioAsociadoDto;
import ar.com.nextel.sfa.client.dto.TipoDomicilioDto;
import ar.com.nextel.sfa.client.widget.UIData;
import ar.com.snoop.gwt.commons.client.widget.ListBox;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author eSalvador
 **/
public class DomiciliosUIData extends UIData {
	//
	private DomiciliosCuentaDto domicilio;
	// /
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

	// TODO: Hacer los combos hardcodeados!
	ListBox facturacion = new ListBox();
	ListBox entrega = new ListBox();
	// ProvinciaDto provincia = new ProvinciaDto();
	// EstadoDomicilioDto estado = new EstadoDomicilioDto();
	// Boolean noNormalizar;
	//
	CheckBox validado = new CheckBox();
	TextBox codigoFNCL = new TextBox();
	CheckBox enCarga = new CheckBox();
	TextBox nombreUsuarioUltimaModificacion = new TextBox();

	// /

	public void setDomicilio(DomiciliosCuentaDto domicilio) {
		if (domicilio == null) {
			this.domicilio = new DomiciliosCuentaDto();
		} else {
			this.domicilio = domicilio;
			/** TODO: Terminar este mapeo! */
			calle.setText(domicilio.getCalle());
			codigoPostal.setText(domicilio.getCodigo_postal());
			localidad.setText(domicilio.getLocalidad());
			partido.setText(domicilio.getPartido());
			numero.setText(domicilio.getNumero().toString());
			piso.setText(domicilio.getPiso());
			departamento.setText(domicilio.getDepartamento());
			entreCalle.setText(domicilio.getEntre_calle());
			manzana.setText(domicilio.getManzana());
			puerta.setText(domicilio.getPuerta());
			ycalle.setText(domicilio.getY_calle());
			cpa.setText(domicilio.getCpa());
			torre.setText(domicilio.getTorre());
			unidadFuncional.setText(domicilio.getUnidad_funcional());
			observaciones.setText(domicilio.getObservaciones());

			/**
			 * TODO: Hacer el mapeo de tiposDomicilioAsociado, para cargar los combos de Entrega y
			 * Facturacion: con lo que venga en Domicilio.
			 */

			for (int i = 0; i < domicilio.getTiposDomicilioAsociado().size(); i++) {
				TipoDomicilioAsociadoDto tipoDomicilioAsociadoDto = domicilio.getTiposDomicilioAsociado()
						.get(i);

				/** Logica para tipoDomicilio: */

				// Si el tipoDomicilio es 0 = No
				if (tipoDomicilioAsociadoDto.getTipoDomicilio().getId() == 0) {
					if (tipoDomicilioAsociadoDto.getTipoDomicilio().getDescripcion().equals("FacturacionNo")) {
						facturacion.setSelectedIndex(2);
					} else if (tipoDomicilioAsociadoDto.getTipoDomicilio().getDescripcion().equals(
							"EntregaNo")) {
						entrega.setSelectedIndex(2);
					}
				} else // Si el tipoDomicilio es 1 = Facturacion
				if (tipoDomicilioAsociadoDto.getTipoDomicilio().getId() == 1) {
					// Y ademas Principal:
					if (tipoDomicilioAsociadoDto.getPrincipal()) {
						facturacion.setSelectedIndex(0);
					} else {
						facturacion.setSelectedIndex(1);
					}
				} else // Si el tipoDomicilio es 4 = Entrega
				if (tipoDomicilioAsociadoDto.getTipoDomicilio().getId() == 4) {
					// Si es entrega:
					if (tipoDomicilioAsociadoDto.getPrincipal()) {
						// Y ademas Principal:
						entrega.setSelectedIndex(0);
					} else {
						entrega.setSelectedIndex(1);
					}
				}
			}
		}
	}

	public DomiciliosCuentaDto getDomicilio() {
		/** TODO: Deberia hacer alguna validacion?? */
		/** TODO: Terminar este mapeo! */
		domicilio.setCalle(calle.getText());
		domicilio.setEntre_calle(entreCalle.getText());
		domicilio.setY_calle(ycalle.getText());
		domicilio.setCodigo_postal(codigoPostal.getText());
		domicilio.setLocalidad(localidad.getText());
		domicilio.setPartido(partido.getText());
		domicilio.setCpa(cpa.getText());
		domicilio.setDepartamento(departamento.getText());
		domicilio.setManzana(manzana.getText());
		domicilio.setNumero(Long.parseLong(numero.getText()));
		domicilio.setObservaciones(observaciones.getText());
		domicilio.setPiso(piso.getText());
		// domicilioNuevo.setProvincia(provincia);
		domicilio.setPuerta(puerta.getText());
		domicilio.setTorre(torre.getText());
		/** Mapeo de Facturacion y Entrega!! */
		// Creo los dos tipos de domicilio que seleccionaron en el Combo:
		TipoDomicilioDto tipoDomicilioFacturacionNuevo = new TipoDomicilioDto();
		TipoDomicilioItem facturacionObjectSelected = (TipoDomicilioItem) facturacion.getSelectedItem();
		tipoDomicilioFacturacionNuevo.setId(Long.parseLong(facturacion.getSelectedItemId()));
		if (facturacionObjectSelected.getTextoAMostrar().equals("No")) {
			tipoDomicilioFacturacionNuevo.setDescripcion(facturacionObjectSelected.getTextoNo());
		} else {
			tipoDomicilioFacturacionNuevo.setDescripcion(facturacion.getSelectedItemText());
		}
		TipoDomicilioItem entregaObjectSelected = (TipoDomicilioItem) entrega.getSelectedItem();
		TipoDomicilioDto tipoDomicilioEntregaNuevo = new TipoDomicilioDto();
		tipoDomicilioEntregaNuevo.setId(Long.parseLong(entrega.getSelectedItemId()));
		if (entregaObjectSelected.getTextoAMostrar().equals("No")) {
			tipoDomicilioEntregaNuevo.setDescripcion(entregaObjectSelected.getTextoNo());
		} else {
			tipoDomicilioEntregaNuevo.setDescripcion(entrega.getSelectedItemText());
		}
		// Creo un tipo de domicilioAsociado:
		TipoDomicilioAsociadoDto tipoDomicilioAsociadoNuevoFacturacion = new TipoDomicilioAsociadoDto();
		tipoDomicilioAsociadoNuevoFacturacion.setTipoDomicilio(tipoDomicilioFacturacionNuevo);
		if (facturacion.getSelectedItemText().equals("Principal")) {
			tipoDomicilioAsociadoNuevoFacturacion.setPrincipal(true);
		} else {
			tipoDomicilioAsociadoNuevoFacturacion.setPrincipal(false);
		}
		tipoDomicilioAsociadoNuevoFacturacion.setCodigoFNCL(codigoFNCL.getSelectedText());
		tipoDomicilioAsociadoNuevoFacturacion.setActivo(true);
		//
		TipoDomicilioAsociadoDto tipoDomicilioAsociadoNuevoEntrega = new TipoDomicilioAsociadoDto();
		if (entrega.getSelectedItemText().equals("Principal")) {
			tipoDomicilioAsociadoNuevoEntrega.setPrincipal(true);
		} else {
			tipoDomicilioAsociadoNuevoEntrega.setPrincipal(false);
		}
		tipoDomicilioAsociadoNuevoEntrega.setCodigoFNCL(codigoFNCL.getSelectedText());
		tipoDomicilioAsociadoNuevoEntrega.setActivo(true);
		tipoDomicilioAsociadoNuevoEntrega.setTipoDomicilio(tipoDomicilioEntregaNuevo);
		//
		// Le agrego los tipoDomicilioAsociado al nuevo Domicilio:
		List<TipoDomicilioAsociadoDto> listaTipoDomiciliosAsociados = new ArrayList();
		listaTipoDomiciliosAsociados.add(tipoDomicilioAsociadoNuevoFacturacion);
		listaTipoDomiciliosAsociados.add(tipoDomicilioAsociadoNuevoEntrega);
		domicilio.setTiposDomicilioAsociado(listaTipoDomiciliosAsociados);
		//
		return domicilio;
	}

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
		fields.add((Widget) validado);
		fields.add(codigoFNCL);
		fields.add(enCarga);
		fields.add(nombreUsuarioUltimaModificacion);
		fields.add(entrega);
		fields.add(facturacion);
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

	public ListBox getFacturacion() {
		return facturacion;
	}

	public ListBox getEntrega() {
		return entrega;
	}

	public TextBox getNombreUsuarioUltimaModificacion() {
		return nombreUsuarioUltimaModificacion;
	}
}
