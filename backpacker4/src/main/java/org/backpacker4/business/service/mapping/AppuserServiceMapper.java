
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.jpa.AppuserEntity;
import org.backpacker4.bean.jpa.PositionEntity;
import org.backpacker4.bean.jpa.PhotoEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AppuserServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public AppuserServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'AppuserEntity' to 'Appuser'
	 * @param appuserEntity
	 */
	public Appuser mapAppuserEntityToAppuser(AppuserEntity appuserEntity) {
		if(appuserEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Appuser appuser = map(appuserEntity, Appuser.class);

		//--- Link mapping ( link to Position )
		if(appuserEntity.getPosition() != null) {
			appuser.setIdPosition(appuserEntity.getPosition().getId());
		}
		//--- Link mapping ( link to Photo )
		if(appuserEntity.getPhoto() != null) {
			appuser.setIdPhoto(appuserEntity.getPhoto().getId());
		}
		return appuser;
	}
	
	/**
	 * Mapping from 'Appuser' to 'AppuserEntity'
	 * @param appuser
	 * @param appuserEntity
	 */
	public void mapAppuserToAppuserEntity(Appuser appuser, AppuserEntity appuserEntity) {
		if(appuser == null) {
			return;
		}

		//--- Generic mapping 
		map(appuser, appuserEntity);

		//--- Link mapping ( link : appuser )
		if( hasLinkToPosition(appuser) ) {
			PositionEntity position1 = new PositionEntity();
			position1.setId( appuser.getIdPosition() );
			appuserEntity.setPosition( position1 );
		} else {
			appuserEntity.setPosition( null );
		}

		//--- Link mapping ( link : appuser )
		if( hasLinkToPhoto(appuser) ) {
			PhotoEntity photo2 = new PhotoEntity();
			photo2.setId( appuser.getIdPhoto() );
			appuserEntity.setPhoto( photo2 );
		} else {
			appuserEntity.setPhoto( null );
		}

	}
	
	/**
	 * Verify that Position id is valid.
	 * @param Position Position
	 * @return boolean
	 */
	private boolean hasLinkToPosition(Appuser appuser) {
		if(appuser.getIdPosition() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Verify that Photo id is valid.
	 * @param Photo Photo
	 * @return boolean
	 */
	private boolean hasLinkToPhoto(Appuser appuser) {
		if(appuser.getIdPhoto() != null) {
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