
package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.PhotoEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.PhotoPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Photo" )
 *
 */
public class PhotoPersistenceJPA extends GenericJpaService<PhotoEntity, Long> implements PhotoPersistence {

	/**
	 * Constructor
	 */
	public PhotoPersistenceJPA() {
		super(PhotoEntity.class);
	}

	@Override
	public PhotoEntity load( Long id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Long id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(PhotoEntity entity) {
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
				Query query = em.createNamedQuery("PhotoEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
