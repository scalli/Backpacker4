
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.Album;

/**
 * Business Service Interface for entity Album.
 */
public interface AlbumService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Album findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Album> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Album save(Album entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Album update(Album entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Album create(Album entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
