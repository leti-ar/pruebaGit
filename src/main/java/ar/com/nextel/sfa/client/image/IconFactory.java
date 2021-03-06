package ar.com.nextel.sfa.client.image;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;

/**
 * IconFactory crea widgets HTML e Hyperlinks que muestran un ícono. Provee variantes para poder acceder a los
 * widgets simples, con titles ( tooltip ) y otros agregados.
 * 
 * IconFactory usa los estilos, definidos como constantes, que están definidos en iconos.css.
 * 
 * @author Ramiro
 * 
 */
public class IconFactory {

	private static final String GWT_ICON_BUTTON_STYLE = "gwt-IconButton";
	private static final String ICON_VISTA_PRELIMINAR_STYLE = "icon-vistaPreliminar";
	private static final String ICON_EXCEL_STYLE = "icon-excel";
	private static final String ICON_TILDE_VERDE_STYLE = "icon-tildeVerde";
	private static final String ICON_COPIAR_STYLE = "icon-copiar";
	private static final String ICON_SILVIO_SOLDAN_STYLE = "icon-silvioSoldan";
	private static final String ICON_WORD_STYLE = "icon-word";
	private static final String ICON_LOCKED_STYLE = "icon-locked";
	private static final String ICON_LOCKED_OTHER_STYLE = "icon-locked-other";
	private static final String ICON_LAPIZ_STYLE = "icon-lapiz";
	private static final String ICON_CANCEL_STYLE = "icon-cancel";
	private static final String ICON_LUPA_STYLE = "icon-lupa";
	private static final String ICON_NUEVO_STYLE = "icon-nuevo";
	private static final String ICON_COMPROBAR_NEGRO_STYLE = "icon-comprobar-negro";
	private static final String ICON_COMPROBAR_VERDE_STYLE = "icon-comprobar-verde";
	private static final String ICON_COMPROBAR_ROJO_STYLE = "icon-comprobar-rojo";
	private static final String ICON_SCORING = "icon-scoring";
	private static final String ICON_OPORTUNIDAD = "icon-oportunidad";
	private static final String ICON_PROSPECT = "icon-prospect";
	private static final String ICON_LED_VERDE = "icon-led-verde";
	private static final String ICON_LED_AMARILLO = "icon-led-amarillo";
	private static final String ICON_LED_ROJO = "icon-led-rojo";
	private static final String ICON_GREEN_FLAG_STYLE  = "icon-green-flag";
	private static final String ICON_YELLOW_FLAG_STYLE = "icon-yellow-flag";
	private static final String ICON_RED_FLAG_STYLE    = "icon-red-flag";
	private static final String ICON_REFRESH_STYLE = "icon-refresh";
	private static final String ICON_BOLSA_PESOS_STYLE = "icon-bolsaPesos";
	private static final String ICON_ART_CHINCHE_STYLE = "icon-art-chinche";
	private static final String ICON_ART_CHINCHE_ON_STYLE = "icon-art-chinche-on";
	private static final String ICON_CONFIRMAR_STYLE = "icon-confirmar";
	private static final String ICON_COPIAR_SOLICITUD_STYLE = "icon-copiar-solicitud";
	private static final String ICON_TIF_STYLE = "icon-imagenTIF";
	private static final String ICON_CONSULTAR_SCORING_STYLE = "icon-consultar-scoring";

	public static HTML lockedOther() {
		return createDiv(ICON_LOCKED_OTHER_STYLE);
	}

	public static HTML lockedOther(String title) {
		return createDiv(ICON_LOCKED_OTHER_STYLE, title);
	}

	public static HTML lupa() {
		return createDiv(ICON_LUPA_STYLE);
	}

	public static HTML lupa(String title) {
		return createDiv(ICON_LUPA_STYLE, title);
	}

	public static HTML cancel() {
		return createDiv(ICON_CANCEL_STYLE);
	}

	public static HTML cancel(String title) {
		return createDiv(ICON_CANCEL_STYLE, title);
	}

