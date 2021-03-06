package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.TypeinfoEntity;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.TypeinfoPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Typeinfo" )
 *
 */
public class TypeinfoPersistenceJPA extends GenericJpaService<TypeinfoEntity, Long> implements TypeinfoPersistence {

	/**
	 * Constructor
	 */
	public TypeinfoPersistenceJPA() {
		super(TypeinfoEntity.class);
	}

	@Override
	public TypeinfoEntity load( Long id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Long id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(TypeinfoEntity entity) {
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
				Query query = em.createNamedQuery("TypeinfoEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
