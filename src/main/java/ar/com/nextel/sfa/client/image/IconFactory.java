package ar.com.nextel.sfa.client.image;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;

/**
 * IconFactory crea widgets HTML e Hyperlinks que muestran un ícono.
 * Provee variantes para poder acceder a los widgets simples, con titles ( tooltip ) y otros agregados.
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

	public static HTML lockedOther() {
		return createDiv(ICON_LOCKED_OTHER_STYLE);
	}

	public static HTML lockedOther(String title) {
		return createDiv(ICON_LOCKED_OTHER_STYLE,title);
	}

	
	public static HTML lupa() {
		return createDiv(ICON_LUPA_STYLE);
	}

	public static HTML lupa(String title) {
		return createDiv(ICON_LUPA_STYLE,title);
	}

	public static HTML cancel() {
		return createDiv(ICON_CANCEL_STYLE);
	}

	public static HTML cancel(String title) {
		return createDiv(ICON_CANCEL_STYLE,title);
	}

	public static HTML lapiz() {
		return createDiv(ICON_LAPIZ_STYLE);
	}

	public static HTML lapiz(String title) {
		return createDiv(ICON_LAPIZ_STYLE,title);
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
		return createDiv(ICON_LOCKED_STYLE,title);
	}

	public static HTML word() {
		return createDiv(ICON_WORD_STYLE);
	}

	public static HTML word(String title) {
		return createDiv(ICON_WORD_STYLE,title);
	}

	public static HTML silvioSoldan() {
		return createDiv(ICON_SILVIO_SOLDAN_STYLE);
	}

	public static HTML silvioSoldan(String title) {
		return createDiv(ICON_SILVIO_SOLDAN_STYLE,title);
	}	
	
	public static Hyperlink silvioSoldanAnchor(String targetHistoryToken) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_SILVIO_SOLDAN_STYLE);
	}
	
	public static Hyperlink silvioSoldanAnchor(String targetHistoryToken, String title) {
		return IconFactory.createAnchor(targetHistoryToken, ICON_SILVIO_SOLDAN_STYLE,title);
	}
	
	public static HTML copiar() {
		return createDiv(ICON_COPIAR_STYLE);
	}
	
	public static HTML copiar(String title) {
		return createDiv(ICON_COPIAR_STYLE,title);
	}
	
	public static HTML tildeVerde() {
		return createDiv(ICON_TILDE_VERDE_STYLE);
	}
	
	public static HTML tildeVerde(String title) {
		return createDiv(ICON_TILDE_VERDE_STYLE,title);
	}
	
	public static HTML excel() {
		return createDiv(ICON_EXCEL_STYLE);
	}
	
	public static HTML excel(String title) {
		return createDiv(ICON_EXCEL_STYLE,title);
	}
	
	public static HTML vistaPreliminar() {
		return createDiv(ICON_VISTA_PRELIMINAR_STYLE);
	}

	public static HTML vistaPreliminar(String title) {
		return createDiv(ICON_VISTA_PRELIMINAR_STYLE,title);
	}


	/**
	 * Retorna un widget HTML ( DIV ) con el estilo que recibe como parámetro
	 * @return
	 */
	private static HTML createDiv(String style) {
		HTML div = new HTML();
		div.addStyleName(style);
		return div;
	}
	
	/**
	 * Retorna un widget HTML ( DIV ) con el estilo que recibe como parámetro,
	 * y el title ( tooltip )
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
	 * @param targetHistoryToken Destino del link
	 * @param iconStyle Icono del anchor
	 * @return
	 */
	private static Hyperlink createAnchor(String targetHistoryToken,String iconStyle) {
		Hyperlink hl = new Hyperlink();
		hl.addStyleName(iconStyle);
		hl.addStyleName(GWT_ICON_BUTTON_STYLE);
		hl.setTargetHistoryToken(targetHistoryToken);
		return hl;
	}

	/**
	 * Retorna un Hyperlink configurado con los parámetros
	 * 
	 * @param targetHistoryToken Destino del link
	 * @param iconStyle Icono del anchor
	 * @param title Title ( tooltip )
	 * @return
	 */
	private static Hyperlink createAnchor(String targetHistoryToken,String iconStyle, String title) {
		Hyperlink hl = IconFactory.createAnchor(targetHistoryToken,iconStyle);
		hl.setTitle(title);
		return hl;
	}

}
