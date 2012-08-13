package ar.com.nextel.sfa.server.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ar.com.nextel.framework.IdentifiableObject;
import ar.com.nextel.model.support.owner.CollectionOwned;
import ar.com.nextel.sfa.client.dto.IdentifiableDto;

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
