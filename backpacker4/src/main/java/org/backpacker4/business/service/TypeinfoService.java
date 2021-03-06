
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Typeinfo;

/**
 * Business Service Interface for entity Typeinfo.
 */
public interface TypeinfoService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Typeinfo findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Typeinfo> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Typeinfo save(Typeinfo entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Typeinfo update(Typeinfo entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Typeinfo create(Typeinfo entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
