
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Appuser;

/**
 * Business Service Interface for entity Appuser.
 */
public interface AppuserService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Appuser findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Appuser> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Appuser save(Appuser entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Appuser update(Appuser entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Appuser create(Appuser entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