	public static HTML lapiz() {
		return createDiv(ICON_LAPIZ_STYLE);
	}

	public static HTML lapiz(String title) {
		return createDiv(ICON_LAPIZ_STYLE, title);
	}

	public static Hyperlink lapizAnchor(String targetHistoryToken) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_LAPIZ_STYLE);
	}

	public static Hyperlink lapizAnchor(String targetHistoryToken, String title) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_LAPIZ_STYLE, title);
	}

	public static HTML locked() {
		return createDiv(ICON_LOCKED_STYLE);
	}

	public static HTML locked(String title) {
		return createDiv(ICON_LOCKED_STYLE, title);
	}

	public static HTML word() {
		return createDiv(ICON_WORD_STYLE);
	}

	public static HTML word(String title) {
		return createDiv(ICON_WORD_STYLE, title);
	}

	public static HTML silvioSoldan() {
		return createDiv(ICON_SILVIO_SOLDAN_STYLE);
	}

	public static HTML silvioSoldan(String title) {
		return createDiv(ICON_SILVIO_SOLDAN_STYLE, title);
	}

	public static Hyperlink silvioSoldanAnchor(String targetHistoryToken) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_SILVIO_SOLDAN_STYLE);
	}

	public static Hyperlink silvioSoldanAnchor(String targetHistoryToken, String title) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_SILVIO_SOLDAN_STYLE, title);
	}

	public static HTML copiar() {
		return createDiv(ICON_COPIAR_STYLE);
	}

	public static HTML copiar(String title) {
		return createDiv(ICON_COPIAR_STYLE, title);
	}

	public static HTML tildeVerde() {
		return createDiv(ICON_TILDE_VERDE_STYLE);
	}

	public static HTML tildeVerde(String title) {
		return createDiv(ICON_TILDE_VERDE_STYLE, title);
	}

	public static HTML excel() {
		return createDiv(ICON_EXCEL_STYLE);
	}

	public static HTML excel(String title) {
		return createDiv(ICON_EXCEL_STYLE, title);
	}

	public static HTML vistaPreliminar() {
		return createDiv(ICON_VISTA_PRELIMINAR_STYLE);
	}

	public static HTML vistaPreliminar(String title) {
		return createDiv(ICON_VISTA_PRELIMINAR_STYLE, title);
	}

	public static HTML nuevo() {
		return createDiv(ICON_NUEVO_STYLE);
	}

	public static HTML nuevo(String title) {
		return createDiv(ICON_NUEVO_STYLE, title);
	}

	public static HTML comprobarNegro() {
		return createDiv(ICON_COMPROBAR_NEGRO_STYLE);
	}

	public static HTML comprobarNegro(String title) {
		return createDiv(ICON_COMPROBAR_NEGRO_STYLE, title);
	}

	public static HTML comprobarVerde() {
		return createDiv(ICON_COMPROBAR_VERDE_STYLE);
	}

	public static HTML comprobarVerde(String title) {
		return createDiv(ICON_COMPROBAR_VERDE_STYLE, title);
	}

	public static HTML comprobarRojo() {
		return createDiv(ICON_COMPROBAR_ROJO_STYLE);
	}

	public static HTML comprobarRojo(String title) {
		return createDiv(ICON_COMPROBAR_ROJO_STYLE, title);
	}

	public static HTML scoring() {
		return createDiv(ICON_SCORING);
	}

	public static HTML scoring(String title) {
		return createDiv(ICON_SCORING, title);
	}

	public static HTML oportunidad() {
		return createDiv(ICON_OPORTUNIDAD);
	}

	public static HTML oportunidad(String title) {
		return createDiv(ICON_OPORTUNIDAD, title);
	}

	public static HTML prospect() {
		return createDiv(ICON_PROSPECT);
	}

	public static HTML prospect(String title) {
		return createDiv(ICON_PROSPECT, title);
	}

	public static HTML ledVerde() {
		return createDiv(ICON_LED_VERDE);
	}

	public static HTML ledVerde(String title) {
		return createDiv(ICON_LED_VERDE, title);
	}

	public static HTML ledAmarillo() {
		return createDiv(ICON_LED_AMARILLO);
	}

	public static HTML ledAmarillo(String title) {
		return createDiv(ICON_LED_AMARILLO, title);
	}

	public static HTML ledRojo() {
		return createDiv(ICON_LED_ROJO);
	}

	public static HTML ledRojo(String title) {
		return createDiv(ICON_LED_ROJO, title);
	}

	public static HTML redFlag() {
		return createDiv(ICON_RED_FLAG_STYLE, "alta");
	}
	
	public static HTML yellowFlag() {
		return createDiv(ICON_YELLOW_FLAG_STYLE, "media");
	}
	
	public static HTML greenFlag() {
		return createDiv(ICON_GREEN_FLAG_STYLE, "baja");
	}

	public static HTML refresh(String title) {
		return createDiv(ICON_REFRESH_STYLE, title);
	}
	
	public static HTML bolsaPesos() {
		return createDiv(ICON_BOLSA_PESOS_STYLE);
	}

	public static HTML chinche() {
		return createDiv(ICON_ART_CHINCHE_STYLE);
	}

	public static HTML chinche(String title) {
		return createDiv(ICON_ART_CHINCHE_STYLE, title);
	}
	
	public static HTML chincheOn() {
		return createDiv(ICON_ART_CHINCHE_ON_STYLE);
	}

	public static HTML chincheON(String title) {
		return createDiv(ICON_ART_CHINCHE_ON_STYLE, title);
	}
	
	public static HTML confirmarCaratula() {
		return createDiv(ICON_CONFIRMAR_STYLE);
	}
	
	public static HTML copiarSolicitud() {
		return createDiv(ICON_COPIAR_SOLICITUD_STYLE);
	}
	
	public static HTML imagenTIF() {
		return createDiv(ICON_TIF_STYLE);
	}

	public static HTML imagenTIF(String title) {
		return createDiv(ICON_TIF_STYLE, title);
	}
	
	public static HTML consultarScoring() {
		return createDiv(ICON_CONSULTAR_SCORING_STYLE);
	}

	public static HTML consultarScoring(String title) {
		return createDiv(ICON_CONSULTAR_SCORING_STYLE, title);
	}
	
	/**
	 * Retorna un widget HTML ( DIV ) con el estilo que recibe como parámetro
	 * 
	 * @return
	 */
	private static HTML createDiv(String style) {
		HTML div = new HTML();
		div.addStyleName(style);
		return div;
	}

	/**
	 * Retorna un widget HTML ( DIV ) con el estilo que recibe como parámetro, y el title ( tooltip )
	 * 
	 * @return
	 */
	private static HTML createDiv(String style, String title) {
		HTML div = IconFactory.createDiv(style);
		div.setTitle(title);
		return div;
	}

	/**
	 * Retorna un Hyperlink configurado con los parámetros
	 * 
	 * @param targetHistoryToken
	 *            Destino del link
	 * @param iconStyle
	 *            Icono del anchor
	 * @return
	 */
	private static Hyperlink createAnchor(String targetHistoryToken, String iconStyle) {
		Hyperlink hl = new Hyperlink();
		hl.addStyleName(iconStyle);
		hl.addStyleName(GWT_ICON_BUTTON_STYLE);
		hl.setTargetHistoryToken(targetHistoryToken);
		return hl;
	}

	/**
	 * Retorna un Hyperlink configurado con los parámetros
	 * 
	 * @param targetHistoryToken
	 *            Destino del link
	 * @param iconStyle
	 *            Icono del anchor
	 * @param title
	 *            Title ( tooltip )
	 * @return
	 */
	private static Hyperlink createAnchor(String targetHistoryToken, String iconStyle, String title) {
		Hyperlink hl = IconFactory.createAnchor(targetHistoryToken, iconStyle);
		hl.setTitle(title);
		return hl;
	}

}
