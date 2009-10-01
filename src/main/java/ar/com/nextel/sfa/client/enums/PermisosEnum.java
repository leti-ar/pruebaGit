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
	AGREGAR_DIVISION_SUSCRIPTOR("Agregar división/suscriptor",Accion.X,false),
	DATOS_CABECERA_INFOCOM("Datos cabecera Infocom",Accion.X,false),
	INFOMACION_DE_EQUIPOS_INFOCOM("Infomación de equipos Infocom",Accion.X,false),
	RESUMEN_POR_EQUIPOS_INFOCOM("Resumen por equipos Infocom",Accion.X,false),
	DATOS_DE_INFOCOM("Datos de infocom",Accion.X,false),
	EECC_GET_LISTAS_PRECIOS_POR_TIPO_SOLICITUD("EECC - getListasPreciosPorTipoSolicitud",Accion.X,false),
	EECC_RESERVAR_NUMERO_TELEFONICO("EECC - reservarNumeroTelefonico",Accion.X,false),
	CREAR_NUEVA_SS("Crear nueva SS",Accion.X,false),
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
	ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU("rootsMenuPanel.cuentasButtonMenu",Accion.X,false),
	ROOTS_MENU_PANEL_VERAZ_BUTTON("rootsMenuPanel.verazButton",Accion.X,false),
	ROOTS_MENU_PANEL_SS_BUTTON("rootsMenuPanel.ssButton",Accion.X,false),
	ROOTS_MENU_PANEL_OPERACIONES_EN_CURSO_BUTTON("rootsMenuPanel.operacionesEnCursoButton",Accion.X,false),
	ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON("rootsMenuPanel.busquedaOportunidadesButton",Accion.X,false),
	EECC_GET_SOLICITUD_SERVICIO_POR_ID("EECC - getSolicitudServicioPorId",Accion.X,false),
	NXS_BUSCAR_CLIENTE("NXS_BUSCAR_CLIENTE",Accion.X,true),
	SCORING_CHECKED("scoringChecked",Accion.X,true);

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