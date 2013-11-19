package ar.com.nextel.sfa.client.enums;

public enum PermisosEnum {
	ACCESO("Acceso",Accion.X,false),
	EECC_BUS_CLASE_CUENTA_DESHABILITADA("EECC - Busqueda clase cuenta deshabilitada",Accion.W,false),
	EECC_BUS_USU_CREACION_DESHABILITADA("EECC - Busqueda usuario de creación deshabilitada",Accion.W,false),
	EECC_BUS_FECHA_CREACION_DESDE_DESHABILITADA("EECC - Busqueda fecha de creación desde deshabilitada",Accion.W,false),
	EECC_BUS_FECHA_CREACION_HASTA_DESHABILITADA("EECC - Busqueda fecha de creación hasta deshabilitada",Accion.W,false),
	EECC_BUS_VEND_DESHABILITADA("EECC - Busqueda vendedor deshabilitada",Accion.W,false),
	SERVICIO_DE_SEARCH("Servicio de search",Accion.X,false),
	EECC_LOCKEAR_O_RESERVAR_NUEVA_CUENTA("EECC - lockearOReservarNuevaCuenta",Accion.X,false),
	EECC_SELECTCUENTA("EECC - selectCuenta",Accion.X,false),
	EECC_UPDATE("EECC - update",Accion.X,false),
	EECC_GET_CONTACTOS("EECC - getContactos",Accion.X,false),
	EECC_GET_CONTACTO_CUENTA("EECC - getContactoCuenta",Accion.X,false),
	DATOS_CABECERA_INFOCOM("Datos cabecera Infocom",Accion.X,false),
	INFOMACION_DE_EQUIPOS_INFOCOM("Infomación de equipos Infocom",Accion.X,false),
	RESUMEN_POR_EQUIPOS_INFOCOM("Resumen por equipos Infocom",Accion.X,false),
	DATOS_DE_INFOCOM("Datos de infocom",Accion.X,false),
	EECC_GET_LISTAS_PRECIOS_POR_TIPO_SOLICITUD("EECC - getListasPreciosPorTipoSolicitud",Accion.X,false),
	EECC_RESERVAR_NUMERO_TELEFONICO("EECC - reservarNumeroTelefonico",Accion.X,false),
	COMBO_TIPOS_DE_SS("Combo Tipos de SS",Accion.X,false),
	COMBO_DE_PLANES("Combo de planes",Accion.X,false),
	DESRRESERVA_DE_NRO_DE_TELEFONO("Desrreserva de Nro de teléfono",Accion.X,false),
	EECC_VERIFY_CUILT("EECC - verifyCUILT",Accion.X,false),
	NORMALIZAR_DOMICILIO("normalizarDomicilio",Accion.X,false),
	NORMALIZAR_CPA("normalizarCPA",Accion.X,false),
	CONSULTA_A_VERAZ("Consulta a Veraz",Accion.X,false),
	EECC_GET_DESCRIBIBLES("EECC - getDescribibles",Accion.X,false),
	EECC_GET_KNOWNINSTANCE("EECC getKnownInstance",Accion.X,false),
	EECC_GET_ALL_KNOWNINSTANCES("EECC - getAllKnownInstances",Accion.X,false),
	EECC_GET_SOLICITUD_SERVICIO_POR_ID("EECC - getSolicitudServicioPorId",Accion.X,false),
	EECC_PERMISSION_FOR("EECC - permissionFor",Accion.X,false),
	GET_CURRENT_USER("Get current user",Accion.X,false),
	SERVICIO_DE_SINCRONIZACION("Servicio de sincronización",Accion.X,false),
	OPP_RES_Y_OP_CSO("Opp/Res y Op.Cso",Accion.X,false),
	EDITAR_OPP("Editar Opp",Accion.X,false),
	BUSQUEDA_DE_OPP("Busqueda de Opp",Accion.X,false),
	CAMBIAR_ESTADO_OPP("Cambiar Estado Opp",Accion.X,false),
	CANCELAR_OP_EN_CSO("Cancelar Op en Cso",Accion.X,false),
	BUSQUEDA_DE_SS_CERRADAS("Busqueda de SS cerradas",Accion.X,false),
	NXS_CON_ACC_REMEQUI("NXS_CON_ACC_REMEQUI",Accion.X,false),
	AGREGAR_DIVISION_SUSCRIPTOR("Agregar división/suscriptor",Accion.X,false),
	NXS_BUSCAR_CLIENTE("NXS_BUSCAR_CLIENTE",Accion.X,false),

