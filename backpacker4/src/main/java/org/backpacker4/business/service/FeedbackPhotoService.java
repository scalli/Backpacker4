
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.FeedbackPhoto;

/**
 * Business Service Interface for entity FeedbackPhoto.
 */
public interface FeedbackPhotoService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param idFeedback
	 * @param idPhoto
	 * @return entity
	 */
	FeedbackPhoto findById( Long idFeedback, Long idPhoto  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<FeedbackPhoto> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	FeedbackPhoto save(FeedbackPhoto entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	FeedbackPhoto update(FeedbackPhoto entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	FeedbackPhoto create(FeedbackPhoto entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param idFeedback
	 * @param idPhoto
	 */
	void delete( Long idFeedback, Long idPhoto );


}
