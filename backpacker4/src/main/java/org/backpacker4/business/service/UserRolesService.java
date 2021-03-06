
package org.backpacker4.business.service;

import java.util.List;

import org.backpacker4.bean.UserRoles;

/**
 * Business Service Interface for entity UserRoles.
 */
public interface UserRolesService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param iduserRoles
	 * @return entity
	 */
	UserRoles findById( Integer iduserRoles  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<UserRoles> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	UserRoles save(UserRoles entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	UserRoles update(UserRoles entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	UserRoles create(UserRoles entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param iduserRoles
	 */
	void delete( Integer iduserRoles );


}
