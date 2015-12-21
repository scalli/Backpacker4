
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Photo;

/**
 * Business Service Interface for entity Photo.
 */
public interface PhotoService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Photo findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Photo> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Photo save(Photo entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Photo update(Photo entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Photo create(Photo entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
