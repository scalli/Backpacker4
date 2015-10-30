package org.backpacker4.test;

import org.backpacker4.bean.jpa.AlbumEntity;

public class AlbumEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public AlbumEntity newAlbumEntity() {

		Long id = mockValues.nextLong();

		AlbumEntity albumEntity = new AlbumEntity();
		albumEntity.setId(id);
		return albumEntity;
	}
	
}
