package ar.com.nextel.sfa.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

public class MapperExtended extends DozerBeanMapper {

	public MapperExtended() {
		super();
	}

	public MapperExtended(List filePaths) {
		super(filePaths);
	}

	/**
	 * Convierte una listas de objetos de una cierta clase a otra. Si la lista es vacia devuelve una lista
	 * vacia
	 */
	public <T> List<T> convertList(Collection lista, Class<T> clase) {
		List<T> salida = new ArrayList();
		if (lista != null) {
			for (Iterator iter = lista.iterator(); iter.hasNext();) {
				Object o = iter.next();
				salida.add((T) map(o, clase));
			}
		}
		return salida;
	}

	/**
	 * Convierte una listas de objetos de una cierta clase a otra utilizando un mappeo custom (mapId). Si la
	 * lista es vacia devuelve una lista vacia.
	 */
	public <T> List<T> convertList(List lista, Class<T> clase, String mapId) {
		List<T> salida = new ArrayList();
		if (lista != null) {
			for (Iterator iter = lista.iterator(); iter.hasNext();) {
				Object o = iter.next();
				salida.add((T) map(o, clase, mapId));
			}
		}
		return salida;
	}

}
