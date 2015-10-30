package org.backpacker4.test;

import org.backpacker4.bean.jpa.PhotoEntity;

public class PhotoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PhotoEntity newPhotoEntity() {

		Long id = mockValues.nextLong();

		PhotoEntity photoEntity = new PhotoEntity();
		photoEntity.setId(id);
		return photoEntity;
	}
	
}
