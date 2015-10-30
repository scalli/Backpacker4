/*
 * Created on 28 okt 2015 ( Time 11:01:06 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.PositionEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.PositionPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Position" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class PositionPersistenceJPA extends GenericJpaService<PositionEntity, Long> implements PositionPersistence {

	/**
	 * Constructor
	 */
	public PositionPersistenceJPA() {
		super(PositionEntity.class);
	}

	@Override
	public PositionEntity load( Long id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Long id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(PositionEntity entity) {
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
				Query query = em.createNamedQuery("PositionEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
