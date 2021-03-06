
package org.backpacker4.persistence.services;

import java.util.List;
import java.util.Map;

import org.backpacker4.bean.jpa.FeedbackPhotoEntity;

/**
 * Basic persistence operations for entity "FeedbackPhoto"
 * 
 * This Bean has a composite Primary Key : FeedbackPhotoEntityKey
 *
 */
public interface FeedbackPhotoPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param feedbackPhoto
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(FeedbackPhotoEntity feedbackPhoto) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param idFeedback
	 * @param idPhoto
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Long idFeedback, Long idPhoto) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param feedbackPhoto
	 */
	public void insert(FeedbackPhotoEntity feedbackPhoto) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param idFeedback
	 * @param idPhoto
	 * @return the entity loaded (or null if not found)
	 */
	public FeedbackPhotoEntity load(Long idFeedback, Long idPhoto) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<FeedbackPhotoEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<FeedbackPhotoEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<FeedbackPhotoEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param feedbackPhoto
	 * @return
	 */
	public FeedbackPhotoEntity save(FeedbackPhotoEntity feedbackPhoto) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<FeedbackPhotoEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
