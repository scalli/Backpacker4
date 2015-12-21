
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.AlbumPhoto;
import org.backpacker4.bean.jpa.AlbumPhotoEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AlbumPhotoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public AlbumPhotoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'AlbumPhotoEntity' to 'AlbumPhoto'
	 * @param albumPhotoEntity
	 */
	public AlbumPhoto mapAlbumPhotoEntityToAlbumPhoto(AlbumPhotoEntity albumPhotoEntity) {
		if(albumPhotoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		AlbumPhoto albumPhoto = map(albumPhotoEntity, AlbumPhoto.class);

		return albumPhoto;
	}
	
	/**
	 * Mapping from 'AlbumPhoto' to 'AlbumPhotoEntity'
	 * @param albumPhoto
	 * @param albumPhotoEntity
	 */
	public void mapAlbumPhotoToAlbumPhotoEntity(AlbumPhoto albumPhoto, AlbumPhotoEntity albumPhotoEntity) {
		if(albumPhoto == null) {
			return;
		}

		//--- Generic mapping 
		map(albumPhoto, albumPhotoEntity);

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