package org.backpacker4.test;

import org.backpacker4.bean.Position;

public class PositionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Position newPosition() {

		Long id = mockValues.nextLong();

		Position position = new Position();
		position.setId(id);
		return position;
	}
	
}
