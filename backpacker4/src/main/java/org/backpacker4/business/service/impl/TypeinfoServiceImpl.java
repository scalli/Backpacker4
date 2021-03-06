
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Typeinfo;
import org.backpacker4.bean.jpa.TypeinfoEntity;
import java.util.List;
import org.backpacker4.business.service.TypeinfoService;
import org.backpacker4.business.service.mapping.TypeinfoServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.TypeinfoPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of TypeinfoService
 */
@Component
public class TypeinfoServiceImpl implements TypeinfoService {

	private TypeinfoPersistence typeinfoPersistence;

	@Resource
	private TypeinfoServiceMapper typeinfoServiceMapper;
	
	public TypeinfoServiceImpl() {
		typeinfoPersistence = PersistenceServiceProvider.getService(TypeinfoPersistence.class);
	}
		
	@Override
	public Typeinfo findById(Long id) {
		TypeinfoEntity entity = typeinfoPersistence.load(id);
		return typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(entity);
	}

	@Override
	public List<Typeinfo> findAll() {
		List<TypeinfoEntity> entities = typeinfoPersistence.loadAll();
		List<Typeinfo> beans = new ArrayList<Typeinfo>();
		for(TypeinfoEntity entity : entities) {
			beans.add(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(entity));
		}
		return beans;
	}

	@Override
	public Typeinfo save(Typeinfo typeinfo) {
		return update(typeinfo) ;
	}

	@Override
	public Typeinfo create(Typeinfo typeinfo) {
		if(typeinfoPersistence.load(typeinfo.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		TypeinfoEntity typeinfoEntity = new TypeinfoEntity();
		typeinfoServiceMapper.mapTypeinfoToTypeinfoEntity(typeinfo, typeinfoEntity);
		TypeinfoEntity typeinfoEntitySaved = typeinfoPersistence.save(typeinfoEntity);
		return typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntitySaved);
	}

	@Override
	public Typeinfo update(Typeinfo typeinfo) {
		TypeinfoEntity typeinfoEntity = typeinfoPersistence.load(typeinfo.getId());
		typeinfoServiceMapper.mapTypeinfoToTypeinfoEntity(typeinfo, typeinfoEntity);
		TypeinfoEntity typeinfoEntitySaved = typeinfoPersistence.save(typeinfoEntity);
		return typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntitySaved);
	}

	@Override
	public void delete(Long id) {
		typeinfoPersistence.delete(id);
	}

	public TypeinfoPersistence getTypeinfoPersistence() {
		return typeinfoPersistence;
	}

	public void setTypeinfoPersistence(TypeinfoPersistence typeinfoPersistence) {
		this.typeinfoPersistence = typeinfoPersistence;
	}

	public TypeinfoServiceMapper getTypeinfoServiceMapper() {
		return typeinfoServiceMapper;
	}

	public void setTypeinfoServiceMapper(TypeinfoServiceMapper typeinfoServiceMapper) {
		this.typeinfoServiceMapper = typeinfoServiceMapper;
	}

}
