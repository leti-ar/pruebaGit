package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.dto.CrearCuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaDto;
import ar.com.nextel.sfa.client.dto.CuentaPotencialDto;
import ar.com.nextel.sfa.client.dto.DocumentoDto;
import ar.com.nextel.sfa.client.dto.TipoDocumentoDto;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;

public class CuentaClientService {

	public static CuentaDto cuentaDto;
	public static CuentaPotencialDto cuentaPotencialDto;
	private static boolean error;
	public static String nombreFromVeraz;
	public static String apellidoFromVeraz;
	public static String sexoVeraz;
	public static String razonSocialFromVeraz;
	public static Integer scoreDniFromVeraz;
	public static String estadoFromVeraz;


	public static void reservaCreacionCuentaFromVeraz(Long idTipoDoc, String nroDoc, String nombre,
			String apellido, String sexo, String razonSocial, Integer scoreDni, String estadoVeraz) {
		nombreFromVeraz = nombre;
		apellidoFromVeraz = apellido;
		sexoVeraz = sexo;//#3481
		razonSocialFromVeraz = razonSocial;
		scoreDniFromVeraz = scoreDni;
		estadoFromVeraz = estadoVeraz;
		reservaCreacionCuenta(idTipoDoc, nroDoc, null, true);
	}

	public static void reservaCreacionCuenta(Long idTipoDoc, String nroDoc, Long idOpp) {
		reservaCreacionCuenta(idTipoDoc, nroDoc, idOpp, true);
	}

	public static void reservaCreacionCuenta(final Long idTipoDoc, final String nroDoc, final Long idOpp,
			boolean redir) {
		cuentaDto = null;
		error = false;
		TipoDocumentoDto tipoDoc = new TipoDocumentoDto(idTipoDoc, null);
		DocumentoDto docDto = new DocumentoDto(nroDoc, tipoDoc);
		CrearCuentaDto crearCuentaDto = new CrearCuentaDto(docDto, idOpp);

		CuentaRpcService.Util.getInstance().reservaCreacionCuenta(crearCuentaDto,
				new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						cuentaDto = ctaDto;
					}

					public void failure(Throwable caught) {
						error = true;
						super.failure(caught);
					}
				});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error)
						return true;
					if (!error) {
						History.newItem(EditarCuentaUI.getEditarCuentaUrl("" + cuentaDto.getId(), null,
								nroDoc, "" + idTipoDoc, null, null, false, false, false, false, false));
					}
					return false;
				}
			});
		}
	}

	public static void crearDivision(final Long id_cuentaPadre) {
		crearDivision(id_cuentaPadre, true);
	}

	public static void crearDivision(final Long id_cuentaPadre, boolean redir) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().crearDivision(id_cuentaPadre,
				new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						cuentaDto = ctaDto;
					}

					public void failure(Throwable caught) {
						error = true;
						super.failure(caught);
					}

				});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error)
						return true;
					if (!error) {
						History.newItem(EditarCuentaUI.getEditarCuentaUrl("" + cuentaDto.getId(), ""
								+ id_cuentaPadre, null, false, true, false));
					}
					return false;
				}
			});
		}
	}

	public static void crearSuscriptor(final Long id_cuentaPadre) {
		crearSuscriptor(id_cuentaPadre, true);
	}

	public static void crearSuscriptor(final Long id_cuentaPadre, boolean redir) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().crearSuscriptor(id_cuentaPadre,
				new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						cuentaDto = ctaDto;
					}

					public void failure(Throwable caught) {
						error = true;
						super.failure(caught);
					}
				});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error)
						return true;
					if (!error) {
						History.newItem(EditarCuentaUI.getEditarCuentaUrl("" + cuentaDto.getId(), ""
								+ id_cuentaPadre, null, false, false, true));
					}
					return false;
				}
			});
		}
	}

	public static void getOportunidadNegocio(final Long idOpp) {
		getOportunidadNegocio(idOpp, true);
	}

	public static void getOportunidadNegocio(final Long idOpp, boolean redir) {
		cuentaPotencialDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().getOportunidadNegocio(idOpp,
				new DefaultWaitCallback<CuentaPotencialDto>() {
					public void success(CuentaPotencialDto ctaPot) {
						cuentaPotencialDto = ctaPot;
					}

					public void failure(Throwable caught) {
						error = true;
						super.failure(caught);
					}
				});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaPotencialDto == null && !error)
						return true;
					if (!error) {
						History.newItem(EditarCuentaUI.getEditarCuentaUrl(""
								+ cuentaPotencialDto.getCuentaOrigen().getId(), null, "" + idOpp, true,
								false, false));
					}
					return false;
				}
			});
		}
	}

	public static void cargarDatosCuenta(Long cuentaID, String cod_vantive) {
		cargarDatosCuenta(cuentaID, cod_vantive, false, true);
	}

	public static void cargarDatosCuenta(final Long cuentaID, final String cod_vantive,
			final boolean filtradoPorDni) {
		cargarDatosCuenta(cuentaID, cod_vantive, filtradoPorDni, true);
	}

	public static void cargarDatosCuenta(final Long cuentaID, final String cod_vantive,
			final boolean filtradoPorDni, boolean redir) {
		cargarDatosCuenta(cuentaID, cod_vantive, filtradoPorDni, redir, false);
	}

	public static void cargarDatosCuenta(final Long cuentaID, final String codVantive,
			final boolean filtradoPorDni, boolean redir, final boolean readOnly) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().selectCuenta(cuentaID, codVantive, filtradoPorDni, 
				new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						cuentaDto = ctaDto;
					}

					public void failure(Throwable caught) {
						error = true;
						ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
						super.failure(caught);
					}
				});
		if (redir) {
			DeferredCommand.addCommand(new IncrementalCommand() {
				public boolean execute() {
					if (cuentaDto == null && !error)
						return true;
					if (!error) {
						History.newItem(EditarCuentaUI.getEditarCuentaUrl("" + cuentaID, codVantive, null,
								null, null, null, false, false, false, filtradoPorDni, readOnly));
					}
					return false;
				}
			});
		}
	}
	
	public static boolean tengoCuentaEnNull(){
		boolean vengoDeOportunidad= HistoryUtils.getParam(EditarCuentaUI.PARAM_OPORTUNIDAD) != null;
		return (vengoDeOportunidad&&CuentaClientService.cuentaPotencialDto == null)|| (!vengoDeOportunidad&&CuentaClientService.cuentaDto==null);
	}
	
