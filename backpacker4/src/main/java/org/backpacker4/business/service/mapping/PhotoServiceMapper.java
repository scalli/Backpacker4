
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Photo;
import org.backpacker4.bean.jpa.PhotoEntity;
import org.backpacker4.bean.jpa.PositionEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class PhotoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public PhotoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'PhotoEntity' to 'Photo'
	 * @param photoEntity
	 */
	public Photo mapPhotoEntityToPhoto(PhotoEntity photoEntity) {
		if(photoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Photo photo = map(photoEntity, Photo.class);

		//--- Link mapping ( link to Position )
		if(photoEntity.getPosition() != null) {
			photo.setIdPosition(photoEntity.getPosition().getId());
		}
		return photo;
	}
	
	/**
	 * Mapping from 'Photo' to 'PhotoEntity'
	 * @param photo
	 * @param photoEntity
	 */
	public void mapPhotoToPhotoEntity(Photo photo, PhotoEntity photoEntity) {
		if(photo == null) {
			return;
		}

		//--- Generic mapping 
		map(photo, photoEntity);

		//--- Link mapping ( link : photo )
		if( hasLinkToPosition(photo) ) {
			PositionEntity position1 = new PositionEntity();
			position1.setId( photo.getIdPosition() );
			photoEntity.setPosition( position1 );
		} else {
			photoEntity.setPosition( null );
		}

	}
	
	/**
	 * Verify that Position id is valid.
	 * @param Position Position
	 * @return boolean
	 */
	private boolean hasLinkToPosition(Photo photo) {
		if(photo.getIdPosition() != null) {
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