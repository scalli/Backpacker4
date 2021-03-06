
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.jpa.UserRolesEntity;
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.business.service.mapping.UserRolesServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.UserRolesPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of UserRolesService
 */
@Component
public class UserRolesServiceImpl implements UserRolesService {

	private UserRolesPersistence userRolesPersistence;

	@Resource
	private UserRolesServiceMapper userRolesServiceMapper;
	
	public UserRolesServiceImpl() {
		userRolesPersistence = PersistenceServiceProvider.getService(UserRolesPersistence.class);
	}
		
	@Override
	public UserRoles findById(Integer iduserRoles) {
		UserRolesEntity entity = userRolesPersistence.load(iduserRoles);
		return userRolesServiceMapper.mapUserRolesEntityToUserRoles(entity);
	}

	@Override
	public List<UserRoles> findAll() {
		List<UserRolesEntity> entities = userRolesPersistence.loadAll();
		List<UserRoles> beans = new ArrayList<UserRoles>();
		for(UserRolesEntity entity : entities) {
			beans.add(userRolesServiceMapper.mapUserRolesEntityToUserRoles(entity));
		}
		return beans;
	}

	@Override
	public UserRoles save(UserRoles userRoles) {
		return update(userRoles) ;
	}

	@Override
	public UserRoles create(UserRoles userRoles) {
		if(userRolesPersistence.load(userRoles.getIduserRoles()) != null) {
			throw new IllegalStateException("already.exists");
		}
		UserRolesEntity userRolesEntity = new UserRolesEntity();
		userRolesServiceMapper.mapUserRolesToUserRolesEntity(userRoles, userRolesEntity);
		System.out.println("De userrolesServiceMapper heeft zijn werk gedaan in create!");
		System.out.println("De username in de entity is nog steeds: " + userRolesEntity.getAppuser().getUsername());
		UserRolesEntity userRolesEntitySaved = userRolesPersistence.save(userRolesEntity);
		System.out.println("Na de persistence is de username in de entity: " + userRolesEntitySaved.getAppuser().getUsername());
		return userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntitySaved);
	}

	@Override
	public UserRoles update(UserRoles userRoles) {
		UserRolesEntity userRolesEntity = userRolesPersistence.load(userRoles.getIduserRoles());
		userRolesServiceMapper.mapUserRolesToUserRolesEntity(userRoles, userRolesEntity);
		System.out.println("De userrolesServiceMapper heeft zijn werk gedaan in update!");
		UserRolesEntity userRolesEntitySaved = userRolesPersistence.save(userRolesEntity);
		return userRolesServiceMapper.mapUserRolesEntityToUserRoles(userRolesEntitySaved);
	}

	@Override
	public void delete(Integer iduserRoles) {
		userRolesPersistence.delete(iduserRoles);
	}

	public UserRolesPersistence getUserRolesPersistence() {
		return userRolesPersistence;
	}

	public void setUserRolesPersistence(UserRolesPersistence userRolesPersistence) {
		this.userRolesPersistence = userRolesPersistence;
	}

	public UserRolesServiceMapper getUserRolesServiceMapper() {
		return userRolesServiceMapper;
	}

	public void setUserRolesServiceMapper(UserRolesServiceMapper userRolesServiceMapper) {
		this.userRolesServiceMapper = userRolesServiceMapper;
	}

}