	CREAR_NUEVA_SS("crearNuevaSS",Accion.X,true),
	VISUALIZAR_CUENTA("visualizarCuenta",Accion.X,true),
	ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU("rootsMenuPanel.cuentasButtonMenu",Accion.X,true),
	ROOTS_MENU_PANEL_CUENTAS_BUSCAR_MENU("rootsMenuPanel.cuentasBuscarMenu",Accion.X,true),
	ROOTS_MENU_PANEL_CUENTAS_AGREGAR_MENU("rootsMenuPanel.cuentasAgregarMenu",Accion.X,true),
	ROOTS_MENU_PANEL_VERAZ_BUTTON("rootsMenuPanel.verazButton",Accion.X,true),
	ROOTS_MENU_PANEL_SS_BUTTON("rootsMenuPanel.ssButton",Accion.X,true),
	ROOTS_MENU_PANEL_OPERACIONES_EN_CURSO_BUTTON("rootsMenuPanel.operacionesEnCursoButton",Accion.X,true),
	ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON("rootsMenuPanel.busquedaOportunidadesButton",Accion.X,true),
	ROOTS_MENU_PANEL_CONFIGURACION_SUCURSAL_BUTTON("rootsMenuPanel.configuracionSucursalMenu",Accion.X,true),
	ROOTS_MENU_PANEL_VALIDACION_STOCK_BUTTON("rootsMenuPanel.validacionStockMenu",Accion.X,true),
	SCORING_CHECKED("scoringChecked",Accion.X,true),
	CERRAR_SS_CON_PIN("cerrarSSConPin",Accion.X,true),
	
	//MGR - Integracion
	//Verificar que los permisos que estoy usando son correctos
	ROOTS_MENU_PANEL_AGREGAR_PROSPECT("rootsMenuPanel.agregarProspectButton",Accion.X,true),
	//MGR - #959 - Para quitar la tabla de "Reservas" de la opcion "Op. en Curso"
	OP_EN_CURSO_SECCION_RESERVAS("opEnCursoSeccionReservas", Accion.X, true),
	//Al crear SS, en la pestaña "Varios"
	VARIOS_CREDITO_FIDELIZACION("variosCreditoFidelizacion",Accion.X,true),
	VARIOS_PATACONEX("variosPataconex",Accion.X,true),
	VARIOS_FIRMAS("variosFirmas",Accion.X,true),
	VARIOS_ANTICIPO("variosAnticipos",Accion.X,true),
	//Para editar cuenta
	ROOTS_MENU_PANEL_CUENTAS_EDITAR("rootsMenuPanel.cuentasEditar",Accion.X,true),
	//MGR - #965 - Para ocultar el campo Factura Electronica
	VER_CAMPO_FACTURA_ELECTRONICA("verCampoFacturaElectronica",Accion.X,true),
	//SB - 0005558 - Permite editar los campos Apellido y Nombre de un Prospect en SFA. 
	VER_CAMPOS_PROSPECT("verCampoProspect",Accion.X,true),
	//SB -  Factura electronica. Obliga a Poner el Check para todos y No editable.
	OBLIGA_CHECK_FACT_ELE("obligaCheckFactEle",Accion.X,true),
	//MGR - #962
	SELECT_OPC_TELEMARKETING_COMB_ORIGEN("selectOpcTelemarketingComboOrigen",Accion.X,true),
	//MGR - #963
	ENVIAR_MAIL_EJECUTIVO_CTA("enviarMailEjecutivoCta",Accion.X,false),
	//Para editar domicilio
	EDITAR_DOMICILIO("editarDomicilio", Accion.X, true),
	//MGR - #1085 - Se vuelve atras con el incidente #1014
	//MGR - #1014
//	COMPARTEN_SS("compartenSS", Accion.X, false),
	//MGR - #1026
	NRO_SS_EDITABLE("nroSSEditable", Accion.X, true),
	//MGR - #1029
	VALIDAR_TRIPTICO("validarTriptico", Accion.X, false),
	//MGR - #1034
	DOC_OBLIGATORIO_CONTACTO("docObligatorioContacto", Accion.X, true),
	//MR
	AUTOCOMPLETAR_TRIPTICO("autocompletarTriptico", Accion.X, true),
	//MGR - #1063
	TIENE_ACCESO_CTA_GOBIERNO("tieneAccesoCtaGobierno", Accion.X, true),
	
