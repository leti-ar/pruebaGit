package ar.com.nextel.sfa.server.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ar.com.nextel.framework.IdentifiableObject;
import ar.com.nextel.framework.repository.Repository;
import ar.com.nextel.sfa.client.dto.EnumDto;

@Component
public class EnumToDtoConverter implements CustomConverter {

	public Repository repository;
	public DozerBeanMapper dozerMapper;

	@Autowired
	public void setDozerMapper(@Qualifier("dozerMapper") DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	@Autowired
	public void setRepository(@Qualifier("repository") Repository repository) {
		this.repository = repository;
	}

	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		Object result = null;
		if (IdentifiableObject.class.isAssignableFrom(sourceClass) && source != null) {
			result = dozerMapper.map(source, destClass);
		} else if (EnumDto.class.isAssignableFrom(sourceClass) && source != null) {
			Long id = ((EnumDto) source).getId();
			if (id.longValue() > 0) {
				result = repository.retrieve(destClass, id);
			}
		}
		return result;
	}
}
