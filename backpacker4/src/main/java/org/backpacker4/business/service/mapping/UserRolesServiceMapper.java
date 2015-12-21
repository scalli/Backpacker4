
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.jpa.UserRolesEntity;
import org.backpacker4.bean.jpa.AppuserEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class UserRolesServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public UserRolesServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'UserRolesEntity' to 'UserRoles'
	 * @param userRolesEntity
	 */
	public UserRoles mapUserRolesEntityToUserRoles(UserRolesEntity userRolesEntity) {
		if(userRolesEntity == null) {
			return null;
		}

		//--- Generic mapping 
		UserRoles userRoles = map(userRolesEntity, UserRoles.class);

		//--- Link mapping ( link to Appuser )
		if(userRolesEntity.getAppuser() != null) {
			userRoles.setUserid(userRolesEntity.getAppuser().getId());
		}
		return userRoles;
	}
	
	/**
	 * Mapping from 'UserRoles' to 'UserRolesEntity'
	 * @param userRoles
	 * @param userRolesEntity
	 */
	public void mapUserRolesToUserRolesEntity(UserRoles userRoles, UserRolesEntity userRolesEntity) {
		if(userRoles == null) {
			return;
		}

		//--- Generic mapping 
		map(userRoles, userRolesEntity);

		//--- Link mapping ( link : userRoles )
		if( hasLinkToAppuser(userRoles) ) {
			AppuserEntity appuser1 = new AppuserEntity();
			appuser1.setId( userRoles.getUserid() );
			userRolesEntity.setAppuser( appuser1 );
		} else {
			userRolesEntity.setAppuser( null );
		}

	}
	
	/**
	 * Verify that Appuser id is valid.
	 * @param Appuser Appuser
	 * @return boolean
	 */
	private boolean hasLinkToAppuser(UserRoles userRoles) {
		if(userRoles.getUserid() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}