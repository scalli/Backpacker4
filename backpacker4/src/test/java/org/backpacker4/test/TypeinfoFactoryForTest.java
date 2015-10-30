package org.backpacker4.test;

import org.backpacker4.bean.Typeinfo;

public class TypeinfoFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Typeinfo newTypeinfo() {

		Long id = mockValues.nextLong();

		Typeinfo typeinfo = new Typeinfo();
		typeinfo.setId(id);
		return typeinfo;
	}
	
}
