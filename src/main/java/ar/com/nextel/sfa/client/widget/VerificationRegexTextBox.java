package ar.com.nextel.sfa.client.widget;

import ar.com.snoop.gwt.commons.client.widget.RegexTextBox;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Verifica que se cumpla la expresion regular en el contenido del texbox
 * mientras se está escribiendo como cuando finaliza el texto;
 * Por este motivo maneja dos expresiones regulares, una lazy que sirve mientras se escribe y la otra definitiva
 * que se chequea una vez terminado el ingreso
 * En caso que no cumpla la condicion muestra un error en pantalla.
 * @author jmonczer
 */

public class VerificationRegexTextBox extends RegexTextBox {
	private MensajeRegex mensajeRegex;

	/**
	 * Constructor VerificacionRegexTextBox
	 * @param regexLazyPattern  Expresion regular que debe satisfacer mientras se escribe el texto
	 * @param mensajeRegex      Contiene la Expresion regular que debe satisfacer al completar todo el texto
	 * 							y el Mensaje que se muestra si no cumple la expresion regular regexPattern
	 */
	public VerificationRegexTextBox(String regexLazyPattern,MensajeRegex mensajeRegex) {
		super(regexLazyPattern,true);
		this.mensajeRegex = mensajeRegex;
	}

	protected BlurHandler getBlurHandler() {
		return new BlurHandler() {
			public void onBlur(BlurEvent event) {
				TextBox sender = (TextBox) event.getSource();
				String text = (sender).getText();
				if ("".equals(text)) {
					return;
				}
				if (text.matches(mensajeRegex.getRegexPattern())) {
					doOnBlurValidation(sender);
				} else {
					doOnBlurValidationError(sender);
				}
			}
		};
	}

	protected void doOnBlurValidationError(TextBox sender) {
		ErrorDialog.getInstance().show(mensajeRegex.getMensaje(), false);
	}

	protected void doOnBlurValidation(TextBox sender) {
	}
	
	public MensajeRegex getMensajeRegex() {
		return this.mensajeRegex;
	}
	
}
