/*
 * Nextel
 *
 * SFA Revolution
 */
package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;

import ar.com.nextel.framework.IdentifiableObject;
import ar.com.nextel.framework.repository.Repository;

/**
 * Clase: IdentifiableObjectToIdConverter Encargado de convertir <code>IdentifiableObjects</code>.
 * <p>
 * Este converter se utiliza para el caso particular de transformación de objetos <tt>Tipo</tt>. Al convertir
 * a <code>WCTO</code> se transforma solo el <code>id</code> del <code>IdentifiableObject</code>, al convertir
 * a <code>IdentifiableObject</code> se obtiene del <code>Repository</code> el objeto con el <code>id</code>
 * obtenido. Creada: Mar 27, 2007
 * 
 * @author llowenthal, mroman
 */
public class IdentifiableObjectToIdConverter implements CustomConverter {

	private Repository repository;

	/**
	 * Carga el repository de la aplicacion (para spring)
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		Object result = null;
		if (sourceClass.equals(Long.class) && source != null) {
			Long id = (Long) source;
			if (id.longValue() > 0) {
				result = repository.retrieve(destClass, id);
			}
		} else if (destClass.equals(Long.class) && source != null) {
			result = ((IdentifiableObject) source).getId();
		}
		return result;
	}
}
