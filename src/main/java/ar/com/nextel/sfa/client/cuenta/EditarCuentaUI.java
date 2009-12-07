package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.enums.CondicionCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoContribuyenteEnum;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.ApplicationUI;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;

public class EditarCuentaUI extends ApplicationUI {

	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();

	public static boolean esEdicionCuenta = true;
	public static boolean edicionReadOnly = false;

	public EditarCuentaUI() {
		super();
	}

	public boolean load() {
		resetEditor();

		// viene de popup "Agregar Cuenta"
		if (HistoryUtils.getParam("nroDoc") != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarCuenta();
			} else {
				Long idTipoDoc = HistoryUtils.getParam("idDoc") != null ? Long.parseLong(HistoryUtils.getParam("idDoc")) : null;
				String nroDoc = HistoryUtils.getParam("nroDoc");
				Long idOpp = HistoryUtils.getParam("idOpp") != null	&& !HistoryUtils.getParam("idOpp").equals("null") ? Long.parseLong(HistoryUtils.getParam("idOpp")) : null;
				CuentaClientService.reservaCreacionCuenta(idTipoDoc, nroDoc, idOpp, false);
				DeferredCommand.addCommand(new IncrementalCommand() {
					public boolean execute() {
						if (CuentaClientService.cuentaDto == null)
							return true;
						doAgregarCuenta();
						return false;
					}
				});
			}

			// viene de popup "Agregar Division"
		} else if (HistoryUtils.getParam("div") != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarDivision();
			} else {
				CuentaClientService.crearDivision(HistoryUtils.getParam("idCtaPadre") != null ? Long.parseLong(HistoryUtils.getParam("idCtaPadre")) : null, false);
				DeferredCommand.addCommand(new IncrementalCommand() {
					public boolean execute() {
						if (CuentaClientService.cuentaDto == null)
							return true;
						doAgregarDivision();
						return false;
					}
				});
			}