//	MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
	//entonces ya no puedo preguntar por el codigo vantive (que era para lo que se estaba usando este metodo)
	//es preferible usar el metodo "isProspectOrProspectEnCarga".
//	public static String getCodigoVantive(){
//		boolean vengoDeOportunidad= HistoryUtils.getParam(EditarCuentaUI.PARAM_OPORTUNIDAD) != null;
//		return vengoDeOportunidad?cuentaPotencialDto.getCodigoVantive() : cuentaDto.getCodigoVantive();
//	}

	public static void cargarDatosCuentaInfocom(final Long cuentaID, final String codVantive,
			final boolean filtradoPorDni, final String url) {
		cuentaDto = null;
		error = false;
		CuentaRpcService.Util.getInstance().selectCuentaParaInfocom(cuentaID, codVantive, filtradoPorDni, 
				new DefaultWaitCallback<CuentaDto>() {
					public void success(CuentaDto ctaDto) {
						cuentaDto = ctaDto;
					}

					public void failure(Throwable caught) {
						error = true;
						ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
						super.failure(caught);
					}
				});
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if (cuentaDto == null && !error)
					return true;
				if (!error) {
					History.newItem(url + cuentaDto.getId());
				}
				return false;
			}
		});
	}

//	MGR - Mejoras Perfil Telemarketing. REQ#1. Cambia la definicion de prospect para Telemarketing
	public static boolean isProspectOrProspectEnCarga() {
		boolean vengoDeOportunidad= HistoryUtils.getParam(EditarCuentaUI.PARAM_OPORTUNIDAD) != null;
		if(vengoDeOportunidad){
			return false;
		}else{
			if(cuentaDto != null){
//				Si no es cliente, es prospect o prospect en carga
				return (!cuentaDto.isCustomer());
			}
			return false;
		}
	}
}
