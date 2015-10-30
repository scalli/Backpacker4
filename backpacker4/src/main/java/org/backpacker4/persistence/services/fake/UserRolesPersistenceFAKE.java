/*
 * Created on 28 okt 2015 ( Time 11:01:07 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.persistence.services.fake;

import java.util.List;
import java.util.Map;

import org.backpacker4.bean.jpa.UserRolesEntity;
import org.backpacker4.persistence.commons.fake.GenericFakeService;
import org.backpacker4.persistence.services.UserRolesPersistence;

/**
 * Fake persistence service implementation ( entity "UserRoles" )
 *
 * @author Telosys Tools Generator
 */
public class UserRolesPersistenceFAKE extends GenericFakeService<UserRolesEntity> implements UserRolesPersistence {

	public UserRolesPersistenceFAKE () {
		super(UserRolesEntity.class);
	}
	
	protected UserRolesEntity buildEntity(int index) {
		UserRolesEntity entity = new UserRolesEntity();
		// Init fields with mock values
		entity.setIduserRoles( nextInteger() ) ;
		entity.setUserrole( nextString() ) ;
		return entity ;
	}
	
	
	public boolean delete(UserRolesEntity entity) {
		log("delete ( UserRolesEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer iduserRoles ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(UserRolesEntity entity) {
		log("insert ( UserRolesEntity : " + entity + ")" ) ;
	}

	public UserRolesEntity load( Integer iduserRoles ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<UserRolesEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<UserRolesEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<UserRolesEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public UserRolesEntity save(UserRolesEntity entity) {
		log("insert ( UserRolesEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<UserRolesEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
