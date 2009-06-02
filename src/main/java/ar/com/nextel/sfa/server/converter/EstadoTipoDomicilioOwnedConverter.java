package ar.com.nextel.sfa.server.converter;

/*
 * Nextel
 *
 * SFA Revolution
 */

/**
 * Conversor de TipoDomicilioAsociado a colecciones de Dto.
 */

import org.dozer.CustomConverter;

import ar.com.nextel.model.personas.beans.EstadoTipoDomicilio;

/**
 * @author eSalvador 
 **/
public class EstadoTipoDomicilioOwnedConverter implements CustomConverter {

	private Class modelClass = EstadoTipoDomicilio.class;
	private Class dtoClass = Long.class;

	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		Object result = null;
		if (destClass == null || sourceClass == null) {
			result = EstadoTipoDomicilio.NO.getId();
		} else if (destClass.equals(EstadoTipoDomicilio.class)) {
			EstadoTipoDomicilio estado = new EstadoTipoDomicilio();
			estado.setId((Long) source);
			result = estado;
		} else if (sourceClass.equals(EstadoTipoDomicilio.class)) {
			if (source == null) {
				result = EstadoTipoDomicilio.NO.getId();
			} else {
				result = ((EstadoTipoDomicilio) source).getId();
			}
		}
		return result;
	}

	public void setModelClass(Object object) {
		modelClass = object.getClass();
	}

	public void setDtoClass(Object object) {
		dtoClass = object.getClass();
	}

	public Class getModelClass() {
		return modelClass;
	}

	public Class getDtoClass() {
		return dtoClass;
	}
}
