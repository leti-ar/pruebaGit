package ar.com.nextel.sfa.client.widget;

import java.util.ArrayList;
import java.util.List;

import ar.com.nextel.sfa.client.util.FormUtils;

import com.google.gwt.user.client.ui.Widget;

/**
 * Mantiene la información a ser editada por un componente visual. Generalmente guarda todos los componetes
 * visuales de edición, como ser TextBoxs, ListBox, DatePickers y otros. También es el encargado de
 * sincronizar los datos entre estos componentes y el DTO correspondiente.
 * 
 * @author jlgperez
 */
public class UIData {
	protected List<Widget> fields = new ArrayList<Widget>();

	public void clean() {
		FormUtils.clearFields(fields);
	}
}
