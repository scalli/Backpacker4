
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Position;

/**
 * Business Service Interface for entity Position.
 */
public interface PositionService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Position findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Position> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Position save(Position entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Position update(Position entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Position create(Position entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
