
package org.backpacker4.persistence.services;

import java.util.List;
import java.util.Map;

import org.backpacker4.bean.jpa.AlbumPhotoEntity;

/**
 * Basic persistence operations for entity "AlbumPhoto"
 * 
 * This Bean has a composite Primary Key : AlbumPhotoEntityKey
 *
 */
public interface AlbumPhotoPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param albumPhoto
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(AlbumPhotoEntity albumPhoto) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param idAlbum
	 * @param idPhoto
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Long idAlbum, Long idPhoto) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param albumPhoto
	 */
	public void insert(AlbumPhotoEntity albumPhoto) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param idAlbum
	 * @param idPhoto
	 * @return the entity loaded (or null if not found)
	 */
	public AlbumPhotoEntity load(Long idAlbum, Long idPhoto) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<AlbumPhotoEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<AlbumPhotoEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<AlbumPhotoEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param albumPhoto
	 * @return
	 */
	public AlbumPhotoEntity save(AlbumPhotoEntity albumPhoto) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<AlbumPhotoEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
