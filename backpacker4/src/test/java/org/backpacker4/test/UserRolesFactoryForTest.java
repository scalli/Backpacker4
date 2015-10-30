package org.backpacker4.test;

import org.backpacker4.bean.UserRoles;

public class UserRolesFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UserRoles newUserRoles() {

		Integer iduserRoles = mockValues.nextInteger();

		UserRoles userRoles = new UserRoles();
		userRoles.setIduserRoles(iduserRoles);
		return userRoles;
	}
	
}
