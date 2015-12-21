package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.AlbumPhoto;

/**
 * Business Service Interface for entity AlbumPhoto.
 */
public interface AlbumPhotoService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param idAlbum
	 * @param idPhoto
	 * @return entity
	 */
	AlbumPhoto findById( Long idAlbum, Long idPhoto  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<AlbumPhoto> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	AlbumPhoto save(AlbumPhoto entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	AlbumPhoto update(AlbumPhoto entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	AlbumPhoto create(AlbumPhoto entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param idAlbum
	 * @param idPhoto
	 */
	void delete( Long idAlbum, Long idPhoto );


}
