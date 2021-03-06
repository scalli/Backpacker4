
package org.backpacker4.persistence.services;

import java.util.List;
import java.util.Map;

import org.backpacker4.bean.jpa.UserRolesEntity;

/**
 * Basic persistence operations for entity "UserRoles"
 * 
 * This Bean has a basic Primary Key : Integer
 *
 */
public interface UserRolesPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param userRoles
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(UserRolesEntity userRoles) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param iduserRoles
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Integer iduserRoles) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param userRoles
	 */
	public void insert(UserRolesEntity userRoles) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param iduserRoles
	 * @return the entity loaded (or null if not found)
	 */
	public UserRolesEntity load(Integer iduserRoles) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<UserRolesEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<UserRolesEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<UserRolesEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param userRoles
	 * @return
	 */
	public UserRolesEntity save(UserRolesEntity userRoles) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<UserRolesEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
