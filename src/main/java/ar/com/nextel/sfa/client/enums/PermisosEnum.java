package ar.com.nextel.sfa.client.enums;

public enum PermisosEnum {
	ACCESO("Acceso", Accion.ACCION_X, false), EECC_BUS_CLASE_CUENTA_DESHABILITADA(
			"EECC - Busqueda clase cuenta deshabilitada", Accion.ACCION_W, false), EECC_BUS_USU_CREACION_DESHABILITADA(
			"EECC - Busqueda usuario de creación deshabilitada", Accion.ACCION_W, false), EECC_BUS_FECHA_CREACION_DESDE_DESHABILITADA(
			"EECC - Busqueda fecha de creación desde deshabilitada", Accion.ACCION_W, false), EECC_BUS_FECHA_CREACION_HASTA_DESHABILITADA(
			"EECC - Busqueda fecha de creación hasta deshabilitada", Accion.ACCION_W, false), EECC_BUS_VEND_DESHABILITADA(
			"EECC - Busqueda vendedor deshabilitada", Accion.ACCION_W, false), SERVICIO_DE_SEARCH(
			"Servicio de search", Accion.ACCION_X, false), EECC_LOCKEAR_O_RESERVAR_NUEVA_CUENTA(
			"EECC - lockearOReservarNuevaCuenta", Accion.ACCION_X, false), EECC_SELECTCUENTA(
			"EECC - selectCuenta", Accion.ACCION_X, false), EECC_UPDATE("EECC - update", Accion.ACCION_X,
			false), EECC_GET_CONTACTOS("EECC - getContactos", Accion.ACCION_X, false), EECC_GET_CONTACTO_CUENTA(
			"EECC - getContactoCuenta", Accion.ACCION_X, false), AGREGAR_DIVISION_SUSCRIPTOR(
			"Agregar división/suscriptor", Accion.ACCION_X, false), DATOS_CABECERA_INFOCOM(
			"Datos cabecera Infocom", Accion.ACCION_X, false), INFOMACION_DE_EQUIPOS_INFOCOM(
			"Infomación de equipos Infocom", Accion.ACCION_X, false), RESUMEN_POR_EQUIPOS_INFOCOM(
			"Resumen por equipos Infocom", Accion.ACCION_X, false), DATOS_DE_INFOCOM("Datos de infocom",
			Accion.ACCION_X, false), EECC_GET_LISTAS_PRECIOS_POR_TIPO_SOLICITUD(
			"EECC - getListasPreciosPorTipoSolicitud", Accion.ACCION_X, false), EECC_RESERVAR_NUMERO_TELEFONICO(
			"EECC - reservarNumeroTelefonico", Accion.ACCION_X, false), CREAR_NUEVA_SS("Crear nueva SS",
			Accion.ACCION_X, false), COMBO_TIPOS_DE_SS("Combo Tipos de SS", Accion.ACCION_X, false), COMBO_DE_PLANES(
			"Combo de planes", Accion.ACCION_X, false), DESRRESERVA_DE_NRO_DE_TELEFONO(
			"Desrreserva de Nro de teléfono", Accion.ACCION_X, false), EECC_VERIFY_CUILT(
			"EECC - verifyCUILT", Accion.ACCION_X, false), NORMALIZAR_DOMICILIO("normalizarDomicilio",
			Accion.ACCION_X, false), NORMALIZAR_CPA("normalizarCPA", Accion.ACCION_X, false), CONSULTA_A_VERAZ(
			"Consulta a Veraz", Accion.ACCION_X, false), EECC_GET_DESCRIBIBLES("EECC - getDescribibles",
			Accion.ACCION_X, false), EECC_GET_KNOWNINSTANCE("EECC getKnownInstance", Accion.ACCION_X, false), EECC_GET_ALL_KNOWNINSTANCES(
			"EECC - getAllKnownInstances", Accion.ACCION_X, false), EECC_PERMISSION_FOR(
			"EECC - permissionFor", Accion.ACCION_X, false), GET_CURRENT_USER("Get current user",
			Accion.ACCION_X, false), SERVICIO_DE_SINCRONIZACION("Servicio de sincronización",
			Accion.ACCION_X, false), OPP_RES_Y_OP_CSO("Opp/Res y Op.Cso", Accion.ACCION_X, false), EDITAR_OPP(
			"Editar Opp", Accion.ACCION_X, false), BUSQUEDA_DE_OPP("Busqueda de Opp", Accion.ACCION_X, false), CAMBIAR_ESTADO_OPP(
			"Cambiar Estado Opp", Accion.ACCION_X, false), CANCELAR_OP_EN_CSO("Cancelar Op en Cso",
			Accion.ACCION_X, false), BUSQUEDA_DE_SS_CERRADAS("Busqueda de SS cerradas", Accion.ACCION_X,
			false), NXS_CON_ACC_REMEQUI("NXS_CON_ACC_REMEQUI", Accion.ACCION_X, false), ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU(
			"rootsMenuPanel.cuentasButtonMenu", Accion.ACCION_X, false), ROOTS_MENU_PANEL_VERAZ_BUTTON(
			"rootsMenuPanel.verazButton", Accion.ACCION_X, false), ROOTS_MENU_PANEL_SS_BUTTON(
			"rootsMenuPanel.ssButton", Accion.ACCION_X, false), ROOTS_MENU_PANEL_OPERACIONES_EN_CURSO_BUTTON(
			"rootsMenuPanel.operacionesEnCursoButton", Accion.ACCION_X, false), ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON(
			"rootsMenuPanel.busquedaOportunidadesButton", Accion.ACCION_X, false), EECC_GET_SOLICITUD_SERVICIO_POR_ID(
			"EECC - getSolicitudServicioPorId", Accion.ACCION_X, false), NXS_BUSCAR_CLIENTE(
			"NXS_BUSCAR_CLIENTE", Accion.ACCION_X, true);

	private String value;
	private String accion;
	private boolean forBrowser;

	PermisosEnum(String value, String accion, boolean forBrowser) {
		this.value = value;
		this.accion = accion;
		this.forBrowser = forBrowser;
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
		public static final String ACCION_R = "read";
		public static final String ACCION_W = "write";
		public static final String ACCION_X = "execute";
	}
}