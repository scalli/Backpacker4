package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.FeedbackPhotoEntity;
import org.backpacker4.bean.jpa.FeedbackPhotoEntityKey;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.FeedbackPhotoPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "FeedbackPhoto" )
 *
 */
public class FeedbackPhotoPersistenceJPA extends GenericJpaService<FeedbackPhotoEntity, FeedbackPhotoEntityKey> implements FeedbackPhotoPersistence {

	/**
	 * Constructor
	 */
	public FeedbackPhotoPersistenceJPA() {
		super(FeedbackPhotoEntity.class);
	}

	@Override
	public FeedbackPhotoEntity load( Long idFeedback, Long idPhoto ) {
		// Build the composite key
		FeedbackPhotoEntityKey key = new FeedbackPhotoEntityKey( idFeedback, idPhoto );
		return super.load( key );
	}

	@Override
	public boolean delete( Long idFeedback, Long idPhoto ) {
		// Build the composite key
		FeedbackPhotoEntityKey key = new FeedbackPhotoEntityKey( idFeedback, idPhoto );
		return super.delete( key );
	}

	@Override
	public boolean delete(FeedbackPhotoEntity entity) {
		if ( entity != null ) {
			// Build the composite key
			FeedbackPhotoEntityKey key = new FeedbackPhotoEntityKey( entity.getIdFeedback(), entity.getIdPhoto() );
			return super.delete( key );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("FeedbackPhotoEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
