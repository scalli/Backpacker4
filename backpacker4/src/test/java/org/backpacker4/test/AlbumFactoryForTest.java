package org.backpacker4.test;

import org.backpacker4.bean.Album;

public class AlbumFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Album newAlbum() {

		Long id = mockValues.nextLong();

		Album album = new Album();
		album.setId(id);
		return album;
	}
	
}
