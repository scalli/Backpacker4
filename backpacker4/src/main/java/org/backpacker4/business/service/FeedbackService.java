
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Feedback;

/**
 * Business Service Interface for entity Feedback.
 */
public interface FeedbackService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Feedback findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Feedback> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Feedback save(Feedback entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Feedback update(Feedback entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Feedback create(Feedback entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
