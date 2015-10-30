package org.backpacker4.test;

import org.backpacker4.bean.jpa.TypeinfoEntity;

public class TypeinfoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TypeinfoEntity newTypeinfoEntity() {

		Long id = mockValues.nextLong();

		TypeinfoEntity typeinfoEntity = new TypeinfoEntity();
		typeinfoEntity.setId(id);
		return typeinfoEntity;
	}
	
}
