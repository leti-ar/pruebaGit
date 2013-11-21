package ar.com.nextel.sfa.server.converter;

import java.util.Collection;

import ar.com.nextel.framework.IdentifiableObject;

/**
 * Para el correcto mapeo de una lista de Caratulas
 * 
 * @author mrotger
 *
 */
public class CollectionCaratulaConverter extends CollectionOwnedConverter {
	
	protected IdentifiableObject getById(Collection<IdentifiableObject> collection, Long id) {
		if(id == null){
			return null;
		}
		
		for (IdentifiableObject idObject : collection) {
			if (idObject.getId().equals(id)) {
				return idObject;
			}
		}
		return null;
	}
}
