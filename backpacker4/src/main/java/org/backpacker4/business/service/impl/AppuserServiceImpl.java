
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.jpa.AppuserEntity;
import java.util.List;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.mapping.AppuserServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.AppuserPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of AppuserService
 */
@Component
public class AppuserServiceImpl implements AppuserService {

	private AppuserPersistence appuserPersistence;

	@Resource
	private AppuserServiceMapper appuserServiceMapper;
	
	public AppuserServiceImpl() {
		appuserPersistence = PersistenceServiceProvider.getService(AppuserPersistence.class);
	}
		
	@Override
	public Appuser findById(Long id) {
		AppuserEntity entity = appuserPersistence.load(id);
		return appuserServiceMapper.mapAppuserEntityToAppuser(entity);
	}

	@Override
	public List<Appuser> findAll() {
		List<AppuserEntity> entities = appuserPersistence.loadAll();
		List<Appuser> beans = new ArrayList<Appuser>();
		for(AppuserEntity entity : entities) {
			beans.add(appuserServiceMapper.mapAppuserEntityToAppuser(entity));
		}
		return beans;
	}

	@Override
	public Appuser save(Appuser appuser) {
		return update(appuser) ;
	}

	@Override
	public Appuser create(Appuser appuser) {
		if(appuserPersistence.load(appuser.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		AppuserEntity appuserEntity = new AppuserEntity();
		appuserServiceMapper.mapAppuserToAppuserEntity(appuser, appuserEntity);
		AppuserEntity appuserEntitySaved = appuserPersistence.save(appuserEntity);
		return appuserServiceMapper.mapAppuserEntityToAppuser(appuserEntitySaved);
	}

	@Override
	public Appuser update(Appuser appuser) {
		AppuserEntity appuserEntity = appuserPersistence.load(appuser.getId());
		appuserServiceMapper.mapAppuserToAppuserEntity(appuser, appuserEntity);
		AppuserEntity appuserEntitySaved = appuserPersistence.save(appuserEntity);
		return appuserServiceMapper.mapAppuserEntityToAppuser(appuserEntitySaved);
	}

	@Override
	public void delete(Long id) {
		appuserPersistence.delete(id);
	}

	public AppuserPersistence getAppuserPersistence() {
		return appuserPersistence;
	}

	public void setAppuserPersistence(AppuserPersistence appuserPersistence) {
		this.appuserPersistence = appuserPersistence;
	}

	public AppuserServiceMapper getAppuserServiceMapper() {
		return appuserServiceMapper;
	}

	public void setAppuserServiceMapper(AppuserServiceMapper appuserServiceMapper) {
		this.appuserServiceMapper = appuserServiceMapper;
	}

}
