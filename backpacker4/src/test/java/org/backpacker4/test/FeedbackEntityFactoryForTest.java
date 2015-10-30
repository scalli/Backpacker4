package org.backpacker4.test;

import org.backpacker4.bean.jpa.FeedbackEntity;

public class FeedbackEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public FeedbackEntity newFeedbackEntity() {

		Long id = mockValues.nextLong();

		FeedbackEntity feedbackEntity = new FeedbackEntity();
		feedbackEntity.setId(id);
		return feedbackEntity;
	}
	
}
