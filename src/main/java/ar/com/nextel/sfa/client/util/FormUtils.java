package ar.com.nextel.sfa.client.util;

import java.util.List;

import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.DatePicker;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * Herramientas útiles para trabajar con formularios
 * 
 * @author jlgperez
 * 
 */
public class FormUtils {

	public static void clearFields(List<Widget> fields) {
		for (Widget field : fields) {
			// Limpia TextBox y TextArea
			if (field instanceof TextBoxBase) {
				((TextBoxBase) field).setText("");
			} else if (field instanceof ListBox) {
				((ListBox) field).setSelectedIndex(0);
			} else if (field instanceof CheckBox) {
				((CheckBox) field).setChecked(false);
			} else if (field instanceof TelefonoTextBox) {
				((TelefonoTextBox) field).clean();
			} else if (field instanceof DatePicker){
				((DatePicker) field).resetSelectedDates();
				((DatePicker) field).setSelectedDate(null);
			}
			
		}
	}
}
