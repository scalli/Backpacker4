package org.backpacker4.test;

import org.backpacker4.bean.jpa.UserRolesEntity;

public class UserRolesEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UserRolesEntity newUserRolesEntity() {

		Integer iduserRoles = mockValues.nextInteger();

		UserRolesEntity userRolesEntity = new UserRolesEntity();
		userRolesEntity.setIduserRoles(iduserRoles);
		return userRolesEntity;
	}
	
}