			// viene de popup "Agregar Suscriptor"
		} else if (HistoryUtils.getParam("sus") != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarSuscriptor();
			} else {
				CuentaClientService.crearSuscriptor(HistoryUtils.getParam("idCtaPadre") != null ? Long.parseLong(HistoryUtils.getParam("idCtaPadre")) : null, false);
				DeferredCommand.addCommand(new IncrementalCommand() {
					public boolean execute() {
						if (CuentaClientService.cuentaDto == null)
							return true;
						doAgregarSuscriptor();
						return false;
					}
				});
			}

			// viene de busqueda OPP
		} else if (HistoryUtils.getParam("opp") != null) {
			if (CuentaClientService.cuentaPotencialDto != null) {
				doBusquedaOPP();
			} else {
				CuentaClientService.getOportunidadNegocio(HistoryUtils.getParam("idOpp") != null ? Long.parseLong(HistoryUtils.getParam("idOpp")) : null, false);
				DeferredCommand.addCommand(new IncrementalCommand() {
					public boolean execute() {
						if (CuentaClientService.cuentaPotencialDto == null)
							return true;
						doBusquedaOPP();
						return false;
					}
				});
			}

			// viene de pantallas de busqueda
		} else if (HistoryUtils.getParam("cuenta_id") != null) {
			if (CuentaClientService.cuentaDto != null) {
				doBusquedaCuenta();
			} else {
				Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id") != null ? HistoryUtils.getParam("cuenta_id") : null);
				String cod_vantive = HistoryUtils.getParam("cod_vantive");
				String filtradoPorDni = HistoryUtils.getParam("filByDni");
				CuentaClientService.cargarDatosCuenta(cuentaID, cod_vantive, filtradoPorDni, false);
				DeferredCommand.addCommand(new IncrementalCommand() {
					public boolean execute() {
						if (CuentaClientService.cuentaDto == null)
							return true;
						doBusquedaCuenta();
						return false;
					}
				});
			}
		}
		return false;
	}

	/**
	 * 
	 */
	private void resetEditor() {
		esEdicionCuenta = true;
		edicionReadOnly = HistoryUtils.getParam("ro") != null;
		cuentaTab.clean();
		cuentaTab.getTabPanel().selectTab(0);
		CuentaDomiciliosForm.getInstance().setHuboCambios(false);
		CuentaContactoForm.getInstance().setFormDirty(false);
		cuentaTab.setPriorityFlag(new Long("0"));
	}

	/**
	 * 
	 * @param cuentaDto
	 */
	private void completarVisualizacionDatos(CuentaDto cuentaDto) {
		cuentaTab.setCuenta2editDto(cuentaDto);
		if (!esEdicionCuenta)
			cuentaTab.getCuentaDatosForm().armarTablaPanelOppDatos();
		else
			cuentaTab.getCuentaDatosForm().armarTablaPanelDatos();
		cargaPanelesCuenta();
		//si hay apellido recibido de veraz y NO hay nadie en la DB aplica los datos aportados por veraz
		if (CuentaClientService.apellidoFromVeraz!=null && cuentaTab.getCuentaDatosForm().getCamposTabDatos().getApellido().getText().equals("")) {
			String nom = CuentaClientService.nombreFromVeraz;
			String ape = CuentaClientService.apellidoFromVeraz;
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getNombre().setText(nom);
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getApellido().setText(ape);
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getRazonSocial().setText(nom + " " + ape);
			CuentaClientService.nombreFromVeraz   = null;
			CuentaClientService.apellidoFromVeraz = null;
		}
		cuentaTab.validarCompletitud(false);
	}

	/**
	 * 	
	 */
	public void firstLoad() {
	}

	/**
	 * 
	 */
	private void cargaPanelesCuenta() {

		// Busca la cuenta con alguno de los dos datos NO nulos.
		CuentaDto cuenta = (CuentaDto) cuentaTab.getCuenta2editDto();
		if (cuenta.getCodigoVantive() != null) {
			cuentaTab.setCliente(cuenta.getCodigoVantive());
		}
		cuentaTab.setRazonSocial(cuenta.getPersona() != null ? cuenta.getPersona().getRazonSocial() : "");
		// carga info pestaña Datos
		if (cuenta.getTipoContribuyente() == null) {
			cuenta.setTipoContribuyente(new TipoContribuyenteDto(TipoContribuyenteEnum.CONS_FINAL.getId(),TipoContribuyenteEnum.CONS_FINAL.getDescripcion()));
		}
		if (esEdicionCuenta)
			cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(cuenta);

		// carga info pestaña Domicilio
		cuentaTab.getCuentaDomicilioForm().getCrearDomicilio().setVisible(esEdicionCuenta);
		if (cuenta.getPersona() != null) {
			cuentaTab.getCuentaDomicilioForm().cargaTablaDomicilios(cuenta);
		}

		// carga info pestaña Contactos
		cuentaTab.getCuentaContactoForm().getCrearButton().setVisible(esEdicionCuenta);
		cuentaTab.getCuentaContactoForm().cargarTablaContactos(cuentaTab.getCuenta2editDto());
		
		// prepara UI para edicion cuenta o visualizacion opp
		cuentaTab.setTabsTipoEditorCuenta(esEdicionCuenta);
		cuentaTab.getCuentaDatosForm().setUItipoEditorCuenta(esEdicionCuenta);

		// agrega tabs al panel principal
		mainPanel.add(cuentaTab.getCuentaEdicionPanel());
	}

	/**
	 * 
	 */
	public boolean unload(String token) {
		return true;
	}

	private void doAgregarCuenta() {
		if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.CTA.getTipo())) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.cuentaDto);
			if (CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT.getId() ||
					CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.CUSTOMER.getId()) {
				cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
			} 
			completarVisualizacionDatos(CuentaClientService.cuentaDto);
		} else if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.DIV.getTipo())) { 
			doAgregarDivision();
		} else if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(TipoCuentaEnum.SUS.getTipo())) {
			doAgregarSuscriptor();
		}
	}

	private void doAgregarDivision() {
		cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarDivision(CuentaClientService.cuentaDto);
		completarVisualizacionDatos(CuentaClientService.cuentaDto);
	}

	private void doAgregarSuscriptor() {
		cuentaTab.getCuentaDatosForm().setAtributosCamposAlAgregarSuscriptor(CuentaClientService.cuentaDto);
		completarVisualizacionDatos(CuentaClientService.cuentaDto);
	}

	private void doBusquedaOPP() {
		esEdicionCuenta = false;
		edicionReadOnly = true;
		cuentaTab.setCuenta2editDto(CuentaClientService.cuentaPotencialDto.getCuentaOrigen());
		if (!CuentaClientService.cuentaPotencialDto.isEsReserva()) {
			cuentaTab.setPriorityFlag(((OportunidadNegocioDto) CuentaClientService.cuentaPotencialDto).getPrioridad().getId());
		}
		cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusquedaFromOpp(CuentaClientService.cuentaPotencialDto);
		cuentaTab.getCuentaDatosForm().ponerDatosOportunidadEnFormulario(CuentaClientService.cuentaPotencialDto);
		cuentaTab.setNumeroCtaPot(CuentaClientService.cuentaPotencialDto.getNumero());
		completarVisualizacionDatos(CuentaClientService.cuentaPotencialDto.getCuentaOrigen());
	}

	private void doBusquedaCuenta() {
		if (RegularExpressionConstants.isVancuc(CuentaClientService.cuentaDto.getCodigoVantive())) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.cuentaDto);
			if (CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT.getId()||
					CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.CUSTOMER.getId()) {
				cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
				edicionReadOnly = true;
			} 
		} else {
			cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(CuentaClientService.cuentaDto);
		}
		if (HistoryUtils.getParam("ro") != null) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
		}
		completarVisualizacionDatos(CuentaClientService.cuentaDto);
	}
}
