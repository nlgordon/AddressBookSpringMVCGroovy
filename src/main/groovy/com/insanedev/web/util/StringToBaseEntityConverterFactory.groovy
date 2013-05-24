package com.insanedev.web.util

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import com.insanedev.BaseEntity;

@Component
class StringToBaseEntityConverterFactory implements ConverterFactory<String, BaseEntity>{
	
	@PersistenceContext
	EntityManager em
	
	public <T extends BaseEntity> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToBaseEntityConverter(em, targetType)
	}
	
	class StringToBaseEntityConverter<T extends BaseEntity> implements Converter<String, T> {
		EntityManager em
		Class<T> baseEntityType
		
		public StringToBaseEntityConverter(EntityManager em, Class<T> baseEntityType) {
			this.em = em
			this.baseEntityType = baseEntityType
		}
		
		public T convert(String source) {
			return em.find(baseEntityType, Long.parseLong(source))
		}
	}
}