	//LM
	TIENE_ACCESO_CTA_GOBIERNO_BS_AS("tieneAccesoCtaGobiernoBsAs", Accion.X, true),
	TIENE_ACCESO_CTA_LAP("tieneAccesoCtaLAP", Accion.X, true),
	TIENE_ACCESO_CTA_LA("tieneAccesoCtaLA", Accion.X, true),
	//MGR - #1073
	BUSQUEDA_POR_CATEG_DOC("busquedaPorCategDoc", Accion.X, true),
	//Para agregar descuentos a las lineas de solicitud de servicio
	AGREGAR_DESCUENTOS("puedeVerDescuentos", Accion.X, true),

	//LM 
	OCULTA_CAMPO_VALIDADO_DOMICILIO("ocultaCampoValidadoDomicilio", Accion.X, true),
	//MGR - #1030
	EVITA_CONTROL_TERMINO_PAGO("evitaControlTerminoPago", Accion.X, false),
	//MGR - #1033
	EVITA_ANEXA_POR_SCORING("evitaAnexaPorScoring", Accion.X, false),
	//MGR - #1031
	MONTO_MAX_ACCESORIOS_DIFERENCIAL("montoMaxAccesoriosDiferencial", Accion.X, false),
	//MGR - #1110
	EVITA_CONTROL_RANGO_TRIPTICO("evitaControlRangoTriptico", Accion.X, false),
	//MGR - #1122
	OCULTA_LINK_GENERAR_SS("ocultaGenerarSS", Accion.X, true),
	//MGR - #1137
	EVITA_CONTROL_TRIPTICO_RANGO_GRUPO("evitaControlTripticoDentroRangoDelGrupo", Accion.X, false),
	//MGR - #1001
	EVITA_CONTROL_CTA_GOBIERNO("evitaControlCtaGobierno", Accion.X, false),
	//MGR - #1165
	EVITA_CONTROL_DESVIOS("evitaControlDesvios", Accion.X, false),
	//MGR - #1184
	OCULTA_CRED_FIDEL_PATACONEX("ocultaCredFidelPataconex", Accion.X, true),
	//MGR - Indica si el combo para SS tranferencia se visualiza o no
	VER_COMBO_VENDEDOR("verComboVendedor",Accion.X,true),
	VER_COMBO_SUCURSAL_ORIGEN("verComboSucursalOrigen",Accion.X,true),
	EVITA_CONTROL_TRIPTICO_RANGO_GRUPO_ADMC("evitaControlTripticoDentroRangoDelGrupoADMC", Accion.X, false),
	VALIDAR_TRIPTICO_AL_GUARDAR("validarTripticoAlGuardar", Accion.X, false),
	VER_COMBO_ESTADO("verComboControl", Accion.X,true),
	//@larce - Indica si en las pantallas de SS se puede ver el panel con los datos del histórico
	VER_HISTORICO("verHistorico",Accion.X,true),
//	MGR - #3466
	PERMITE_CUALQUIER_PRECIO_VTA_PLAN("permiteCualquierPrecioVtaPlan", Accion.X, true);

	private String value;
    private String accion;
	private boolean forBrowser;
	
	PermisosEnum(String value, String accion, boolean forBrowser) {
		this.value=value;
		this.accion=accion;
		this.forBrowser=forBrowser;
	}
	
	public String getValue() {
		return value;
	}
	public boolean isForBrowser() {
		return forBrowser;
	}
	public String getAccion() {
		return accion;
	}
	
	private class Accion {
		public static final String R = "read";
		public static final String W = "write";
		public static final String X = "execute";
	}
}