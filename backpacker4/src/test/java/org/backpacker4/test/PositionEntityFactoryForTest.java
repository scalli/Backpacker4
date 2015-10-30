package org.backpacker4.test;

import org.backpacker4.bean.jpa.PositionEntity;

public class PositionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PositionEntity newPositionEntity() {

		Long id = mockValues.nextLong();

		PositionEntity positionEntity = new PositionEntity();
		positionEntity.setId(id);
		return positionEntity;
	}
	
}
