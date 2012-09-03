package ar.com.nextel.sfa.client.cuenta;

import java.util.HashMap;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.OportunidadNegocioDto;
import ar.com.nextel.sfa.client.dto.TipoContribuyenteDto;
import ar.com.nextel.sfa.client.enums.CondicionCuentaEnum;
import ar.com.nextel.sfa.client.enums.TipoContribuyenteEnum;
import ar.com.nextel.sfa.client.enums.TipoCuentaEnum;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.util.RegularExpressionConstants;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;

public class EditarCuentaUI extends ApplicationUI {

	private final CuentaEdicionTabPanel cuentaTab = CuentaEdicionTabPanel.getInstance();

	public static boolean esEdicionCuenta = true;
	public static boolean edicionReadOnly = false;
	public static boolean verCrearNuevoDomicilio = true;

	public static final String PARAM_CUENTA_ID = "cuenta_id";
	public static final String PARAM_COD_VANTIVE = "cod_vantive";
	public static final String PARAM_N_DOCUMENTO = "nroDoc";
	public static final String PARAM_ID_DOCUMENTO = "idDoc";
	public static final String PARAM_OPORTUNIDAD = "opp";
	public static final String PARAM_ID_OPORTUNIDAD = "idOpp";
	public static final String PARAM_ID_CUENTA_PADRE = "idCtaPadre";
	public static final String PARAM_DIVISION = "div";
	public static final String PARAM_SUSCRIPTOR = "sus";
	public static final String PARAM_READ_ONLY = "ro";
	public static final String PARAM_FILTRADO_X_DNI = "filByDni";

	public EditarCuentaUI() {
		super();
	}

