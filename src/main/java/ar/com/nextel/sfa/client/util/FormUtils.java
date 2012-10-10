package ar.com.nextel.sfa.client.util;

import java.util.List;

import ar.com.nextel.sfa.client.widget.FechaDatePicker;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.snoop.gwt.commons.client.widget.datepicker.DatePicker;
import ar.com.snoop.gwt.commons.client.widget.datepicker.SimpleDatePicker;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
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
				((CheckBox) field).setValue(false);
			} else if (field instanceof TelefonoTextBox) {
				((TelefonoTextBox) field).clean();
			} else if (field instanceof DatePicker) {
				((DatePicker) field).resetSelectedDates();
				((DatePicker) field).setSelectedDate(null);
			} else if (field instanceof Label) {
				((Label) field).setText("");
			} else if (field instanceof FechaDatePicker) {
				((FechaDatePicker) field).clean();
			}
		}
	}

	/**
	 * @author eSalvador Metodo que ademas de limpiar los datos cargados, setea en Enable(true) los
	 *         componentes.
	 */
	public static void cleanAndEnableFields(List<Widget> fields) {
		for (Widget field : fields) {
			// Limpia TextBox y TextArea
			if (field instanceof TextBoxBase) {
				((TextBoxBase) field).setText("");
				((TextBoxBase) field).setEnabled(true);
				((TextBoxBase) field).setReadOnly(false);
			} else if (field instanceof ListBox) {
				((ListBox) field).setSelectedIndex(0);
				((ListBox) field).setEnabled(true);
			} else if (field instanceof CheckBox) {
				((CheckBox) field).setValue(false);
				((CheckBox) field).setEnabled(true);
			} else if (field instanceof TelefonoTextBox) {
				((TelefonoTextBox) field).clean();
			} else if (field instanceof DatePicker) {
				((DatePicker) field).resetSelectedDates();
				((DatePicker) field).setSelectedDate(null);
				((DatePicker) field).setEnabled(true);
			} else if (field instanceof FechaDatePicker) {
				((FechaDatePicker) field).clean();
			}
		}
	}

	/**
	 * @author eSalvador Metodo que SOLO setea en Enable(TRUE) los componentes.
	 */
	public static void enableFields(List<Widget> fields) {
		for (Widget field : fields) {
			if (field instanceof TextBoxBase) {
				((TextBoxBase) field).setEnabled(true);
				((TextBoxBase) field).setReadOnly(false);
			} else if (field instanceof ListBox) {
				((ListBox) field).setEnabled(true);
			} else if (field instanceof CheckBox) {
				((CheckBox) field).setEnabled(true);
			} else if (field instanceof TelefonoTextBox) {
				// TODO: Falta averiguar como se habilita/deshabilita.
			} else if (field instanceof DatePicker) {
				((DatePicker) field).setEnabled(true);
			}
		}
	}

	/**
	 * @author eSalvador Metodo que SOLO setea en Enable(FALSE) los componentes.
	 */
	public static void disableFields(List<Widget> fields) {
		for (Widget field : fields) {
			if (field instanceof TextBoxBase) {
				((TextBoxBase) field).setEnabled(false);
				((TextBoxBase) field).setReadOnly(true);
			} else if (field instanceof ListBox) {
				((ListBox) field).setEnabled(false);
			} else if (field instanceof CheckBox) {
				((CheckBox) field).setEnabled(false);
			} else if (field instanceof TelefonoTextBox) {
				// TODO: Falta averiguar como se habilita/deshabilita.
			} else if (field instanceof SimpleDatePicker) {
				((SimpleDatePicker) field).getTextBox().setEnabled(false);
				((SimpleDatePicker) field).setEnabled(false);
			} else if (field instanceof DatePicker) {
				((DatePicker) field).setEnabled(false);
			}
		}
	}

	/**
	 * 
	 * @param field
	 * @param dato
	 * @return
	 */
	public static boolean fieldDirty(Widget field, String dato) {
		if (dato == null)
			dato = "";
		if (field == null && !dato.equals(""))
			return true;
		String fieldText = "";
		if (field instanceof DatePicker) {
			if (!((DatePicker) field).isVisible()) 
				return false;
			fieldText = ((DatePicker) field).getTextBox().getText();
		} else if (field instanceof TextBox) {
			if (!((TextBox) field).isVisible() || !((TextBox) field).isEnabled())
				return false;
			fieldText = ((TextBox) field).getText();
		} else if (field instanceof TextArea) {
			if (!((TextArea) field).isVisible() || !((TextArea) field).isEnabled())
				return false;
			fieldText = ((TextArea) field).getText();
		} else if (field instanceof ListBox) {
			if (!((ListBox) field).isVisible() || !((ListBox) field).isEnabled()) 
				return false;
			fieldText = ((ListBox) field).getValue((((ListBox) field).getSelectedIndex()));
		}
		if (!fieldText.trim().equals(((String) dato).trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param field
	 * @return
	 */
	public static boolean fieldEmpty(Widget field) {
		if (field == null)
			return true;
		String fieldText = "";
		if (field instanceof DatePicker) {
			fieldText = ((DatePicker) field).getTextBox().getText();
		} else if (field instanceof TextBox) {
			fieldText = ((TextBox) field).getText();
		} else if (field instanceof TextArea) {
			fieldText = ((TextArea) field).getText();
		} else if (field instanceof ListBox) {
			fieldText = ((ListBox) field).getItemText((((ListBox) field).getSelectedIndex()));
		}
		return fieldText.trim().equals("");
	}
}
