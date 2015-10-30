package org.backpacker4.test;

import org.backpacker4.bean.jpa.AppuserEntity;

public class AppuserEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public AppuserEntity newAppuserEntity() {

		Long id = mockValues.nextLong();

		AppuserEntity appuserEntity = new AppuserEntity();
		appuserEntity.setId(id);
		return appuserEntity;
	}
	
}
