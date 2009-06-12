package ar.com.nextel.sfa.client.debug;

import com.google.gwt.user.client.ui.UIObject;

/**
 * 
 * Define las constantes de identificación de debug de los componentes.
 * 
 * Las constantes con prefijo GWT_* tienen el prefijo que le agrega GWT al id, por lo que 
 * son las que deberían usar los que leen los ids ( por ejemplo scripts Selenium ).
 * 
 * Las constantes que no tienen el prefijo, son las que debe usar el que setea el valor del ID
 * 
 *  
 * @author ramiro
 *
 */
public class DebugConstants {

	private DebugConstants() {};
	
	public static final String SEP = "-";
	public static final String BUTTON = "Button";
	public static final String TEXTBOX = "TextBox";
	public static final String COMBO = "Combo";
	public static final String TABLE = "Table";

	public static final String GWTPREFIX = UIObject.DEBUG_ID_PREFIX;
	
	public static final String SFA_DEBUGID_PREFIX = "sfa";
	
	public static final String BUSQUEDA_CUENTAS_MODULE_ID = "busquedaCuentas";

	// Constantes de los componentes
	
	public static final String BUSQUEDA_CUENTAS_BOTON_BUSCAR = SFA_DEBUGID_PREFIX + SEP + BUSQUEDA_CUENTAS_MODULE_ID + SEP + BUTTON + SEP + "buscar";
	public static final String GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR = GWTPREFIX + BUSQUEDA_CUENTAS_BOTON_BUSCAR;
	
	public static final String BUSQUEDA_CUENTAS_TEXTBOX_FLOTAID = SFA_DEBUGID_PREFIX + SEP + BUSQUEDA_CUENTAS_MODULE_ID + SEP  + BUTTON + SEP + "flotaId";
	public static final String GWT_BUSQUEDA_CUENTAS_TEXTBOX_FLOTAID = GWTPREFIX + BUSQUEDA_CUENTAS_TEXTBOX_FLOTAID;
	
	public static final String BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS = SFA_DEBUGID_PREFIX + SEP + BUSQUEDA_CUENTAS_MODULE_ID + SEP  + COMBO + SEP + "predefinidas";
	public static final String GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS = GWTPREFIX + BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS;
	
	public static final String BUSQUEDA_CUENTAS_TABLE_RESULT = SFA_DEBUGID_PREFIX + SEP + BUSQUEDA_CUENTAS_MODULE_ID + SEP  + TABLE + SEP + "result";
	public static final String GWT_BUSQUEDA_CUENTAS_TABLE_RESULT = GWTPREFIX + BUSQUEDA_CUENTAS_TABLE_RESULT;
	
	public static final String TABLE_PAGE_BAR_PREV = SFA_DEBUGID_PREFIX + SEP + "prev";
	public static final String TABLE_PAGE_BAR_NEXT = SFA_DEBUGID_PREFIX + SEP + "next";
	public static final String TABLE_PAGE_BAR_FIRST = SFA_DEBUGID_PREFIX + SEP + "first";
	public static final String TABLE_PAGE_BAR_LAST = SFA_DEBUGID_PREFIX + SEP + "last";
	
	public static final String GWT_TABLE_PAGE_BAR_PREV = GWTPREFIX + TABLE_PAGE_BAR_PREV;
	public static final String GWT_TABLE_PAGE_BAR_NEXT = GWTPREFIX + TABLE_PAGE_BAR_NEXT;
	public static final String GWT_TABLE_PAGE_BAR_FIRST = GWTPREFIX + TABLE_PAGE_BAR_FIRST;
	public static final String GWT_TABLE_PAGE_BAR_LAST = GWTPREFIX + TABLE_PAGE_BAR_LAST;
}
