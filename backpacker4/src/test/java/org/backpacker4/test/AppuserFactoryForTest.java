package org.backpacker4.test;

import org.backpacker4.bean.Appuser;

public class AppuserFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Appuser newAppuser() {

		Long id = mockValues.nextLong();

		Appuser appuser = new Appuser();
		appuser.setId(id);
		return appuser;
	}
	
}
