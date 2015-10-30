package org.backpacker4.test;

import org.backpacker4.bean.Photo;

public class PhotoFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Photo newPhoto() {

		Long id = mockValues.nextLong();

		Photo photo = new Photo();
		photo.setId(id);
		return photo;
	}
	
}
