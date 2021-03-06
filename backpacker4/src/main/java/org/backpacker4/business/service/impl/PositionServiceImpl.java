
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Position;
import org.backpacker4.bean.jpa.PositionEntity;
import java.math.BigDecimal;
import java.util.List;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.business.service.mapping.PositionServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.PositionPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of PositionService
 */
@Component
public class PositionServiceImpl implements PositionService {

	private PositionPersistence positionPersistence;

	@Resource
	private PositionServiceMapper positionServiceMapper;
	
	public PositionServiceImpl() {
		positionPersistence = PersistenceServiceProvider.getService(PositionPersistence.class);
	}
		
	@Override
	public Position findById(Long id) {
		PositionEntity entity = positionPersistence.load(id);
		return positionServiceMapper.mapPositionEntityToPosition(entity);
	}

	@Override
	public List<Position> findAll() {
		List<PositionEntity> entities = positionPersistence.loadAll();
		List<Position> beans = new ArrayList<Position>();
		for(PositionEntity entity : entities) {
			beans.add(positionServiceMapper.mapPositionEntityToPosition(entity));
		}
		return beans;
	}

	@Override
	public Position save(Position position) {
		return update(position) ;
	}

	@Override
	public Position create(Position position) {
		if(positionPersistence.load(position.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		PositionEntity positionEntity = new PositionEntity();
		positionServiceMapper.mapPositionToPositionEntity(position, positionEntity);
		PositionEntity positionEntitySaved = positionPersistence.save(positionEntity);
		return positionServiceMapper.mapPositionEntityToPosition(positionEntitySaved);
	}

	@Override
	public Position update(Position position) {
		PositionEntity positionEntity = positionPersistence.load(position.getId());
		positionServiceMapper.mapPositionToPositionEntity(position, positionEntity);
		PositionEntity positionEntitySaved = positionPersistence.save(positionEntity);
		return positionServiceMapper.mapPositionEntityToPosition(positionEntitySaved);
	}

	@Override
	public void delete(Long id) {
		positionPersistence.delete(id);
	}

	public PositionPersistence getPositionPersistence() {
		return positionPersistence;
	}

	public void setPositionPersistence(PositionPersistence positionPersistence) {
		this.positionPersistence = positionPersistence;
	}

	public PositionServiceMapper getPositionServiceMapper() {
		return positionServiceMapper;
	}

	public void setPositionServiceMapper(PositionServiceMapper positionServiceMapper) {
		this.positionServiceMapper = positionServiceMapper;
	}

}
