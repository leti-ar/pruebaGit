/*
 * Nextel
 *
 * SFA Revolution
 */
package ar.com.nextel.sfa.server.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.dozer.CustomConverter;

import ar.com.nextel.framework.IdentifiableObject;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.support.owner.CollectionOwned;
import ar.com.nextel.sfa.client.dto.IdentifiableDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.server.util.MapperExtended;

/**
 * Conversor de CollectionOwned a colecciones de Dto.
 */
public class CollectionOwnedConverter implements CustomConverter {

	private MapperExtended mapper;
	private Class modelClass = LineaSolicitudServicio.class;
	private Class dtoClass = LineaSolicitudServicioDto.class;
	private Repository repository;

	public void setDozerMapper(MapperExtended dozerMapper) {
		this.mapper = dozerMapper;
	}

	public void setModelClass(Object object) {
		modelClass = object.getClass();
	}

	public void setDtoClass(Object object) {
		dtoClass = object.getClass();
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		Object result = null;
		if (destination instanceof CollectionOwned) {
			result = dtoCollectionToCollectionOwned((Collection) destination, (Collection) source);
		} else if (source instanceof CollectionOwned) {
			result = mapper.convertList((Collection) source, dtoClass);
		}
		return result;
	}

	/**
	 * Actualiza una coleccion con objetos de modelo desde una de Dtos
	 * 
	 * @param innerCollection
	 * @param wctoCollection
	 * @return
	 */
	private Object dtoCollectionToCollectionOwned(Collection destination, Collection source) {
		if (source == null || source.isEmpty()) {
			Collection dest = ((CollectionOwned) destination).getCollection();
			dest.clear();
			return dest;
		}
		List newDestination = new ArrayList();
		for (Iterator iterator = source.iterator(); iterator.hasNext();) {
			IdentifiableDto sourceItem = (IdentifiableDto) iterator.next();
			IdentifiableObject destItem = getById(destination, sourceItem.getId());
			if (destItem != null) {
				mapper.map(sourceItem, destItem);
				newDestination.add(destItem);
			} else {
				Object o = repository.createNewObject(modelClass);
				mapper.map(sourceItem, o);
				newDestination.add(o);
			}
		}
		destination.clear();
		// Les asigno un fake Id a los nuevos para que me deje agregar varios objetos con id en null en el
		// Set. Esto es por un problema de arquitectura que redefinio los equals.
		long fakeId = 0;
		// Uso "for" y "add" para que funcione el interceptor
		for (Object object : newDestination) {
			boolean needFakeId = ((IdentifiableObject) object).getId() == null;
			if (needFakeId)
				((IdentifiableObject) object).setId(fakeId--);
			destination.add(object);
			if (needFakeId)
				((IdentifiableObject) object).setId(null);
		}
		return ((CollectionOwned) destination).getCollection();
	}

	private IdentifiableObject getById(Collection<IdentifiableObject> collection, Long id) {
		for (IdentifiableObject idObject : collection) {
			if (idObject.getId().equals(id)) {
				return idObject;
			}
		}
		return null;
	}

}
