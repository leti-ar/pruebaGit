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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.nextel.framework.IdentifiableObject;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.model.solicitudes.beans.LineaSolicitudServicio;
import ar.com.nextel.model.support.owner.CollectionOwned;
import ar.com.nextel.sfa.client.dto.IdentifiableDto;
import ar.com.nextel.sfa.client.dto.LineaSolicitudServicioDto;
import ar.com.nextel.sfa.server.util.MapperExtended;

/**
 * Conversor de colecciones para Dozer.
 */
@Component
public class CollectionOwnedConverter implements CustomConverter {

	private MapperExtended mapper;
	private Repository repository;
	private Class modelClass = LineaSolicitudServicio.class;
	private Class dtoClass = LineaSolicitudServicioDto.class;

	@Autowired
	public void setDozerMapper(@Qualifier("dozerMapper") MapperExtended dozerMapper) {
		this.mapper = dozerMapper;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
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
	 * Actualiza una coleccion de objetos de modelo
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
			IdentifiableObject destItem = getById(destination,sourceItem.getId());
			if(destItem != null){
				mapper.map(sourceItem, destItem);
				newDestination.add(destItem);
			} else {
				newDestination.add(mapper.map(sourceItem, modelClass));
			}
		}
		destination.clear();
		// Uso for para que funcione el interceptor
		for (Object object : newDestination) {
			destination.add(object);
		}
		return ((CollectionOwned) destination).getCollection();
//		List removeSet = new ArrayList();
//		for (Iterator iterator = destination.iterator(); iterator.hasNext();) {
//			IdentifiableObject o = (IdentifiableObject) iterator.next();
//			if (!source.contains(o)) {
//				removeSet.add(o);
//			}
//		}
//		Iterator sourceIt = source.iterator();
//		for (; sourceIt.hasNext();) {
//			Object sourceItem = sourceIt.next();
//			IdentifiableObject o = (IdentifiableObject) mapper.map(sourceItem, modelClass);
//			if (o.getId() == null) {
//				destination.add(o);
//			} else if (destination.contains(o)) {
//				mapper.map(o, repository.retrieve(o.getClass(), o.getId()));
//			}
//		}
//		destination.removeAll(removeSet);
//
//		return ((CollectionOwned) destination).getCollection();
	}

	private IdentifiableObject getById(Collection<IdentifiableObject> collection, Long id) {
		for (IdentifiableObject idObject : collection) {
			if (idObject.getId().equals(id)) {
				return idObject;
			}
		}
		return null;
	}
	//
	// /**
	// * Transforma un wcto en un objeto de modelo. En el caso de que sea un wcto nuevo crea el objeto, sino
	// lo
	// * obtiene del repositorio y lo actualiza
	// *
	// * @param wcto
	// * @return
	// */
	// private Object transformWctoToModelObject(WCTO wcto) {
	// Class modelObjectClass = classResolver.resolve(wcto);
	//
	// Long id = null;
	// if (BaseIdentifiableBusinessWCTO.class.isAssignableFrom(wcto.getClass())) {
	// id = ((BaseIdentifiableBusinessWCTO) wcto).getId();
	// }
	// Object modelObject = obtainModelObject(modelObjectClass, id);
	// wctoTransformer.transform(wcto, modelObject);
	// return modelObject;
	// }
	//
	// /**
	// * Determina si el objeto es nuevo o no.
	// *
	// * @param collection
	// * @param object
	// * @return
	// */
	// private boolean newItem(Collection collection, Object object) {
	// return !collection.contains(object);
	// }
	//
	// /**
	// * Obtiene un objeto de modelo en el caso de que ya exista o lo crea en el caso contrario.
	// *
	// * @param modelObjectClass
	// * @param id
	// * @return
	// */
	// private Object obtainModelObject(Class modelObjectClass, Long id) {
	// Object modelObject = null;
	// if (id != null && id.longValue() > 0) {
	// modelObject = this.repository.retrieve(modelObjectClass, id);
	// } else {
	// modelObject = this.repository.createNewObject(modelObjectClass);
	// }
	// return modelObject;
	// }

}