	public boolean load() {
		resetEditor();

		// viene de popup "Agregar Cuenta"
		if (HistoryUtils.getParam(PARAM_N_DOCUMENTO) != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarCuenta();
			} else {
				Long idTipoDoc = HistoryUtils.getParam(PARAM_ID_DOCUMENTO) != null ? Long
						.parseLong(HistoryUtils.getParam(PARAM_ID_DOCUMENTO)) : null;
				String nroDoc = HistoryUtils.getParam(PARAM_N_DOCUMENTO);
				Long idOpp = HistoryUtils.getParam(PARAM_ID_OPORTUNIDAD) != null
						&& !HistoryUtils.getParam(PARAM_ID_OPORTUNIDAD).equals("null") ? Long
						.parseLong(HistoryUtils.getParam(PARAM_ID_OPORTUNIDAD)) : null;
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
		} else if (HistoryUtils.getParam(PARAM_DIVISION) != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarDivision();
			} else {
				CuentaClientService.crearDivision(HistoryUtils.getParam(PARAM_ID_CUENTA_PADRE) != null ? Long
						.parseLong(HistoryUtils.getParam(PARAM_ID_CUENTA_PADRE)) : null, false);
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
		} else if (HistoryUtils.getParam(PARAM_SUSCRIPTOR) != null) {
			if (CuentaClientService.cuentaDto != null) {
				doAgregarSuscriptor();
			} else {
				CuentaClientService.crearSuscriptor(
						HistoryUtils.getParam(PARAM_ID_CUENTA_PADRE) != null ? Long.parseLong(HistoryUtils
								.getParam(PARAM_ID_CUENTA_PADRE)) : null, false);
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
		} else if (HistoryUtils.getParam(PARAM_OPORTUNIDAD) != null) {
			if (CuentaClientService.cuentaPotencialDto != null) {
				doBusquedaOPP();
			} else {
				CuentaClientService.getOportunidadNegocio(
						HistoryUtils.getParam(PARAM_ID_OPORTUNIDAD) != null ? Long.parseLong(HistoryUtils
								.getParam(PARAM_ID_OPORTUNIDAD)) : null, false);
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
		} else if (HistoryUtils.getParam(PARAM_CUENTA_ID) != null) {
			if (CuentaClientService.cuentaDto != null) {
				doBusquedaCuenta();
			} else {
				Long cuentaID = Long.parseLong(HistoryUtils.getParam(PARAM_CUENTA_ID) != null ? HistoryUtils
						.getParam(PARAM_CUENTA_ID) : null);
				String cod_vantive = HistoryUtils.getParam(PARAM_COD_VANTIVE);
				String filtradoPorDni = ClientContext.getInstance().getSecretParams().get(
						PARAM_FILTRADO_X_DNI);
				// String filtradoPorDni = HistoryUtils.getParam(PARAM_FILTRADO_X_DNI);
				CuentaClientService.cargarDatosCuenta(cuentaID, cod_vantive, filtradoPorDni != null, false);
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
		//MGR - #955
		//Viene de nexus
		else if(ClientContext.getInstance().vengoDeNexus()){
			Long custId = null;
			String customerCode = ClientContext.getInstance().getClienteNexus().getCustomerCode();
			String customerId = ClientContext.getInstance().getClienteNexus().getCustomerId();
			if(customerId != null){
				custId = Long.valueOf(customerId);
			}
			CuentaClientService.cargarDatosCuenta(custId, customerCode);
		}
		
		return false;
	}

	/**
	 * 
	 */
	private void resetEditor() {
		esEdicionCuenta = true;
		verCrearNuevoDomicilio = true;
		String paramReadOnly = ClientContext.getInstance().getSecretParams().get(PARAM_READ_ONLY);
		edicionReadOnly = paramReadOnly != null && "true".equals(paramReadOnly);
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
		// si hay apellido recibido de veraz y NO hay nadie en la DB aplica los datos aportados por veraz
		if (CuentaClientService.apellidoFromVeraz != null
				&& cuentaTab.getCuentaDatosForm().getCamposTabDatos().getApellido().getText().equals("")) {
			String nom = CuentaClientService.nombreFromVeraz;
			String ape = CuentaClientService.apellidoFromVeraz;
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getNombre().setText(nom);
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getApellido().setText(ape);
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getRazonSocial().setText(nom + " " + ape);
			if (cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().getItemCount() <= 0) {//#3481
				setearComboSexo(CuentaClientService.sexoVeraz);
			} else {
				cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().selectByText(CuentaClientService.sexoVeraz);
			}
			CuentaClientService.nombreFromVeraz = null;
			CuentaClientService.apellidoFromVeraz = null;
			CuentaClientService.sexoVeraz = null;
		} else if (CuentaClientService.razonSocialFromVeraz != null 
					&& cuentaTab.getCuentaDatosForm().getCamposTabDatos().getRazonSocial().getText().equals("")) { //#3481
			cuentaTab.getCuentaDatosForm().getCamposTabDatos().getRazonSocial().setText(CuentaClientService.razonSocialFromVeraz);
			if (cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().getItemCount() <= 0) {//#3481
				setearComboSexo(CuentaClientService.sexoVeraz);
			} else {
				cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().selectByText(CuentaClientService.sexoVeraz);
				CuentaDatosForm.getInstance().cambiarVisibilidadCamposSegunSexo();
			}
			CuentaClientService.razonSocialFromVeraz = null;
		}
		cuentaTab.validarCompletitud(false);
	}

	private void setearComboSexo(final String sexoVeraz) {
		WaitWindow.show();
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().getItemCount() <= 0){
					return true;
				}
				cuentaTab.getCuentaDatosForm().getCamposTabDatos().getSexo().selectByText(sexoVeraz);
				CuentaDatosForm.getInstance().cambiarVisibilidadCamposSegunSexo();
				WaitWindow.hide();
				return false;
			}
		});
		
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
			cuenta.setTipoContribuyente(new TipoContribuyenteDto(TipoContribuyenteEnum.CONS_FINAL.getId(),
					TipoContribuyenteEnum.CONS_FINAL.getDescripcion()));
		}
		if (esEdicionCuenta)
			cuentaTab.getCuentaDatosForm().ponerDatosBusquedaEnFormulario(cuenta, false);

