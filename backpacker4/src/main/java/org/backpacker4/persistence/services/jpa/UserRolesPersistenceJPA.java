package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.UserRolesEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.UserRolesPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "UserRoles" )
 *
 */
public class UserRolesPersistenceJPA extends GenericJpaService<UserRolesEntity, Integer> implements UserRolesPersistence {

	/**
	 * Constructor
	 */
	public UserRolesPersistenceJPA() {
		super(UserRolesEntity.class);
	}

	@Override
	public UserRolesEntity load( Integer iduserRoles ) {
		return super.load( iduserRoles );
	}

	
	@Override
	public UserRolesEntity save( UserRolesEntity entityToSave ){
		System.out.println("De username in de entity in de persistence is nog steeds: " + entityToSave.getAppuser().getUsername());
		System.out.println("De entity to save is: " + entityToSave.toString());
		return super.save(entityToSave);
}
	
	@Override
	public boolean delete( Integer iduserRoles ) {
		return super.delete( iduserRoles );
	}

	@Override
	public boolean delete(UserRolesEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getIduserRoles() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("UserRolesEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
