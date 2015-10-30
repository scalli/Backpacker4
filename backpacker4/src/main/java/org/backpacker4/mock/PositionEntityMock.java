
/*
 * Created on 28 okt 2015 ( Time 11:01:06 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.mock;

import java.util.LinkedList;
import java.util.List;

import org.backpacker4.bean.jpa.PositionEntity;
import org.backpacker4.mock.tool.MockValues;

public class PositionEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public PositionEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextLong() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public PositionEntity createInstance( Long id ) {
		PositionEntity entity = new PositionEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setLatitude( mockValues.nextBigDecimal() ) ; // java.math.BigDecimal 
		entity.setLongitude( mockValues.nextBigDecimal() ) ; // java.math.BigDecimal 
		// Init Link fields (if any)
		// setListOfPhoto( TODO ) ; // List<Photo> 
		// setListOfFeedback( TODO ) ; // List<Feedback> 
		// setListOfAppuser( TODO ) ; // List<Appuser> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<PositionEntity> createList(int count) {
		List<PositionEntity> list = new LinkedList<PositionEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
