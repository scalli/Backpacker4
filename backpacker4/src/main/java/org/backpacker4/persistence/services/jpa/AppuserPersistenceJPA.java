
package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.AppuserEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.AppuserPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Appuser" )
 *
 */
public class AppuserPersistenceJPA extends GenericJpaService<AppuserEntity, Long> implements AppuserPersistence {

	/**
	 * Constructor
	 */
	public AppuserPersistenceJPA() {
		super(AppuserEntity.class);
	}

	@Override
	public AppuserEntity load( Long id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Long id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(AppuserEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getId() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("AppuserEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
