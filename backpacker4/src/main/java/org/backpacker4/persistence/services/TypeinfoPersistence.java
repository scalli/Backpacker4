package org.backpacker4.persistence.services;

import java.util.List;
import java.util.Map;

import org.backpacker4.bean.jpa.TypeinfoEntity;

/**
 * Basic persistence operations for entity "Typeinfo"
 * 
 * This Bean has a basic Primary Key : Long
 *
 */
public interface TypeinfoPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param typeinfo
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(TypeinfoEntity typeinfo) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param id
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Long id) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param typeinfo
	 */
	public void insert(TypeinfoEntity typeinfo) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param id
	 * @return the entity loaded (or null if not found)
	 */
	public TypeinfoEntity load(Long id) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<TypeinfoEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<TypeinfoEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<TypeinfoEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param typeinfo
	 * @return
	 */
	public TypeinfoEntity save(TypeinfoEntity typeinfo) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<TypeinfoEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
