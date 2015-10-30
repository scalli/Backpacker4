package org.backpacker4.test;

import org.backpacker4.bean.Feedback;

public class FeedbackFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Feedback newFeedback() {

		Long id = mockValues.nextLong();

		Feedback feedback = new Feedback();
		feedback.setId(id);
		return feedback;
	}
	
}