		// carga info pestaña Domicilio
		cuentaTab.getCuentaDomicilioForm().getCrearDomicilio().setVisible(verCrearNuevoDomicilio);
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
//		verCrearNuevoDomicilio = false;
		if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.CTA.getTipo())) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.cuentaDto);
			if (CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT
					.getId()
					|| CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.CUSTOMER
							.getId()) {
				cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
			}
			completarVisualizacionDatos(CuentaClientService.cuentaDto);
		} else if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.DIV.getTipo())) {
			doAgregarDivision();
		} else if (CuentaClientService.cuentaDto.getCategoriaCuenta().getDescripcion().equals(
				TipoCuentaEnum.SUS.getTipo())) {
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
		verCrearNuevoDomicilio = false;
		edicionReadOnly = true;
		cuentaTab.setCuenta2editDto(CuentaClientService.cuentaPotencialDto.getCuentaOrigen());
		if (!CuentaClientService.cuentaPotencialDto.isEsReserva()) {
			cuentaTab.setPriorityFlag(((OportunidadNegocioDto) CuentaClientService.cuentaPotencialDto)
					.getPrioridad().getId());
		}
		cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusquedaFromOpp(
				CuentaClientService.cuentaPotencialDto);
		cuentaTab.getCuentaDatosForm().ponerDatosOportunidadEnFormulario(
				CuentaClientService.cuentaPotencialDto);
		cuentaTab.setNumeroCtaPot(CuentaClientService.cuentaPotencialDto.getNumero());
		cuentaTab.getCuentaNotasForm().cargarTablaCuentas(CuentaClientService.cuentaPotencialDto);
		completarVisualizacionDatos(CuentaClientService.cuentaPotencialDto.getCuentaOrigen());
	}

	private void doBusquedaCuenta() {
		if (RegularExpressionConstants.isVancuc(CuentaClientService.cuentaDto.getCodigoVantive())) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposCuenta(CuentaClientService.cuentaDto);
			if (CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.PROSPECT
					.getId()
					|| CuentaClientService.cuentaDto.getCondicionCuenta().getId() == CondicionCuentaEnum.CUSTOMER
							.getId()) {
				cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
				edicionReadOnly = true;
			}
		} else {
			cuentaTab.getCuentaDatosForm().setAtributosCamposAlMostrarResuladoBusqueda(
					CuentaClientService.cuentaDto);
		}
		if ("true".equals(ClientContext.getInstance().getSecretParams().get(PARAM_READ_ONLY))) {
			cuentaTab.getCuentaDatosForm().setAtributosCamposSoloLectura();
		}
		completarVisualizacionDatos(CuentaClientService.cuentaDto);
	}

	public static String getEditarCuentaUrl(String idCuenta, String idCuentaPadre, String idOportunidad,
			boolean oportunidad, boolean division, boolean suscriptor) {
		return getEditarCuentaUrl(idCuenta, null, null, null, idOportunidad, idCuentaPadre, oportunidad,
				division, suscriptor, false, false);
	}

	public static String getEditarCuentaUrl(String idCuenta, String codVantive, String nDocumento,
			String idDocumento, String idOportunidad, String idCuentaPadre, boolean oportunidad,
			boolean division, boolean suscriptor, boolean filtradoDni, boolean readOnly) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(PARAM_CUENTA_ID, idCuenta);
		params.put(PARAM_COD_VANTIVE, codVantive);
		params.put(PARAM_N_DOCUMENTO, nDocumento);
		params.put(PARAM_ID_DOCUMENTO, idDocumento);
		params.put(PARAM_OPORTUNIDAD, oportunidad ? "true" : null);
		params.put(PARAM_ID_OPORTUNIDAD, idOportunidad);
		params.put(PARAM_ID_CUENTA_PADRE, idCuentaPadre);
		params.put(PARAM_DIVISION, division ? "true" : null);
		params.put(PARAM_SUSCRIPTOR, suscriptor ? "true" : null);
		ClientContext.getInstance().getSecretParams().put(PARAM_READ_ONLY, readOnly ? "true" : null);
		ClientContext.getInstance().getSecretParams().put(PARAM_FILTRADO_X_DNI, filtradoDni ? "true" : null);
		return UILoader.EDITAR_CUENTA + HistoryUtils.getParamsFromMap(params);
	}
}
