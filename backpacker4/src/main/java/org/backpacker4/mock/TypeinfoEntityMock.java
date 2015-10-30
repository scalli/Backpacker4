
/*
 * Created on 28 okt 2015 ( Time 11:01:06 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.mock;

import java.util.LinkedList;
import java.util.List;

import org.backpacker4.bean.jpa.TypeinfoEntity;
import org.backpacker4.mock.tool.MockValues;

public class TypeinfoEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public TypeinfoEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextLong() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public TypeinfoEntity createInstance( Long id ) {
		TypeinfoEntity entity = new TypeinfoEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setDescription( mockValues.nextString(100) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfFeedback( TODO ) ; // List<Feedback> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<TypeinfoEntity> createList(int count) {
		List<TypeinfoEntity> list = new LinkedList<TypeinfoEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
