

package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.AlbumEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.AlbumPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Album" )
 *
 */
public class AlbumPersistenceJPA extends GenericJpaService<AlbumEntity, Long> implements AlbumPersistence {

	/**
	 * Constructor
	 */
	public AlbumPersistenceJPA() {
		super(AlbumEntity.class);
	}

	@Override
	public AlbumEntity load( Long id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Long id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(AlbumEntity entity) {
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
				Query query = em.createNamedQuery("AlbumEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
