
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Album;
import org.backpacker4.bean.jpa.AlbumEntity;
import org.backpacker4.bean.jpa.AppuserEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AlbumServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public AlbumServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'AlbumEntity' to 'Album'
	 * @param albumEntity
	 */
	public Album mapAlbumEntityToAlbum(AlbumEntity albumEntity) {
		if(albumEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Album album = map(albumEntity, Album.class);

		//--- Link mapping ( link to Appuser )
		if(albumEntity.getAppuser() != null) {
			album.setIdUser(albumEntity.getAppuser().getId());
		}
		return album;
	}
	
	/**
	 * Mapping from 'Album' to 'AlbumEntity'
	 * @param album
	 * @param albumEntity
	 */
	public void mapAlbumToAlbumEntity(Album album, AlbumEntity albumEntity) {
		if(album == null) {
			return;
		}

		//--- Generic mapping 
		map(album, albumEntity);

		//--- Link mapping ( link : album )
		if( hasLinkToAppuser(album) ) {
			AppuserEntity appuser1 = new AppuserEntity();
			appuser1.setId( album.getIdUser() );
			albumEntity.setAppuser( appuser1 );
		} else {
			albumEntity.setAppuser( null );
		}

	}
	
	/**
	 * Verify that Appuser id is valid.
	 * @param Appuser Appuser
	 * @return boolean
	 */
	private boolean hasLinkToAppuser(Album album) {
		if(album.getIdUser() != null) {
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