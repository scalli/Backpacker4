package org.backpacker4.test;

import org.backpacker4.bean.AlbumPhoto;

public class AlbumPhotoFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public AlbumPhoto newAlbumPhoto() {

		Long idAlbum = mockValues.nextLong();
		Long idPhoto = mockValues.nextLong();

		AlbumPhoto albumPhoto = new AlbumPhoto();
		albumPhoto.setIdAlbum(idAlbum);
		albumPhoto.setIdPhoto(idPhoto);
		return albumPhoto;
	}
	
}
