package org.backpacker4.test;

import org.backpacker4.bean.jpa.AlbumPhotoEntity;

public class AlbumPhotoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public AlbumPhotoEntity newAlbumPhotoEntity() {

		Long idAlbum = mockValues.nextLong();
		Long idPhoto = mockValues.nextLong();

		AlbumPhotoEntity albumPhotoEntity = new AlbumPhotoEntity();
		albumPhotoEntity.setIdAlbum(idAlbum);
		albumPhotoEntity.setIdPhoto(idPhoto);
		return albumPhotoEntity;
	}
	
}
